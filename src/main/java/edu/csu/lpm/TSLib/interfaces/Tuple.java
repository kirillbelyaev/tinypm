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
public interface Tuple 
{
    /* we use macros to indicate the general method exit codes within the tuple
    implementation */
    public final int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public final int INDICATE_OPERATION_SUCCESS = 0;
    
  
    public enum TupleTypes /* we deal with two types of tuples */
    {
        COORDINATION,
        COLLABORATION
    }
    
    public enum TupleTypesNames /* we deal with two types of tuples */
    {
        COORDINATION_TUPLE, /* provides instructions */
        COLLABORATION_TUPLE /* data sharing mechanism */
    }
    
    
}
