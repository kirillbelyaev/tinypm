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

package edu.csu.lpm.interfaces;

import java.util.ArrayList;

/**
 *
 * @author kirill
 */

public interface Parser 
{
    /* we use macros to indicate the general method exit codes within the parser implementation */
    public static int INDICATE_IMMEDIATE_EXIT_STATUS = -5;
    public static int INDICATE_ARGUMENT_MISMATCH = -2;
    public static int INDICATE_INVALID_ARGUMENT_VALUE = -6;
    public static int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public static int INDICATE_EXECUTION_SUCCESS = 0;
    
    public enum LPM_COMMANDS 
    {
        EXIT,
        HELP,
        SHOW_CAPABILITIES,
        SHOW_CAPABILITIES_CLASS_COMPONENTS,
        COUNT_CAPABILITIES_CLASS_COMPONENTS,
        MOVE_COMPONENT_TO_CAPABILITIES_CLASS,
        SHOW_CAPABILITIES_CLASS_CAPABILITIES,
        ADD_CAPABILITIES_CLASS_CAPABILITY,
        REMOVE_CAPABILITIES_CLASS_CAPABILITY,
        
        /* add new commands */
        SHOW_CAPABILITIES_CLASSES,
        COUNT_CAPABILITIES_CLASSES,
        CREATE_CAPABILITIES_CLASS,
        DELETE_CAPABILITIES_CLASS, /* NOT recommended for implementation due to
        management problems with orphaned applications */
        
        /* add support for communicative classes */
        SHOW_COMMUNICATIVE_CLASSES,
        COUNT_COMMUNICATIVE_CLASSES,
        CREATE_COMMUNICATIVE_CLASS,
        SHOW_COMMUNICATIVE_CLASS_COMPONENTS,
        COUNT_COMMUNICATIVE_CLASS_COMPONENTS,
        MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS,
        SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES,
        SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES,
        ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY,
        ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY,
        REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY,
        REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY,
        
        //Authentication commands
        CHANGE_PASSWORD
        
    }
    
    public enum LPM_ERRORS
    {
        COUNT_CAPABILITIES_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_CAPABILITIES_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_CAPABILITIES_CLASS_CAPABILITIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        ADD_CAPABILITIES_CLASS_CAPABILITY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        REMOVE_CAPABILITIES_CLASS_CAPABILITY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        
         /* add new commands */
        COUNT_CAPABILITIES_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        SHOW_CAPABILITIES_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        CREATE_CAPABILITIES_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        MOVE_COMPONENT_TO_CAPABILITIES_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        
        /* additional error messages */
        Enforcer_CMD_Parameters_ERROR,
        Enforcer_execute_CMD_ERROR,
        DB_Layer_WRITE_RECORD_ERROR,
        DB_Layer_CAPABILITY_EXISTS_ERROR,
        DB_Layer_CAPABILITY_DOES_NOT_EXIST_ERROR,
        DB_Layer_NO_CAPABILITIES_EXIST_ERROR,
        DB_Layer_COLLABORATION_POLICY_EXISTS_ERROR,
        DB_Layer_COORDINATION_POLICY_EXISTS_ERROR,
        DB_Layer_COLLABORATION_POLICY_DOES_NOT_EXIST_ERROR,
        DB_Layer_COORDINATION_POLICY_DOES_NOT_EXIST_ERROR,       
        DB_Layer_DELETE_RECORD_ERROR,
        DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR,
        
        
        /* add support for communicative classes */
        SHOW_COMMUNICATIVE_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        COUNT_COMMUNICATIVE_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE,
        CREATE_COMMUNICATIVE_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        SHOW_COMMUNICATIVE_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        COUNT_COMMUNICATIVE_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2,
        SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1,
        ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3,
        ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3,
        REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3,
        REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3
    }
    
    public final String HELP_MESSAGE = ""
      +  "The following are the commands you can apply at the LPM prompt: \n"
      + LPM_COMMANDS.HELP +  " : print this help info. \n"
      + LPM_COMMANDS.EXIT + " : exit the LPM shell. \n"
      + LPM_COMMANDS.SHOW_CAPABILITIES + " : show all available Linux capabilities supported by the OS kernel \n"  
      + LPM_COMMANDS.SHOW_CAPABILITIES_CLASS_COMPONENTS + " : show a list of components that belong to a CAPABILITIES class (required arguments: CAPABILITIES_class_ID (integer)) \n"   
      + LPM_COMMANDS.COUNT_CAPABILITIES_CLASS_COMPONENTS + " : show the number of components that belong to a CAPABILITIES class (required arguments: CAPABILITIES_class_ID (integer)) \n"
      + LPM_COMMANDS.SHOW_CAPABILITIES_CLASS_CAPABILITIES + " : show the list of CAPABILITIES associated with the CAPABILITIES class  (required arguments: CAPABILITIES_class_ID (integer)) \n"
      + LPM_COMMANDS.ADD_CAPABILITIES_CLASS_CAPABILITY + " : add a CAPABILITY to a CAPABILITIES class (required arguments: CAPABILITIES_class_ID (integer), CAPABILITY) \n"  
      + LPM_COMMANDS.REMOVE_CAPABILITIES_CLASS_CAPABILITY + " : remove a CAPABILITY from a CAPABILITIES class  (required arguments: CAPABILITIES_class_ID (integer), CAPABILITY) \n"
      + LPM_COMMANDS.MOVE_COMPONENT_TO_CAPABILITIES_CLASS + " : move a component to a CAPABILITIES class  (required arguments: component_path_ID, CAPABILITIES_class_ID (integer)) \n"


      /* add new commands */
      + LPM_COMMANDS.COUNT_CAPABILITIES_CLASSES + " : show the number of CAPABILITIES classes \n" 
      + LPM_COMMANDS.SHOW_CAPABILITIES_CLASSES + " : show the list of CAPABILITIES classes \n"
      + LPM_COMMANDS.CREATE_CAPABILITIES_CLASS + " : create/rename CAPABILITIES class (required arguments: CAPABILITIES class ID (integer), CAPABILITIES_class_name_description) \n"  

      /* add support for communicative classes */
      + LPM_COMMANDS.COUNT_COMMUNICATIVE_CLASSES + " : show the number of COMMUNICATIVE classes \n" 
      + LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASSES + " : show the list of COMMUNICATIVE classes \n"
      + LPM_COMMANDS.CREATE_COMMUNICATIVE_CLASS + " : create/rename COMMUNICATIVE class (required arguments: COMMUNICATIVE class ID (integer), COMMUNICATIVE_class_name_description) \n"  
      + LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASS_COMPONENTS + " : show a list of components that belong to a COMMUNICATIVE class (required arguments: COMMUNICATIVE_class_ID (integer)) \n"   
      + LPM_COMMANDS.COUNT_COMMUNICATIVE_CLASS_COMPONENTS + " : show the number of components that belong to a COMMUNICATIVE class (required arguments: COMMUNICATIVE_class_ID (integer)) \n"
      + LPM_COMMANDS.MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS + " : move a component to a COMMUNICATIVE class (required arguments: component_path_ID, COMMUNICATIVE_class_ID (integer)) \n"
      + LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES + " : show the list of COLLABORATION POLICIES associated with the COMMUNICATIVE class (required arguments: COMMUNICATIVE_class_ID (integer)) \n"
      + LPM_COMMANDS.SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES + " : show the list of COORDINATION POLICIES associated with the COMMUNICATIVE class (required arguments: COMMUNICATIVE_class_ID (integer)) \n"
      + LPM_COMMANDS.ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY + " : add a COLLABORATION POLICY to a COMMUNICATIVE class (required arguments: COMMUNICATIVE_class_ID (integer), component path ID, object path) \n"  
      + LPM_COMMANDS.ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY + " : add a COORDINATION POLICY to a COMMUNICATIVE class (required arguments: COMMUNICATIVE_class_ID (integer), component path ID, component path ID) \n"  
      + LPM_COMMANDS.REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY + " : remove a COLLABORATION POLICY from a COMMUNICATIVE class  (required arguments: COMMUNICATIVE_class_ID (integer), component path ID, object path) \n"
      + LPM_COMMANDS.REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY + " : remove a COORDINATION POLICY from a COMMUNICATIVE class  (required arguments: COMMUNICATIVE_class_ID (integer), component path ID, component path ID) \n"
      + LPM_COMMANDS.CHANGE_PASSWORD + ":change the authentication details for system login{required OLD PASSWORD, NEW PASSWORD}"
      ;
    
    
    public int parse_and_execute_Command(String e);
    
    public ArrayList get_ResultOutput();
    
    
}
