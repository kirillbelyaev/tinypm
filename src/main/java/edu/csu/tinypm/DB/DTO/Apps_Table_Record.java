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

package edu.csu.tinypm.DB.DTO;

import edu.csu.tinypm.DB.interfaces.Apps_Table;
import java.io.Serializable;
import java.io.*;

/**
 *
 * @author kirill
 */
public class Apps_Table_Record implements Serializable
{
    
    private String COLUMN_APP_DESC = "";
    private String COLUMN_APP_PATH = "";
    private String COLUMN_POLICY_CLASS_ID = "";
    private String COLUMN_APP_CONTAINER_ID = "";
    private String COLUMN_STATUS = "";
    
    /* set to indicate which field to update when calling write_Apps_Table_Record() */
    private String UPDATE_COLUMN = "";
    
    
public Apps_Table_Record()
	{
            
	}
    
    public String get_UPDATE_COLUMN() {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_ID() {
        this.UPDATE_COLUMN = Apps_Table.COLUMN_POLICY_CLASS_ID;
    }
    
    
    public void set_UPDATE_COLUMN_to_STATUS() {
        this.UPDATE_COLUMN = Apps_Table.COLUMN_STATUS;
    }

    
    public void set_UPDATE_COLUMN_to_APP_PATH() {
        this.UPDATE_COLUMN = Apps_Table.COLUMN_APP_PATH;
    }

    
    public String get_COLUMN_APP_DESC() {
        return this.COLUMN_APP_DESC;
    }

    public void set_COLUMN_APP_DESC(String COLUMN_APP_DESC) {
        if (COLUMN_APP_DESC != null) this.COLUMN_APP_DESC = COLUMN_APP_DESC;
    }

    public String get_COLUMN_APP_PATH() {
        return this.COLUMN_APP_PATH;
    }

    public void set_COLUMN_APP_PATH(String COLUMN_APP_PATH) 
    {
        if (COLUMN_APP_PATH != null)
        {
            if (!COLUMN_APP_PATH.isEmpty())
            {    
                File f = new File(COLUMN_APP_PATH);
                
                /* set only if an app is an actual file, does exist and not 
                a directory */
                if (f.isFile()) this.COLUMN_APP_PATH = COLUMN_APP_PATH;
                else System.out.println("Apps_Table_Record.set_COLUMN_APP_PATH(): app does not exist in the filesystem! ");
            }
        }
    }

    public String get_COLUMN_POLICY_CLASS_ID() {
        return this.COLUMN_POLICY_CLASS_ID;
    }

    public void set_COLUMN_POLICY_CLASS_ID(String COLUMN_POLICY_CLASS_ID) 
    {    
        Integer id = null;
        
        if (COLUMN_POLICY_CLASS_ID != null)
        {
           try
           {//check if string is a number    
               id = Integer.valueOf(COLUMN_POLICY_CLASS_ID);
               this.COLUMN_POLICY_CLASS_ID = id.toString();
               id = null;
           } catch (NumberFormatException nfex)
           {
               //Logger.getLogger(Policy_Classes_Table_Record.class.getName()).log(Level.SEVERE, null, nfex);
               System.out.println("Apps_Table_Record.set_COLUMN_POLICY_CLASS_ID(): PCID string is not a number! ");
           }    
        }   
    }

    public String get_COLUMN_APP_CONTAINER_ID() {
        return this.COLUMN_APP_CONTAINER_ID;
    }

    public void set_COLUMN_APP_CONTAINER_ID(String COLUMN_APP_CONTAINER_ID) {
        if (COLUMN_APP_CONTAINER_ID != null) this.COLUMN_APP_CONTAINER_ID = COLUMN_APP_CONTAINER_ID;
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

    
     
}
