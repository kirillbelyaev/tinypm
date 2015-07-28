/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

/* Parser interface represents the BL (Business Logic) layer commands available to the user shell*/ 

package edu.csu.tinypm.implementation;

import edu.csu.tinypm.DB.DAO.RecordDAO;
import edu.csu.tinypm.DB.DTO.AppsTableRecord;
import edu.csu.tinypm.DB.DTO.PolicyClassesTableRecord;
import edu.csu.tinypm.DB.exceptions.RecordDAO_Exception;
import edu.csu.tinypm.DB.implementation.DB_Dispatcher;
import edu.csu.tinypm.DB.implementation.RecordDAO_implement;
import edu.csu.tinypm.interfaces.LinuxCapabilitiesPolicyContainer;


import edu.csu.tinypm.interfaces.Parser;
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

    private PolicyClassesTableRecord pcrec = null;
    private AppsTableRecord apprec = null;
    
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
     
    public String get_ERROR_MESSAGE() {
        return this.ERROR_MESSAGE;
    }

    private void set_ERROR_MESSAGE(String m) {
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
    
    
    private void refill_ResultOutput_with_POLICY_CLASS_ID_AND_NAME(PolicyClassesTableRecord[] r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        String row = null;
        for (int i = 0; i < r.length; i++)
        {
            row = " PCID: ";
            row = row.concat(r[i].get_COLUMN_POLICY_CLASS_ID());
            row = row.concat(" | ");
            row = row.concat(" Class Name: ");
            row = row.concat(r[i].get_COLUMN_POLICY_CLASS_NAME());
            
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
            
        } else if (e.indexOf(PM_COMMANDS.COUNT_POLICY_CLASSES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_COUNT_POLICY_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.COUNT_POLICY_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }    
               
        } else if (e.indexOf(PM_COMMANDS.SHOW_POLICY_CLASSES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_POLICY_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                  this.set_ERROR_MESSAGE(PM_ERRORS.SHOW_POLICY_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        
        } else if (e.indexOf(PM_COMMANDS.CREATE_POLICY_CLASS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_CREATE_POLICY_CLASS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.CREATE_POLICY_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            } 
            
        } else if (e.indexOf(PM_COMMANDS.ADD_POLICY_CLASS_POLICY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_ADD_POLICY_CLASS_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.ADD_POLICY_CLASS_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
        } else if (e.indexOf(PM_COMMANDS.SHOW_POLICY_CLASS_POLICIES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_POLICY_CLASS_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.SHOW_POLICY_CLASS_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(PM_COMMANDS.REMOVE_POLICY_CLASS_POLICY.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_REMOVE_POLICY_CLASS_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.REMOVE_POLICY_CLASS_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        }  else if (e.indexOf(PM_COMMANDS.SHOW_CAPABILITIES.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            this.parse_and_execute_SHOW_CAPABILITIES(e);
            
        
        } else if (e.indexOf(PM_COMMANDS.COUNT_POLICY_CLASS_APPS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_COUNT_POLICY_CLASS_APPS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.COUNT_POLICY_CLASS_APPS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            } 
        } else if (e.indexOf(PM_COMMANDS.SHOW_POLICY_CLASS_APPS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_SHOW_POLICY_CLASS_APPS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.SHOW_POLICY_CLASS_APPS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
        } else if (e.indexOf(PM_COMMANDS.MOVE_APP_TO_POLICY_CLASS.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            if (this.parse_and_execute_MOVE_APP_TO_POLICY_CLASS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.set_ERROR_MESSAGE(PM_ERRORS.MOVE_APP_TO_POLICY_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }                         
        } else if (e.indexOf(PM_COMMANDS.HELP.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            this.parse_and_execute_HELP(e);
        } else if (e.indexOf(PM_COMMANDS.EXIT.toString()) == INDICATE_EXECUTION_SUCCESS) 
        {
            return  Parser.INDICATE_IMMEDIATE_EXIT_STATUS;
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
        this.set_ERROR_MESSAGE(Parser.HELP_MESSAGE);
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
        if (this.pcrec == null) this.pcrec = new PolicyClassesTableRecord();
        
        if (this.apprec == null) this.apprec = new AppsTableRecord();
        
        this.tokenizer = new StringTokenizer(e, " ");
        
        count = this.tokenizer.countTokens(); //obtain the number of tokens before cycling through them
        
        this.tokenizer.nextToken();//skip the 1st token
        
        while (this.tokenizer.hasMoreTokens())
        {
            String field = this.tokenizer.nextToken();
            field = field.trim();
            this.commandParameters.add(field);
        }
        
        return count;
    }
    
    /* extended methods */
    
    private Integer parse_and_execute_COUNT_POLICY_CLASSES(String e)
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
                    count = this.db.count_Distinct_Policy_Classes_Table_Records_on_PCID();
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
    
    
    private Integer parse_and_execute_SHOW_POLICY_CLASSES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        PolicyClassesTableRecord[] ra = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1)
        { 
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    ra = this.db.read_Policy_Classes_Table_Records_On_All_Classes();
                    if (ra != null)
                    {    
                        this.set_ResultSize(ra.length);
                        this.refill_ResultOutput_with_POLICY_CLASS_ID_AND_NAME(ra);
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
    
    
    private Integer parse_and_execute_CREATE_POLICY_CLASS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.pcrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.pcrec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(0));
                    this.pcrec.set_COLUMN_POLICY_CLASS_NAME(this.commandParameters.get(1));
                    this.pcrec.set_UPDATE_COLUMN_to_POLICY_CLASS_NAME(); /* indicate the update column */
                }    
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Policy_Classes_Table_Record(this.pcrec) != INDICATE_EXECUTION_SUCCESS) 
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
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
    
    
    private ArrayList<String> get_POLICY_CLASS_POLICIES(String pcid)
    {
        PolicyClassesTableRecord pcr [] = null;
        ArrayList<String> caps = null;

        if (pcid == null || pcid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.pcrec == null) return null;
        
        this.pcrec.set_COLUMN_POLICY_CLASS_ID(pcid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                pcr = this.db.read_Policy_Classes_Table_Records_On_PCID(this.pcrec);  
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
                if (!pcr[i].check_if_COLUMN_POLICY_CLASS_POLICIES_is_Empty())
                    caps.add(pcr[i].get_COLUMN_POLICY_CLASS_POLICIES()); /* add non-empty
                policies only */
            }    
        }
        
        /* let us ensure that we return only non-empty policies */
        if (caps != null)
            if ( !caps.isEmpty() ) return caps;
            else return null;
        
        return null; /* return NULL by default */
    }
    
    private int check_if_Policy_Exists (String pcid, String p)
    {
        if (pcid == null || pcid.isEmpty() || p == null || p.isEmpty()) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> caps = this.get_POLICY_CLASS_POLICIES(pcid.trim());
        
        if (caps != null)
            for (int i = 0; i < caps.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (caps.get(i).contains(p.trim())) return INDICATE_EXECUTION_SUCCESS;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    private ArrayList<String>  prepare_EnforcerParameters (String pcid, String app)
    {
        if (pcid == null || pcid.isEmpty() || app == null || app.isEmpty()) return null;
        
        ArrayList<String> caps = this.get_POLICY_CLASS_POLICIES(pcid.trim());
        
        String policies[] = null;
        
        if (caps != null)
        { 
            /* obtain the policies in the 1st element */
            policies = caps.get(0).trim().split(" "); 
            
            /* let's reuse the list */
            caps.clear();
            
            for (int i = 0; i < policies.length; i++)
                caps.add(policies[i].trim());
                        
            caps.add(app.trim()); //add the application entry last
        }    
        
        return caps;
    }
    
    
    private Integer parse_and_execute_ADD_POLICY_CLASS_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.pcrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.pcrec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(0));
                    this.pcrec.set_COLUMN_POLICY_CLASS_POLICIES(this.commandParameters.get(1));
                    this.pcrec.set_UPDATE_COLUMN_to_POLICY_CLASS_POLICIES(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.check_if_Policy_Exists(this.pcrec.get_COLUMN_POLICY_CLASS_ID(), this.pcrec.get_COLUMN_POLICY_CLASS_POLICIES()) == INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            
            ArrayList<String> caps = this.get_POLICY_CLASS_POLICIES(this.pcrec.get_COLUMN_POLICY_CLASS_ID().trim());
            
            if (caps != null) 
            {  /* reset the policies in pc record */  
                this.pcrec.set_COLUMN_POLICY_CLASS_POLICIES(caps.get(0)); 
                this.pcrec.add_POLICY_CLASS_POLICY(this.commandParameters.get(1)); /* do it once more */
            
            } else this.pcrec.add_POLICY_CLASS_POLICY(this.commandParameters.get(1)); /* if no policies exist */       

            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Policy_Classes_Table_Record(this.pcrec) != INDICATE_EXECUTION_SUCCESS) 
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
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
            
                /* after updating policies for a policy class in the DB layer we
                finally proceed to the enforcer section */
                ArrayList<String> apps = this.get_POLICY_CLASS_APPS(this.pcrec.get_COLUMN_POLICY_CLASS_ID().trim());

                /* no apps - no enforcement! */
                if (apps != null)
                {
                    /* Time to call the enforcer after proceeding to the DB layer */
                    /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */ 

                    /* execute enforcer for every app that belongs to a policy class */
                    for (int i = 0; i < apps.size(); i++)
                    {    
                        if (this.ei.build_EnforcerCMDParameters(this.prepare_EnforcerParameters(this.pcrec.get_COLUMN_POLICY_CLASS_ID(), apps.get(i))) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (this.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error

                    }
                }   
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    private Integer parse_and_execute_SHOW_POLICY_CLASS_POLICIES(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.pcrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> caps = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.pcrec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(0));
                    caps = this.get_POLICY_CLASS_POLICIES(this.pcrec.get_COLUMN_POLICY_CLASS_ID().trim());
                    
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

    private Integer parse_and_execute_REMOVE_POLICY_CLASS_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.pcrec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.pcrec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(0));
                    this.pcrec.set_COLUMN_POLICY_CLASS_POLICIES(this.commandParameters.get(1));
                    this.pcrec.set_UPDATE_COLUMN_to_POLICY_CLASS_POLICIES(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.check_if_Policy_Exists(this.pcrec.get_COLUMN_POLICY_CLASS_ID(), this.pcrec.get_COLUMN_POLICY_CLASS_POLICIES()) == INDICATE_CONDITIONAL_EXIT_STATUS) return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if
            policy does not exist */
            
            ArrayList<String> caps = this.get_POLICY_CLASS_POLICIES(this.pcrec.get_COLUMN_POLICY_CLASS_ID().trim());
            
            if (caps != null) 
            {  /* reset the policies in pc record */  
                this.pcrec.set_COLUMN_POLICY_CLASS_POLICIES(caps.get(0)); 
                this.pcrec.remove_POLICY_CLASS_POLICY(this.commandParameters.get(1)); /* do it once more */
            
            } else return RecordDAO.EMPTY_RESULT; /* if no policies exist */   
            
            //System.out.println("policies are: " + this.pcrec.get_COLUMN_POLICY_CLASS_POLICIES());
            
            //this.ei.buildEnforcerCMDParams(this.return_modified_app_policies(this.rec.getApp_PATH(), this.rec.getCAP_Attr(), 1)); //1 indicates add instruction
            
            //if (this.ei.executeCmd() != 0) return -1; //terminate if libcap execution involves error
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Policy_Classes_Table_Record(this.pcrec) != INDICATE_EXECUTION_SUCCESS) 
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
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
            
                /* after updating policies for a policy class in the DB layer we
                finally proceed to the enforcer section */
                ArrayList<String> apps = this.get_POLICY_CLASS_APPS(this.pcrec.get_COLUMN_POLICY_CLASS_ID().trim());

                /* no apps - no enforcement! */
                if (apps != null)
                {
                    /* Time to call the enforcer after proceeding to the DB layer */
                    /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */ 

                    /* execute enforcer for every app that belongs to a policy class */
                    for (int i = 0; i < apps.size(); i++)
                    {    
                        if (this.ei.build_EnforcerCMDParameters(this.prepare_EnforcerParameters(this.pcrec.get_COLUMN_POLICY_CLASS_ID(), apps.get(i))) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (this.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error

                    }
                }
                
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    /* apps table operations */
    
    private Integer parse_and_execute_COUNT_POLICY_CLASS_APPS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.apprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        
        Integer count = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        
        if (num_tokens == 2)
        { 
            
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                {    
                    this.apprec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(0));
                }    
                else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;  
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    count = this.db.count_Distinct_Apps_Table_Records_on_PCID(this.apprec);
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
    
    
    private ArrayList<String> get_POLICY_CLASS_APPS(String pcid)
    {
        AppsTableRecord[] appsr = null;
        ArrayList<String> apps = null;

        if (pcid == null || pcid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.apprec == null) return null;
        
        this.apprec.set_COLUMN_POLICY_CLASS_ID(pcid.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                appsr = this.db.read_Apps_Table_Records_On_PCID(this.apprec);  
            }    
        } catch (RecordDAO_Exception rex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }
        
        if (appsr != null)
        {    
            apps = new ArrayList<String>();
            for (int i=0; i < appsr.length; i++)
                apps.add(appsr[i].get_COLUMN_APP_PATH());
        }
        
        return apps;
    }
    
    
    private Integer parse_and_execute_SHOW_POLICY_CLASS_APPS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.apprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        ArrayList<String> apps = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                { 
                    this.apprec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(0));
                    apps = this.get_POLICY_CLASS_APPS(this.apprec.get_COLUMN_POLICY_CLASS_ID().trim());
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

    private Integer parse_and_execute_MOVE_APP_TO_POLICY_CLASS(String e)
    {
        if (e == null || e.isEmpty()) return INDICATE_INVALID_ARGUMENT_VALUE;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (this.apprec == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
       
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                { 
                    this.apprec.set_COLUMN_APP_PATH(this.commandParameters.get(0));
                    this.apprec.set_COLUMN_POLICY_CLASS_ID(this.commandParameters.get(1));
                    this.apprec.set_UPDATE_COLUMN_to_POLICY_CLASS_ID(); /* indicate the update column */
                    
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            
            /* Time to call the enforcer before proceeding to the DB layer */
            /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */ 
            if (this.ei.build_EnforcerCMDParameters(this.prepare_EnforcerParameters(this.apprec.get_COLUMN_POLICY_CLASS_ID(), this.apprec.get_COLUMN_APP_PATH())) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            if (this.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
            
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.write_Apps_Table_Record(this.apprec) == INDICATE_EXECUTION_SUCCESS) {    
                        this.set_ResultSize(0);
                        this.refill_ResultOutput("");
                        return INDICATE_EXECUTION_SUCCESS;
                    } else
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
            
            
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    
    
}
