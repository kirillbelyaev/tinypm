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

import edu.csu.lpm.TSLib.implementation.ContentTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;
import edu.csu.lpm.TSLib.interfaces.Tuple;
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
public class TSLib_UnitTests_ControlTuple {
    
    public TSLib_UnitTests_ControlTuple() {
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
    
    
    private final String FIELD_APP_PATH_VALID = "/bin/ping";
    private final String FIELD_APP_PATH_INVALID = "/bin/non_existent/ping";
    private final String FIELD_RequestMessage = "/usr/home/containers/container-b/data-pool/runtime-log.txt";
    private final String FIELD_XML_CoordinationMessage = "<XML_MESSAGE length=\"0000060\"><field>8</field></XML_MESSAGE>";
    private final String FIELD_Payload = 
    "[^[[32m  OK  ^[[0m] Started Show Plymouth Boot Screen.\n" +
    "[^[[32m  OK  ^[[0m] Reached target Paths.\n" +
    "[^[[32m  OK  ^[[0m] Reached target Basic System.\n" +
    "[^[[32m  OK  ^[[0m] Reached target Initrd Default Target.\n" +
    "Starting dracut pre-pivot and cleanup hook...\n" +
    "[^[[32m  OK  ^[[0m] Started dracut pre-pivot and cleanup hook.\n" +
    "Starting Cleaning Up and Shutting Down Daemons...\n" +
    "[^[[32m  OK  ^[[0m] Stopped target Timers.\n" +
    "Starting Plymouth switch root service...\n";

    private final String ValidLocation = System.getProperty("user.home");
    private final String InvalidLocation = "/non/existent/dir";
    private final String EmptyString = "";
    private final String RootDir = "/";
    
    private StringBuffer Body = new StringBuffer();
    private StringBuffer StringBufferValue = null;
    
    private String StringValue = null;
    private int IntValue = -1;
    private final int SampleSequenceNumber = 101;
    
    private boolean TerminateNow = false;
    private boolean Result = false;
    
    
    private ControlTuple_implement CLT = new ControlTuple_implement();
    private ContentTuple_implement CNT = new ContentTuple_implement();
    
    
    @Test
    public void test_ControlTuple_set_ID_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_set_ID_Field() ");
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_SourceID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_ControlTuple_set_ID_Field() ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.CLT.set_SourceID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_SourceID_Field(this.EmptyString);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_SourceID_Field(this.RootDir);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_SourceID_Field(null);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_set_ID_Field() ");
        System.out.println("\n"); 
    }
    
    @Test
    public void test_ControlTuple_get_ID_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_get_ID_Field() ");
        System.out.println("\n"); 
        
        this.StringValue = this.CLT.get_SourceID_Field();
        assertNotNull(this.StringValue);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID field is: " + this.StringValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_get_ID_Field() ");
        System.out.println("\n");
    }
    
    @Test
    public void test_ControlTuple_set_RequestMessage_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_set_RequestMessage_Field() ");
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_RequestMessage_Field(this.FIELD_XML_CoordinationMessage);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_ControlTuple_set_RequestMessage_Field() ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.CLT.set_RequestMessage_Field(this.EmptyString);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_RequestMessage_Field(this.RootDir);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CLT.set_RequestMessage_Field(null);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_set_RequestMessage_Field() ");
        System.out.println("\n"); 
    }
    
    @Test
    public void test_ControlTuple_get_RequestMessage_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_get_RequestMessage_Field() ");
        System.out.println("\n"); 
        
        this.StringValue = this.CLT.get_RequestMessage_Field();
        assertNotNull(this.StringValue);
        System.out.println("executing get_RequestMessage_Field() ");
        System.out.println("RequestMessage field is: " + this.StringValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_get_RequestMessage_Field() ");
        System.out.println("\n");
    }
    
    
    @Test
    public void test_ControlTuple_set_Type_Field_to_Collaboration()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_set_Type_Field_to_Collaboration() ");
        System.out.println("\n"); 
        
        this.CLT.set_Type_Field_to_Collaboration();
        System.out.println("executing set_Type_Field_to_Collaboration() ");
        System.out.println("\n"); 
            
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_set_Type_Field_to_Collaboration() ");
        System.out.println("\n"); 
    }
    
    @Test
    public void test_ControlTuple_set_Type_Field_to_Coordination()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_set_Type_Field_to_Coordination() ");
        System.out.println("\n"); 
        
        this.CLT.set_Type_Field_to_Coordination();
        System.out.println("executing set_Type_Field_to_Coordination() ");
        System.out.println("\n"); 
            
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_set_Type_Field_to_Coordination() ");
        System.out.println("\n"); 
    }
    
    @Test
    public void test_ControlTuple_get_Type_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple_get_Type_Field() ");
        System.out.println("\n"); 
        
        this.StringValue = this.CLT.get_Type_Field();
        assertNotNull(this.StringValue);
        System.out.println("executing get_Type_Field() ");
        System.out.println("Type field is: " + this.StringValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple_get_Type_Field() ");
        System.out.println("\n");
    }
    
    
    
    @Test
    public void test_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple ");
        System.out.println("\n"); 
        
        /* set to direct the execution of test methods with valid input only */
        this.TerminateNow = true;
        
        this.test_ControlTuple_set_ID_Field();
        this.test_ControlTuple_get_ID_Field();
        
        this.test_ControlTuple_set_RequestMessage_Field();
        this.test_ControlTuple_get_RequestMessage_Field();
        
        this.test_ControlTuple_set_Type_Field_to_Collaboration();
        this.test_ControlTuple_get_Type_Field();
        
        this.test_ControlTuple_set_Type_Field_to_Coordination();
        this.test_ControlTuple_get_Type_Field();
        
        /* now test matching */
        
        this.Result = this.CLT.match_on_SourceID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CLT.match_on_SourceID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CLT.match_on_SourceID_Field(null);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CLT.match_on_SourceID_Field(this.EmptyString);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        
        this.Result = this.CLT.match_on_Type_Field(Tuple.TupleTypes.COLLABORATION.toString());
        System.out.println("executing match_on_Type_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        
        this.Result = this.CLT.match_on_Type_Field(Tuple.TupleTypes.COORDINATION.toString());
        System.out.println("executing match_on_Type_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CLT.match_on_Type_Field(this.EmptyString);
        System.out.println("executing match_on_Type_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        
        this.Result = this.CLT.match_on_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("executing match_on_RequestMessage_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        
        this.Result = this.CLT.match_on_RequestMessage_Field(this.FIELD_XML_CoordinationMessage);
        System.out.println("executing match_on_RequestMessage_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CLT.match_on_RequestMessage_Field(this.EmptyString);
        System.out.println("executing match_on_RequestMessage_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple ");
        System.out.println("\n"); 
    }
    
}
