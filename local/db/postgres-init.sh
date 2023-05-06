###
### Uncomment the script below to run CustomSQL against the local PSQL DB
### The $POSTGRES_USER and $POSTGRES_DB will already exist without needing this custom script

##!/bin/bash
#
## Setups entitlement manager database and required roles for PostgresDB
#
#set -e
#export POSTGRES_HOST_AUTH_METHOD=trust
#psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
#  SQL GOES HERE
#EOSQL
