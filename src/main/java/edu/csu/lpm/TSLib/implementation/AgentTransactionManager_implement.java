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

package edu.csu.lpm.TSLib.implementation;

import edu.csu.lpm.TSLib.interfaces.AgentTransactionManager;
import edu.csu.lpm.TSLib.interfaces.TransactionManager;
import edu.csu.lpm.TSLib.interfaces.Tuple;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirill
 */

public class AgentTransactionManager_implement implements AgentTransactionManager  
{
    private PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
    private ControlTuple_implement REPLY_CLT = null;
    
    /* by nature coordination is symmetric - both parties have to exchange 
    control tuples. In case one party does not expect the informative coordination
    message back the other party has to acknowledge the receive by appending the 
    acknowledgement control tuple in return so that sending party obtains confirmation.
    NOTE - this transaction method has been split into 2 parts - passive & active - 
    therefore it is not used in practice and is given as a REFERENCE */
    private int perform_PersistentCoordinativeTransaction(ControlTuple_implement clt, String ts_location) 
    {
        ControlTuple_implement request_pct = null;  
        ControlTuple_implement reply_pct = null;
        
        if (clt == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (ts_location == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        /* check if both source/destination IDs are set */
        if (clt.get_SourceID_Field().isEmpty() && clt.get_DestinationID_Field().isEmpty()) return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS;
        
        /* check if type field is set to coordination */
        if (!clt.match_on_Type_Field(Tuple.TupleTypes.COORDINATION.toString())) return TransactionManager.INDICATE_NOT_COORDINATION_TYPE_FIELD_STATUS;
        
        if (this.PTS != null)
        {
            /* start transaction with creating a TS in persistent storage */
            if (this.PTS.create_TupleSpace(ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
            {
                /* append a valid control tuple */
                if (this.PTS.append_ControlTuple(clt, ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                {
                    /* SLEEP 1 - give a chance to TSC (TS Controller) to read and shuttle a control tuple */
                    try 
                    {    
                        Thread.sleep(TransactionManager.AGENT_SLEEP_INTERVAL);   
                    } catch (InterruptedException ex) 
                    {
                        Logger.getLogger(AgentTransactionManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                        
                        /* delete tuple space on exit */
                        if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {    
                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                        }
                            
                        return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                    }
                    
                    /* now take our control tuple */
                    request_pct = this.PTS.take_ControlTuple(ts_location);
                    
                    /* if take operation is successful */
                    if (request_pct != null)
                    {
                        /* SLEEP 2 - give a chance to TSC (TS Controller) to append
                        a control tuple from other party */
                        try 
                        {     
                            Thread.sleep(TransactionManager.CONTROLLER_SLEEP_INTERVAL);
                        } catch (InterruptedException ex) 
                        {
                            Logger.getLogger(AgentTransactionManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                            
                            /* delete tuple space on exit */
                            if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                            {    
                                return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                            }
                            
                            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                        }
                        
                        /* now take reply control tuple if TS has one such tuple */
                        if (this.PTS.count_ControlTuples(ts_location) == 1)
                        {
                            /* take reply control tuple */
                            reply_pct = this.PTS.take_ControlTuple(ts_location);
                            
                            /* if obtaining reply control tuple is successful */
                            if (reply_pct != null)
                            {
                                /* if source ID of original matches with destination ID of reply and type fields match */
                                if (clt.match_on_Type_Field(reply_pct.get_Type_Field()) == true 
                                && clt.match_on_SourceID_Field(reply_pct.get_DestinationID_Field()) == true 
                                && clt.match_on_DestinationID_Field(reply_pct.get_SourceID_Field()) == true)
                                {
                                    /* delete tuple space to complete transaction */
                                    if (this.PTS.delete_TupleSpace(ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                    {
                                        /* assign the reply to private variable */
                                        this.REPLY_CLT = reply_pct; 
                                        return TransactionManager.INDICATE_OPERATION_SUCCESS;
                                    }  else { return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS; } 
                                    
                                } else {
                                            /* delete tuple space on exit */
                                            if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                            {    
                                                return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                                            }
                                    
                                            return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS; 
                                       }    
                            
                            } else { 
                                        /* delete tuple space on exit */
                                        if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                        {    
                                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                                        }
                                
                                        return TransactionManager.INDICATE_TAKE_CONTROL_TUPLE_FAILED_STATUS; 
                                   }   
                        
                        } else { 
                                    /* delete tuple space on exit */
                                    if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                    {    
                                        return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                                    }
                            
                                    return TransactionManager.INDICATE_CONTROL_TUPLE_NOT_PRESENT_STATUS; 
                               }
                        
                    } else { 
                                /* delete tuple space on exit */
                                if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                {    
                                    return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                                }
                        
                                return TransactionManager.INDICATE_TAKE_CONTROL_TUPLE_FAILED_STATUS; 
                           }   
                    
                } else { 
                            /* delete tuple space on exit */
                            if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                            {    
                                return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                            }
                    
                            return TransactionManager.INDICATE_APPEND_CONTROL_TUPLE_FAILED_STATUS; 
                       }   
            
            } else {                
                        /* delete tuple space on exit */
                        if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {    
                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                        }
                        
                        return TransactionManager.INDICATE_CREATE_TUPLE_SPACE_FAILED_STATUS;                    
                   }             
        }    
        
        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    /* Active transaction - appends a control tuple and then takes it - performs send functionality.
    the method also validates the control tuple parameter */
    @Override
    public int perform_ActivePersistentCoordinativeTransaction(ControlTuple_implement clt, String ts_location) 
    {
        ControlTuple_implement request_pct = null;
        
        if (clt == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (ts_location == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (ts_location.isEmpty()) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        /* check if both source/destination IDs are set */
        if (clt.get_SourceID_Field().isEmpty() && clt.get_DestinationID_Field().isEmpty()) return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS;
        
        /* check if type field is set to coordination */
        if (!clt.match_on_Type_Field(Tuple.TupleTypes.COORDINATION.toString())) return TransactionManager.INDICATE_NOT_COORDINATION_TYPE_FIELD_STATUS;
        
        if (this.PTS != null)
        {
            /* start transaction with creating a TS in persistent storage */
            if (this.PTS.create_TupleSpace(ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
            {
                /* append a valid control tuple */
                if (this.PTS.append_ControlTuple(clt, ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                {
                    /* SLEEP 1 - give a chance to TSC (TS Controller) to read and shuttle a control tuple */
                    try 
                    {    
                        Thread.sleep(TransactionManager.AGENT_SLEEP_INTERVAL);   
                    } catch (InterruptedException ex) 
                    {
                        Logger.getLogger(AgentTransactionManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                        
                        /* delete tuple space on exit */
                        if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {    
                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                        }
                            
                        return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                    }
                    
                    /* now take our control tuple */
                    request_pct = this.PTS.take_ControlTuple(ts_location);
                    
                    /* if take operation is successful */
                    if (request_pct != null)
                    {
                        /* delete tuple space to complete transaction */
                        if (this.PTS.delete_TupleSpace(ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {
                            return TransactionManager.INDICATE_OPERATION_SUCCESS;
                        }  else { return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS; } 
                        
                    } else { /* if take operation failed */
                                /* delete tuple space on exit */
                                if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                {    
                                    return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                                }
                        
                                return TransactionManager.INDICATE_TAKE_CONTROL_TUPLE_FAILED_STATUS; 
                           }   
                    
                } else { /* if append operation failed */
                            /* delete tuple space on exit */
                            if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                            {    
                                return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                            }
                    
                            return TransactionManager.INDICATE_APPEND_CONTROL_TUPLE_FAILED_STATUS; 
                       }   
            
            } else { /* if create tuple space operation failed */               
                        /* delete tuple space on exit */
                        if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {    
                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                        }
                        
                        return TransactionManager.INDICATE_CREATE_TUPLE_SPACE_FAILED_STATUS;             
                   }             
        }   
        
        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    /* Passive transaction - takes a control tuple - performs receive functionality.
    for this method we do not really need the control tuple parameter since an app 
    may receive a control tuple from any recipient and should perform validation
    outside the method based on proprietary logic.
    Return value of ZERO signifies success and that the private REPLY_CLT class variable
    is set to non null and ready to be obtained via get_ReplyControlTuple() */
    @Override
    public int perform_PassivePersistentCoordinativeTransaction(String ts_location) 
    {  
        ControlTuple_implement reply_pct = null;
        
        if (ts_location == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (ts_location.isEmpty()) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (this.PTS != null)
        {
            /* start transaction with creating a TS in persistent storage */
            if (this.PTS.create_TupleSpace(ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
            {
                /* SLEEP 2 - give a chance to TSC (TS Controller) to append
                a control tuple from other party */
                try 
                {    
                    Thread.sleep(TransactionManager.AGENT_SLEEP_INTERVAL);
                } catch (InterruptedException ex) 
                {
                    Logger.getLogger(AgentTransactionManager_implement.class.getName()).log(Level.SEVERE, null, ex);

                    /* delete tuple space on exit */
                    if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                    {    
                        return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                    }

                    return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                }

                /* now take reply control tuple if TS has one such tuple */
                if (this.PTS.count_ControlTuples(ts_location) == 1)
                {
                    /* take reply control tuple */
                    reply_pct = this.PTS.take_ControlTuple(ts_location);

                    /* if obtaining reply control tuple is successful */
                    if (reply_pct != null)
                    {
                        /* delete tuple space to complete transaction */
                        if (this.PTS.delete_TupleSpace(ts_location) == PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {
                            /* assign the reply to private variable */
                            this.REPLY_CLT = reply_pct; 

                            return TransactionManager.INDICATE_OPERATION_SUCCESS;

                        }  else { return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS; } 

                    } else { /* if take operation failed */
                                /* delete tuple space on exit */
                                if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                                {    
                                    return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                                }

                                return TransactionManager.INDICATE_TAKE_CONTROL_TUPLE_FAILED_STATUS; 
                           }   
                    
                } else { /* if count control tuples operation does not return 1 */
                            /* delete tuple space on exit */
                            if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                            {    
                                return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                            }

                            return TransactionManager.INDICATE_CONTROL_TUPLE_NOT_PRESENT_STATUS; 
                       }            
            } else { /* if create tuple space operation failed */             
                        /* delete tuple space on exit */
                        if (this.PTS.delete_TupleSpace(ts_location) != PersistentTupleSpace_implement.INDICATE_OPERATION_SUCCESS)
                        {    
                            return TransactionManager.INDICATE_DELETE_TUPLE_SPACE_FAILED_STATUS;
                        }
                        
                        return TransactionManager.INDICATE_CREATE_TUPLE_SPACE_FAILED_STATUS;                
                   }    
        }    
        
        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    @Override
    public int perform_PersistentCollaborativeTransaction(ControlTuple_implement clt) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private File assembleReplica(ContentTuple_implement cnt)
    {
        return null;
    }        

    /* in case perform_PassivePersistentCoordinativeTransaction() returns ZERO 
    we can obtain the private REPLY_CLT class variable */
    @Override
    public ControlTuple_implement get_ReplyControlTuple() 
    {
        return this.REPLY_CLT;
    }
}
