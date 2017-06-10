#!/bin/sh

#/*
# * Lightweight Policy Machine for Linux (LPM) Reference Monitor Prototype
# *   
# * Copyright (C) 2015-2017 Kirill A Belyaev
# * Colorado State University
# * Department of Computer Science,
# * Fort Collins, CO  80523-1873, USA
# *
# * E-mail contact:
# * kirillbelyaev@yahoo.com
# * kirill@cs.colostate.edu
# *   
# * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
# * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
# * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
#*/

BASE="/tinyPM-v2/tinyPM/"

#check whether the scripts directory is accessible
cd $HOME/$BASE/scripts||
{
   echo "Cannot change to scripts directory! Exiting." >&2
   exit $E_XCD;
}

./lpm-shell.sh $HOME/.m2/repository/org/xerial/sqlite-jdbc/3.7.2/sqlite-jdbc-3.7.2.jar
