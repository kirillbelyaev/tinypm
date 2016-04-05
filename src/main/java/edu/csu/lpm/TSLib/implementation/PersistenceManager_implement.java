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

import edu.csu.lpm.TSLib.interfaces.PersistenceManager;
import edu.csu.lpm.TSLib.interfaces.TupleSpace;
import java.io.File;

/**
 *
 * @author kirill
 */
public class PersistenceManager_implement implements PersistenceManager
{

    @Override
    public int countTuples(String location) 
    {
        if (location != null)
        {    
            if (!location.isEmpty())
            {
                File base = new File (location);
                
                if (base == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;

                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (!ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                        } else {       
                                    if (ts.isDirectory())
                                    {
                                        if (ts.list() == null)
                                        {
                                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                                        }
                                        
                                        /* return the length of the string array
                                        if length is 0 - the dir is empty */
                                        return ts.list().length;
                                        
                                    } else {
                                               return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                { 
                    se.printStackTrace();
                    return PersistenceManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create_TupleSpace(String location) 
    {
        if (location != null)
        {    
            if (!location.isEmpty())
            {
                File base = new File (location);

                if (base == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                
                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_EXISTS_STATUS;
                        } else {       
                                    if (ts.mkdir())
                                    {
                                        return PersistenceManager.INDICATE_OPERATION_SUCCESS;
                                    } else {
                                                return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                { 
                    se.printStackTrace();
                    return PersistenceManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete_TupleSpace(String location) 
    {
        if (location != null)
        {    
            if (!location.isEmpty())
            {
                File base = new File (location);
                
                if (base == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;

                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (!ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                        } else {       
                                    if (ts.isDirectory())
                                    {
                                        if (this.countTuples(location) == 0) /* directory is empty */
                                        {   
                                            if (ts.delete() == true)
                                            {    
                                                return PersistenceManager.INDICATE_OPERATION_SUCCESS;
                                            } else {
                                                      return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;  
                                                   }
                                        } else {
                                                  return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;  
                                               }
                                    } else {
                                                return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                { 
                    se.printStackTrace();
                    return PersistenceManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int append_ContentTuple(ContentTuple_implement ct, String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int append_ControlTuple(ControlTuple_implement ct, String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple_implement read_ContentTuple(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ControlTuple_implement read_ControlTuple(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ControlTuple_implement take_ControlTuple(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple_implement take_ContentTuple(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
