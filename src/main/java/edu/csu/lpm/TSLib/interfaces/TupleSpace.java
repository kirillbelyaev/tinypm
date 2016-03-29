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

import edu.csu.lpm.TSLib.implementation.ContentTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;

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
        final String TupleSpaceName = "ts";
        
        /* we use macros to indicate the general method exit codes within the 
        tuple space implementation */
        public final int INDICATE_CONTROL_TUPLE_EXISTS_STATUS = -1;
        public final int INDICATE_CONTENT_TUPLE_EXISTS_STATUS = -2;
        public final int INDICATE_OPERATION_SUCCESS = 0;
        public final int INDICATE_TUPLE_SPACE_EXISTS_STATUS = -3;
        public final int INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS = -4;
        public final int INDICATE_TUPLE_SPACE_NOT_EMPTY_STATUS = -5;
        public final int INDICATE_TUPLE_IS_NULL_STATUS = -6;
        
        
        public String get_TupleSpaceName();
        
        /*
        Technically should not be a part of an interface
        but rather be in interface implementation
        
        Object TS [] = null;
        ArrayList <Object> TSL = null;
        */
        
        public int create_TupleSpace();
        
        //public Object [] create_TupleSpace(String name);
        
        
        public int delete_TupleSpace();
        
        //public int delete_TupleSpace(String name);
        
        
        /* some rudimentary informative support */
        public int countTuples();
        
        public int countControlTuples();
        
        public int countContentTuples();
        
        
        /* read operation reads a tuple but does not remove it from TS */
        public ControlTuple_implement read_ControlTuple();
        
        public ContentTuple_implement read_ContentTuple();
        
        /* append operation adds a tuple without affecting existing tuples in a tuple space */
        public int append_ControlTuple(ControlTuple_implement ct);
        
        public int append_ContentTuple(ContentTuple_implement ct);
        
        
        /* take operation returns a tuple while removing it from a tuple space */
        public ControlTuple_implement take_ControlTuple();
        
        public ContentTuple_implement take_ContentTuple();
    
}
