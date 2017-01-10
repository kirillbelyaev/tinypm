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
public interface ControllerTransactionManager extends TransactionManager
{   
    /* by nature coordination is symmetric - both parties have to exchange control tuples
    this method performs this in both directions using facilitate_UnidirectionalPersistentCoordinativeTransaction()
    method for every source TS */
    public int facilitate_BidirectionalPersistentCoordinativeTransaction(String ts_location1, String ts_location2);
    
    /* by nature collaboration is unidirectional since one party initiates
    a request for a data object mediated through controller */
    public int facilitate_PersistentCollaborativeTransaction(String ts_location);
    
}
