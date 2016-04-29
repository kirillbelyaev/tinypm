/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
 * Colorado State University
 * Department of Computer Science,
 * Fort Collins, CO  80523-1873, USA
 *
 * E-mail contact:
 * kirillbelyaev@yahoo.com
 * kirill@cs.colostate.edu
 *   
 * This work is licensed under the Creative Commons Attribution-NonCommercial 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc/3.0/ or send 
 * a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
*/

package edu.csu.lpm.TSLib.interfaces;

/**
 *
 * @author kirill
 */
public interface TupleSpace 
{
        /* fixed name precludes the creation of the directory with the same
        name by another app in the same IRE. Well - at least theoretically
        it will not allow to recreate the directory .... 
        Probably the main motivation behind this design is the fact that only
        a single tuple space is allowed within an IRE (Isolated Runtime
        Environment) because only a single application is deployed in it...
        Therefore it is easier for LPM to just check a single TS directory 
        location using uniform naming convention instead of assuming TS name 
        that is given by the internal application logic. 
        Sort of similar to UNIX domain sockets convention. */
        final String TupleSpaceName = "/ts";
        final String ControlTupleName = "/control-tuple";
        final String ContentTupleName = "/content-tuple";
        
        /* we use macros to indicate the general method exit codes within the 
        tuple space implementation */
        public final int INDICATE_OPERATION_SUCCESS = 0;
        public final int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
        public final int INDICATE_EXCEPTION_OCCURRENCE_STATUS = -2;
        public final int INDICATE_TUPLE_SPACE_EXISTS_STATUS = -3;
        public final int INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS = -4;
        public final int INDICATE_TUPLE_SPACE_NOT_EMPTY_STATUS = -5;
        public final int INDICATE_TUPLE_IS_NULL_STATUS = -6;
        public final int INDICATE_CONTROL_TUPLE_EXISTS_STATUS = -7;
        public final int INDICATE_CONTENT_TUPLE_EXISTS_STATUS = -8;
        
}
