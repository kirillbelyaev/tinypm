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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    //se.printStackTrace();
                    Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, se);
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
                    //se.printStackTrace();
                    Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, se);
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
                    //se.printStackTrace();
                    Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, se);
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
    public int append_ControlTuple(ControlTuple_implement ct, String location) 
    {
        if (ct == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (location == null) return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
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

                                    File c_t = new File (location + TupleSpace.TupleSpaceName + TupleSpace.ControlTupleName);
                                    
                                    if (c_t.exists()) /* if control tuple already exists */
                                    {
                                        return TupleSpace.INDICATE_CONTROL_TUPLE_EXISTS_STATUS;
                                    } else {
                                                /* introduce serialization using internal Java facility,
                                                instead of relying on external libraries */
                                                /* usage of external library is problematic due to licensing issues */
                                                //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

                                                try 
                                                {
                                                    OutputStream control_tuple = new FileOutputStream(location + TupleSpace.TupleSpaceName + TupleSpace.ControlTupleName);
                                                    
                                                    OutputStream buffer = new BufferedOutputStream(control_tuple);

                                                    ObjectOutput oos = new ObjectOutputStream(buffer);
                                                    /* serialize the POJO to file */
                                                    oos.writeObject(ct);
                                                    
                                                    /* close streams */
                                                    oos.close();
                                                    buffer.close();
                                                    control_tuple.close();
                                                    
                                                    return PersistenceManager.INDICATE_OPERATION_SUCCESS;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                    return PersistenceManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                                                }
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
                //se.printStackTrace();
                Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, se);
                return PersistenceManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
            }
        }
        
        return PersistenceManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple_implement read_ContentTuple(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ControlTuple_implement read_ControlTuple(String location) 
    {   
        if (location == null) return null;
        
        if (!location.isEmpty())
        {
            File base = new File (location);

            if (base == null) return null;

            try 
            {
                if (base.isDirectory())
                {
                    File ts = new File (location + TupleSpace.TupleSpaceName);

                    if (ts == null) return null;

                    if (!ts.exists()) /* could be a file or a directory with the same name */
                    {
                        return null;
                    } else {       
                                if (ts.isDirectory())
                                {
                                    if (ts.list() == null)
                                    {
                                        return null;
                                    }

                                    File c_t = new File (location + TupleSpace.TupleSpaceName + TupleSpace.ControlTupleName);
                                    
                                    if (!c_t.exists()) /* if control tuple does not exist */
                                    {
                                        return null;
                                    } else {
                                                /* make sure it is a regular file and not a directory */
                                                if (!c_t.isFile()) return null;
                                        
                                                /* introduce serialization using internal Java facility,
                                                instead of relying on external libraries */
                                                /* usage of external library is problematic due to licensing issues */
                                                //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                                                
                                                try 
                                                {
                                                    InputStream control_tuple = new FileInputStream(location + TupleSpace.TupleSpaceName + TupleSpace.ControlTupleName);
                                                    
                                                    InputStream buffer = new BufferedInputStream(control_tuple);

                                                    ObjectInput ois = new ObjectInputStream(buffer);
                                                    
                                                    ControlTuple_implement ct = null;
                                                    
                                                    /* de-serialize to POJO from file */
                                                    try 
                                                    {    
                                                        ct = (ControlTuple_implement) ois.readObject();
                                                    } catch (ClassNotFoundException ex) 
                                                    {
                                                        Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                        return null;
                                                    }
                                                    
                                                    /* close streams */
                                                    ois.close();
                                                    buffer.close();
                                                    control_tuple.close();
                                                    
                                                    /* return POJO */
                                                    return ct;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                    return null;
                                                }
                                           }
                                } else {
                                           return null;
                                       }                 
                           }    
                } else {
                            return null;                  
                       }
            } catch (SecurityException se)
            { 
                //se.printStackTrace();
                Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, se);
                return null; 
            }
        }
        
        return null;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ControlTuple_implement take_ControlTuple(String location) 
    {
        if (location == null) return null;
        
        if (!location.isEmpty())
        {
            File base = new File (location);

            if (base == null) return null;

            try 
            {
                if (base.isDirectory())
                {
                    File ts = new File (location + TupleSpace.TupleSpaceName);

                    if (ts == null) return null;

                    if (!ts.exists()) /* could be a file or a directory with the same name */
                    {
                        return null;
                    } else {       
                                if (ts.isDirectory())
                                {
                                    if (ts.list() == null)
                                    {
                                        return null;
                                    }

                                    File c_t = new File (location + TupleSpace.TupleSpaceName + TupleSpace.ControlTupleName);
                                    
                                    if (!c_t.exists()) /* if control tuple does not exist */
                                    {
                                        return null;
                                    } else {
                                                /* make sure it is a regular file and not a directory */
                                                if (!c_t.isFile()) return null;
                                        
                                                /* introduce serialization using internal Java facility,
                                                instead of relying on external libraries */
                                                /* usage of external library is problematic due to licensing issues */
                                                //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                                                
                                                try 
                                                {
                                                    InputStream control_tuple = new FileInputStream(location + TupleSpace.TupleSpaceName + TupleSpace.ControlTupleName);
                                                    
                                                    InputStream buffer = new BufferedInputStream(control_tuple);

                                                    ObjectInput ois = new ObjectInputStream(buffer);
                                                    
                                                    ControlTuple_implement ct = null;
                                                    
                                                    /* de-serialize to POJO from file */
                                                    try 
                                                    {    
                                                        ct = (ControlTuple_implement) ois.readObject();
                                                    } catch (ClassNotFoundException ex) 
                                                    {
                                                        Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                        return null;
                                                    }
                                                    
                                                    /* close streams */
                                                    ois.close();
                                                    buffer.close();
                                                    control_tuple.close();
                                                    
                                                    /* remove tuple file */
                                                    if (c_t.delete() != true) return null;
                                                    
                                                    /* return POJO */
                                                    return ct;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                    return null;
                                                }
                                           }
                                } else {
                                           return null;
                                       }                 
                           }    
                } else {
                            return null;                  
                       }
            } catch (SecurityException se)
            { 
                //se.printStackTrace();
                Logger.getLogger(PersistenceManager_implement.class.getName()).log(Level.SEVERE, null, se);
                return null; 
            }
        }
        
        return null;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple_implement take_ContentTuple(String location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
