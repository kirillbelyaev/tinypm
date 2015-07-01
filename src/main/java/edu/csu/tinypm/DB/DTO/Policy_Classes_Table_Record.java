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

import edu.csu.tinypm.DB.interfaces.Policy_Classes_Table;
import edu.csu.tinypm.interfaces.LinuxCAPPolicyContainer;
import java.io.Serializable;

/**
 *
 * @author kirill
 */

public class Policy_Classes_Table_Record implements Serializable
{
    private String COLUMN_POLICY_CLASS_ID = ""; /* primary key */
    
    private String COLUMN_POLICY_CLASS_NAME = "";
    
    private String COLUMN_POLICY_CLASS_POLICIES = "";
    
    private String COLUMN_STATUS = "";
    
    private LinuxCAPPolicyContainer.LinuxCapabilities LCS[] = LinuxCAPPolicyContainer.LinuxCapabilities.values();
    
    /* set to indicate which field to update when calling write_Policy_Classes_Table_Record() */
    private String UPDATE_COLUMN = "";
    
    
public Policy_Classes_Table_Record()
	{
            
	}


    public String get_UPDATE_COLUMN() {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_ID() {
        this.UPDATE_COLUMN = Policy_Classes_Table.COLUMN_POLICY_CLASS_ID;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_NAME() {
        this.UPDATE_COLUMN = Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_POLICIES() {
        this.UPDATE_COLUMN = Policy_Classes_Table.COLUMN_POLICY_CLASS_POLICIES;
    }


    public void set_UPDATE_COLUMN_to_STATUS() {
        this.UPDATE_COLUMN = Policy_Classes_Table.COLUMN_STATUS;
    }
    
    public String get_COLUMN_POLICY_CLASS_NAME() {
        return this.COLUMN_POLICY_CLASS_NAME;
    }

    public void set_COLUMN_POLICY_CLASS_NAME(String COLUMN_POLICY_CLASS_NAME) {
        if (COLUMN_POLICY_CLASS_NAME != null) this.COLUMN_POLICY_CLASS_NAME = COLUMN_POLICY_CLASS_NAME;
    }

    public LinuxCAPPolicyContainer.LinuxCapabilities[] get_LCS() {
        return this.LCS;
    }

    public void set_LCS(LinuxCAPPolicyContainer.LinuxCapabilities[] LCS) {
        this.LCS = LCS;
    }

    public String get_CAP(String cap) {
        
        if (cap == null) return null;
        
        for (LinuxCAPPolicyContainer.LinuxCapabilities LCS1 : LCS) {
            if (LCS1.toString().equals(cap.trim())) {
                return LCS1.toString();  
            }
        }
        
        return null;
    }
    
    
    public int get_CAP_index(String cap) {
        
        if (cap == null) return -1;
        
        for (LinuxCAPPolicyContainer.LinuxCapabilities LCS1 : LCS) {
            if (LCS1.toString().equals(cap.trim())) {
                return LCS1.ordinal();  
            }
        }
        
        return -1;
    }
    
    
    public String get_COLUMN_POLICY_CLASS_POLICIES() {
        return this.COLUMN_POLICY_CLASS_POLICIES;
    }

    public void set_COLUMN_POLICY_CLASS_POLICIES(String COLUMN_CAPS) {
        if (COLUMN_CAPS != null) this.COLUMN_POLICY_CLASS_POLICIES = COLUMN_CAPS;
    }
    
    
    public void add_POLICY_CLASS_POLICY(String COLUMN_CAPS) {
        if (COLUMN_CAPS != null)
        {
            if (!this.COLUMN_POLICY_CLASS_POLICIES.contains(COLUMN_CAPS))
            {
                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(COLUMN_CAPS);
                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(" ");
            }    
        }    
    }
    
    public void remove_POLICY_CLASS_POLICY(String COLUMN_CAPS) {
        if (COLUMN_CAPS != null)
        {
            int start = this.COLUMN_POLICY_CLASS_POLICIES.indexOf(COLUMN_CAPS);
            int len = COLUMN_CAPS.length();
            String first_half = null;
            
            if (start != -1)
            {
                if (start - 1 > 0)
                    first_half = this.COLUMN_POLICY_CLASS_POLICIES.substring(0, start - 1);
                else first_half = "";
                
                String second_half = this.COLUMN_POLICY_CLASS_POLICIES.substring(start + len +1); 
                
                this.COLUMN_POLICY_CLASS_POLICIES = "";
                
                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(first_half);
                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(second_half);
                
            }
        }    
    }
    
    
    public String produce_PCS_DB_DDL()
    {
        String Columns = "( " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + ", " + Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME + ", " + Policy_Classes_Table.COLUMN_STATUS + ", ";
        
        for (int i = 0; i < Policy_Classes_Table.LCS.length; i++)
            if (i != Policy_Classes_Table.LCS.length - 1)
                Columns = Columns.concat(Policy_Classes_Table.LCS[i].toString() + ", ");
            else 
                Columns = Columns.concat(Policy_Classes_Table.LCS[i].toString() + " ) ");  
        
        return Columns;
    }     
    

    public String get_COLUMN_POLICY_CLASS_ID() {
        return this.COLUMN_POLICY_CLASS_ID;
    }

    public void set_COLUMN_POLICY_CLASS_ID(String COLUMN_POLICY_CLASS_ID) {
        //if (COLUMN_POLICY_CLASS_ID != null) this.COLUMN_POLICY_CLASS_ID = COLUMN_POLICY_CLASS_ID;
        
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
               System.out.println("Policy_Classes_Table_Record.setCOLUMN_POLICY_CLASS_ID(): ID string is not a number! ");
           }    
        }
        
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
