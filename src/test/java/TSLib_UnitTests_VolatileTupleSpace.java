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
import edu.csu.lpm.TSLib.implementation.VolatileTupleSpace_implement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author kirill
 */
public class TSLib_UnitTests_VolatileTupleSpace 
{
    
    public TSLib_UnitTests_VolatileTupleSpace() {
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
    
    /* globals */
    
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
    
    private String StringValue = null;
    private int IntValue = -1;
    private final int SampleSequenceNumber = 101;
    
    private boolean TerminateNow = false;
    
    private VolatileTupleSpace_implement VTS = new VolatileTupleSpace_implement();
    private ControlTuple_implement CLT = new ControlTuple_implement();
    private ContentTuple_implement CNT = new ContentTuple_implement();
    
    /* VTS tests */
    
    @Test
    public void test_VolatileTupleSpace_create_TupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_create_TupleSpace ");
        System.out.println("\n");
                
        this.IntValue = this.VTS.create_TupleSpace();
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_VolatileTupleSpace_create_TupleSpace ");
            System.out.println("\n");
            return;
        }
            
        this.IntValue = this.VTS.create_TupleSpace();
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_create_TupleSpace ");
        System.out.println("\n");
    }

    @Test
    public void test_VolatileTupleSpace_delete_TupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_delete_TupleSpace ");
        System.out.println("\n");
                
        this.IntValue = this.VTS.delete_TupleSpace();
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_VolatileTupleSpace_delete_TupleSpace ");
            System.out.println("\n");
            return;
        }
            
        this.IntValue = this.VTS.delete_TupleSpace();
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_delete_TupleSpace ");
        System.out.println("\n");
    }
    
    @Test
    public void test_VolatileTupleSpace_count_Tuples()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_count_Tuples ");
        System.out.println("\n");
        
        this.IntValue = this.VTS.count_Tuples();
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_count_Tuples ");
        System.out.println("\n");
    }    
    
    @Test
    public void test_VolatileTupleSpace_append_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_append_ControlTuple ");
        System.out.println("\n");
        
        if (this.CLT == null) this.CLT = new ControlTuple_implement();
        
        this.CLT.set_SourceID_Field(this.FIELD_APP_PATH_VALID);
        this.CLT.set_Type_Field_to_Collaboration();
        this.CLT.set_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("setting ControlTuple fields ");
        System.out.println("\n");
        
        this.IntValue = this.VTS.append_ControlTuple(this.CLT);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_VolatileTupleSpace_append_ControlTuple ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.VTS.append_ControlTuple(null);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.VTS.append_ControlTuple(this.CLT);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_append_ControlTuple ");
        System.out.println("\n");
    }    
    
    
    @Test
    public void test_VolatileTupleSpace_read_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_read_ControlTuple ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.VTS.read_ControlTuple();
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_SourceID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_read_ControlTuple ");
        System.out.println("\n");
    }    
    
    @Test
    public void test_VolatileTupleSpace_take_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_take_ControlTuple ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.VTS.take_ControlTuple();
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_SourceID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_VolatileTupleSpace_take_ControlTuple ");
            System.out.println("\n");
            return;
        }
        
        this.CLT = null;
        this.CLT = this.VTS.take_ControlTuple();
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_SourceID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_take_ControlTuple ");
        System.out.println("\n");
    } 
    
    
    @Test
    public void test_VolatileTupleSpace_append_ContentTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_append_ContentTuple ");
        System.out.println("\n");
        
        if (this.CNT == null) this.CNT = new ContentTuple_implement();
        
        this.Body.append(this.FIELD_Payload);
        
        this.CNT.set_DestinationID_Field(this.FIELD_APP_PATH_VALID);
        this.CNT.set_SequenceNumber_Field(this.SampleSequenceNumber);
        this.CNT.set_Payload_Field(this.Body);
        System.out.println("setting ContentTuple fields ");
        System.out.println("\n");
        
        this.IntValue = this.VTS.append_ContentTuple(this.CNT);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_VolatileTupleSpace_append_ContentTuple ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.VTS.append_ContentTuple(null);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + IntValue);
        System.out.println("\n");
        
        this.IntValue = this.VTS.append_ContentTuple(this.CNT);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_append_ContentTuple ");
        System.out.println("\n");
    }
    
    
    @Test
    public void test_VolatileTupleSpace_read_ContentTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_read_ContentTuple ");
        System.out.println("\n");
        
        this.CNT = null;
        this.CNT = this.VTS.read_ContentTuple();
        System.out.println("executing read_ContentTuple() ");
        if (this.CNT != null)
        {
            System.out.println("tuple ID field is: " + this.CNT.get_DestinationID_Field());
            System.out.println("tuple Sequence Number field is: " + this.CNT.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + this.CNT.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_read_ContentTuple ");
        System.out.println("\n");
    }    
    
    @Test
    public void test_VolatileTupleSpace_take_ContentTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace_take_ContentTuple ");
        System.out.println("\n");
        
        this.CNT = null;
        this.CNT = this.VTS.take_ContentTuple();
        System.out.println("executing take_ContentTuple() ");
        if (this.CNT != null)
        {
            System.out.println("tuple ID field is: " + this.CNT.get_DestinationID_Field());
            System.out.println("tuple Sequence Number field is: " + this.CNT.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + this.CNT.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_VolatileTupleSpace_take_ContentTuple ");
            System.out.println("\n");
            return;
        }
        
        this.CNT = null;
        this.CNT = this.VTS.take_ContentTuple();
        System.out.println("executing take_ContentTuple() ");
        if (this.CNT != null)
        {    
            System.out.println("tuple ID field is: " + this.CNT.get_DestinationID_Field());
            System.out.println("tuple Sequence Number field is: " + this.CNT.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + this.CNT.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace_take_ContentTuple ");
        System.out.println("\n");
    }    
    
    
    
    @Test
    public void test_VolatileTupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace ");
        System.out.println("\n"); 
     
        /* set to direct the execution of test methods with valid input only */
        this.TerminateNow = true;
        
        this.test_VolatileTupleSpace_create_TupleSpace();
        
        this.test_VolatileTupleSpace_count_Tuples();
        
        
        this.test_VolatileTupleSpace_append_ControlTuple();
        
        this.test_VolatileTupleSpace_count_Tuples();
        
        this.test_VolatileTupleSpace_read_ControlTuple();
        
        
        this.test_VolatileTupleSpace_append_ContentTuple();
        
        this.test_VolatileTupleSpace_count_Tuples();
        
        this.test_VolatileTupleSpace_read_ContentTuple();
        
        
        this.test_VolatileTupleSpace_take_ContentTuple();
        
        this.test_VolatileTupleSpace_take_ControlTuple();
        
        this.test_VolatileTupleSpace_count_Tuples();
        
        
        this.test_VolatileTupleSpace_delete_TupleSpace();
    
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_VolatileTupleSpace ");
        System.out.println("\n");
    } 
    
}
