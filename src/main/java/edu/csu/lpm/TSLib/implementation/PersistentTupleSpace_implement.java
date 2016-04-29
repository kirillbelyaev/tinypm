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
import edu.csu.lpm.TSLib.interfaces.PersistentTupleSpace;
import edu.csu.lpm.TSLib.interfaces.TupleSpace;

/**
 *
 * @author kirill
 */
public class PersistentTupleSpace_implement implements PersistentTupleSpace
{

    @Override
    public int count_Tuples(String location) 
    {
        if (location != null)
        {    
            if (!location.isEmpty())
            {
                File base = new File (location);
                
                if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

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
                                               return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                { 
                    //se.printStackTrace();
                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
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

                if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                
                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_EXISTS_STATUS;
                        } else {       
                                    if (ts.mkdir())
                                    {
                                        return PersistentTupleSpace.INDICATE_OPERATION_SUCCESS;
                                    } else {
                                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                { 
                    //se.printStackTrace();
                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
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
                
                if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (!ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                        } else {       
                                    if (ts.isDirectory())
                                    {
                                        if (this.count_Tuples(location) == 0) /* directory is empty */
                                        {   
                                            if (ts.delete() == true)
                                            {    
                                                return PersistentTupleSpace.INDICATE_OPERATION_SUCCESS;
                                            } else {
                                                      return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;  
                                                   }
                                        } else {
                                                  return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;  
                                               }
                                    } else {
                                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                { 
                    //se.printStackTrace();
                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int append_ContentTuple(ContentTuple_implement ct, String location) 
    {
        if (ct == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (location == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (!location.isEmpty())
        {
            File base = new File (location);

            if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                if (base.isDirectory())
                {
                    File ts = new File (location + TupleSpace.TupleSpaceName);

                    if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

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

                                    File c_t = new File (location + TupleSpace.TupleSpaceName + TupleSpace.ContentTupleName);
                                    
                                    if (c_t.exists()) /* if control tuple already exists */
                                    {
                                        return TupleSpace.INDICATE_CONTENT_TUPLE_EXISTS_STATUS;
                                    } else {
                                                /* introduce serialization using internal Java facility,
                                                instead of relying on external libraries */
                                                /* usage of external library is problematic due to licensing issues */
                                                //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

                                                try 
                                                {
                                                    OutputStream content_tuple = new FileOutputStream(location + TupleSpace.TupleSpaceName + TupleSpace.ContentTupleName);
                                                    
                                                    OutputStream buffer = new BufferedOutputStream(content_tuple);

                                                    ObjectOutput oos = new ObjectOutputStream(buffer);
                                                    /* serialize the POJO to file */
                                                    oos.writeObject(ct);
                                                    
                                                    /* close streams */
                                                    oos.close();
                                                    buffer.close();
                                                    content_tuple.close();
                                                    
                                                    return PersistentTupleSpace.INDICATE_OPERATION_SUCCESS;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                                                }
                                           }
                                } else {
                                           return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                       }                 
                           }    
                } else {
                            return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                       }
            } catch (SecurityException se)
            { 
                //se.printStackTrace();
                Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
            }
        }
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int append_ControlTuple(ControlTuple_implement ct, String location) 
    {
        if (ct == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (location == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        if (!location.isEmpty())
        {
            File base = new File (location);

            if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                if (base.isDirectory())
                {
                    File ts = new File (location + TupleSpace.TupleSpaceName);

                    if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

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
                                                    
                                                    return PersistentTupleSpace.INDICATE_OPERATION_SUCCESS;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
                                                }
                                           }
                                } else {
                                           return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                       }                 
                           }    
                } else {
                            return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                       }
            } catch (SecurityException se)
            { 
                //se.printStackTrace();
                Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
            }
        }
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple_implement read_ContentTuple(String location) 
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

                                    File c_t = new File (location + TupleSpace.TupleSpaceName + TupleSpace.ContentTupleName);
                                    
                                    if (!c_t.exists()) /* if content tuple does not exist */
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
                                                    InputStream content_tuple = new FileInputStream(location + TupleSpace.TupleSpaceName + TupleSpace.ContentTupleName);
                                                    
                                                    InputStream buffer = new BufferedInputStream(content_tuple);

                                                    ObjectInput ois = new ObjectInputStream(buffer);
                                                    
                                                    ContentTuple_implement ct = null;
                                                    
                                                    /* de-serialize to POJO from file */
                                                    try 
                                                    {    
                                                        ct = (ContentTuple_implement) ois.readObject();
                                                    } catch (ClassNotFoundException ex) 
                                                    {
                                                        Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                        return null;
                                                    }
                                                    
                                                    /* close streams */
                                                    ois.close();
                                                    buffer.close();
                                                    content_tuple.close();
                                                    
                                                    /* return POJO */
                                                    return ct;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                return null; 
            }
        }
        
        return null;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                                                        Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
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
                                                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
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
                                                        Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
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
                                                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                return null; 
            }
        }
        
        return null;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple_implement take_ContentTuple(String location) 
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

                                    File c_t = new File (location + TupleSpace.TupleSpaceName + TupleSpace.ContentTupleName);
                                    
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
                                                    InputStream content_tuple = new FileInputStream(location + TupleSpace.TupleSpaceName + TupleSpace.ContentTupleName);
                                                    
                                                    InputStream buffer = new BufferedInputStream(content_tuple);

                                                    ObjectInput ois = new ObjectInputStream(buffer);
                                                    
                                                    ContentTuple_implement ct = null;
                                                    
                                                    /* de-serialize to POJO from file */
                                                    try 
                                                    {    
                                                        ct = (ContentTuple_implement) ois.readObject();
                                                    } catch (ClassNotFoundException ex) 
                                                    {
                                                        Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
                                                        return null;
                                                    }
                                                    
                                                    /* close streams */
                                                    ois.close();
                                                    buffer.close();
                                                    content_tuple.close();
                                                    
                                                    /* remove tuple file */
                                                    if (c_t.delete() != true) return null;
                                                    
                                                    /* return POJO */
                                                    return ct;

                                                } catch (IOException ex) 
                                                {
                                                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                return null; 
            }
        }
        
        return null;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int count_ControlTuples(String location) 
    {
        if (location != null)
        {    
            if (!location.isEmpty())
            {
                File base = new File (location);
                
                if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (!ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                        } else {       
                                    if (ts.isDirectory())
                                    {    
                                        int count = 0;
                                        File [] files = ts.listFiles();
                                        
                                        if (files == null)
                                        {
                                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                                        }
                                        
                                        
                                        for (int i = 0; i < files.length; i++)
                                        {
                                            /* if strings match and file is a normal file */
                                            if (files[i].getName().compareTo(TupleSpace.ControlTupleName.substring(1)) == 0 && files[i].isFile() == true)
                                                count = count+1;
                                        }
                                        
                                        /* return the occurrence count
                                        if count is 0 - the specified file does not exist */
                                        return count;
                                        
                                    } else {
                                               return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                {
                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    @Override
    public int count_ContentTuples(String location) 
    {
        if (location != null)
        {    
            if (!location.isEmpty())
            {
                File base = new File (location);
                
                if (base == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                try 
                {
                    if (base.isDirectory())
                    {
                        File ts = new File (location + TupleSpace.TupleSpaceName);
                        
                        if (ts == null) return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (!ts.exists()) /* could be a file or a directory with the same name */
                        {
                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                        } else {       
                                    if (ts.isDirectory())
                                    {    
                                        int count = 0;
                                        File [] files = ts.listFiles();
                                        
                                        if (files == null)
                                        {
                                            return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
                                        }
                                        
                                        
                                        for (int i = 0; i < files.length; i++)
                                        {
                                            /* if strings match and file is a normal file */
                                            if (files[i].getName().compareTo(TupleSpace.ContentTupleName.substring(1)) == 0 && files[i].isFile() == true)
                                                count = count+1;
                                        }
                                        
                                        /* return the occurrence count
                                        if count is 0 - the specified file does not exist */
                                        return count;
                                        
                                    } else {
                                               return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
                                           }                 
                               }    
                    } else {
                                return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;                  
                           }
                } catch (SecurityException se)
                {
                    Logger.getLogger(PersistentTupleSpace_implement.class.getName()).log(Level.SEVERE, null, se);
                    return PersistentTupleSpace.INDICATE_EXCEPTION_OCCURRENCE_STATUS; 
                }
            }    
        }   
        
        return PersistentTupleSpace.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
}
