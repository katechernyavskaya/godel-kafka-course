#!/bin/bash

set -ef -o pipefail

# max_memory function taken from https://github.com/fabric8io-images/java/blob/master/images/jboss/openjdk8/jdk/run-java.sh#L162-L175
max_memory() {
  # High number which is the max limit until which memory is supposed to be
  # unbounded.
  local mem_file="/sys/fs/cgroup/memory/memory.limit_in_bytes"
  if [ -r "${mem_file}" ]; then
    local max_mem_cgroup="$(cat ${mem_file})"
    local max_mem_meminfo_kb="$(cat /proc/meminfo | awk '/MemTotal/ {print $2}')"
    local max_mem_meminfo="$(expr $max_mem_meminfo_kb \* 1024)"
    if [ ${max_mem_cgroup:-0} != -1 ] && [ ${max_mem_cgroup:-0} -lt ${max_mem_meminfo:-0} ]
    then
      echo "${max_mem_cgroup}"
    fi
  fi
}

JAR_LOCATION=/usr/local/bin/service.jar

percentage_of_memory_to_use_for_heap=33

if [ -n "${HEAP_SIZE_PERCENTAGE}" ]; then
  percentage_of_memory_to_use_for_heap="${HEAP_SIZE_PERCENTAGE}"
fi

container_max_memory=$(max_memory)

heap_size_bytes=$((percentage_of_memory_to_use_for_heap*container_max_memory / 100))
heap_size_mb=$((heap_size_bytes / 1048576))

JVM_FLAGS="-Xms${heap_size_mb}m -Xmx${heap_size_mb}m"

if [ -n "${ADDITIONAL_JVM_ARGS}" ]; then
  JVM_FLAGS="${JVM_FLAGS} ${ADDITIONAL_JVM_ARGS}"
fi

echo "JVM_FLAGS:  ${JVM_FLAGS}"

echo "Running application"
START_CMD="exec java -Dhttp.proxyHost=${http_proxy%:*} -Dhttp.proxyPort=${http_proxy#*:} $JVM_FLAGS -jar $JAR_LOCATION"

$START_CMD
