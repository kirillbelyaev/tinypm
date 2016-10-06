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
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;
import java.io.Serializable;
import edu.csu.lpm.DB.interfaces.CapabilitiesClassesTable;

/**
 *
 * @author kirill
 */

public class CapabilitiesClassesTableRecord implements Record, Serializable
{
    private String COLUMN_CLASS_ID = ""; /* primary key */
    
    private String COLUMN_CLASS_NAME = "";
    
    private String COLUMN_CAPABILITIES = "";
    
    private String COLUMN_STATUS = "";
    
    private LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS[] = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.values();
    
    /* set to indicate which field to update when calling write_Policy_Classes_Table_Record() */
    private String UPDATE_COLUMN = "";
    

    public String get_UPDATE_COLUMN() 
    {
        return this.UPDATE_COLUMN;
    }
    
    public void set_UPDATE_COLUMN_to_CLASS_ID() 
    {
        this.UPDATE_COLUMN = CapabilitiesClassesTable.COLUMN_CLASS_ID;
    }
    
    public void set_UPDATE_COLUMN_to_CLASS_NAME() 
    {
        this.UPDATE_COLUMN = CapabilitiesClassesTable.COLUMN_CLASS_NAME;
    }
    
    public void set_UPDATE_COLUMN_to_CAPABILITIES() 
    {
        this.UPDATE_COLUMN = CapabilitiesClassesTable.COLUMN_CAPABILITIES;
    }


    public void set_UPDATE_COLUMN_to_STATUS() 
    {
        this.UPDATE_COLUMN = CapabilitiesClassesTable.COLUMN_STATUS;
    }
    
    public String get_COLUMN_CLASS_NAME() 
    {
        return this.COLUMN_CLASS_NAME;
    }

    public void set_COLUMN_CLASS_NAME(String COLUMN_CLASS_NAME) 
    {
        if (COLUMN_CLASS_NAME != null) this.COLUMN_CLASS_NAME = COLUMN_CLASS_NAME;
    }

    public LinuxCapabilitiesPolicyContainer.LinuxCapabilities[] get_LCS() 
    {
        return this.LCS;
    }

    public void set_LCS(LinuxCapabilitiesPolicyContainer.LinuxCapabilities[] LCS) 
    {
        this.LCS = LCS;
    }
    
    
    public int check_if_Capability_is_valid(String cap) 
    {   
        if (cap == null) return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (cap.isEmpty()) return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        for (LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS1 : LCS) 
        {
            if (LCS1.toString().equals(cap.trim())) 
            {
                //return LCS1.ordinal();  
                return RecordDAO.INDICATE_EXECUTION_SUCCESS; //Found
            }
        }
        
        return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS; //NOT found
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
                            if (this.check_if_Capability_is_valid(policies[i]) == RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS)
                                return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
                        
                        return RecordDAO.INDICATE_EXECUTION_SUCCESS; //ALL caps are valid
                    }
                    
                    return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
                }
                
                return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
        }
           
        return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;  //NOT found      
    }        
    
    
    public String get_COLUMN_CAPABILITIES() 
    {
        return this.COLUMN_CAPABILITIES;
    }
    
    
    public boolean check_if_COLUMN_CAPABILITIES_is_Empty() 
    {
        if (this.get_COLUMN_CAPABILITIES().isEmpty() ) return true;
        else return false;
    }
    

    public void set_COLUMN_CAPABILITIES(String COLUMN_CAPS) 
    {
        if (COLUMN_CAPS != null)
        { 
            if (this.check_if_Capabilities_are_valid(COLUMN_CAPS.trim()) != -1 )
                this.COLUMN_CAPABILITIES = COLUMN_CAPS.trim(); 
        }               
    }
    
    
    public void reset_COLUMN_CAPABILITIES() 
    {
        this.COLUMN_CAPABILITIES = "";
    }
    
    
    public void add_CAPABILITY(String CAP) 
    {
        String policies[] = null;
        if (CAP != null)
        {
            if (this.check_if_Capability_is_valid(CAP) == 0)
            {   
                policies = this.COLUMN_CAPABILITIES.trim().split(" ");
                
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
                        
                        this.COLUMN_CAPABILITIES = ""; //reset to incorporate a new policy
                        
                        if (policies[0].length() == 0) //if COLUMN_POLICY_CLASS_POLICIES is empty
                        {    
                            this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(CAP.trim());
                            this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(" ");
                        } else //if at least a single policy exists
                        {
                            for (int i = 0; i < policies.length; i++)
                            { 
                                this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(policies[i]);
                                this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(" ");        
                            }
                        
                            //now add the new policy
                            this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(CAP.trim());
                            this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(" ");

                        }                                      
                    }              
                }
            }
        }    
    }
    
    public void remove_CAPABILITY(String CAP) 
    {
        String policies[] = null;
        if (CAP != null)
        {
            if (this.check_if_Capability_is_valid(CAP) == 0)
            {
                
                policies = this.COLUMN_CAPABILITIES.trim().split(" ");
                
                if (policies != null)
                {    
                    if (policies.length > 0) //it is always the case since even the empty string is tokenized into a 1 element array
                    {
                        //System.out.println("check_if_Capabilities_are_valid: policies len is: " + policies.length);
                        this.COLUMN_CAPABILITIES = ""; //reset to incorporate a new policy
                        
                        for (int i = 0; i < policies.length; i++)
                        {    
                            if (policies[i].equals(CAP.trim()))
                            {    
                                ; // skip the policy 
                            } else //rewrite the rest
                            {    
                                this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(policies[i]);
                                this.COLUMN_CAPABILITIES = this.COLUMN_CAPABILITIES.concat(" ");  
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
    
    
    public String produce_CAPC_DB_DDL()
    {
        String Columns = "( " + CapabilitiesClassesTable.COLUMN_CLASS_ID + ", " + CapabilitiesClassesTable.COLUMN_CLASS_NAME + ", " + CapabilitiesClassesTable.COLUMN_STATUS + ", ";
        
        for (int i = 0; i < CapabilitiesClassesTable.LCS.length; i++)
            if (i != CapabilitiesClassesTable.LCS.length - 1)
                Columns = Columns.concat(CapabilitiesClassesTable.LCS[i].toString() + ", ");
            else 
                Columns = Columns.concat(CapabilitiesClassesTable.LCS[i].toString() + " ) ");  
        
        return Columns;
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
               System.out.println("Policy_Classes_Table_Record.set_COLUMN_POLICY_CLASS_ID(): PCID string is not a number! ");
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
