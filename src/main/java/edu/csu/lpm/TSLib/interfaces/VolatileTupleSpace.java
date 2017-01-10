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
