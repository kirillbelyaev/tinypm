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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirill
 */
public class ControllerTransactionManager_implement implements ControllerTransactionManager
{
    private PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
    
    /* by nature coordination is symmetric - both parties have to exchange control tuples.
    this method performs this in one direction - reading a control tuple from source TS
    and appending it to destination TS of destination ID.
    ts_location parameter denotes the source TS for source ID */
    private int facilitate_UnidirectionalPersistentCoordinativeTransaction(String ts_location)
    {
        ControlTuple_implement clt = null;
        
        if (ts_location == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (ts_location.isEmpty()) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (this.PTS != null)
        {
            /* start facilitation with reading a control tuple from TS 1 in persistent storage */
            if (this.PTS.count_ControlTuples(ts_location) == 1)
            {
                /* read control tuple from TS 1 */
                clt = this.PTS.read_ControlTuple(ts_location);

                /* if reading control tuple from TS 1 is successful */
                if (clt != null)
                {
                    
                    System.out.println("DEBUG: ControllerTransactionManager_implement:: facilitate_PersistentCoordinativeTransaction(). read_ControlTuple from TS1 successful. \n");
                    System.out.println("DEBUG: ControllerTransactionManager_implement:: facilitate_PersistentCoordinativeTransaction(). sourceID is: " + clt.get_SourceID_Field());
                    System.out.println("DEBUG: ControllerTransactionManager_implement:: facilitate_PersistentCoordinativeTransaction(). destinationID is: " + clt.get_DestinationID_Field());
                    
                    /* check if:
                       1: type field is coordination
                       2: if Source/Destination IDs are valid - registered in 
                          Communication Policy Class (CPC) layer and coordination
                          between them is enabled 
                    */
                   if (clt.match_on_Type_Field(Tuple.TupleTypes.COORDINATION.toString()) == true 
                   && this.validate_Coordination(clt.get_SourceID_Field(), clt.get_DestinationID_Field()) == true)
                   {
                       
                       System.out.println("DEBUG: ControllerTransactionManager_implement:: facilitate_PersistentCoordinativeTransaction(). validation of ControlTuple from TS1 successful. \n");                       
                       System.out.println("DEBUG: ControllerTransactionManager_implement:: facilitate_PersistentCoordinativeTransaction() :: get_TupleSpaceLocation(): return value is: " + this.get_TupleSpaceLocation(clt.get_DestinationID_Field()) );
                           
                       /* Now check if we can append a control tuple in destination TS 2 -
                       - if TS is empty and does not have a control tuple present
                       TS location should be obtained through translation function */
                       if (this.PTS.count_ControlTuples(this.get_TupleSpaceLocation(clt.get_DestinationID_Field())) == 0)
                       {
                           
                           System.out.println("DEBUG: ControllerTransactionManager_implement:: facilitate_PersistentCoordinativeTransaction() :: count_ControlTuples():  destination TS is empty. \n");
                           
                           /* append a control tuple to TS 2 */
                            if (this.PTS.append_ControlTuple(clt, this.get_TupleSpaceLocation(clt.get_DestinationID_Field())) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
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
    
    /* by nature coordination is symmetric - both parties have to exchange control tuples.
    this method performs this in both directions using facilitate_UnidirectionalPersistentCoordinativeTransaction()
    method for every source TS */
    @Override
    public int facilitate_BidirectionalPersistentCoordinativeTransaction(String ts_location1, String ts_location2)
    {
        if (ts_location1 == null || ts_location2 == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (ts_location1.isEmpty() || ts_location2.isEmpty()) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        /* make sure tuple spaces are different */
        if (ts_location1.compareTo(ts_location2) == 0) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        /* SLEEP */
        try 
        {
            Thread.sleep(TransactionManager.CONTROLLER_SLEEP_INTERVAL); /* 5 seconds */
        } catch (InterruptedException ex) {
            Logger.getLogger(ControllerTransactionManager_implement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* return success even if only one side succeeds */
        if (this.facilitate_UnidirectionalPersistentCoordinativeTransaction(ts_location1) == TransactionManager.INDICATE_OPERATION_SUCCESS
        || this.facilitate_UnidirectionalPersistentCoordinativeTransaction(ts_location2) == TransactionManager.INDICATE_OPERATION_SUCCESS)
        {
            return TransactionManager.INDICATE_OPERATION_SUCCESS;
        }
        
        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    /* check the validity of source/destination IDs to coordinate using
    the TBD CPC layer - check if such an access control policy exists */
    private boolean validate_Coordination(String sid, String did)
    {
        String id1 = "/s/chopin/b/grad/kirill/containers/container-1/bin/applicationA";
        String id2 = "/s/chopin/b/grad/kirill/containers/container-2/bin/applicationB";
        
        if (sid != null && did != null)
        {
            if (!sid.isEmpty() && !did.isEmpty())
            {
                /* return true for now to mock the CPC functionality */
                if ((sid.compareTo(id1) == 0 || sid.compareTo(id2) == 0) && (did.compareTo(id1) == 0 || did.compareTo(id2) == 0))
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
        String id1 = "/s/chopin/b/grad/kirill/containers/container-1/bin/applicationA";
        String id2 = "/s/chopin/b/grad/kirill/containers/container-2/bin/applicationB";
        
        if (id != null)
        {
            if (!id.isEmpty())
            {
                /* return mock TS location for now to mock the CPC functionality */
                if (id.compareTo(id1) == 0)
                {
                    ts_location = "/s/chopin/b/grad/kirill/containers/container-1/";
                    return ts_location;
                }
                
                if (id.compareTo(id2) == 0)
                {
                    ts_location = "/s/chopin/b/grad/kirill/containers/container-2/";
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
