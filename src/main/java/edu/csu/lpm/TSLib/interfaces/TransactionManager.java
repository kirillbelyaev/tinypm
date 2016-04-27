/*
 * Copyright (C) 2016 kirill.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
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
    public final int AGENT_SLEEP_INTERVAL = 10000; /* should be longer */
    public final int CONTROLLER_SLEEP_INTERVAL = 5000; /* should be shorter to allow shuttling */
    public final int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public final int INDICATE_EXCEPTION_OCCURRENCE_STATUS = -2;
    public final int INDICATE_OPERATION_SUCCESS = 0;
    public final int INDICATE_NOT_COORDINATION_TYPE_FIELD_STATUS = -12;
    public final int INDICATE_FIELDS_VALIDATION_FAILED_STATUS = -13;
    public final int INDICATE_CREATE_TUPLE_SPACE_FAILED_STATUS = -14;
    public final int INDICATE_APPEND_CONTROL_TUPLE_FAILED_STATUS = -15;
    public final int INDICATE_TAKE_CONTROL_TUPLE_FAILED_STATUS = -16;
    public final int INDICATE_CONTROL_TUPLE_NOT_PRESENT_STATUS = -17;
    public final int INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS = -18;
    public final int INDICATE_READ_CONTROL_TUPLE_FAILED_STATUS = -19;
    
    
}
