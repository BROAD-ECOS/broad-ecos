#!/bin/bash
# your repository folder
cd  /opt/lopl

# fetch changes, git stores them in FETCH_HEAD
git fetch
 
# check for remote changes in origin repository
newUpdatesAvailable=`git diff HEAD FETCH_HEAD`
if [ "$newUpdatesAvailable" != "" ]
then
        # create the fallback
        git branch fallbacks
        git checkout fallbacks
 
        git add .
        git add -u
        git commit -m `date "+%Y-%m-%d"`
        echo "fallback created"
    
        git checkout master
        git merge FETCH_HEAD
        echo "merged updates"
	   
	
	/opt/maven/bin/mvn clean package
	
	killall java
	cp target/lopl.jar   ../lopl.jar
	nohup java -jar lopl.jar &
else
        echo "no updates available"
fi
