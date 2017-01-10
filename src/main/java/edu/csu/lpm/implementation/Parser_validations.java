/*
 * To change Parser_implement license header, choose License Headers in Project Properties.
 * To change Parser_implement template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csu.lpm.implementation;

import edu.csu.lpm.interfaces.Parser;
import static edu.csu.lpm.interfaces.Parser.INDICATE_ARGUMENT_MISMATCH;
import static edu.csu.lpm.interfaces.Parser.INDICATE_EXECUTION_SUCCESS;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author maalv
 */
public class Parser_validations {

    private static StringTokenizer tokenizer = null;

    public static String validate(String e) {

        Parser.LPM_COMMANDS pmCmd = Parser.LPM_COMMANDS.valueOf(e.split(" ")[0]);
        String errorMsg = "success";
        switch (pmCmd) {
            case COUNT_CAPABILITIES_CLASSES:
                if (Parse_and_Execute.command_COUNT_CAPABILITIES_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = Parser.LPM_ERRORS.COUNT_CAPABILITIES_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString();

                }
                break;
            case SHOW_CAPABILITIES_CLASSES:
                if (Parse_and_Execute.command_SHOW_CAPABILITIES_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = Parser.LPM_ERRORS.SHOW_CAPABILITIES_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString();
                }
                break;
            case CREATE_CAPABILITIES_CLASS:
                if (Parse_and_Execute.command_CREATE_CAPABILITIES_CLASS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = Parser.LPM_ERRORS.CREATE_CAPABILITIES_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString();
                }
                break;
            case ADD_CAPABILITIES_CLASS_CAPABILITY:
                if (Parse_and_Execute.command_ADD_CAPABILITIES_CLASS_CAPABILITY(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = Parser.LPM_ERRORS.ADD_CAPABILITIES_CLASS_CAPABILITY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString();
                }
                break;
            case SHOW_CAPABILITIES_CLASS_CAPABILITIES:
                if (Parse_and_Execute.command_SHOW_CAPABILITIES_CLASS_CAPABILITIES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.SHOW_CAPABILITIES_CLASS_CAPABILITIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());

                }
                break;
            case REMOVE_CAPABILITIES_CLASS_CAPABILITY:
                if (Parse_and_Execute.command_REMOVE_CAPABILITIES_CLASS_CAPABILITY(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.REMOVE_CAPABILITIES_CLASS_CAPABILITY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                }
                break;
            case COUNT_CAPABILITIES_CLASS_COMPONENTS:
                if (Parse_and_Execute.command_COUNT_CAPABILITIES_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.COUNT_CAPABILITIES_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());

                }
                break;
            case SHOW_CAPABILITIES_CLASS_COMPONENTS:
                if (Parse_and_Execute.command_SHOW_CAPABILITIES_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.SHOW_CAPABILITIES_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());

                }
                break;
            case MOVE_COMPONENT_TO_CAPABILITIES_CLASS:
                if (Parse_and_Execute.command_MOVE_COMPONENT_TO_CAPABILITIES_CLASS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.MOVE_COMPONENT_TO_CAPABILITIES_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                }
                break;
            case COUNT_COMMUNICATIVE_CLASSES:
                if (Parse_and_Execute.command_COUNT_COMMUNICATIVE_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.COUNT_COMMUNICATIVE_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());

                }
                break;
            case SHOW_COMMUNICATIVE_CLASSES:
                if (Parse_and_Execute.command_SHOW_COMMUNICATIVE_CLASSES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.SHOW_COMMUNICATIVE_CLASSES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());

                }
                break;

            case CREATE_COMMUNICATIVE_CLASS:
                if (Parse_and_Execute.command_CREATE_COMMUNICATIVE_CLASS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.CREATE_COMMUNICATIVE_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());

                }
                break;
            case SHOW_COMMUNICATIVE_CLASS_COMPONENTS:
                if (Parse_and_Execute.command_SHOW_COMMUNICATIVE_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.SHOW_COMMUNICATIVE_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                }
                break;
            case COUNT_COMMUNICATIVE_CLASS_COMPONENTS:
                if (Parse_and_Execute.command_COUNT_COMMUNICATIVE_CLASS_COMPONENTS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.COUNT_COMMUNICATIVE_CLASS_COMPONENTS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());

                }
                break;
            case MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS:
                if (Parse_and_Execute.command_MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());

                }
                break;
            case SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES:
                if (Parse_and_Execute.command_SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                }
                break;
            case SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES:
                if (Parse_and_Execute.command_SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());

                }
                break;
            case ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY:
                if (Parse_and_Execute.command_ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());
                }
                break;
            case ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY:
                if (Parse_and_Execute.command_ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());

                }
                break;
            case REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY:
                if (Parse_and_Execute.command_REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());
                }
                break;
            case REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY:
                if (Parse_and_Execute.command_REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());

                }
                break;
            case CHANGE_PASSWORD:
                if (Parse_and_Execute.command_CHANGE_PASSWORD(e) == INDICATE_ARGUMENT_MISMATCH) {
                    errorMsg = (Parser.LPM_ERRORS.REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_3.toString());

                }
                break;
            case HELP:
            	Parse_and_Execute.command_HELP(e);
            	break;	

        }
        return errorMsg;
    }

}
