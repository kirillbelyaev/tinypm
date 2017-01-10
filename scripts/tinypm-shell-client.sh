#!/bin/bash

#/*
#tinyPM Prototype
#Kirill Belyaev. Copyright (c) @2015 Colorado State University 
#Department of Computer Science, Fort Collins, CO  80523-1873, USA
#*/

ERROR=-1
BASE="/tinyPM-v2/tinyPM/"
INSTALL_BASE="/tinyPM-v2/tinyPM/target/classes/"
DB_NAME="tinypm.db"

#echo $HOME$INSTALL_BASE$DB_NAME

function copy_db ()
{
if [ -e $HOME/$BASE/$DB_NAME ]
then
        echo "Base db file found!"
        echo "making a copy to target."
	cp -n $HOME/$BASE/$DB_NAME $HOME/$INSTALL_BASE
else
	echo "no base db file found!"
	exit $ERROR
fi
}

#check whether the tinypm db exists - it obviously should by now...
# If not then copy the tinypm database to the target folder for the first run
if [ -e $HOME/$INSTALL_BASE/$DB_NAME ]
then
        echo "db file found!"
else
	echo "no db file found!"
	copy_db
fi


# Please copy the tinypm database to the target folder for the first run
#cp ../tinypm.db ../target/classes/


ARGS=2

if [ $# -ne $ARGS ]  # Correct number of arguments passed to script?
then
  echo "2 argument must be supplied:"
  echo "argument 1: absolute path to the sqlite driver and argument 2: server address"
  exit $E_BADARGS
fi

#In our case (to save disk space) the driver is located in a separate maven .m2 directory but in reality all dependencies should be supplied in the distribution 
#SQLITE_DRV=/s/chopin/b/grad/kirill/.m2/repository/org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar
SQLITE_DRV=$1
SERVER_ADDRESS=$2
JAVA=`/bin/which java`

cd $HOME/$INSTALL_BASE
export CLASSPATH=$SQLITE_DRV:$CLASSPATH
$JAVA edu/csu/tinypm/machine/ClientMain $SERVER_ADDRESS

