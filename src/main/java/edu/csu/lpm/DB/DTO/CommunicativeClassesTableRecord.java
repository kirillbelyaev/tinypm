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

/*
Data transfer object
Data transfer object (DTO)[1][2] is an object that carries data between 
processes. The motivation for its use has to do with the fact that 
communication between processes is usually done resorting to remote interfaces.
*/

package edu.csu.lpm.DB.DTO;

import edu.csu.lpm.DB.DAO.RecordDAO;
import edu.csu.lpm.DB.interfaces.CommunicativeClassesTable;
import java.io.Serializable;
import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author kirill
 */

public class CommunicativeClassesTableRecord implements Record, Serializable
{   
    /* reference to communicative policy class */
    private String COLUMN_CLASS_ID = "";
    
    private String COLUMN_CLASS_NAME = "";
    
    /* a biple - path_to_component_1; path_to_component_2
    if such a record exists - that serves as a permission for coordination */
    private String COLUMN_COORDINATION_RECORD = "";
    
    /* a biple - component ID; data_object_path
    if such a record exists - that serves as a permission for collaboration */
    private String COLUMN_COLLABORATION_RECORD = "";
    
    private String COLUMN_STATUS = "";
    
    /* set to indicate which field to update when calling write_Apps_Table_Record() */
    private String UPDATE_COLUMN = "";
    
    
    public String get_UPDATE_COLUMN() 
    {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_CLASS_ID() 
    {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_CLASS_ID;
    }
    
    
    public void set_UPDATE_COLUMN_to_STATUS() 
    {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_STATUS;
    }

    
    public void set_UPDATE_COLUMN_to_CLASS_NAME() 
    {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_CLASS_NAME;
    }

    
    public void set_UPDATE_COLUMN_to_COLLABORATION_RECORD() 
    {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD;
    }

    public void set_UPDATE_COLUMN_to_COORDINATION_RECORD() 
    {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_COORDINATION_RECORD;
    }
    
    
    public String get_COLUMN_CLASS_NAME() 
    {
        return this.COLUMN_CLASS_NAME;
    }

    public void set_COLUMN_CLASS_NAME(String COLUMN_CLASS_NAME) 
    {
        if (COLUMN_CLASS_NAME != null) this.COLUMN_CLASS_NAME = COLUMN_CLASS_NAME;
    }

    public String get_COLUMN_COORDINATION_RECORD() 
    {
        return this.COLUMN_COORDINATION_RECORD;
    }

    public int set_COLUMN_COORDINATION_RECORD(String component_1_ID, String component_2_ID) 
    {
        final String separator = " ";
        if (component_1_ID != null && component_2_ID != null)
        {
            if (!component_1_ID.isEmpty() && !component_2_ID.isEmpty())
            {    
                File c1 = new File(component_1_ID);
                
                File c2 = new File(component_2_ID);
                /* set only if an app is an actual file, does exist and not 
                a directory */
                if (c1.isFile() && c2.isFile())
                {    
                    this.COLUMN_COORDINATION_RECORD = component_1_ID + separator + component_2_ID;          
                    return RecordDAO.INDICATE_EXECUTION_SUCCESS;
                }    
                else 
                {    
                    System.out.println("Communicative_Classes_Table_Record.set_COLUMN_COORDINATION_RECORD(): component does not exist on the filesystem! ");
                    return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
                }    
            }
            
            return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    public String get_COLUMN_COLLABORATION_RECORD() 
    {
        return this.COLUMN_COLLABORATION_RECORD;
    }
    
    public boolean check_if_COLUMN_COLLABORATION_RECORD_is_Empty() 
    {
        if (this.get_COLUMN_COLLABORATION_RECORD().isEmpty() ) return true;
        else return false;
    }

    public int set_COLUMN_COLLABORATION_RECORD(String componentID, String object_path) 
    {
        final String separator = " ";
        if (componentID != null && object_path != null)
        {
            if (!componentID.isEmpty() && !object_path.isEmpty())
            {    
                File c = new File(componentID);
                
                File o = new File(object_path);
                
                /* set only if an app is an actual file, does exist and not 
                a directory */
                if (c.isFile() && o.isFile()) 
                {    
                    this.COLUMN_COLLABORATION_RECORD = componentID + separator + object_path;
                    return RecordDAO.INDICATE_EXECUTION_SUCCESS;
                    
                } else 
                {    
                    System.out.println("Communicative_Classes_Table_Record.set_COLUMN_COLLABORATION_RECORD(): object does not exist on the filesystem! ");
                    return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
                }    
            }
            
            return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    public boolean check_if_COLUMN_COORDINATION_RECORD_is_Empty() 
    {
        if (this.get_COLUMN_COORDINATION_RECORD().isEmpty() ) return true;
        else return false;
    }
    
    /* alternative method to set the field when reading a record from db */
    public void set_COLUMN_COORDINATION_RECORD(String record) 
    {
        final String separator = " ";
        StringTokenizer tokenizer = null;
        String component_1_ID = null;
        String component_2_ID = null;
        
        if (record != null)
        {
            if (!record.isEmpty())
            {    
                tokenizer = new StringTokenizer(record, separator);

                int count = tokenizer.countTokens(); //obtain the number of tokens before cycling through them

                /* terminate if record is not composed out of 2 sub-records */
                if (count != 2) return;
                
                //tokenizer.nextToken(); //skip the 1st token

                while (tokenizer.hasMoreTokens())
                {
                    String field = tokenizer.nextToken();
                    field = field.trim();
                    
                    /* 1st pass - assign to 1st variable */
                    if (component_1_ID == null) component_1_ID = field;
                    
                    /* 2nd pass - assign to 2nd variable */
                    if (component_1_ID != null) component_2_ID = field;
                }
            
        
                /* now proceed to processing */      
                if (component_1_ID != null && component_2_ID != null)
                {
                    if (!component_1_ID.isEmpty() && !component_2_ID.isEmpty())
                    {    
                        File f1 = new File(component_1_ID);

                        File f2 = new File(component_2_ID);
                        /* set only if an app is an actual file, does exist and not 
                        a directory */
                        if (f1.isFile() && f2.isFile()) this.COLUMN_COORDINATION_RECORD = component_1_ID + separator + component_2_ID;
                        else System.out.println("Communicative_Classes_Table_Record.set_COLUMN_COORDINATION_RECORD(): component does not exist on the filesystem! ");
                    }
                }
        
            } /* end of if record not empty */
            
        } /* end of if record not null */
    }
     
    /* alternative method to set the field when reading a record from db */
    public void set_COLUMN_COLLABORATION_RECORD(String record) 
    {
        final String separator = " ";
        StringTokenizer tokenizer = null;
        String componentID = null;
        String object_path = null;
        
        if (record != null)
        {
            if (!record.isEmpty())
            {    
                tokenizer = new StringTokenizer(record, separator);

                int count = tokenizer.countTokens(); //obtain the number of tokens before cycling through them

                /* terminate if record is not composed out of 2 sub-records */
                if (count != 2) return;
                
                //tokenizer.nextToken(); //skip the 1st token

                while (tokenizer.hasMoreTokens())
                {
                    String field = tokenizer.nextToken();
                    field = field.trim();
                    
                    /* 1st pass - assign to 1st variable */
                    if (componentID == null) componentID = field;
                    
                    /* 2nd pass - assign to 2nd variable */
                    if (componentID != null) object_path = field;
                }
            
        
                /* now proceed to processing */
                if (componentID != null && object_path != null)
                {
                    if (!componentID.isEmpty() && !object_path.isEmpty())
                    {    
                        File f = new File(componentID);

                        File o = new File(object_path);

                        /* set only if an app is an actual file, does exist and not 
                        a directory */
                        if (f.isFile() && o.isFile()) this.COLUMN_COLLABORATION_RECORD = componentID + separator + object_path;
                        else System.out.println("Communicative_Classes_Table_Record.set_COLUMN_COLLABORATION_RECORD(): component does not exist on the filesystem! ");
                    }
                }
            
            } /* end of if record not empty */
        
        } /* end of if record not null */
    }

    public String get_COLUMN_CLASS_ID() 
    {
        return this.COLUMN_CLASS_ID;
    }

    public int set_COLUMN_CLASS_ID(String COLUMN_CLASS_ID) 
    {    
        Integer id = null;
        
        if (COLUMN_CLASS_ID != null)
        {
           if (!COLUMN_CLASS_ID.isEmpty())
           {    
               try
               {//check if string is a number    
                   id = Integer.valueOf(COLUMN_CLASS_ID);
                   this.COLUMN_CLASS_ID = id.toString();
                   id = null;
                   return RecordDAO.INDICATE_EXECUTION_SUCCESS;
               } catch (NumberFormatException nfex)
               {
                   //Logger.getLogger(Policy_Classes_Table_Record.class.getName()).log(Level.SEVERE, null, nfex);
                   System.out.println("Components_Table_Record.set_COLUMN_COMPONENT_CLASS_ID(): CID string is not a number! ");
                   return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
               }
           }
           
           return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
    }


    public String get_COLUMN_STATUS() 
    {
        return this.COLUMN_STATUS;
    }

    public void set_COLUMN_STATUS(String COLUMN_STATUS) 
    {
        if (COLUMN_STATUS != null) this.COLUMN_STATUS = COLUMN_STATUS;
    }
    
    
    
    public void set_COLUMN_STATUS_Active() 
    {
        Integer one = 1;
        this.COLUMN_STATUS = one.toString();
    }

    public void set_COLUMN_STATUS_Inactive() 
    {
        Integer zero = 0;
        this.COLUMN_STATUS = zero.toString();
    }

    
     
}
