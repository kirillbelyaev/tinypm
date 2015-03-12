/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.interfaces;

import java.util.ArrayList;

/**
 *
 * @author kirill
 */
public interface Parser 
{
    public int INDICATE_IMMEDIATE_EXIT_STATUS = -5;
    public int INDICATE_ARGUMENT_MISMATCH = -2;
    public int INDICATE_INVALID_ARGUMENT_VALUE = -6;
    
    public enum PM_COMMANDS 
    {
        EXIT,
        HELP,
        SHOW_APP_POLICIES,
        ADD_APP_POLICY,
        DELETE_APP_POLICY,
        COUNT_APP_POLICIES
    }
    
    public enum PM_ERRORS
    {
        COUNT_APP_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_APP_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        UPDATE_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        ADD_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        DELETE_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2
    }
    
    public final String HELP_MESSAGE = ""
          +  "The following are the commands you can apply at the PM prompt: \n"
          + PM_COMMANDS.HELP +  " : print this help info. \n"
          + PM_COMMANDS.EXIT + " : exit the PM shell. \n"
          + PM_COMMANDS.COUNT_APP_POLICIES + " : show the number of policies for the APP.  (App_path argument is required) \n"
          + PM_COMMANDS.SHOW_APP_POLICIES + " : show the list of policies for the APP  (App_path argument is required) \n"
          + PM_COMMANDS.ADD_APP_POLICY + " : create a policy for the APP  (policy argument and App_path argument are required) \n"  
          + PM_COMMANDS.DELETE_APP_POLICY + " : delete a policy for the APP  (policy argument and App_path argument are required) \n";
    
    
    public int parse_and_execute_Command(String e);
    
    public ArrayList getResultOutput();
    
    
}
