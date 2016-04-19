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
public interface VolatileTupleSpace extends TupleSpace
{       
        public String get_TupleSpaceName();
        
        /*
        Technically should not be a part of an interface
        but rather be in interface implementation
        
        Object TS [] = null;
        ArrayList <Object> TSL = null;
        */
        
        public int create_TupleSpace();
        
        public int delete_TupleSpace();
        
        /* some rudimentary informative support */
        public int count_Tuples();
        
        public int count_ControlTuples();
        
        public int count_ContentTuples();
        
        
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
