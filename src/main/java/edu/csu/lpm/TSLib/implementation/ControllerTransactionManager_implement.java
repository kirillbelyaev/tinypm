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
package edu.csu.lpm.TSLib.implementation;

import edu.csu.lpm.TSLib.interfaces.ControllerTransactionManager;
import edu.csu.lpm.TSLib.interfaces.TransactionManager;
import edu.csu.lpm.TSLib.interfaces.Tuple;

/**
 *
 * @author kirill
 */
public class ControllerTransactionManager_implement implements ControllerTransactionManager
{
    private PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
    
    /* by nature coordination is symmetric - both parties have to exchange control tuples.
    this method performs this in one direction - appending control tuple to TS of destination ID */
    @Override
    public int facilitate_PersistentCoordinativeTransaction(String location)
    {
        ControlTuple_implement clt = null;
        
        if (location == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (location.isEmpty()) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (this.PTS != null)
        {
            /* start facilitation with reading a control tuple from TS 1 in persistent storage */
            if (this.PTS.count_ControlTuples(location) == 1)
            {
                /* read control tuple from TS 1 */
                clt = this.PTS.read_ControlTuple(location);

                /* if reading control tuple from TS 1 is successful */
                if (clt != null)
                {
                   /* 1: type field is coordination
                      2: if sender ID is valid - registered in Communication Policy Class (CPC)
                         and coordination is enabled */
                   if (clt.match_on_Type_Field(Tuple.TupleTypes.COORDINATION.toString()) == true && this.validate_Coordination(clt.get_ID_Field(), clt.get_ID_Field()) == true)
                   {
                       /* SLEEP 1 - give a chance to TSC (TS Controller) to read and shuttle a control tuple */
//                    try 
//                    {    
//                        Thread.sleep(TransactionManager.DEFAULT_SLEEP_INTERVAL);   
//                    } catch (InterruptedException ex) 
//                    {
//                        Logger.getLogger(AgentTransactionManager_implement.class.getName()).log(Level.SEVERE, null, ex);
//                        
//                        /* delete tuple space on exit */
//                        if (this.PTS.delete_TupleSpace(location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
//                        {    
//                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
//                        }
//                            
//                        return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
//                    }
                       /* Now check if we can append a control tuple in destination TS 2.
                       TS location should be obtained through translation function */
                       if (this.PTS.count_ControlTuples(this.get_TupleSpaceLocation(clt.get_ID_Field())) == 0)
                       {
                           /* append a control tuple to TS 2 */
                            if (this.PTS.append_ControlTuple(clt, this.get_TupleSpaceLocation(clt.get_ID_Field())) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                            {
                               return TransactionManager.INDICATE_OPERATION_SUCCESS; 
                               
                            } else { return TransactionManager.INDICATE_APPEND_CONTROL_TUPLE_FAILED_STATUS; }
                            
                       } else { return TransactionManager.INDICATE_APPEND_CONTROL_TUPLE_FAILED_STATUS; } 
                       
                   } else { return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS; } 
                   
                } else { return TransactionManager.INDICATE_READ_CONTROL_TUPLE_FAILED_STATUS; }
                
            } else { return TransactionManager.INDICATE_CONTROL_TUPLE_NOT_PRESENT_STATUS; }
        }                
                
        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    /* check the validity of source/destination IDs to coordinate using
    the TBD CPC layer - check if such an access control policy exists */
    private boolean validate_Coordination(String sid, String did)
    {
        if (sid != null && did != null)
        {
            if (!sid.isEmpty() && !did.isEmpty())
            {
                /* return true for now to mock the CPC functionality */
                return true;
            }    
        }
        return false;
    }     
    
    /* obtain the TS location for corresponding app IDs using
    the TBD CPC layer */
    private String get_TupleSpaceLocation(String id)
    {
        String ts_location = null;
        /* mock IDs */
        String id1 = "/s/chopin/b/grad/kirill/containers/container-1/bin/coordinator-a";
        String id2 = "/s/chopin/b/grad/kirill/containers/container-2/bin/coordinator-b";
        
        if (id != null)
        {
            if (!id.isEmpty())
            {
                /* return mock TS location for now to mock the CPC functionality */
                if (id.compareTo(id1) == 0)
                {
                    ts_location = "/s/chopin/b/grad/kirill/containers/container-a/";
                    return ts_location;
                }
                
                if (id.compareTo(id2) == 0)
                {
                    ts_location = "/s/chopin/b/grad/kirill/containers/container-b/";
                    return ts_location;
                }
            }    
        }
        
        return ts_location;
    }

    @Override
    public int facilitate_PersistentCollaborativeTransaction(String location) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
