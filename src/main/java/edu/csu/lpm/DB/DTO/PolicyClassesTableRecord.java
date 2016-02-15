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

import edu.csu.lpm.DB.interfaces.PolicyClassesTable;
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;
import java.io.Serializable;

/**
 *
 * @author kirill
 */

public class PolicyClassesTableRecord implements Record, Serializable
{
    private String COLUMN_POLICY_CLASS_ID = ""; /* primary key */
    
    private String COLUMN_POLICY_CLASS_NAME = "";
    
    private String COLUMN_POLICY_CLASS_POLICIES = "";
    
    private String COLUMN_STATUS = "";
    
    private LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS[] = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.values();
    
    /* set to indicate which field to update when calling write_Policy_Classes_Table_Record() */
    private String UPDATE_COLUMN = "";
    
    
public PolicyClassesTableRecord()
	{
            
	}


    public String get_UPDATE_COLUMN() {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_ID() {
        this.UPDATE_COLUMN = PolicyClassesTable.COLUMN_POLICY_CLASS_ID;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_NAME() {
        this.UPDATE_COLUMN = PolicyClassesTable.COLUMN_POLICY_CLASS_NAME;
    }
    
    public void set_UPDATE_COLUMN_to_POLICY_CLASS_POLICIES() {
        this.UPDATE_COLUMN = PolicyClassesTable.COLUMN_POLICY_CLASS_POLICIES;
    }


    public void set_UPDATE_COLUMN_to_STATUS() {
        this.UPDATE_COLUMN = PolicyClassesTable.COLUMN_STATUS;
    }
    
    public String get_COLUMN_POLICY_CLASS_NAME() {
        return this.COLUMN_POLICY_CLASS_NAME;
    }

    public void set_COLUMN_POLICY_CLASS_NAME(String COLUMN_POLICY_CLASS_NAME) {
        if (COLUMN_POLICY_CLASS_NAME != null) this.COLUMN_POLICY_CLASS_NAME = COLUMN_POLICY_CLASS_NAME;
    }

    public LinuxCapabilitiesPolicyContainer.LinuxCapabilities[] get_LCS() {
        return this.LCS;
    }

    public void set_LCS(LinuxCapabilitiesPolicyContainer.LinuxCapabilities[] LCS) {
        this.LCS = LCS;
    }
    
    
    public int check_if_Capability_is_valid(String cap) {
        
        if (cap == null) return -1;
        if (cap.isEmpty()) return -1;
        
        for (LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS1 : LCS) {
            if (LCS1.toString().equals(cap.trim())) {
                //return LCS1.ordinal();  
                return 0; //Found
            }
        }
        
        return -1; //NOT found
    }
    
    public int check_if_Capabilities_are_valid(String caps)
    {
        String policies[] = null;
        if (caps != null)
        {    
            if (!caps.isEmpty())
            {    
                policies = caps.trim().split(" ");
            
                if (policies != null)
                {    
                    if (policies.length > 0)
                    {
                        //System.out.println("check_if_Capabilities_are_valid: policies len is: " + policies.length);
                        for (int i = 0; i < policies.length; i++)
                            if (this.check_if_Capability_is_valid(policies[i]) == -1)
                                return -1;
                        
                        return 0; //ALL caps are valid
                    }
                    
                    return -1;
                }
                
                return -1;
            }
            
            return -1;
        }
           
        return -1;  //NOT found      
    }        
    
    
    public String get_COLUMN_POLICY_CLASS_POLICIES() {
        return this.COLUMN_POLICY_CLASS_POLICIES;
    }
    
    
    public boolean check_if_COLUMN_POLICY_CLASS_POLICIES_is_Empty() {
        if (this.get_COLUMN_POLICY_CLASS_POLICIES().isEmpty() ) return true;
        else return false;
    }
    

    public void set_COLUMN_POLICY_CLASS_POLICIES(String COLUMN_CAPS) {
        if (COLUMN_CAPS != null)
        { 
            if (this.check_if_Capabilities_are_valid(COLUMN_CAPS.trim()) != -1 )
                this.COLUMN_POLICY_CLASS_POLICIES = COLUMN_CAPS.trim();
            
        }    
            
    }
    
    
    public void reset_COLUMN_POLICY_CLASS_POLICIES() {
           this.COLUMN_POLICY_CLASS_POLICIES = "";
    }
    
    
    public void add_POLICY_CLASS_POLICY(String CAP) {
        String policies[] = null;
        if (CAP != null)
        {
            if (this.check_if_Capability_is_valid(CAP) == 0)
            {   
                policies = this.COLUMN_POLICY_CLASS_POLICIES.trim().split(" ");
                
                if (policies != null)
                {    
                    if (policies.length > 0) //it is always the case since even the empty string is tokenized into a 1 element array
                    {
                        //System.out.println("check_if_Capabilities_are_valid: policies len is: " + policies.length);
                        for (int i = 0; i < policies.length; i++)
                        {    
                            if (policies[i].equals(CAP.trim()))
                                return; // policy already exists
                        }
                        
                        this.COLUMN_POLICY_CLASS_POLICIES = ""; //reset to incorporate a new policy
                        
                        if (policies[0].length() == 0) //if COLUMN_POLICY_CLASS_POLICIES is empty
                        {    
                            this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(CAP.trim());
                            this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(" ");
                        } else //if at least a single policy exists
                        {
                            for (int i = 0; i < policies.length; i++)
                            { 
                                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(policies[i]);
                                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(" ");        
                            }
                        
                            //now add the new policy
                            this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(CAP.trim());
                            this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(" ");

                        }                                      
                    }              
                }
            }
        }    
    }
    
    public void remove_POLICY_CLASS_POLICY(String CAP) {
        String policies[] = null;
        if (CAP != null)
        {
            if (this.check_if_Capability_is_valid(CAP) == 0)
            {
                
                policies = this.COLUMN_POLICY_CLASS_POLICIES.trim().split(" ");
                
                if (policies != null)
                {    
                    if (policies.length > 0) //it is always the case since even the empty string is tokenized into a 1 element array
                    {
                        //System.out.println("check_if_Capabilities_are_valid: policies len is: " + policies.length);
                        this.COLUMN_POLICY_CLASS_POLICIES = ""; //reset to incorporate a new policy
                        
                        for (int i = 0; i < policies.length; i++)
                        {    
                            if (policies[i].equals(CAP.trim()))
                            {    
                                ; // skip the policy 
                            } else //rewrite the rest
                            {    
                                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(policies[i]);
                                this.COLUMN_POLICY_CLASS_POLICIES = this.COLUMN_POLICY_CLASS_POLICIES.concat(" ");  
                            }
                        }
                        
                    }
                }    
            }
        }    
            /* obsolete
            
            int start = this.COLUMN_POLICY_CLASS_POLICIES.indexOf(CAP);
            int len = CAP.length();
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
            */          
    }
    
    
    public String produce_PCS_DB_DDL()
    {
        String Columns = "( " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + ", " + PolicyClassesTable.COLUMN_POLICY_CLASS_NAME + ", " + PolicyClassesTable.COLUMN_STATUS + ", ";
        
        for (int i = 0; i < PolicyClassesTable.LCS.length; i++)
            if (i != PolicyClassesTable.LCS.length - 1)
                Columns = Columns.concat(PolicyClassesTable.LCS[i].toString() + ", ");
            else 
                Columns = Columns.concat(PolicyClassesTable.LCS[i].toString() + " ) ");  
        
        return Columns;
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
               System.out.println("Policy_Classes_Table_Record.set_COLUMN_POLICY_CLASS_ID(): PCID string is not a number! ");
           }    
        }    
    }


    public String get_COLUMN_STATUS() {
        return this.COLUMN_STATUS;
    }

    public void set_COLUMN_STATUS(String COLUMN_STATUS) {
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
