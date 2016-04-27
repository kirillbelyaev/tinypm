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
public interface ControllerTransactionManager extends TransactionManager
{   
    /* by nature coordination is symmetric - both parties have to exchange control tuples
    this method performs this in one direction - appending control tuple to TS 
    of destination ID.
    ts_location parameter denotes the TS for source ID */
    //public int facilitate_UnidirectionalPersistentCoordinativeTransaction(String location);
    
    public int facilitate_BidirectionalPersistentCoordinativeTransaction(String ts_location1, String ts_location2);
    
    public int facilitate_PersistentCollaborativeTransaction(String location);
    
    //File assembleReplica(ContentTuple_implement cnt);
    
}
