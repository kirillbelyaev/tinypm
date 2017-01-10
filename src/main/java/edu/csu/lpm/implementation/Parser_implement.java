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

import edu.csu.lpm.DB.DAO.UserAuthDAO;
import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.factory.UserAuthDAO_Factory;
import edu.csu.lpm.DB.implementation.DB_Dispatcher;
import edu.csu.lpm.DB.implementation.RecordDAO_implement;
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;
import edu.csu.lpm.interfaces.Parser;
import edu.csu.lpm.machine.PM_Shell;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirill
 */
public class Parser_implement implements Parser {

    ///////////////// STATIC VARIABLES ///////////////////////////////////////
    static ArrayList<String> commandParameters = null;
    static CapabilitiesClassesTableRecord caprec = null;
    static CommunicativeClassesTableRecord comrec = null;
    static ComponentsTableRecord comprec = null;
    static RecordDAO_implement db = null;
    static UserAuthDAO userdb = null;
    static ArrayList<String> ResultOutput = null;
    static int resultSize = -1;
    static String ERROR_MESSAGE = null;
    static Enforcer_implement ei = null;

    //////////////////////// PRIVATE STATIC VARIABLES////////////////////////
    private static Parser_implement parser;
    private StringTokenizer tokenizer = null;

    //This is set by the Parser Execute Class to record the various responses
    private static Integer returnResult;

    ///////////////////////// PRIVATE VARIABLES ///////////////////////////////
    private DB_Dispatcher dd = null;

    private Parser_implement() {
        this.dd = new DB_Dispatcher();
        ResultOutput = new ArrayList();
        ERROR_MESSAGE = new String();
        ei = new Enforcer_implement();
    }

    public static Parser_implement getInstance() {

        if (parser == null) {
            parser = new Parser_implement();
        }
        return parser;
    }

    public String get_ERROR_MESSAGE() {
        return Parser_implement.ERROR_MESSAGE;
    }

    static void set_ERROR_MESSAGE(String m) {
        if (m != null) {
            ERROR_MESSAGE = m;
        }
    }

    private int get_ResultSize() {
        return resultSize;
    }

    static void set_ResultSize(int n) {
        resultSize = n;
    }

    @Override
    public ArrayList<String> get_ResultOutput() {
        return Parser_implement.ResultOutput;
    }

    private void set_ResultOutput(ArrayList r) {
        if (r != null) {
            Parser_implement.ResultOutput = r;
        }
    }

    static void refill_ResultOutput(String r) {
        if (r == null) {
            return;
        }
        ResultOutput.clear();
        ResultOutput.add(r);
    }

    static void refill_ResultOutput(ArrayList<String> r) {
        if (r == null) {
            return;
        }
        ResultOutput.clear();
        for (int i = 0; i < r.size(); i++) {
            ResultOutput.add(r.get(i));
        }
    }

    static void refill_ResultOutput_with_CAPABILITIES_CLASS_ID_AND_NAME(CapabilitiesClassesTableRecord[] r) {
        if (r == null) {
            return;
        }
        ResultOutput.clear();
        String row = null;
        for (int i = 0; i < r.length; i++) {
            row = " CAPC_ID: ";
            row = row.concat(r[i].get_COLUMN_CLASS_ID());
            row = row.concat(" | ");
            row = row.concat(" Class Name: ");
            row = row.concat(r[i].get_COLUMN_CLASS_NAME());

            ResultOutput.add(row);

            row = null;
            //this.ResultOutput.add(r[i].get_COLUMN_POLICY_CLASS_NAME());
        }
    }

    private void refill_Result_Output_with_all_Capabilities() {
        LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS[] = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.values();

        if (LCS == null) {
            return;
        }

        Parser_implement.ResultOutput.clear();

        Parser_implement.ResultOutput.add("Linux Capabilities (consult the capabilities (7) manual page for an overview of Linux capabilities) are:");

        for (LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS1 : LCS) {
            Parser_implement.ResultOutput.add(LCS1.toString());
        }
    }

    private int obtain_DB_Handler() {
        if (Parser_implement.db == null) //minimize the number of calls - do it only once
        {
            if (this.dd != null) {
                try {
                    Parser_implement.db = this.dd.dispatch_DB_Access();

                } catch (SQLException sex) {
                    Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, sex);
                }
            }
        }
        if (userdb == null) {
            try {
                userdb = dd.dispatch_userDB_Access();
            } catch (SQLException ex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RecordDAO_Exception ex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (Parser_implement.db != null) {
            return INDICATE_EXECUTION_SUCCESS;
        } else {
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }
    }

    public UserAuthDAO getUserAuthDAO() {
        if (userdb == null) {
            try {
                userdb = dd.dispatch_userDB_Access();
            } catch (SQLException ex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RecordDAO_Exception ex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userdb;
    }

    /**
     * Removed the if statements and added it to the validations class
     *
     * @param e
     * @return
     */
    @Override
    public int parse_and_execute_Command(String e) {
        try {
            if (e == null) {
                return INDICATE_INVALID_ARGUMENT_VALUE;
            }

            if (this.obtain_DB_Handler() != INDICATE_EXECUTION_SUCCESS) {
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

            set_ERROR_MESSAGE("");
            refill_ResultOutput("");
            e = e.trim();
            String resultValidate = Parser_validations.validate(e);
            if (e.isEmpty() || e.equals("")) {
                refill_ResultOutput("");
                set_ERROR_MESSAGE("");
                return INDICATE_EXECUTION_SUCCESS;
            } else if (e.equals("\n")) {
                refill_ResultOutput("");
                set_ERROR_MESSAGE("");
            } else if (!resultValidate.equalsIgnoreCase("success")) {
                set_ERROR_MESSAGE(resultValidate);
                return INDICATE_CONDITIONAL_EXIT_STATUS;
            } else if (e.indexOf(LPM_COMMANDS.SHOW_CAPABILITIES.toString()) == INDICATE_EXECUTION_SUCCESS) {
                this.parse_and_execute_SHOW_CAPABILITIES(e);
            }  else if (e.indexOf(LPM_COMMANDS.EXIT.toString()) == INDICATE_EXECUTION_SUCCESS) {
                return Parser.INDICATE_IMMEDIATE_EXIT_STATUS;

            } 

            if (returnResult != null) {
                return returnResult;
            }
            return INDICATE_EXECUTION_SUCCESS;
        } catch (IllegalArgumentException ae) {
            try {
                PM_Shell.out.writeUTF("Invalid command value");
            } catch (IOException ex) {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
            return INDICATE_INVALID_ARGUMENT_VALUE;
        }
    }

    

    private void parse_and_execute_SHOW_CAPABILITIES(String e) {
        if (e == null || e.isEmpty()) {
            return;
        }
        set_ResultSize(0);
        this.refill_Result_Output_with_all_Capabilities();
    }

    public static int getReturnResult() {
        return returnResult;
    }

    public static void setReturnResult(int returnResult) {
        Parser_implement.returnResult = returnResult;
    }

}
