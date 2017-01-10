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

public class BL_CommunicativeClasses_UnitTests 
{  
    @BeforeClass
    public static void setUpClass() 
    {
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
    }
    
    @Before
    public void setUp() 
    {
    }
    
    @After
    public void tearDown() 
    {
    }

    private final String separator = " ";
    private final String component_1_ID = "/s/missouri/a/nobackup/kirill/containers/container-1/bin/componentA";
    private final String component_2_ID = "/s/missouri/a/nobackup/kirill/containers/container-2/bin/componentB";       
    private final String object_path = "/s/missouri/a/nobackup/kirill/logs/secure.log";
        
    private final String coord_record = component_2_ID + separator + component_1_ID;   
    private final String collab_record = component_1_ID + separator + object_path; 
    
    
    @Test
    public void testParser() 
    {
        int output = -1;
        boolean Out;
        
        String CMD = "";
        
        String CMD_0 = "COUNT_COMMUNICATIVE_CLASSES";
        
        String CMD_1 = "SHOW_COMMUNICATIVE_CLASSES";
        
        String CMD_2 = "CREATE_COMMUNICATIVE_CLASS";
        
        String CMD_3 = "CREATE_COMMUNICATIVE_CLASS 1 web_caching_service";
        
        String CMD_4 = "CREATE_COMMUNICATIVE_CLASS A web_caching_service";
        
        String CMD_5 = "CREATE_COMMUNICATIVE_CLASS 2 web_caching_service";
              
        
        String CMD_6 = "SHOW_COMMUNICATIVE_CLASS_COMPONENTS";
        
        String CMD_7 = "SHOW_COMMUNICATIVE_CLASS_COMPONENTS 1";
        
        String CMD_8 = "SHOW_COMMUNICATIVE_CLASS_COMPONENTS 2";
        
        
        String CMD_9 = "COUNT_COMMUNICATIVE_CLASS_COMPONENTS";
        
        String CMD_10 = "COUNT_COMMUNICATIVE_CLASS_COMPONENTS 1";
        
        String CMD_11 = "COUNT_COMMUNICATIVE_CLASS_COMPONENTS 2";
        
        
        String CMD_12 = "MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS 1";
        
        String CMD_13 = "MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS /s/chopin/b/grad/kirill/apps/ping 1";
        
        String CMD_14 = "SHOW_COMMUNICATIVE_CLASS_COMPONENTS 1";
        
        String CMD_15 = "MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS /s/chopin/b/grad/kirill/apps/ping 2";
        
        String CMD_16 = "SHOW_COMMUNICATIVE_CLASS_COMPONENTS 2";
        
        String CMD_17 = "MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS /s/chopin/b/grad/kirill/apps/ping 3";
        
        String CMD_18 = "SHOW_COMMUNICATIVE_CLASS_COMPONENTS 3";
        
        
        String CMD_19 = "HELP";
        
        String CMD_20 = "EXIT";
        
        String CMD_21 = "SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES 1";
        
        String CMD_22 = "SHOW_COMMUNICATIVE_CLASS_COLLABORATION_POLICIES 2";
        
        String CMD_23 = "SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES 1";
        
        String CMD_24 = "SHOW_COMMUNICATIVE_CLASS_COORDINATION_POLICIES 2";
        
        
        String CMD_25 = "ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1";
        
        String CMD_26 = "ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1 component_pathID";
        
        String CMD_27 = "ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1 component_pathID object_path";
        
        String CMD_28 = "ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1 " + this.collab_record;
        
        
        String CMD_29 = "ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1";
        
        String CMD_30 = "ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1 component_pathID";
        
        String CMD_31 = "ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1 component_pathID component_pathID_2";
        
        String CMD_32 = "ADD_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1 " + this.coord_record;
        
        
        String CMD_33 = "REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1";
        
        String CMD_34 = "REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1 component_pathID";
        
        String CMD_35 = "REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1 component_pathID object_path";
       
        String CMD_36 = "REMOVE_COMMUNICATIVE_CLASS_COLLABORATION_POLICY 1 " + this.collab_record;
        
        
        String CMD_37 = "REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1";
        
        String CMD_38 = "REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1 component_pathID";
        
        String CMD_39 = "REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1 component_pathID component_pathID_2";
        
        String CMD_40 = "REMOVE_COMMUNICATIVE_CLASS_COORDINATION_POLICY 1 " + this.coord_record;
        
        String CMD_41 = "MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS " + this.component_1_ID + " 1";
        
        String CMD_42 = "MOVE_COMPONENT_TO_COMMUNICATIVE_CLASS " + this.component_2_ID + " 1";
        
        /* class id invalid input tests */
        String CMD_43 = "ADD_COMMUNICATIVE_CLASS_COLLABORATION_POLICY abc " + this.collab_record;
        
        
        /* start parser instance */
        Parser_implement p = Parser_implement.getInstance();
        
        System.out.println("\n");
        System.out.println("command is: " + CMD);
        output = p.parse_and_execute_Command(CMD); 
	//assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        //System.out.println("output is: " + output);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_0);
        output = p.parse_and_execute_Command(CMD_0);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_1);
        output = p.parse_and_execute_Command(CMD_1);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_2);
        output = p.parse_and_execute_Command(CMD_2);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_3);
        output = p.parse_and_execute_Command(CMD_3);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_4);
        output = p.parse_and_execute_Command(CMD_4);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_1);
        output = p.parse_and_execute_Command(CMD_1);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_0);
        output = p.parse_and_execute_Command(CMD_0);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_6);
        output = p.parse_and_execute_Command(CMD_6);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_7);
        output = p.parse_and_execute_Command(CMD_7);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_8);
        output = p.parse_and_execute_Command(CMD_8);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_9);
        output = p.parse_and_execute_Command(CMD_9);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_10);
        output = p.parse_and_execute_Command(CMD_10);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_11);
        output = p.parse_and_execute_Command(CMD_11);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_12);
        output = p.parse_and_execute_Command(CMD_12);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_13);
        output = p.parse_and_execute_Command(CMD_13);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_14);
        output = p.parse_and_execute_Command(CMD_14);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_15);
        output = p.parse_and_execute_Command(CMD_15);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_16);
        output = p.parse_and_execute_Command(CMD_16);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_10);
        output = p.parse_and_execute_Command(CMD_10);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_22);
        output = p.parse_and_execute_Command(CMD_22);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_23);
        output = p.parse_and_execute_Command(CMD_23);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_24);
        output = p.parse_and_execute_Command(CMD_24);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_25);
        output = p.parse_and_execute_Command(CMD_25);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_26);
        output = p.parse_and_execute_Command(CMD_26);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_27);
        output = p.parse_and_execute_Command(CMD_27);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_28);
        output = p.parse_and_execute_Command(CMD_28);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_41);
        output = p.parse_and_execute_Command(CMD_41);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_28);
        output = p.parse_and_execute_Command(CMD_28);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_28);
        output = p.parse_and_execute_Command(CMD_28);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());      
     
        System.out.println("\n");
        System.out.println("command is: " + CMD_29);
        output = p.parse_and_execute_Command(CMD_29);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_30);
        output = p.parse_and_execute_Command(CMD_30);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_31);
        output = p.parse_and_execute_Command(CMD_31);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_32);
        output = p.parse_and_execute_Command(CMD_32);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_23);
        output = p.parse_and_execute_Command(CMD_23);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_42);
        output = p.parse_and_execute_Command(CMD_42);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());       
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_32);
        output = p.parse_and_execute_Command(CMD_32);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_33);
        output = p.parse_and_execute_Command(CMD_33);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_34);
        output = p.parse_and_execute_Command(CMD_34);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_35);
        output = p.parse_and_execute_Command(CMD_35);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_36);
        output = p.parse_and_execute_Command(CMD_36);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_36);
        output = p.parse_and_execute_Command(CMD_36);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_28);
        output = p.parse_and_execute_Command(CMD_28);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_21);
        output = p.parse_and_execute_Command(CMD_21);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_28);
        output = p.parse_and_execute_Command(CMD_28);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_37);
        output = p.parse_and_execute_Command(CMD_37);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_38);
        output = p.parse_and_execute_Command(CMD_38);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_39);
        output = p.parse_and_execute_Command(CMD_39);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_40);
        output = p.parse_and_execute_Command(CMD_40);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_23);
        output = p.parse_and_execute_Command(CMD_23);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_40);
        output = p.parse_and_execute_Command(CMD_40);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
        System.out.println("\n");
        System.out.println("command is: " + CMD_43);
        output = p.parse_and_execute_Command(CMD_43);
        //assertTrue("parse_and_execute_Command: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("error mesage is: " + p.get_ERROR_MESSAGE());
        System.out.println("parser.getResultOutput is: " + p.get_ResultOutput());
        
    }
    
}
