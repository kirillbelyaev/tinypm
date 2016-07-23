/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

/*
 Data transfer object
Data transfer object (DTO)[1][2] is an object that carries data between 
processes. The motivation for its use has to do with the fact that 
communication between processes is usually done resorting to remote interfaces.
*/

package edu.csu.lpm.DB.DTO;

import edu.csu.lpm.DB.interfaces.CommunicativeClassesTable;
import java.io.Serializable;
import java.io.*;

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
    
    
    public String get_UPDATE_COLUMN() {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_CLASS_ID() {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_CLASS_ID;
    }
    
    
    public void set_UPDATE_COLUMN_to_STATUS() {
        this.UPDATE_COLUMN = CommunicativeClassesTable.COLUMN_STATUS;
    }

    
    public void set_UPDATE_COLUMN_to_CLASS_NAME() {
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
    
    
    public String get_COLUMN_CLASS_NAME() {
        return this.COLUMN_CLASS_NAME;
    }

    public void set_COLUMN_CLASS_NAME(String COLUMN_CLASS_NAME) {
        if (COLUMN_CLASS_NAME != null) this.COLUMN_CLASS_NAME = COLUMN_CLASS_NAME;
    }

    public String get_COLUMN_COORDINATION_RECORD() 
    {
        return this.COLUMN_COORDINATION_RECORD;
    }

    public void set_COLUMN_COORDINATION_RECORD(String component_1_ID, String component_2_ID) 
    {
        final String separator = " ";
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
    }
    
    public String get_COLUMN_COLLABORATION_RECORD() 
    {
        return this.COLUMN_COLLABORATION_RECORD;
    }

    public void set_COLUMN_COLLABORATION_RECORD(String componentID, String object_path) 
    {
        final String separator = " ";
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
    }

    public String get_COLUMN_CLASS_ID() 
    {
        return this.COLUMN_CLASS_ID;
    }

    public void set_COLUMN_CLASS_ID(String COLUMN_CLASS_ID) 
    {    
        Integer id = null;
        
        if (COLUMN_CLASS_ID != null)
        {
           try
           {//check if string is a number    
               id = Integer.valueOf(COLUMN_CLASS_ID);
               this.COLUMN_CLASS_ID = id.toString();
               id = null;
           } catch (NumberFormatException nfex)
           {
               //Logger.getLogger(Policy_Classes_Table_Record.class.getName()).log(Level.SEVERE, null, nfex);
               System.out.println("Components_Table_Record.set_COLUMN_COMPONENT_CAPABILITIES_POLICY_CLASS_ID(): PCID string is not a number! ");
           }    
        }   
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
