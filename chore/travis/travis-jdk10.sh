#!/bin/bash

# This script intends to be run on TravisCI
set -e

wget https://raw.githubusercontent.com/sormuras/bach/master/install-jdk.sh
chmod +x install-jdk.sh

# Use the default (the one present in the base container) collection of trusted certificate authority (CA) certificates for java
source ./install-jdk.sh -f 10 -c

./chore/travis/travis-test.sh