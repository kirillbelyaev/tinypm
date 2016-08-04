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

import java.io.Serializable;
import java.io.*;
import edu.csu.lpm.DB.interfaces.ComponentsTable;

/**
 *
 * @author kirill
 */
public class ComponentsTableRecord implements Record, Serializable
{
    
    private String COLUMN_COMPONENT_DESC = "";
    
    /* absolute path is currently used as the component ID */
    private String COLUMN_COMPONENT_PATH_ID = "";
    
    /* reserved for future use - could be used for alternative ID mechanisms */
    private String COLUMN_COMPONENT_ID = "";
    
    /* reference to capabilities policy class */
    private String COLUMN_COMPONENT_CAPABILITIES_CLASS_ID = "";
    
    /* reference to communicative policy class */
    private String COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID = "";
    
    /* reserved for future use */
    private String COLUMN_COMPONENT_CONTAINER_ID = "";
    
    /* reserved for possible use - indicates the component's tuple space location */
    private String COLUMN_COMPONENT_TUPLE_SPACE_PATH = "";
    
    private String COLUMN_STATUS = "";
    
    /* set to indicate which field to update when calling write_Apps_Table_Record() */
    private String UPDATE_COLUMN = "";

    
    public String get_UPDATE_COLUMN() {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_COMPONENT_CAPABILITIES_CLASS_ID() {
        this.UPDATE_COLUMN = ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID;
    }
    
    public void set_UPDATE_COLUMN_to_COMPONENT_COMMUNICATIVE_CLASS_ID() {
        this.UPDATE_COLUMN = ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID;
    }
    
    public void set_UPDATE_COLUMN_to_STATUS() {
        this.UPDATE_COLUMN = ComponentsTable.COLUMN_STATUS;
    }

    
    public void set_UPDATE_COLUMN_to_COMPONENT_PATH_ID() {
        this.UPDATE_COLUMN = ComponentsTable.COLUMN_COMPONENT_PATH_ID;
    }

    
    public String get_COLUMN_COMPONENT_DESC() {
        return this.COLUMN_COMPONENT_DESC;
    }

    public void set_COLUMN_COMPONENT_DESC(String COLUMN_COMPONENT_DESC) {
        if (COLUMN_COMPONENT_DESC != null) this.COLUMN_COMPONENT_DESC = COLUMN_COMPONENT_DESC;
    }

    public String get_COLUMN_COMPONENT_PATH_ID() {
        return this.COLUMN_COMPONENT_PATH_ID;
    }

    public void set_COLUMN_COMPONENT_PATH_ID(String COLUMN_COMPONENT_PATH_ID) 
    {
        if (COLUMN_COMPONENT_PATH_ID != null)
        {
            if (!COLUMN_COMPONENT_PATH_ID.isEmpty())
            {    
                File f = new File(COLUMN_COMPONENT_PATH_ID);
                
                /* set only if an app is an actual file, does exist and not 
                a directory */
                if (f.isFile()) this.COLUMN_COMPONENT_PATH_ID = COLUMN_COMPONENT_PATH_ID;
                else System.out.println("Components_Table_Record.set_COLUMN_COMPONENT_PATH_ID(): component does not exist on the filesystem! ");
            }
        }
    }

    public String get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID() {
        return this.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID;
    }

    public void set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(String COLUMN_COMPONENT_CAPABILITIES_CLASS_ID) 
    {    
        Integer id = null;
        
        if (COLUMN_COMPONENT_CAPABILITIES_CLASS_ID != null)
        {
           try
           {//check if string is a number    
               id = Integer.valueOf(COLUMN_COMPONENT_CAPABILITIES_CLASS_ID);
               this.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID = id.toString();
               id = null;
           } catch (NumberFormatException nfex)
           {
               //Logger.getLogger(Policy_Classes_Table_Record.class.getName()).log(Level.SEVERE, null, nfex);
               System.out.println("Components_Table_Record.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(): CID string is not a number! ");
           }    
        }   
    }
    
    
    public String get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID() {
        return this.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID;
    }

    
    public void set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(String COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID) 
    {    
        Integer id = null;
        
        if (COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID != null)
        {
           try
           {//check if string is a number    
               id = Integer.valueOf(COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID);
               this.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID = id.toString();
               id = null;
           } catch (NumberFormatException nfex)
           {
               //Logger.getLogger(Policy_Classes_Table_Record.class.getName()).log(Level.SEVERE, null, nfex);
               System.out.println("Components_Table_Record.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(): CID string is not a number! ");
           }    
        }   
    }
    

    public String get_COLUMN_COMPONENT_CONTAINER_ID() {
        return this.COLUMN_COMPONENT_CONTAINER_ID;
    }

    public void set_COLUMN_COMPONENT_CONTAINER_ID(String COLUMN_COMPONENT_CONTAINER_ID) {
        if (COLUMN_COMPONENT_CONTAINER_ID != null) this.COLUMN_COMPONENT_CONTAINER_ID = COLUMN_COMPONENT_CONTAINER_ID;
    }
    
    public String get_COLUMN_COMPONENT_ID() 
    {
        return this.COLUMN_COMPONENT_ID;
    }

    public void set_COLUMN_COMPONENT_ID(String COLUMN_COMPONENT_ID) 
    {
        if (COLUMN_COMPONENT_ID != null) this.COLUMN_COMPONENT_ID = COLUMN_COMPONENT_ID;
    }

    public String get_COLUMN_STATUS() {
        return this.COLUMN_STATUS;
    }

    public void set_COLUMN_STATUS(String COLUMN_STATUS) {
        if (COLUMN_STATUS != null) this.COLUMN_STATUS = COLUMN_STATUS;
    }
    
    
    
    public void set_Status_Active() 
    {
            Integer one = 1;
            this.COLUMN_STATUS = one.toString();
    }

    public void set_Status_Inactive() 
    {
            Integer zero = 0;
            this.COLUMN_STATUS = zero.toString();
    }

    public String get_COLUMN_COMPONENT_TUPLE_SPACE_PATH() 
    {
        return this.COLUMN_COMPONENT_TUPLE_SPACE_PATH;
    }
     
    
    public void set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(String COLUMN_COMPONENT_TUPLE_SPACE_PATH) 
    {
        if (COLUMN_COMPONENT_TUPLE_SPACE_PATH != null) this.COLUMN_COMPONENT_TUPLE_SPACE_PATH = COLUMN_COMPONENT_TUPLE_SPACE_PATH;
    }
    
}
