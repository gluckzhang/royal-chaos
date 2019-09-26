#!/bin/bash

# fails if anything fails
set -e

# a list of java projects to be tested
projects="chaosmachine tripleagent"

for project in $projects
do
	echo "Testing project $project"
	cd $project
	mvn -q test
	cd ..
done