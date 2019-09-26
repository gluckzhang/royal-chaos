#!/bin/bash
set -e

sudo apt-get -y install openjdk-8-jdk
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64/

./chore/travis/travis-test.sh