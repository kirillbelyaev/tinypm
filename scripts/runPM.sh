#!/bin/sh

BASE="/tinyPM-v2/tinyPM/"

#check whether the scripts directory is accessible
cd $HOME/$BASE/scripts||
{
   echo "Cannot change to scripts directory! Exiting." >&2
   exit $E_XCD;
}

./tinypm-shell.sh $HOME/.m2/repository/org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar
