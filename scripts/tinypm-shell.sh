#!/bin/bash

#/*
#tinyPM Prototype
#Kirill Belyaev. Copyright (c) @2015 Colorado State University 
#Department of Computer Science, Fort Collins, CO  80523-1873, USA
#*/

# Please copy the lc database to the target folder
#cp ../lc.db ../target/classes/
#

ARGS=1

if [ $# -ne $ARGS ]  # Correct number of arguments passed to script?
then
  echo "1 argument must be supplied:"
  echo "argument 1: absolute path to the sqlite driver "
  exit $E_BADARGS
fi

#In my case (to save disk space) the driver is located in a separate maven .m2 directory but in reality all dependencies should be supplied in the distribution 
#SQLITE_DRV=/s/chopin/b/grad/kirill/.m2/repository/org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar
SQLITE_DRV=$1
JAVA=`/bin/which java`

cd ../target/classes/
export CLASSPATH=$SQLITE_DRV:$CLASSPATH
$JAVA edu/csu/tinypm/machine/Main