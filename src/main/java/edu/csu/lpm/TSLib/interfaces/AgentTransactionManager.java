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

import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;

/**
 *
 * @author kirill
 */
public interface AgentTransactionManager extends TransactionManager
{   
    /* Active transaction - appends a control tuple and then takes it - performs send functionality.
    the method also validates the control tuple parameter */
    public int perform_ActivePersistentCoordinativeTransaction(ControlTuple_implement clt, String ts_location);
    
    /* Passive transaction - takes a control tuple - performs receive functionality.
    for this method we do not really need the control tuple parameter since an app 
    may receive a control tuple from any recipient and should perform validation
    outside the method based on proprietary logic.
    Return value of ZERO signifies success and that the private REPLY_CLT class variable
    is set to non null and ready to be obtained via get_ReplyControlTuple() */
    public int perform_PassivePersistentCoordinativeTransaction(String ts_location); 
    
    /* in case perform_PassivePersistentCoordinativeTransaction() returns ZERO 
    we can obtain the private REPLY_CLT class variable */
    public ControlTuple_implement get_ReplyControlTuple();
    
    /* by nature collaboration is unidirectional since one party initiates 
    a request for a data object mediated through controller and waits for replica
    chunks in the form of content tuples
    
    clt - control tuple with collaboration request to append.
    ts_location - base directory where TS of the requester is located.
    object_path - absolute path to the location where replica of the requested 
    data object indicated in the control tuple request field should be assembled. */
    public int perform_PersistentCollaborativeTransaction(ControlTuple_implement clt, String ts_location, String object_path);
    
}
