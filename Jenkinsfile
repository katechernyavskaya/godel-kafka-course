@Library('10x-SharedLib@helm/v2.8') _

def primaryBranch = 'master'
if (env.BRANCH_NAME.endsWith("--deploy")) {
    primaryBranch = env.BRANCH_NAME
}

runPipeline(
  repositoryName: 'godel-kafka-course',
  serviceName: 'godelkafkacourse',
  namespace: 'godel-kafka-course',
  alias: 'godel-kafka-course',
  primaryBranch: primaryBranch,
  targetBranch: primaryBranch,
  enable_cd: true,
  deployThis: false,
  run_terraform: true,
  archiveJunitReport: true,
  prBranchDeploy: false
)
