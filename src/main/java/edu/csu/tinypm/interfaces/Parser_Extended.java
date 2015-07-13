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
        SHOW_CAPABILITIES,
        SHOW_POLICY_CLASS_APPS,
        COUNT_POLICY_CLASS_APPS,
        MOVE_APP_TO_POLICY_CLASS,
        SHOW_POLICY_CLASS_POLICIES,
        ADD_POLICY_CLASS_POLICY,
        REMOVE_POLICY_CLASS_POLICY,
        
        /* add new commands */
        SHOW_POLICY_CLASSES,
        COUNT_POLICY_CLASSES,
        CREATE_POLICY_CLASS,
        DELETE_POLICY_CLASS /* NOT recommended for implementation due to
        management problems with orphaned applications */
        
    }
    
    public enum PM_ERRORS
    {
        COUNT_POLICY_CLASS_APPS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_POLICY_CLASS_APPS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_POLICY_CLASS_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        ADD_POLICY_CLASS_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        REMOVE_POLICY_CLASS_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        
         /* add new commands */
        COUNT_POLICY_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        SHOW_POLICY_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        CREATE_POLICY_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2
        
        
    }
    
    public final String HELP_MESSAGE = ""
          +  "The following are the commands you can apply at the PM prompt: \n"
          + PM_COMMANDS.HELP +  " : print this help info. \n"
          + PM_COMMANDS.EXIT + " : exit the PM shell. \n"
          + PM_COMMANDS.SHOW_CAPABILITIES + " : show all available Linux capabilities supported by the OS kernel \n"  
          + PM_COMMANDS.SHOW_POLICY_CLASS_APPS + " : show a list of applications that belong to a policy class (required arguments: policy_class_ID (integer)) \n"   
          + PM_COMMANDS.COUNT_POLICY_CLASS_APPS + " : show the number of apps that belong to a policy class  (required arguments: policy_class_ID (integer)) \n"
          + PM_COMMANDS.SHOW_POLICY_CLASS_POLICIES + " : show the list of policies associated with the policy class  (required arguments: policy_class_ID (integer)) \n"
          + PM_COMMANDS.ADD_POLICY_CLASS_POLICY + " : add a policy to a policy class (required arguments: policy_class_ID (integer), policy) \n"  
          + PM_COMMANDS.REMOVE_POLICY_CLASS_POLICY + " : remove a policy from a policy class  (required arguments: policy_class_ID (integer), policy) \n"
          
          /* add new commands */
          + PM_COMMANDS.COUNT_POLICY_CLASSES + " : show the number of policy classes \n" 
          + PM_COMMANDS.SHOW_POLICY_CLASSES + " : show the list of policy classes \n"
          + PM_COMMANDS.CREATE_POLICY_CLASS + " : create policy class (required arguments: policy class ID (integer), policy_class_name) \n";  
    
    
    public int parse_and_execute_Command(String e);
    
    public ArrayList get_ResultOutput();
    
    
}
