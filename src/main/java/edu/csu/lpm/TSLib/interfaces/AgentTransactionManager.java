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

import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;

/**
 *
 * @author kirill
 */
public interface AgentTransactionManager extends TransactionManager
{   
    /* by nature coordination is symmetric - both parties have to exchange control tuples */
    public int perform_PersistentCoordinativeTransaction(ControlTuple_implement clt, String location);
    
    public ControlTuple_implement get_ReplyControlTuple();
    
    //int stop_Coordination();
    
    int perform_PersistentCollaborativeTransaction(ControlTuple_implement clt);
    
    //int stop_Collaboration();
    
    //File assembleReplica(ContentTuple_implement cnt);
    
}
