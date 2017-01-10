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

import edu.csu.lpm.implementation.Parser_implement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author kirill
 */
public class BL_CapabilitiesClasses_UnitTests 
{
        
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testParser() 
    {
        int output = -1;
        boolean Out;
        
        String CMD = "";
        String CMD_0 = "COUNT_CAPABILITIES_CLASSES";
        
        String CMD_9 = "SHOW_CAPABILITIES_CLASSES";
        String CMD_10 = "CREATE_CAPABILITIES_CLASS";
        String CMD_11 = "CREATE_CAPABILITIES_CLASS 1 general_applications_class";
        String CMD_12 = "CREATE_CAPABILITIES_CLASS A general_applications_class";
        String CMD_13 = "CREATE_CAPABILITIES_CLASS 2 general_applications_class_2";
        
        
        String CMD_5 = "ADD_CAPABILITIES_CLASS_CAPABILITY 1 CAP_KILL";
        
        String CMD_6 = "SHOW_CAPABILITIES_CLASS_CAPABILITIES 1";
        
        String CMD_7 = "ADD_CAPABILITIES_CLASS_CAPABILITY 1 CAP_CHOWN";
        
        String CMD_8 = "ADD_CAPABILITIES_CLASS_CAPABILITY 1 CAP_DAC_OVERRIDE";
        
        String CMD_4 = "REMOVE_CAPABILITIES_CLASS_CAPABILITY 1 CAP_CHOWN";
        
        String CMD_3 = "SHOW_CAPABILITIES abc";
        
        String CMD_2 = "SHOW_CAPABILITIES";
        
        String CMD_1 = "HELP";
        
        String CMD_14 = "EXIT";
        
        
        String CMD_15 = "COUNT_CAPABILITIES_CLASS_COMPONENTS";
        
        String CMD_16 = "COUNT_CAPABILITIES_CLASS_COMPONENTS 1";
        
        String CMD_17 = "COUNT_CAPABILITIES_CLASS_COMPONENTS 2";
        
        String CMD_18 = "SHOW_CAPABILITIES_CLASS_COMPONENTS 1";
        
        
        String CMD_19 = "MOVE_COMPONENT_TO_CAPABILITIES_CLASS 1";
        String CMD_20 = "MOVE_COMPONENT_TO_CAPABILITIES_CLASS /s/chopin/b/grad/kirill/apps/ping 1";
        String CMD_21 = "MOVE_COMPONENT_TO_CAPABILITIES_CLASS /s/chopin/b/grad/kirill/apps/ping 2";
        
        String CMD_22 = "SHOW_CAPABILITIES_CLASS_COMPONENTS 2";
        
        String CMD_23 = "MOVE_COMPONENT_TO_CAPABILITIES_CLASS /s/chopin/b/grad/kirill/apps/ping 3";
        
        String CMD_24 = "SHOW_CAPABILITIES_CLASS_COMPONENTS 3";
        
        
        Parser_implement p = Parser_implement.getInstance();
        
        System.out.println("command is: " + CMD);
        output = p.parse_and_execute_Command(CMD); 
	//assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        //System.out.println("output is: " + output);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_0);
        output = p.parse_and_execute_Command(CMD_0);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_9);
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_10);
        output = p.parse_and_execute_Command(CMD_10);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_11);
        output = p.parse_and_execute_Command(CMD_11);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_9);
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_12);
        output = p.parse_and_execute_Command(CMD_12);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_9);
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_13);
        output = p.parse_and_execute_Command(CMD_13);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_9);
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_5);
        output = p.parse_and_execute_Command(CMD_5);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_6);
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_5);
        output = p.parse_and_execute_Command(CMD_5);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_6);
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_7);
        output = p.parse_and_execute_Command(CMD_7);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_6);
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_8);
        output = p.parse_and_execute_Command(CMD_8);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_6);
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_4);
        output = p.parse_and_execute_Command(CMD_4);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_6);
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_3);
        output = p.parse_and_execute_Command(CMD_3);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_2);
        output = p.parse_and_execute_Command(CMD_2);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_1);
        output = p.parse_and_execute_Command(CMD_1);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_14);
        output = p.parse_and_execute_Command(CMD_14);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_15);
        output = p.parse_and_execute_Command(CMD_15);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_16);
        output = p.parse_and_execute_Command(CMD_16);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_17);
        output = p.parse_and_execute_Command(CMD_17);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_18);
        output = p.parse_and_execute_Command(CMD_18);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("command is: " + CMD_19);
        output = p.parse_and_execute_Command(CMD_19);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_20);
        output = p.parse_and_execute_Command(CMD_20);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_18);
        output = p.parse_and_execute_Command(CMD_18);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_16);
        output = p.parse_and_execute_Command(CMD_16);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_22);
        output = p.parse_and_execute_Command(CMD_22);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_17);
        output = p.parse_and_execute_Command(CMD_17);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_23);
        output = p.parse_and_execute_Command(CMD_23);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_24);
        output = p.parse_and_execute_Command(CMD_24);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_18);
        output = p.parse_and_execute_Command(CMD_18);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        System.out.println("command is: " + CMD_16);
        output = p.parse_and_execute_Command(CMD_16);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        /*------*/
                
        
    }
    
}
