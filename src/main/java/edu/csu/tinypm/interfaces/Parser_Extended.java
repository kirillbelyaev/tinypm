/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/


/* Parser interface represents the BL (Business Logic) layer commands available to the user shell*/ 

package edu.csu.tinypm.interfaces;

import java.util.ArrayList;

/**
 *
 * @author kirill
 */
public interface Parser_Extended 
{
    /* we use macros to indicate the general method exit codes within the parser implementation */
    public int INDICATE_IMMEDIATE_EXIT_STATUS = -5;
    public int INDICATE_ARGUMENT_MISMATCH = -2;
    public int INDICATE_INVALID_ARGUMENT_VALUE = -6;
    public int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public int INDICATE_EXECUTION_SUCCESS = 0;
    
    public enum PM_COMMANDS 
    {
        EXIT,
        HELP,
        SHOW_APPS,
        SHOW_APP_POLICIES,
        ADD_APP_POLICY,
        DELETE_APP_POLICY,
        COUNT_APP_POLICIES,
        
        /* add new commands */
        SHOW_POLICY_CLASSES,
        COUNT_POLICY_CLASSES,
        //SHOW_POLICY_CLASS_POLICIES,
        CREATE_POLICY_CLASS,
        DELETE_POLICY_CLASS //not recommended for implementation
        
    }
    
    public enum PM_ERRORS
    {
        COUNT_APP_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_APPS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        SHOW_APP_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        UPDATE_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        ADD_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        DELETE_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        
         /* add new commands */
        COUNT_POLICY_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        SHOW_POLICY_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        CREATE_POLICY_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2
        
        
    }
    
    public final String HELP_MESSAGE = ""
          +  "The following are the commands you can apply at the PM prompt: \n"
          + PM_COMMANDS.HELP +  " : print this help info. \n"
          + PM_COMMANDS.EXIT + " : exit the PM shell. \n"
          + PM_COMMANDS.SHOW_APPS + " : show a list of applications stored in the tinyPM db \n"   
          + PM_COMMANDS.COUNT_APP_POLICIES + " : show the number of policies for the APP.  (App_path argument is required) \n"
          + PM_COMMANDS.SHOW_APP_POLICIES + " : show the list of policies for the APP  (App_path argument is required) \n"
          + PM_COMMANDS.ADD_APP_POLICY + " : add a policy to a policy class (required arguments: policy ID (integer), policy (capability)) \n"  
          + PM_COMMANDS.DELETE_APP_POLICY + " : delete a policy for the APP  (policy argument and App_path argument are required) \n"
          
          /* add new commands */
          + PM_COMMANDS.COUNT_POLICY_CLASSES + " : show the number of policy classes \n" 
          + PM_COMMANDS.SHOW_POLICY_CLASSES + " : show the list of policy classes \n"
          + PM_COMMANDS.CREATE_POLICY_CLASS + " : create policy class (required arguments: policy ID (integer), policy_class_name) \n";  
    
    
    public int parse_and_execute_Command(String e);
    
    public ArrayList getResultOutput();
    
    
}
