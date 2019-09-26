#!/bin/bash
set -e

export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64/

wget archive.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip
unzip -qq apache-maven-3.3.9-bin.zip
export M2_HOME=$PWD/apache-maven-3.3.9

./chore/travis/travis-test.sh