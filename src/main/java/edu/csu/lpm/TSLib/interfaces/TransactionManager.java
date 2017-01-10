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
public interface TransactionManager 
{
    /* we use macros to indicate the general method exit codes within the tuple
    implementation */
    public final int SHORT_SLEEP_INTERVAL = 100; /* 5 milliseconds */
    public final int AGENT_SLEEP_INTERVAL = 10000; /* should be longer */
    public final int CONTROLLER_SLEEP_INTERVAL = 5000; /* should be shorter to allow shuttling */
    public final int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public final int INDICATE_EXCEPTION_OCCURRENCE_STATUS = -2;
    public final int INDICATE_OPERATION_SUCCESS = 0;
    public final int INDICATE_NOT_COLLABORATION_TYPE_FIELD_STATUS = -11;
    public final int INDICATE_NOT_COORDINATION_TYPE_FIELD_STATUS = -12;
    public final int INDICATE_FIELDS_VALIDATION_FAILED_STATUS = -13;
    public final int INDICATE_CREATE_TUPLE_SPACE_FAILED_STATUS = -14;
    public final int INDICATE_APPEND_CONTROL_TUPLE_FAILED_STATUS = -15;
    public final int INDICATE_TAKE_CONTROL_TUPLE_FAILED_STATUS = -16;
    public final int INDICATE_CONTROL_TUPLE_NOT_PRESENT_STATUS = -17;
    public final int INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS = -18;
    public final int INDICATE_READ_CONTROL_TUPLE_FAILED_STATUS = -19;
    
    public final int INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS = -20;
    public final int INDICATE_TAKE_CONTENT_TUPLE_FAILED_STATUS = -21;
    public final int INDICATE_CONTENT_TUPLE_NOT_PRESENT_STATUS = -22;
    
    public final int INDICATE_REPLICA_FRAGMENTATION_FAILED_STATUS = 100;
    public final int INDICATE_REPLICA_ASSEMBLY_FAILED_STATUS = 101;
    
}
