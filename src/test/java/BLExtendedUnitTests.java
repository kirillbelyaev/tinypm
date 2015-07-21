
/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

import edu.csu.tinypm.implementation.Parser_Extended_implement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kirill
 */
public class BLExtendedUnitTests {
    
    public BLExtendedUnitTests() {
    }
    
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    @Test
    public void testParser() 
    {
        int output = -1;
        boolean Out;
        
        String CMD = "";
        String CMD_0 = "COUNT_POLICY_CLASSES";
        
        String CMD_9 = "SHOW_POLICY_CLASSES";
        String CMD_10 = "CREATE_POLICY_CLASS";
        String CMD_11 = "CREATE_POLICY_CLASS 1 general_applications_policy_class";
        String CMD_12 = "CREATE_POLICY_CLASS A general_applications_policy_class";
        String CMD_13 = "CREATE_POLICY_CLASS 2 general_applications_policy_class_2";
        
        
        String CMD_5 = "ADD_POLICY_CLASS_POLICY 1 CAP_KILL";
        
        String CMD_6 = "SHOW_POLICY_CLASS_POLICIES 1";
        
        String CMD_7 = "ADD_POLICY_CLASS_POLICY 1 CAP_CHOWN";
        
        String CMD_8 = "ADD_POLICY_CLASS_POLICY 1 CAP_DAC_OVERRIDE";
        
        String CMD_4 = "REMOVE_POLICY_CLASS_POLICY 1 CAP_CHOWN";
        
        String CMD_3 = "SHOW_CAPABILITIES abc";
        
        String CMD_2 = "SHOW_CAPABILITIES";
        
        String CMD_1 = "HELP";
        
        String CMD_14 = "EXIT";
        
        
        String CMD_15 = "COUNT_POLICY_CLASS_APPS";
        
        String CMD_16 = "COUNT_POLICY_CLASS_APPS 1";
        
        String CMD_17 = "COUNT_POLICY_CLASS_APPS 2";
        
        String CMD_18 = "SHOW_POLICY_CLASS_APPS 1";
        
        
        String CMD_19 = "MOVE_APP_TO_POLICY_CLASS 1";
        String CMD_20 = "MOVE_APP_TO_POLICY_CLASS /bin/ping 1";
        String CMD_21 = "MOVE_APP_TO_POLICY_CLASS /bin/ping 2";
        
        String CMD_22 = "SHOW_POLICY_CLASS_APPS 2";
        
        String CMD_23 = "MOVE_APP_TO_POLICY_CLASS /bin/ping 3";
        
        String CMD_24 = "SHOW_POLICY_CLASS_APPS 3";
        
        
        Parser_Extended_implement p = new Parser_Extended_implement();
        
        output = p.parse_and_execute_Command(CMD); 
	//assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        //System.out.println("output is: " + output);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_0);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_10);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_11);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_12);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_13);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_5);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_5);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_7);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_8);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_4);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_3);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        output = p.parse_and_execute_Command(CMD_2);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
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
        
        /*------*/
                
        
    }
    
}
