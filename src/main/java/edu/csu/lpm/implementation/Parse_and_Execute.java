/*
 * Linux Policy Machine (LPM) Prototype
*/
package edu.csu.lpm.implementation;

import edu.csu.lpm.DB.DAO.RecordDAO;
import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import static edu.csu.lpm.implementation.Parser_implement.caprec;
import edu.csu.lpm.interfaces.Parser;
import static edu.csu.lpm.interfaces.Parser.INDICATE_ARGUMENT_MISMATCH;
import static edu.csu.lpm.interfaces.Parser.INDICATE_CONDITIONAL_EXIT_STATUS;
import static edu.csu.lpm.interfaces.Parser.INDICATE_EXECUTION_SUCCESS;
import static edu.csu.lpm.interfaces.Parser.INDICATE_INVALID_ARGUMENT_VALUE;
import edu.csu.lpm.machine.PM_Shell;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains all the command execute method
 * @author maalv
 */
public class Parse_and_Execute {

    private static StringTokenizer tokenizer = null;

    static Integer command_COUNT_CAPABILITIES_CLASSES(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        Integer count = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1) {
            try {//execute the db layer
                if (Parser_implement.db != null) {
                    count = Parser_implement.db.count_Distinct_Capabilities_Classes_Table_Records_on_CID();
                    Parser_implement.resultSize = count;
                    Parser_implement.refill_ResultOutput(count.toString());
                    Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(edu.csu.lpm.implementation.Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    static Integer command_SHOW_CAPABILITIES_CLASSES(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        CapabilitiesClassesTableRecord[] ra = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1) {
            try {//execute the db layer
                if (Parser_implement.db != null) {
                    ra = Parser_implement.db.read_Capabilities_Classes_Table_Records_On_All_Classes();
                    if (ra != null) {
                        Parser_implement.set_ResultSize(ra.length);
                        Parser_implement.refill_ResultOutput_with_CAPABILITIES_CLASS_ID_AND_NAME(ra);
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else {
                        Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                        return RecordDAO.EMPTY_RESULT;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    static Integer command_CREATE_CAPABILITIES_CLASS(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.caprec == null) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 3) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 1) {
                    Parser_implement.caprec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0));
                    Parser_implement.caprec.set_COLUMN_CLASS_NAME(Parser_implement.commandParameters.get(1));

                    Parser_implement.caprec.reset_COLUMN_CAPABILITIES();
                    /* reset policies */

                    Parser_implement.caprec.set_UPDATE_COLUMN_to_CLASS_NAME();
                    /* indicate the update column */
                } else {
                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    if (Parser_implement.db.write_CapabilitiesClassesTableRecord(Parser_implement.caprec) != INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    } else {
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    static Integer command_ADD_CAPABILITIES_CLASS_CAPABILITY(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.caprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 3) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 1) {
                    Parser_implement.caprec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0));
                    Parser_implement.caprec.set_COLUMN_CAPABILITIES(Parser_implement.commandParameters.get(1));

                    Parser_implement.caprec.set_COLUMN_CLASS_NAME("");
                    /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */

                    Parser_implement.caprec.set_UPDATE_COLUMN_to_CAPABILITIES();
                    /* indicate the update column */

                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            if (check_if_Capability_Exists(Parser_implement.caprec.get_COLUMN_CLASS_ID(), Parser_implement.caprec.get_COLUMN_CAPABILITIES()) == INDICATE_EXECUTION_SUCCESS) {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_CAPABILITY_EXISTS_ERROR.toString());
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
                /* return if policy already exists */
            }

            ArrayList<String> caps = get_CAPABILITIES_CLASS_CAPABILITIES(Parser_implement.caprec.get_COLUMN_CLASS_ID().trim());

            if (caps != null) {
                /* reset the policies in pc record */
                Parser_implement.caprec.set_COLUMN_CAPABILITIES(caps.get(0));
                Parser_implement.caprec.add_CAPABILITY(Parser_implement.commandParameters.get(1));
                /* do it once more */

            } else {
                Parser_implement.caprec.add_CAPABILITY(Parser_implement.commandParameters.get(1)); /* if no policies exist */
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    if (Parser_implement.db.write_CapabilitiesClassesTableRecord(Parser_implement.caprec) == INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");

                        /* after updating policies for a policy class in the DB layer we
                        finally proceed to the enforcer section */
                        ArrayList<String> apps = get_CAPABILITIES_CLASS_COMPONENTS(Parser_implement.caprec.get_COLUMN_CLASS_ID().trim());

                        /* no components - no enforcement! */
                        if (apps != null) {
                            /* Time to call the enforcer after proceeding to the DB layer */
 /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */

 /* execute enforcer for every app that belongs to a policy class */
                            for (int i = 0; i < apps.size(); i++) {
                                if (Parser_implement.ei.build_EnforcerCMD_Parameters(prepare_EnforcerParameters(Parser_implement.caprec.get_COLUMN_CLASS_ID(), apps.get(i))) != INDICATE_EXECUTION_SUCCESS) {
                                    Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.Enforcer_CMD_Parameters_ERROR.toString());
                                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                                }
                                if (Parser_implement.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS) {
                                    Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.Enforcer_execute_CMD_ERROR.toString());
                                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                                    return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
                                }
                            }
                        }
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }

        } else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    static Integer command_REMOVE_CAPABILITIES_CLASS_CAPABILITY(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE); 
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.caprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 3) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 1) {
                    Parser_implement.caprec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0));
                    Parser_implement.caprec.set_COLUMN_CAPABILITIES(Parser_implement.commandParameters.get(1));
                    Parser_implement.caprec.set_UPDATE_COLUMN_to_CAPABILITIES();
                    /* indicate the update column */

                } else {
                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS); 
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            if (check_if_Capability_Exists(Parser_implement.caprec.get_COLUMN_CLASS_ID(), Parser_implement.caprec.get_COLUMN_CAPABILITIES()) == INDICATE_CONDITIONAL_EXIT_STATUS) {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_CAPABILITY_DOES_NOT_EXIST_ERROR.toString());
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
                /* return if
            policy does not exist */
            }

            ArrayList<String> caps = get_CAPABILITIES_CLASS_CAPABILITIES(Parser_implement.caprec.get_COLUMN_CLASS_ID().trim());

            if (caps != null) {
                /* reset the policies in pc record */
                Parser_implement.caprec.set_COLUMN_CAPABILITIES(caps.get(0));
                Parser_implement.caprec.remove_CAPABILITY(Parser_implement.commandParameters.get(1));
                /* do it once more */

            } else {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_NO_CAPABILITIES_EXIST_ERROR.toString());
                Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                return RecordDAO.EMPTY_RESULT;
                /* if no policies exist */
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    if (Parser_implement.db.write_CapabilitiesClassesTableRecord(Parser_implement.caprec) == INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");

                        /* after updating policies for a policy class in the DB layer we
                        finally proceed to the enforcer section */
                        ArrayList<String> apps = get_CAPABILITIES_CLASS_COMPONENTS(Parser_implement.caprec.get_COLUMN_CLASS_ID().trim());

                        /* no components - no enforcement! */
                        if (apps != null) {
                            /* Time to call the enforcer after proceeding to the DB layer */
 /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */

 /* execute enforcer for every app that belongs to a policy class */
                            for (int i = 0; i < apps.size(); i++) {
                                if (Parser_implement.ei.build_EnforcerCMD_Parameters(prepare_EnforcerParameters(Parser_implement.caprec.get_COLUMN_CLASS_ID(), apps.get(i))) != INDICATE_EXECUTION_SUCCESS) {
                                    Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.Enforcer_CMD_Parameters_ERROR.toString());
                                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                                }

                                if (Parser_implement.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS) {
                                    Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.Enforcer_execute_CMD_ERROR.toString());
                                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                                    return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
                                }
                            }
                        }
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    public static Integer command_COUNT_CAPABILITIES_CLASS_COMPONENTS(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        Integer count = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 2) {

            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 0) {
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    count = Parser_implement.db.count_Components_Table_Records_on_CAPCID(Parser_implement.comprec);
                    Parser_implement.set_ResultSize(count);
                    Parser_implement.refill_ResultOutput(count.toString());
                     Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    

    public static Integer command_SHOW_CAPABILITIES_CLASS_CAPABILITIES(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE); 
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.caprec == null) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS); 
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        ArrayList<String> caps = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 2) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 0) {
                    Parser_implement.caprec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0));
                    caps = get_CAPABILITIES_CLASS_CAPABILITIES(Parser_implement.caprec.get_COLUMN_CLASS_ID().trim());

                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS); 
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            if (caps != null) {
                Parser_implement.set_ResultSize(caps.size());
                Parser_implement.refill_ResultOutput(caps);
                 Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                return INDICATE_EXECUTION_SUCCESS;
            } else {
                Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                return RecordDAO.EMPTY_RESULT;
            }

        } else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
    }

    

    public static Integer command_SHOW_CAPABILITIES_CLASS_COMPONENTS(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        ArrayList<String> apps = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 0) {
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                         Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }

                    apps = get_CAPABILITIES_CLASS_COMPONENTS(Parser_implement.comprec.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID().trim());
                } else {
                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            if (apps != null) {
                Parser_implement.set_ResultSize(apps.size());
                Parser_implement.refill_ResultOutput(apps);
                 Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                return INDICATE_EXECUTION_SUCCESS;
            } else {
                 Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                return RecordDAO.EMPTY_RESULT;
            }

        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
    }

    public static Integer command_MOVE_COMPONENT_TO_CAPABILITIES_CLASS(String e) {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 3) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 1) {
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_PATH_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(Parser.INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }

                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(Parser_implement.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                         Parser_implement.setReturnResult(Parser.INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }

                    Parser_implement.comprec.set_UPDATE_COLUMN_to_COMPONENT_CAPABILITIES_CLASS_ID();
                    /* indicate the update column */

                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            /* Time to call the enforcer before proceeding to the DB layer */
 /* terminate if cmd is not prepared correctly - actually if prepare_EnforcerParameters() returns null */
            if (Parser_implement.ei.build_EnforcerCMD_Parameters(prepare_EnforcerParameters(Parser_implement.comprec.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(), Parser_implement.comprec.get_COLUMN_COMPONENT_PATH_ID())) != INDICATE_EXECUTION_SUCCESS) {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.Enforcer_CMD_Parameters_ERROR.toString());
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            if (Parser_implement.ei.execute_CMD() != INDICATE_EXECUTION_SUCCESS) {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.Enforcer_execute_CMD_ERROR.toString());
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; //terminate if libcap execution involves error
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    if (Parser_implement.db.write_ComponentsTableRecord(Parser_implement.comprec) == INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                         Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }

        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    public static Integer command_COUNT_COMMUNICATIVE_CLASSES(String e) {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        Integer count = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1) {
            try {//execute the db layer
                if (Parser_implement.db != null) {
                    count = Parser_implement.db.count_Distinct_Communicative_Classes_Table_Records_on_CID();
                    Parser_implement.set_ResultSize(count);
                    Parser_implement.refill_ResultOutput(count.toString());
                     Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    public static Integer command_SHOW_COMMUNICATIVE_CLASSES(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        CommunicativeClassesTableRecord[] ra = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1) {
            try {//execute the db layer
                if (Parser_implement.db != null) {
                    ra = Parser_implement.db.read_Communicative_Classes_Table_Records_On_All_Classes();
                    if (ra != null) {
                        Parser_implement.set_ResultSize(ra.length);
                        refill_ResultOutput_with_COMMUNICATIVE_CLASS_ID_AND_NAME(ra);
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS); 
                        return INDICATE_EXECUTION_SUCCESS;
                    } else {
                         Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                        return RecordDAO.EMPTY_RESULT;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    private static void refill_ResultOutput_with_COMMUNICATIVE_CLASS_ID_AND_NAME(CommunicativeClassesTableRecord[] r) {
        if (r == null) {
            return;
        }
        Parser_implement.ResultOutput.clear();
        String row = null;
        for (int i = 0; i < r.length; i++) {
            row = " COMC_ID: ";
            row = row.concat(r[i].get_COLUMN_CLASS_ID());
            row = row.concat(" | ");
            row = row.concat(" Class Name: ");
            row = row.concat(r[i].get_COLUMN_CLASS_NAME());

            Parser_implement.ResultOutput.add(row);

            row = null;
        }
    }
    
     
    public static Integer command_MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS(String e)
    {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
       
        if (num_tokens == 3)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 1)
                { 
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_PATH_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS)
                    {
                         Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }    
                    
                    /*
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(Parser_implement.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS)
                    {
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }
                    */
                    
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(Parser_implement.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS)
                    {
                         Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }    
                        
                    Parser_implement.comprec.set_UPDATE_COLUMN_to_COMPONENT_COMMUNICATIVE_CLASS_ID(); /* indicate the update column */
                    
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            /* No need for immediate enforcement since communicative policies
            are only checked upon request of the TSC. In contrast to capabilities
            that are tied to the executable, communicative policies represent the
            realistic access control dimension that is only checked upon request */
                    
            try 
            {//execute the db layer
                if (Parser_implement.db != null)
                {    
                    if (Parser_implement.db.write_ComponentsTableRecord(Parser_implement.comprec) == INDICATE_EXECUTION_SUCCESS) 
                    {    
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                         Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    } else
                    { 
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return  INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
            
            
        }  else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    public static Integer command_SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(String e)
    {
        if (e == null || e.isEmpty()){
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        ArrayList<String> policies = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 0)
                {
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    policies = get_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(Parser_implement.comrec.get_COLUMN_CLASS_ID().trim());
                    
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            if (policies != null)
            {
                Parser_implement.set_ResultSize(policies.size());
                Parser_implement.refill_ResultOutput(policies);
                 Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                return INDICATE_EXECUTION_SUCCESS;
            } else {
                 Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                return RecordDAO.EMPTY_RESULT;
            }
            
        }  else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
    }
    
    
    private static ArrayList<String> get_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(String cid)
    {
        CommunicativeClassesTableRecord[] comr = null;
        ArrayList<String> policies = null;

        if (cid == null || cid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) return null;
        
        /* check the validity of input */
        if (Parser_implement.comrec.set_COLUMN_CLASS_ID(cid.trim()) != Parser.INDICATE_EXECUTION_SUCCESS)
            return null;

        try 
        {//execute the db layer
            if (Parser_implement.db != null)
            {    
                comr = Parser_implement.db.read_Communicative_Classes_Table_Records_On_CID(Parser_implement.comrec);  
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
    
    public static Integer command_SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(String e)
    {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        ArrayList<String> policies = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 2)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 0)
                {
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS){
                          Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
          
                    policies = get_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(Parser_implement.comrec.get_COLUMN_CLASS_ID().trim());
                    
                } else {
                     Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            if (policies != null)
            {
                Parser_implement.set_ResultSize(policies.size());
                Parser_implement.refill_ResultOutput(policies);
                 Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                return INDICATE_EXECUTION_SUCCESS;
            } else {
                 Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                return RecordDAO.EMPTY_RESULT;
            }
            
        }  else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
    }
    
    private static ArrayList<String> get_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(String cid)
    {
        CommunicativeClassesTableRecord[] comr = null;
        ArrayList<String> policies = null;

        if (cid == null || cid.isEmpty()) return null;
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) return null;
        
        /* check the validity of input */
        if (Parser_implement.comrec.set_COLUMN_CLASS_ID(cid.trim()) != Parser.INDICATE_EXECUTION_SUCCESS)
            return null;

        try 
        {//execute the db layer
            if (Parser_implement.db != null)
            {    
                comr = Parser_implement.db.read_Communicative_Classes_Table_Records_On_CID(Parser_implement.comrec);  
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
    
    
    public static Integer command_ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 1)
                {  
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.
                            commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS){
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_COLLABORATION_RECORD(Parser_implement.commandParameters.
                            get(1), Parser_implement.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS){
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    
                    Parser_implement.comrec.set_COLUMN_CLASS_NAME(""); /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //Parser_implement.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            /* we can add a policy record only if such a component is associated
            with a particular class - can not just create a record with a random
            component */
            if (check_if_Component_belongs_to_Class(Parser_implement.commandParameters.get(0), Parser_implement.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR.toString());
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if component is not associated with a class */
            }    
            
            if (check_if_CollaborationPolicy_Exists(Parser_implement.comrec.get_COLUMN_CLASS_ID(), Parser_implement.comrec.get_COLUMN_COLLABORATION_RECORD()) == INDICATE_EXECUTION_SUCCESS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COLLABORATION_POLICY_EXISTS_ERROR.toString());
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (Parser_implement.db != null)
                {    
                    if (Parser_implement.db.write_CommunicativeClassesTableRecord(Parser_implement.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                         Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);                   
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else {
            return INDICATE_ARGUMENT_MISMATCH;
        }
         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    
    
    private static int check_if_CollaborationPolicy_Exists (String cid, String p)
    {
        if (cid == null || cid.isEmpty() || p == null || p.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        ArrayList<String> policies = get_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES(cid.trim());
        
        if (policies != null)
            for (int i = 0; i < policies.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (policies.get(i).contains(p.trim())){
                     Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
   

    public static Integer command_CREATE_COMMUNICATIVE_CLASS(String e) {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 3) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 1) {
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    Parser_implement.comrec.set_COLUMN_CLASS_NAME(Parser_implement.commandParameters.get(1));

                    //Parser_implement.caprec.reset_COLUMN_CAPABILITIES(); /* reset policies */
                    Parser_implement.comrec.set_UPDATE_COLUMN_to_CLASS_NAME();
                    /* indicate the update column */
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    if (Parser_implement.db.write_CommunicativeClassesTableRecord(Parser_implement.comrec) != INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    } else {
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                         Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                        return INDICATE_EXECUTION_SUCCESS;
                    }
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    public static Integer command_SHOW_COMMUNICATIVE_CLASS_COMPONENTS(String e) {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        ArrayList<String> components = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 0) {
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return Parser.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }

                    components = get_COMMUNICATIVE_CLASS_COMPONENTS(Parser_implement.comprec.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID().trim());
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            if (components != null) {
                Parser_implement.set_ResultSize(components.size());
                Parser_implement.refill_ResultOutput(components);
                 Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                return INDICATE_EXECUTION_SUCCESS;
            } else {
                 Parser_implement.setReturnResult(RecordDAO.EMPTY_RESULT);
                return RecordDAO.EMPTY_RESULT;
            }

        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
    }

    private static ArrayList<String> get_COMMUNICATIVE_CLASS_COMPONENTS(String pcid) {
        ComponentsTableRecord[] compsr = null;
        ArrayList<String> components = null;

        if (pcid == null || pcid.isEmpty()) {
            return null;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
            return null;
        }

        if (Parser_implement.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(pcid.trim()) != Parser.INDICATE_EXECUTION_SUCCESS) {
            return null;
        }

        try {//execute the db layer
            if (Parser_implement.db != null) {
                compsr = Parser_implement.db.read_Components_Table_Records_On_COMCID(Parser_implement.comprec);
            }
        } catch (RecordDAO_Exception rex) {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }

        if (compsr != null) {
            components = new ArrayList<String>();
            for (ComponentsTableRecord compsr1 : compsr) {
                components.add(compsr1.get_COLUMN_COMPONENT_PATH_ID());
            }
        }

        return components;
    }

    public static Integer command_COUNT_COMMUNICATIVE_CLASS_COMPONENTS(String e) {
        if (e == null || e.isEmpty()) {
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        Integer count = null;
        int num_tokens = tokenize_and_build_command_parameters(e.trim());

        if (num_tokens == 2) {
            if (Parser_implement.commandParameters != null) {
                if (Parser_implement.commandParameters.size() > 0) {
                    if (Parser_implement.comprec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(Parser_implement.commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                         Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
                        return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
                    }
                } else {
                     Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                 Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            try {//execute the db layer
                if (Parser_implement.db != null) {
                    count = Parser_implement.db.count_Components_Table_Records_on_COMCID(Parser_implement.comprec);
                    Parser_implement.set_ResultSize(count);
                    Parser_implement.refill_ResultOutput(count.toString());
                     Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
            } catch (RecordDAO_Exception rex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }
        } else {
             Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }

         Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    private static ArrayList<String> prepare_EnforcerParameters(String pcid, String app) {
        if (pcid == null || pcid.isEmpty() || app == null || app.isEmpty()) {
            return null;
        }

        ArrayList<String> caps = get_CAPABILITIES_CLASS_CAPABILITIES(pcid.trim());

        String policies[] = null;

        /* by now we know that if get_CAPABILITIES_CLASS_CAPABILITIES() returns null 
        - that means that no policies exist. That is because we already 
        ensure that pcid parameter should not be null in the first place,
        otherwise Parser_implement method will terminate immediately. */
        if (caps != null) {
            /* obtain the policies in the 1st element */
            policies = caps.get(0).trim().split(" ");

            /* let's reuse the list */
            caps.clear();

            for (int i = 0; i < policies.length; i++) {
                caps.add(policies[i].trim());
            }

            caps.add(app.trim()); //add the application entry last
        } else /* no policies exist for the app */ {
            caps = new ArrayList<String>();
            caps.add(app);
        }

        return caps;
    }
    
    public static Integer command_ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()){
             Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
             Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 1)
                {
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.
                            commandParameters.get(0)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_COORDINATION_RECORD(Parser_implement.commandParameters.get(1), 
                            Parser_implement.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    
                    Parser_implement.comrec.set_COLUMN_CLASS_NAME(""); /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //Parser_implement.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else {
                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            /* we can add a policy record only if such a component is associated
            with a particular class - can not just create a record with a random
            component */
            /* both components should belong to the same class */
            if (check_if_Component_belongs_to_Class(Parser_implement.commandParameters.get(0), Parser_implement.commandParameters.get(1)) != Parser.INDICATE_EXECUTION_SUCCESS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR.toString());
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if component is not associated with a class */
            }
            
            /* we can add a policy record only if such a component is associated
            with a particular class - can not just create a record with a random
            component */
            /* both components should belong to the same class */
            if (check_if_Component_belongs_to_Class(Parser_implement.commandParameters.get(0), Parser_implement.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COMPONENT_DOES_NOT_BELONG_TO_CLASS_ERROR.toString());
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if component is not associated with a class */
            }
            
            if (check_if_CoordinationPolicy_Exists(Parser_implement.comrec.get_COLUMN_CLASS_ID(), Parser_implement.comrec.get_COLUMN_COORDINATION_RECORD()) == INDICATE_EXECUTION_SUCCESS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COORDINATION_POLICY_EXISTS_ERROR.toString());
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (Parser_implement.db != null)
                {    
                    if (Parser_implement.db.write_CommunicativeClassesTableRecord(Parser_implement.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);                   
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_WRITE_RECORD_ERROR.toString());
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else  {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
        
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    private static int check_if_Component_belongs_to_Class (String cid, String component)
    {
        if (cid == null || cid.isEmpty() || component == null || component.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        ArrayList<String> components = get_COMMUNICATIVE_CLASS_COMPONENTS(cid.trim());
        
        if (components != null)
            for (int i = 0; i < components.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (components.get(i).contains(component.trim())) {
                    Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
        
    public static Integer command_REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 1)
                {
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0)) 
                            != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_COLLABORATION_RECORD(Parser_implement.commandParameters.get(1), 
                            Parser_implement.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    
                    //Parser_implement.comrec.set_COLUMN_CLASS_NAME(""); 
                    /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //Parser_implement.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else {
                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            if (check_if_CollaborationPolicy_Exists(Parser_implement.comrec.get_COLUMN_CLASS_ID(), Parser_implement.comrec.get_COLUMN_COLLABORATION_RECORD()) == INDICATE_CONDITIONAL_EXIT_STATUS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COLLABORATION_POLICY_DOES_NOT_EXIST_ERROR.toString());
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (Parser_implement.db != null)
                {    
                    if (Parser_implement.db.delete_Communicative_Classes_Table_Record_On_CollaborationRecord_And_CID(Parser_implement.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);                   
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_DELETE_RECORD_ERROR.toString());
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else{
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    public static Integer command_REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY(String e)
    {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        
        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comrec == null) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        int num_tokens = tokenize_and_build_command_parameters(e.trim());
        
        if (num_tokens == 4)
        {    
            if (Parser_implement.commandParameters != null)
            {
                if (Parser_implement.commandParameters.size() > 1)
                {
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_CLASS_ID(Parser_implement.commandParameters.get(0))
                            != Parser.INDICATE_EXECUTION_SUCCESS) {
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* check the validity of input */
                    if (Parser_implement.comrec.set_COLUMN_COORDINATION_RECORD(Parser_implement.commandParameters.get(1),
                            Parser_implement.commandParameters.get(2)) != Parser.INDICATE_EXECUTION_SUCCESS) {
                       Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    
                    //Parser_implement.comrec.set_COLUMN_CLASS_NAME(""); 
                    /* make blank in case a policy is added to non-existent
                    policy class therefore triggering the creation of new policy class record */
                    
                    //Parser_implement.comrec.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();/* indicate the update column */
                    
                } else {
                    Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                    return INDICATE_CONDITIONAL_EXIT_STATUS;
                }
            } else {
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }
            
            if (check_if_CoordinationPolicy_Exists(Parser_implement.comrec.get_COLUMN_CLASS_ID(), Parser_implement.comrec.get_COLUMN_COORDINATION_RECORD()) == INDICATE_CONDITIONAL_EXIT_STATUS)
            {
                Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_COORDINATION_POLICY_DOES_NOT_EXIST_ERROR.toString());
                Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                return INDICATE_CONDITIONAL_EXIT_STATUS; /* return if policy already exists */
            }
            
            try 
            {//execute the db layer
                if (Parser_implement.db != null)
                {    
                    if (Parser_implement.db.delete_Communicative_Classes_Table_Record_On_CoordinationRecord_And_CID(Parser_implement.comrec) == INDICATE_EXECUTION_SUCCESS)
                    {    
                        Parser_implement.set_ResultSize(0);
                        Parser_implement.refill_ResultOutput("");
                        
                        /* No need for immediate enforcement since communicative policies
                        are only checked upon request of the TSC. In contrast to capabilities
                        that are tied to the executable, communicative policies represent the
                        realistic access control dimension that is only checked upon request */
                        Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);                   
                        return INDICATE_EXECUTION_SUCCESS;
                    }    
                    else
                    {
                        Parser_implement.set_ERROR_MESSAGE(Parser.LPM_ERRORS.DB_Layer_DELETE_RECORD_ERROR.toString());
                        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
                        return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                }    
            } catch (RecordDAO_Exception rex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
            }   
            
        }  else {
            Parser_implement.setReturnResult(INDICATE_ARGUMENT_MISMATCH);
            return INDICATE_ARGUMENT_MISMATCH;
        }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    /**
     * Does all the work for change password command.
     * Format: CHANGE_PASSWORD <old-password> <new-password>
     * @param e
     * @return 
     */
    public static Integer command_CHANGE_PASSWORD(String e)
    {
        try {
            if (e == null || e.isEmpty()) {
                Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
                return INDICATE_INVALID_ARGUMENT_VALUE;
            }
            
            String [] cmdParam = e.split(" ");
            //cmdParam[1] = old-password
            //cmdParam[2] = new-password
            if(cmdParam.length != 3) {
                return INDICATE_ARGUMENT_MISMATCH;
            }
            
            String old_password =  cmdParam[1];
            String actualPassword = Parser_implement.userdb.getPasswordFromDB();
            if(!old_password.equals(actualPassword)) {
                PM_Shell.out.writeUTF("Invalid Detail. Unable to perform command");
                return INDICATE_INVALID_ARGUMENT_VALUE;
            }
            String new_password = cmdParam[2];
            Parser_implement.userdb.updateNewPasswordInDB(new_password);
            PM_Shell.out.writeUTF("Password changed successfully");
        } catch (SQLException ex) {
            Logger.getLogger(Parse_and_Execute.class.getName()).log(Level.SEVERE, null, ex);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        } catch (IOException ex) {
            Logger.getLogger(Parse_and_Execute.class.getName()).log(Level.SEVERE, null, ex);
        }
        return INDICATE_EXECUTION_SUCCESS;
        
     
    }
    
    public static void command_HELP(String e) {
        if (e == null || e.isEmpty()) {
            return;
        }
        Parser_implement.set_ResultSize(0);
        Parser_implement.refill_ResultOutput("");
        Parser_implement.set_ERROR_MESSAGE(Parser.HELP_MESSAGE);
    }
    
    
    private static int check_if_CoordinationPolicy_Exists (String cid, String p)
    {
        if (cid == null || cid.isEmpty() || p == null || p.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
        
        ArrayList<String> policies = get_COMMUNICATIVE_CLASS_COORDINATION_POLICIES(cid.trim());
        
        if (policies != null)
            for (int i = 0; i < policies.size(); i++)
                //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
                if (policies.get(i).contains(p.trim()))  {
                    Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }
    
    ////////////////////// PRIVATE METHODS ///////////////////////////////
    private static int tokenize_and_build_command_parameters(String e) {
        if (e == null || e.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_INVALID_ARGUMENT_VALUE);
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
        int count = -1;

        if (Parser_implement.commandParameters == null) {
            Parser_implement.commandParameters = new ArrayList<String>();
        } else {
            Parser_implement.commandParameters.clear();
        }

        /* initialize the records only once and then reuse in other methods to save memory */
        if (Parser_implement.caprec == null) {
            Parser_implement.caprec = new CapabilitiesClassesTableRecord();
        }

        if (Parser_implement.comrec == null) {
            Parser_implement.comrec = new CommunicativeClassesTableRecord();
        }

        if (Parser_implement.comprec == null) {
            Parser_implement.comprec = new ComponentsTableRecord();
        }

        tokenizer = new StringTokenizer(e, " ");

        count = tokenizer.countTokens(); //obtain the number of tokens before cycling through them

        tokenizer.nextToken();//skip the 1st token which is the command itself

        while (tokenizer.hasMoreTokens()) {
            String field = tokenizer.nextToken();
            field = field.trim();
            Parser_implement.commandParameters.add(field);
        }
        return count;
    }

    private static int check_if_Capability_Exists(String pcid, String p) {
        if (pcid == null || pcid.isEmpty() || p == null || p.isEmpty()) {
            Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        ArrayList<String> caps = get_CAPABILITIES_CLASS_CAPABILITIES(pcid.trim());

        if (caps != null) {
            for (int i = 0; i < caps.size(); i++) //if (caps.get(i).compareTo(p.trim()) == 0) return 0;
            {
                if (caps.get(i).contains(p.trim())) {
                    Parser_implement.setReturnResult(INDICATE_EXECUTION_SUCCESS);
                    return INDICATE_EXECUTION_SUCCESS;
                }
            }
        }

        Parser_implement.setReturnResult(INDICATE_CONDITIONAL_EXIT_STATUS);
        return INDICATE_CONDITIONAL_EXIT_STATUS;
    }

    private static ArrayList<String> get_CAPABILITIES_CLASS_COMPONENTS(String pcid) {
        ComponentsTableRecord[] appsr = null;
        ArrayList<String> apps = null;

        if (pcid == null || pcid.isEmpty()) {
            return null;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (Parser_implement.comprec == null) {
            return null;
        }

        if (Parser_implement.comprec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(pcid.trim()) != Parser.INDICATE_EXECUTION_SUCCESS) {
            return null;
        }

        try {//execute the db layer
            if (Parser_implement.db != null) {
                appsr = Parser_implement.db.read_Components_Table_Records_On_CAPCID(Parser_implement.comprec);
            }
        } catch (RecordDAO_Exception rex) {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }

        if (appsr != null) {
            apps = new ArrayList<String>();
            for (int i = 0; i < appsr.length; i++) {
                apps.add(appsr[i].get_COLUMN_COMPONENT_PATH_ID());
            }
        }

        return apps;
    }

    private static ArrayList<String> get_CAPABILITIES_CLASS_CAPABILITIES(String pcid) {
        CapabilitiesClassesTableRecord pcr[] = null;
        ArrayList<String> caps = null;

        if (pcid == null || pcid.isEmpty()) {
            return null;
        }

        /* if record is not created beforehand by 
        tokenize_and_build_command_parameters() method - terminate */
        if (caprec == null) {
            return null;
        }

        caprec.set_COLUMN_CLASS_ID(pcid.trim());

        try {//execute the db layer
            if (Parser_implement.db != null) {
                pcr = Parser_implement.db.read_Capabilities_Classes_Table_Records_On_CID(caprec);
            }
        } catch (RecordDAO_Exception rex) {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, rex);
        }

        if (pcr != null) {
            caps = new ArrayList<String>();
            for (int i = 0; i < pcr.length; i++) {
                /* let us make sure that a policy class record does have policies */
                if (!pcr[i].check_if_COLUMN_CAPABILITIES_is_Empty()) {
                    caps.add(pcr[i].get_COLUMN_CAPABILITIES()); /* add non-empty
                policies only */
                }
            }
        }

        /* let us ensure that we return only non-empty policies */
        if (caps != null) {
            if (!caps.isEmpty()) {
                return caps;
            } else {
                return null;
            }
        }

        return null;
        /* return NULL by default */
    }
}
