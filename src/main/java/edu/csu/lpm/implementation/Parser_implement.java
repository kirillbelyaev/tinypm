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

/* Parser interface represents the BL (Business Logic) layer commands available to the user shell */ 

package edu.csu.lpm.implementation;

import edu.csu.lpm.DB.DAO.RecordDAO;
import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.implementation.DB_Dispatcher;
import edu.csu.lpm.DB.implementation.RecordDAO_implement;
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;
import edu.csu.lpm.interfaces.Parser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirill
 */
public class Parser_implement implements Parser   
{
    
    private StringTokenizer tokenizer = null;
    private ArrayList <String> commandParameters = null;

    private CapabilitiesClassesTableRecord caprec = null;
    private CommunicativeClassesTableRecord comrec = null;
    private ComponentsTableRecord comprec = null;
    
    private DB_Dispatcher dd = null;
    private RecordDAO_implement db = null;
    
    private ArrayList <String> ResultOutput = null;
    private int resultSize = -1;
    
    private String ERROR_MESSAGE = null;
    private Enforcer_implement ei = null;
    

    public Parser_implement() 
    {
        this.dd = new DB_Dispatcher();
        this.ResultOutput = new ArrayList();
        this.ERROR_MESSAGE = new String();
        this.ei = new Enforcer_implement();
    }
     
    public String get_ERROR_MESSAGE() 
    {
        return this.ERROR_MESSAGE;
    }

    private void set_ERROR_MESSAGE(String m) 
    {
        if (m != null) this.ERROR_MESSAGE = m;
    }
    
    private int get_ResultSize() 
    {
        return this.resultSize;
    }

    private void set_ResultSize(int n) 
    {
        this.resultSize = n;
    }
    
    @Override
    public ArrayList get_ResultOutput() 
    {
        return this.ResultOutput;
    }
    
    private void set_ResultOutput(ArrayList r) 
    {
        if (r != null) this.ResultOutput = r;
    }
    
    private void refill_ResultOutput(String r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        this.ResultOutput.add(r);
    }
    
    
    private void refill_ResultOutput(ArrayList<String> r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        for (int i = 0; i < r.size(); i++)
            this.ResultOutput.add(r.get(i));        
    }
    
    
    private void refill_ResultOutput_with_CAPABILITIES_CLASS_ID_AND_NAME(CapabilitiesClassesTableRecord[] r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        String row = null;
        for (int i = 0; i < r.length; i++)
        {
            row = " CAPC_ID: ";
            row = row.concat(r[i].get_COLUMN_CLASS_ID());
            row = row.concat(" | ");
            row = row.concat(" Class Name: ");
            row = row.concat(r[i].get_COLUMN_CLASS_NAME());
            
            this.ResultOutput.add(row);
            
            row = null;
            //this.ResultOutput.add(r[i].get_COLUMN_POLICY_CLASS_NAME());
        }    
    }
    
    
    private void refill_Result_Output_with_all_Capabilities() 
    {
        LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS[] = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.values();
        
        if (LCS == null) return;
        
        this.ResultOutput.clear();
        
        this.ResultOutput.add("Linux Capabilities (consult the capabilities (7) manual page for an overview of Linux capabilities) are:");
        
        for (LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS1 : LCS) {
            this.ResultOutput.add(LCS1.toString());
            }
    }
    
    
    
    private int obtain_DB_Handler()
    {
        if (this.db == null) //minimize the number of calls - do it only once
        {    
            if (this.dd != null)
            {    
                try 
                {
                        this.db = this.dd.dispatch_DB_Access();
                } catch (SQLException sex) 
                {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, sex);
                }
            }
        }
        
        if (this.db != null) return INDICATE_EXECUTION_SUCCESS;
        else return INDICATE_CONDITIONAL_EXIT_STATUS;   
    }        
    
    
    @Override
    public int parse_and_execute_Command(String e)
    {
        if (e == null) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        if (this.obtain_DB_Handler() != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        this.set_ERROR_MESSAGE("");
        this.refill_ResultOutput("");
        
        e = e.trim();
        
        if (e.isEmpty() || e.equals("")) 
        {
            this.refill_ResultOutput("");
            this.set_ERROR_MESSAGE("");
            return INDICATE_EXECUTION_SUCCESS;
        } else if (e.equals("\n")) 
        {
            this.refill_ResultOutput("");
            this.set_ERROR_MESSAGE("");
            
        } else if (e.indexOf(LPM_COMMANDS.COUNT_CAPABILITIES_CLASSES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_COUNT_CAPABILITIES_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.COUNT_CAPABILITIES_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
               
        } else if (e.indexOf(LPM_COMMANDS.SHOW_CAPABILITIES_CLASSES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_CAPABILITIES_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                  this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_CAPABILITIES_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        
        } else if (e.indexOf(LPM_COMMANDS.CREATE_CAPABILITIES_CLASS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_CREATE_CAPABILITIES_CLASS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.CREATE_CAPABILITIES_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            } 
            
        } else if (e.indexOf(LPM_COMMANDS.ADD_CAPABILITIES_CLASS_CAPABILITY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_ADD_CAPABILITIES_CLASS_CAPABILITY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.ADD_CAPABILITIES_CLASS_CAPABILITY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
        } else if (e.indexOf(LPM_COMMANDS.SHOW_CAPABILITIES_CLASS_CAPABILITIES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_CAPABILITIES_CLASS_CAPABILITIES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_CAPABILITIES_CLASS_CAPABILITIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(LPM_COMMANDS.REMOVE_CAPABILITIES_CLASS_CAPABILITY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_REMOVE_CAPABILITIES_CLASS_CAPABILITY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.REMOVE_CAPABILITIES_CLASS_CAPABILITY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        }  else if (e.indexOf(LPM_COMMANDS.SHOW_CAPABILITIES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            this.parse_and_execute_SHOW_CAPABILITIES(e);
        } else if (e.indexOf(LPM_COMMANDS.COUNT_CAPABILITIES_CLASS_COMPONENTS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_COUNT_CAPABILITIES_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.COUNT_CAPABILITIES_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            } 
        } else if (e.indexOf(LPM_COMMANDS.SHOW_CAPABILITIES_CLASS_COMPONENTS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_CAPABILITIES_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_CAPABILITIES_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(LPM_COMMANDS.MOVE_COMPONENT_TO_CAPABILITIES_CLASS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_MOVE_COMPONENT_TO_CAPABILITIES_CLASS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.MOVE_COMPONENT_TO_CAPABILITIES_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }                         
        } else if (e.indexOf(LPM_COMMANDS.HELP.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            this.parse_and_execute_HELP(e);
        } else if (e.indexOf(LPM_COMMANDS.EXIT.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            return  Parser.INDICATE_IMMEDIATE_EXIT_STATUS;
            
        /* add support for communicative classes */
        } else if (e.indexOf(LPM_COMMANDS.COUNT_COMMUNICATIVE_CLASSES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_COUNT_COMMUNICATIVE_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.COUNT_COMMUNICATIVE_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }     
        
        } else if (e.indexOf(LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASSES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_COMMUNICATIVE_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                  this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_COMMUNICATIVE_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        
        } else if (e.indexOf(LPM_COMMANDS.CREATE_COMMUNICATIVE_CLASS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_CREATE_COMMUNICATIVE_CLASS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.CREATE_COMMUNICATIVE_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            } 
        } else if (e.indexOf(LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASS_COMPONENTS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_COMMUNICATIVE_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_COMMUNICATIVE_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(LPM_COMMANDS.COUNT_COMMUNICATIVE_CLASS_COMPONENTS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_COUNT_COMMUNICATIVE_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.COUNT_COMMUNICATIVE_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
        } else if (e.indexOf(LPM_COMMANDS.MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
        } else if (e.indexOf(LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
        } else if (e.indexOf(LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(LPM_COMMANDS.ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(LPM_COMMANDS.ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }   
        } else if (e.indexOf(LPM_COMMANDS.REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
        } else if (e.indexOf(LPM_COMMANDS.REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
            
            
        /* print out the help message */    
        } else
        {
            this.parse_and_execute_HELP(e);
        }    
        
        return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private void parse_and_execute_HELP(String e)
    {
        if (e == null || e.isEmpty()) return;
        this.set_ResultSize(0);
        this.refill_ResultOutput("");
        this.set_ERROR_MESSAGE(HELP_MESSAGE);
    }
    
    
    private void parse_and_execute_SHOW_CAPABILITIES(String e)
    {
        if (e == null || e.isEmpty()) return;
        this.set_ResultSize(0);
        this.refill_Result_Output_with_all_Capabilities();
    }
    
    
    
    private int tokenize_and_build_command_parameters(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        int count = -1;
        
        if (this.commandParameters == null)
        {    
             this.commandParameters = new ArrayList <String>();
        } else
        {
            this.commandParameters.clear();
        }    
        
        
        /* initialize the records only once and then reuse in other methods to save memory */ 
        if (this.caprec == null) this.caprec = new CapabilitiesClassesTableRecord();
        
        if (this.comrec == null) this.comrec = new CommunicativeClassesTableRecord();
        
        if (this.comprec == null) this.comprec = new ComponentsTableRecord();
        
        this.tokenizer = new StringTokenizer(e, " ");
        
        count = this.tokenizer.countTokens(); //obtain the number of tokens before cycling through them
        
        this.tokenizer.nextToken();//skip the 1st token which is the command itself
        
        while (this.tokenizer.hasMoreTokens())
        {
            String field = this.tokenizer.nextToken();
            field = field.trim();
            this.commandParameters.add(field);
        }
        
        return count;
    }
    
    /* extended methods */
    
    private Integer parse_and_execute_COUNT_CAPABILITIES_CLASSES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        Integer count = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1)
        {    
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    count = this.db.count_Distinct_Capabilities_Classes_Table_Records_on_CID();
                    this.set_ResultSize(count);
                    this.refill_ResultOutput(count.toString());
                    return INDICATE_EXECUTION_SUCCESS;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }        
    
    
    private Integer parse_and_execute_SHOW_CAPABILITIES_CLASSES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        CapabilitiesClassesTableRecord[] ra = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1)
        { 
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    ra = this.db.read_Capabilities_Classes_Table_Records_On_All_Classes();
                    if (ra != null)
                    {    
                        this.set_ResultSize(ra.length);
                        this.refill_ResultOutput_with_CAPABILITIES_CLASS_ID_AND_NAME(ra);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else return RecordDAO.EMPTY_RESULT;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private Integer parse_and_execute_CREATE_CAPABILITIES_CLASS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.caprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
       
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.caprec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    this.caprec.set_COLUMN_CLASS_NAME(this.commandParameters.get(1));
                    
                    this.caprec.reset_COLUMN_CAPABILITIES(); /* reset policies */
                    
                    this.caprec.set_UPDATE_COLUMN_to_CLASS_NAME(); /* indicate the update column */      
                }    
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Capabilities_Classes_Table_Record(this.caprec) != INDICATE_EXECUTION_SUCCESS) 
                    {  
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                    else
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        return INDICATE_EXECUTION_SUCCESS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private ArrayList<String> get_CAPABILITIES_CLASS_CAPABILITIES(String pcid)
    {
        CapabilitiesClassesTableRecord pcr [] = null;
        ArrayList<String> caps = null;

        if (pcid == null || pcid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.caprec == null) return null;
        
        this.caprec.set_COLUMN_CLASS_ID(pcid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                pcr = this.db.read_Capabilities_Classes_Table_Records_On_CID(this.caprec);  
            }    
        } catch (RecordDAO_Exception rex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }
        
        if (pcr != null)
        {    
            caps = new ArrayList<String>();
            for (int i = 0; i < pcr.length; i++)
            {
                /* let us make sure that a policy class record does have policies */
                if (!pcr[i].check_if_COLUMN_CAPABILITIES_is_Empty())
                    caps.add(pcr[i].get_COLUMN_CAPABILITIES()); /* add non-empty
                policies only */
            }    
        }
        
        /* let us ensure that we return only non-empty policies */
        if (caps != null)
            if ( !caps.isEmpty() ) return caps;
            else return null;
        
        return null; /* return NULL by default */
    }
    
    private int check_if_Capability_Exists (String pcid, String p)
    {
        if (pcid == null || pcid.isEmpty() || p == null || p.isEmpty()) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> caps = this.get_CAPABILITIES_CLASS_CAPABILITIES(pcid.trim());
        
        if (caps != null)
            for (int i = 0; i < caps.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (caps.get(i).contains(p.trim())) return INDICATE_EXECUTION_SUCCESS;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private ArrayList<String>  prepare_EnforcerParameters (String pcid, String app)
    {
        if (pcid == null || pcid.isEmpty() || app == null || app.isEmpty()) return null;
        
        ArrayList<String> caps = this.get_CAPABILITIES_CLASS_CAPABILITIES(pcid.trim());
        
        String policies[] = null;
        
        /* by now we know that if get_CAPABILITIES_CLASS_CAPABILITIES() returns null 
        - that means that no policies exist. That is because we already 
        ensure that pcid parameter should not be null in the first place,
        otherwise this method will terminate immediately. */
        if (caps != null)
        { 
            /* obtain the policies in the 1st element */
            policies = caps.get(0).trim().split(" "); 
            
            /* let's reuse the list */
            caps.clear();
            
            for (int i = 0; i < policies.length; i++)
                caps.add(policies[i].trim());
                        
            caps.add(app.trim()); //add the application entry last
        } else /* no policies exist for the app */
        {
            caps = new ArrayList<String>();
            caps.add(app);
        }    
        
        return caps;
    }
    
    
    private Integer parse_and_execute_ADD_CAPABILITIES_CLASS_CAPABILITY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.caprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.caprec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    this.caprec.set_COLUMN_CAPABILITIES(this.commandParameters.get(1));
                    
                    this.caprec.set_COLUMN_CLASS_NAME(""); /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    this.caprec.set_UPDATE_COLUMN_to_CAPABILITIES(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.check_if_Capability_Exists(this.caprec.get_COLUMN_CLASS_ID(), this.caprec.get_COLUMN_CAPABILITIES()) == INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_CAPABILITY_EXISTS_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
                
            
            ArrayList<String> caps = this.get_CAPABILITIES_CLASS_CAPABILITIES(this.caprec.get_COLUMN_CLASS_ID().trim());
            
            if (caps != null) 
            {  /* reset the policies in pc record */  
                this.caprec.set_COLUMN_CAPABILITIES(caps.get(0)); 
                this.caprec.add_CAPABILITY(this.commandParameters.get(1)); /* do it once more */
            
            } else this.caprec.add_CAPABILITY(this.commandParameters.get(1)); /* if no policies exist */       

            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Capabilities_Classes_Table_Record(this.caprec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        
                        /* after updating policies for a policy class in the DB layer we
                        finally proceed to the enforcer section */
                        ArrayList<String> apps = this.get_CAPABILITIES_CLASS_COMPONENTS(this.caprec.get_COLUMN_CLASS_ID().trim());

                        /* no components - no enforcement! */
                        if (apps != null)
                        {
                            /* Time to call the enforcer after proceeding to the DB layer */
                            /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */
                            
                            /* execute enforcer for every app that belongs to a policy class */
                            for (int i = 0; i < apps.size(); i++)
                            {    
                                if (this.ei.build_EnforcerCMD_Parameters(this.prepare_EnforcerParameters(this.caprec.get_COLUMN_CLASS_ID(), apps.get(i))) != INDICATE_EXECUTION_SUCCESS)
                                {   
                                    this.set_ERROR_MESSAGE(LPM_ERRORS.Enforcer_CMD_Parameters_ERROR.toString());
                                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                                }    
                                if (this.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS)
                                {
                                    this.set_ERROR_MESSAGE(LPM_ERRORS.Enforcer_execute_CMD_ERROR.toString());
                                    return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
                                }    
                            }
                        }
                     
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private Integer parse_and_execute_SHOW_CAPABILITIES_CLASS_CAPABILITIES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.caprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> caps = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.caprec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    caps = this.get_CAPABILITIES_CLASS_CAPABILITIES(this.caprec.get_COLUMN_CLASS_ID().trim());
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (caps != null)
            {
                this.set_ResultSize(caps.size());
                this.refill_ResultOutput(caps);
                return INDICATE_EXECUTION_SUCCESS;
            } else return RecordDAO.EMPTY_RESULT;
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
    }

    private Integer parse_and_execute_REMOVE_CAPABILITIES_CLASS_CAPABILITY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.caprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.caprec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    this.caprec.set_COLUMN_CAPABILITIES(this.commandParameters.get(1));
                    this.caprec.set_UPDATE_COLUMN_to_CAPABILITIES(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.check_if_Capability_Exists(this.caprec.get_COLUMN_CLASS_ID(), this.caprec.get_COLUMN_CAPABILITIES()) == INDICATE_CONDITIONAL_EXIT_STATUS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_CAPABILITY_DOES_NOT_EXIST_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if
            policy does not exist */
            }
                
            ArrayList<String> caps = this.get_CAPABILITIES_CLASS_CAPABILITIES(this.caprec.get_COLUMN_CLASS_ID().trim());
            
            if (caps != null) 
            {  /* reset the policies in pc record */  
                this.caprec.set_COLUMN_CAPABILITIES(caps.get(0)); 
                this.caprec.remove_CAPABILITY(this.commandParameters.get(1)); /* do it once more */
            
            } else 
            {  
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_NO_CAPABILITIES_EXIST_ERROR.toString());
                return RecordDAO.EMPTY_RESULT; /* if no policies exist */   
            }
            
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Capabilities_Classes_Table_Record(this.caprec) == INDICATE_EXECUTION_SUCCESS) 
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        
                        /* after updating policies for a policy class in the DB layer we
                        finally proceed to the enforcer section */
                        ArrayList<String> apps = this.get_CAPABILITIES_CLASS_COMPONENTS(this.caprec.get_COLUMN_CLASS_ID().trim());

                        /* no components - no enforcement! */
                        if (apps != null)
                        {
                            /* Time to call the enforcer after proceeding to the DB layer */
                            /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */
                            
                            /* execute enforcer for every app that belongs to a policy class */
                            for (int i = 0; i < apps.size(); i++)
                            {    
                                if (this.ei.build_EnforcerCMD_Parameters(this.prepare_EnforcerParameters(this.caprec.get_COLUMN_CLASS_ID(), apps.get(i))) != INDICATE_EXECUTION_SUCCESS)
                                {
                                    this.set_ERROR_MESSAGE(LPM_ERRORS.Enforcer_CMD_Parameters_ERROR.toString());
                                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                                }

                                if (this.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS)
                                {
                                    this.set_ERROR_MESSAGE(LPM_ERRORS.Enforcer_execute_CMD_ERROR.toString());
                                    return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
                                }    
                            }
                        }
                        
                        return INDICATE_EXECUTION_SUCCESS;
                    }
                    else
                    {
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }       
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    /* components table operations */
    
    private Integer parse_and_execute_COUNT_CAPABILITIES_CLASS_COMPONENTS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        Integer count = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        { 
            
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                {    
                    this.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.commandParameters.get(0));
                }    
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;  
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    count = this.db.count_Components_Table_Records_on_CAPCID(this.comprec);
                    this.set_ResultSize(count);
                    this.refill_ResultOutput(count.toString());
                    return INDICATE_EXECUTION_SUCCESS;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    } 
    
    
    private ArrayList<String> get_CAPABILITIES_CLASS_COMPONENTS(String pcid)
    {
        ComponentsTableRecord[] appsr = null;
        ArrayList<String> apps = null;

        if (pcid == null || pcid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return null;
        
        this.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(pcid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                appsr = this.db.read_Components_Table_Records_On_CAPCID(this.comprec);  
            }    
        } catch (RecordDAO_Exception rex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }
        
        if (appsr != null)
        {    
            apps = new ArrayList<String>();
            for (int i=0; i < appsr.length; i++)
                apps.add(appsr[i].get_COLUMN_COMPONENT_PATH_ID());
        }
        
        return apps;
    }
    
    
    private Integer parse_and_execute_SHOW_CAPABILITIES_CLASS_COMPONENTS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> apps = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.commandParameters.get(0));
                    apps = this.get_CAPABILITIES_CLASS_COMPONENTS(this.comprec.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID().trim());
                } 
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (apps != null)
            {
                this.set_ResultSize(apps.size());
                this.refill_ResultOutput(apps);
                return INDICATE_EXECUTION_SUCCESS;
            } else return RecordDAO.EMPTY_RESULT;
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
    }

    private Integer parse_and_execute_MOVE_COMPONENT_TO_CAPABILITIES_CLASS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
       
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                { 
                    this.comprec.set_COLUMN_COMPONENT_PATH_ID(this.commandParameters.get(0));
                    this.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.commandParameters.get(1));
                    this.comprec.set_UPDATE_COLUMN_to_COMPONENT_CAPABILITIES_CLASS_ID(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            
            /* Time to call the enforcer before proceeding to the DB layer */
            /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */ 
            if (this.ei.build_EnforcerCMD_Parameters(this.prepare_EnforcerParameters(this.comprec.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(), this.comprec.get_COLUMN_COMPONENT_PATH_ID())) != INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.Enforcer_CMD_Parameters_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
            
            if (this.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS)
            {   
                this.set_ERROR_MESSAGE(LPM_ERRORS.Enforcer_execute_CMD_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
            }
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Components_Table_Record(this.comprec) == INDICATE_EXECUTION_SUCCESS) {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        return INDICATE_EXECUTION_SUCCESS;
                    } else
                    { 
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
            
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    /* add support for communicative classes */
    
    private Integer parse_and_execute_COUNT_COMMUNICATIVE_CLASSES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        Integer count = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1)
        {    
            try 
            {//execute the db layer
                if (this.db != null)
                {   
                    count = this.db.count_Distinct_Communicative_Classes_Table_Records_on_CID();
                    this.set_ResultSize(count);
                    this.refill_ResultOutput(count.toString());
                    return INDICATE_EXECUTION_SUCCESS;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private Integer parse_and_execute_SHOW_COMMUNICATIVE_CLASSES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        CommunicativeClassesTableRecord[] ra = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1)
        { 
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    ra = this.db.read_Communicative_Classes_Table_Records_On_All_Classes();
                    if (ra != null)
                    {    
                        this.set_ResultSize(ra.length);
                        this.refill_ResultOutput_with_COMMUNICATIVE_CLASS_ID_AND_NAME(ra);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else return RecordDAO.EMPTY_RESULT;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private void refill_ResultOutput_with_COMMUNICATIVE_CLASS_ID_AND_NAME(CommunicativeClassesTableRecord[] r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        String row = null;
        for (int i = 0; i < r.length; i++)
        {
            row = " COMC_ID: ";
            row = row.concat(r[i].get_COLUMN_CLASS_ID());
            row = row.concat(" | ");
            row = row.concat(" Class Name: ");
            row = row.concat(r[i].get_COLUMN_CLASS_NAME());
            
            this.ResultOutput.add(row);
            
            row = null;
        }    
    }
    
    private Integer parse_and_execute_CREATE_COMMUNICATIVE_CLASS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
       
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    /* check the validity of input */
                    if (this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS)
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    this.comrec.set_COLUMN_CLASS_NAME(this.commandParameters.get(1));
                    
                    //this.caprec.reset_COLUMN_CAPABILITIES(); /* reset policies */
                    
                    this.comrec.set_UPDATE_COLUMN_to_CLASS_NAME(); /* indicate the update column */      
                }    
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Communicative_Classes_Table_Record(this.comrec) != INDICATE_EXECUTION_SUCCESS) 
                    {  
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                    else
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        return INDICATE_EXECUTION_SUCCESS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private Integer parse_and_execute_SHOW_COMMUNICATIVE_CLASS_COMPONENTS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> components = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(this.commandParameters.get(0));
                    components = this.get_COMMUNICATIVE_CLASS_COMPONENTS(this.comprec.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID().trim());
                } 
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (components != null)
            {
                this.set_ResultSize(components.size());
                this.refill_ResultOutput(components);
                return INDICATE_EXECUTION_SUCCESS;
            } else return RecordDAO.EMPTY_RESULT;
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
    }
    
    
    private ArrayList<String> get_COMMUNICATIVE_CLASS_COMPONENTS(String pcid)
    {
        ComponentsTableRecord[] compsr = null;
        ArrayList<String> components = null;

        if (pcid == null || pcid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return null;
        
        this.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(pcid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                compsr = this.db.read_Components_Table_Records_On_COMCID(this.comprec);  
            }    
        } catch (RecordDAO_Exception rex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }
        
        if (compsr != null)
        {    
            components = new ArrayList<String>();
            for (ComponentsTableRecord compsr1 : compsr) 
            {
                components.add(compsr1.get_COLUMN_COMPONENT_PATH_ID());
            }
        }
        
        return components;
    }
    
    private Integer parse_and_execute_COUNT_COMMUNICATIVE_CLASS_COMPONENTS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        Integer count = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        {          
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                {    
                    this.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(this.commandParameters.get(0));
                }    
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;  
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    count = this.db.count_Components_Table_Records_on_COMCID(this.comprec);
                    this.set_ResultSize(count);
                    this.refill_ResultOutput(count.toString());
                    return INDICATE_EXECUTION_SUCCESS;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private Integer parse_and_execute_MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
       
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                { 
                    this.comprec.set_COLUMN_COMPONENT_PATH_ID(this.commandParameters.get(0));
                    this.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.commandParameters.get(1));
                    this.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(this.commandParameters.get(1));
                    this.comprec.set_UPDATE_COLUMN_to_COMPONENT_COMMUNICATIVE_CLASS_ID(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            /* No need for immediate enforcement since communicative policies
            are only checked upon request of the TSC. In contrast to capabilities
            that are tied to the executable, communicative policies represent the
            realistic access control dimension that is only checked upon request */
                    
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Components_Table_Record(this.comprec) == INDICATE_EXECUTION_SUCCESS) 
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        return INDICATE_EXECUTION_SUCCESS;
                    } else
                    { 
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
            
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private Integer parse_and_execute_SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> policies = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    policies = this.get_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(this.comrec.get_COLUMN_CLASS_ID().trim());
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (policies != null)
            {
                this.set_ResultSize(policies.size());
                this.refill_ResultOutput(policies);
                return INDICATE_EXECUTION_SUCCESS;
            } else return RecordDAO.EMPTY_RESULT;
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
    }
    
    
    private ArrayList<String> get_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(String cid)
    {
        CommunicativeClassesTableRecord[] comr = null;
        ArrayList<String> policies = null;

        if (cid == null || cid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return null;
        
        this.comrec.set_COLUMN_CLASS_ID(cid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                comr = this.db.read_Communicative_Classes_Table_Records_On_CID(this.comrec);  
            }    
        } catch (RecordDAO_Exception rex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }
        
        if (comr != null)
        {    
            policies = new ArrayList<String>();
            for (int i = 0; i < comr.length; i++)
            {
                /* let us make sure that a policy class record does have policies */
                if (!comr[i].check_if_COLUMN_COLLABORATION_RECORD_is_Empty())
                    policies.add(comr[i].get_COLUMN_COLLABORATION_RECORD()); /* add non-empty
                policies only */
            }    
        }
        
        /* let us ensure that we return only non-empty policies */
        if (policies != null)
            if ( !policies.isEmpty() ) return policies;
            else return null;
        
        return null; /* return NULL by default */
    }
    
    private Integer parse_and_execute_SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> policies = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    policies = this.get_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(this.comrec.get_COLUMN_CLASS_ID().trim());
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (policies != null)
            {
                this.set_ResultSize(policies.size());
                this.refill_ResultOutput(policies);
                return INDICATE_EXECUTION_SUCCESS;
            } else return RecordDAO.EMPTY_RESULT;
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
    }
    
    private ArrayList<String> get_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(String cid)
    {
        CommunicativeClassesTableRecord[] comr = null;
        ArrayList<String> policies = null;

        if (cid == null || cid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return null;
        
        this.comrec.set_COLUMN_CLASS_ID(cid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                comr = this.db.read_Communicative_Classes_Table_Records_On_CID(this.comrec);  
            }    
        } catch (RecordDAO_Exception rex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }
        
        if (comr != null)
        {    
            policies = new ArrayList<String>();
            for (int i = 0; i < comr.length; i++)
            {
                /* let us make sure that a policy class record does have policies */
                if (!comr[i].check_if_COLUMN_COORDINATION_RECORD_is_Empty())
                    policies.add(comr[i].get_COLUMN_COORDINATION_RECORD()); /* add non-empty
                policies only */
            }    
        }
        
        /* let us ensure that we return only non-empty policies */
        if (policies != null)
            if ( !policies.isEmpty() ) return policies;
            else return null;
        
        return null; /* return NULL by default */
    }
    
    
    private Integer parse_and_execute_ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {  
                    /* check the validity of input */
                    if (this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS)
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    /* check the validity of input */
                    if (this.comrec.set_COLUMN_COLLABORATION_RECORD(this.commandParameters.get(1), this.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS)
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    
                    this.comrec.set_COLUMN_CLASS_NAME(""); /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //this.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            /* we can add a policy record only if such a component is associated
            with a particular class - can not just create a record with a random
            component */
            if (this.check_if_Component_belongs_to_Class(this.commandParameters.get(0), this.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if component is not associated with a class */
            }    
            
            if (this.check_if_CollaborationPolicy_Exists(this.comrec.get_COLUMN_CLASS_ID(), this.comrec.get_COLUMN_COLLABORATION_RECORD()) == INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COLLABORATION_POLICY_EXISTS_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_CommunicativeClassesTableRecord(this.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                                           
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private int check_if_CollaborationPolicy_Exists (String cid, String p)
    {
        if (cid == null || cid.isEmpty() || p == null || p.isEmpty()) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> policies = this.get_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(cid.trim());
        
        if (policies != null)
            for (int i = 0; i < policies.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (policies.get(i).contains(p.trim())) return INDICATE_EXECUTION_SUCCESS;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private int check_if_Component_belongs_to_Class (String cid, String component)
    {
        if (cid == null || cid.isEmpty() || component == null || component.isEmpty()) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> components = this.get_COMMUNICATIVE_CLASS_COMPONENTS(cid.trim());
        
        if (components != null)
            for (int i = 0; i < components.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (components.get(i).contains(component.trim())) return INDICATE_EXECUTION_SUCCESS;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private Integer parse_and_execute_ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    /* check the validity of input */
                    if (this.comrec.set_COLUMN_COORDINATION_RECORD(this.commandParameters.get(1), this.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS)
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    
                    this.comrec.set_COLUMN_CLASS_NAME(""); /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //this.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            /* we can add a policy record only if such a component is associated
            with a particular class - can not just create a record with a random
            component */
            /* both components should belong to the same class */
            if (this.check_if_Component_belongs_to_Class(this.commandParameters.get(0), this.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if component is not associated with a class */
            }
            
            /* we can add a policy record only if such a component is associated
            with a particular class - can not just create a record with a random
            component */
            /* both components should belong to the same class */
            if (this.check_if_Component_belongs_to_Class(this.commandParameters.get(0), this.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if component is not associated with a class */
            }
            
            if (this.check_if_CoordinationPolicy_Exists(this.comrec.get_COLUMN_CLASS_ID(), this.comrec.get_COLUMN_COORDINATION_RECORD()) == INDICATE_EXECUTION_SUCCESS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COORDINATION_POLICY_EXISTS_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_CommunicativeClassesTableRecord(this.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                                           
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private int check_if_CoordinationPolicy_Exists (String cid, String p)
    {
        if (cid == null || cid.isEmpty() || p == null || p.isEmpty()) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> policies = this.get_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(cid.trim());
        
        if (policies != null)
            for (int i = 0; i < policies.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (policies.get(i).contains(p.trim())) return INDICATE_EXECUTION_SUCCESS;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private Integer parse_and_execute_REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    /* check the validity of input */
                    if (this.comrec.set_COLUMN_COLLABORATION_RECORD(this.commandParameters.get(1), this.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS)
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    
                    //this.comrec.set_COLUMN_CLASS_NAME(""); 
                    /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //this.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.check_if_CollaborationPolicy_Exists(this.comrec.get_COLUMN_CLASS_ID(), this.comrec.get_COLUMN_COLLABORATION_RECORD()) == INDICATE_CONDITIONAL_EXIT_STATUS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COLLABORATION_POLICY_DOES_NOT_EXIST_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.delete_Communicative_Classes_Table_Record_On_CollaborationRecord_And_CID(this.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                                           
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_DELETE_RECORD_ERROR.toString());
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private Integer parse_and_execute_REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.comrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.comrec.set_COLUMN_CLASS_ID(this.commandParameters.get(0));
                    /* check the validity of input */
                    if (this.comrec.set_COLUMN_COORDINATION_RECORD(this.commandParameters.get(1), this.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS)
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    
                    //this.comrec.set_COLUMN_CLASS_NAME(""); 
                    /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //this.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.check_if_CoordinationPolicy_Exists(this.comrec.get_COLUMN_CLASS_ID(), this.comrec.get_COLUMN_COORDINATION_RECORD()) == INDICATE_CONDITIONAL_EXIT_STATUS)
            {
                this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_COORDINATION_POLICY_DOES_NOT_EXIST_ERROR.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.delete_Communicative_Classes_Table_Record_On_CoordinationRecord_And_CID(this.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                                           
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        this.set_ERROR_MESSAGE(LPM_ERRORS.DB_Layer_DELETE_RECORD_ERROR.toString());
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
}
