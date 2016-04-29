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
public interface PersistentTupleSpace extends TupleSpace
{
    public int count_Tuples(String location);
    
    public int count_ControlTuples(String location);
    
    public int count_ContentTuples(String location);
    
    public int create_TupleSpace(String location);
    
    public int delete_TupleSpace(String location);
    
    public int append_ContentTuple(ContentTuple_implement ct, String location);
    
    public int append_ControlTuple(ControlTuple_implement ct, String location);
    
    public ContentTuple_implement read_ContentTuple(String location);
    
    public ControlTuple_implement read_ControlTuple(String location);
    
    public ControlTuple_implement take_ControlTuple(String location);
    
    public ContentTuple_implement take_ContentTuple(String location);
    
}
