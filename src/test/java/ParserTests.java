
/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

import edu.csu.tinypm.implementation.Parser_implement;
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
public class ParserTests {
    
    public ParserTests() {
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
        String CMD_0 = "COUNT_APP_POLICIES";
        String CMD_1 = "COUNT_APP_POLICIES /bin/ping";
        String CMD_2 = "SHOW_APP_POLICIES /bin/ping";
        String CMD_3 = "ADD_APP_POLICY CAP_KILL  /bin/ping";
        String CMD_4 = "DELETE_APP_POLICY CAP_KILL  /bin/ping";
        String CMD_5 = "ADD_APP_POLICY CAP_KILL /bin/ping";
        String CMD_6 = "HELP";
        String CMD_7 = "EXIT";
        String CMD_8 = "SHOW_APPS";
        
        String CMD_9 = "ADD_APP_POLICY cap_net_raw /s/chopin/b/grad/kirill/ping";
        
        String CMD_10 = "DELETE_APP_POLICY cap_net_raw /s/chopin/b/grad/kirill/ping";
        
        String CMD_11 = "SHOW_CAPS";
        
        Parser_implement p = new Parser_implement();
        
        output = p.parse_and_execute_Command(CMD); 
	//assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        //System.out.println("output is: " + output);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_0);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_1);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_2);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        output = p.parse_and_execute_Command(CMD_3);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        System.out.println("executing CMD 4 ------------------------- ");
        
        
        output = p.parse_and_execute_Command(CMD_4);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_2);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        output = p.parse_and_execute_Command(CMD_5);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_2);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_8);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        System.out.println("executing CMD 9 ------------------------- ");
        
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1 | output == Parser.INDICATE_IMMEDIATE_EXIT_STATUS);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_11);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1 | output == Parser.INDICATE_IMMEDIATE_EXIT_STATUS);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
        
        output = p.parse_and_execute_Command(CMD_7);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1 | output == Parser.INDICATE_IMMEDIATE_EXIT_STATUS);
        System.out.println("error mesage is: " + p.getERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.getResultOutput());
        
    }
    
}
