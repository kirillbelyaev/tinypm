/*
 * Copyright (C) 2016 kirill.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

import edu.csu.lpm.TSLib.implementation.ContentTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;
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
public class TSLib_UnitTests_ContentTuple {
    
    public TSLib_UnitTests_ContentTuple() {
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
    public void test_ContentTuple_set_ID_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple_set_ID_Field() ");
        System.out.println("\n"); 
        
        this.IntValue = this.CNT.set_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_ContentTuple_set_ID_Field() ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.CNT.set_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CNT.set_ID_Field(this.EmptyString);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CNT.set_ID_Field(this.RootDir);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        this.IntValue = this.CNT.set_ID_Field(null);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple_set_ID_Field() ");
        System.out.println("\n"); 
    }
    
    
    @Test
    public void test_ContentTuple_get_ID_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple_get_ID_Field() ");
        System.out.println("\n"); 
        
        this.StringValue = this.CNT.get_ID_Field();
        assertNotNull(this.StringValue);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID field is: " + this.StringValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple_get_ID_Field() ");
        System.out.println("\n");
    }
    
    @Test
    public void test_ContentTuple_set_SequenceNumber_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple_set_SequenceNumber_Field() ");
        System.out.println("\n"); 
        
        this.IntValue = this.CNT.set_SequenceNumber_Field(this.SampleSequenceNumber);
        System.out.println("executing set_SequenceNumber_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_ContentTuple_set_SequenceNumber_Field() ");
            System.out.println("\n");
            return;
        }
        
        
        this.IntValue = this.CNT.set_SequenceNumber_Field(null);
        System.out.println("executing set_SequenceNumber_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple_set_SequenceNumber_Field() ");
        System.out.println("\n"); 
    }
    
    @Test
    public void test_ContentTuple_get_SequenceNumber_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple_get_SequenceNumber_Field() ");
        System.out.println("\n"); 
        
        this.IntValue = this.CNT.get_SequenceNumber_Field();
        System.out.println("executing get_SequenceNumber_Field() ");
        System.out.println("SequenceNumber field is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple_get_SequenceNumber_Field() ");
        System.out.println("\n");
    }
    
    @Test
    public void test_ContentTuple_set_Payload_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple_set_Payload_Field() ");
        System.out.println("\n"); 
        
        this.Body.append(this.FIELD_Payload);
        
        this.IntValue = this.CNT.set_Payload_Field(this.Body);
        System.out.println("executing set_Payload_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_ContentTuple_set_Payload_Field() ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.CNT.set_Payload_Field(null);
        System.out.println("executing set_Payload_Field() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n"); 
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple_set_Payload_Field() ");
        System.out.println("\n"); 
    }
    
    @Test
    public void test_ContentTuple_get_Payload_Field()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple_get_Payload_Field() ");
        System.out.println("\n"); 
        
        this.StringBufferValue = this.CNT.get_Payload_Field();
        //assertNotNull(this.StringBufferValue);
        System.out.println("executing get_Payload_Field() ");
        System.out.println("Payload field is: " + this.StringBufferValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple_get_Payload_Field() ");
        System.out.println("\n");
    }
    
    @Test
    public void test_ContentTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple ");
        System.out.println("\n");
        
        /* set to direct the execution of test methods with valid input only */
        this.TerminateNow = true;
        
        this.test_ContentTuple_set_ID_Field();
        this.test_ContentTuple_get_ID_Field();
        
        this.test_ContentTuple_set_SequenceNumber_Field();
        this.test_ContentTuple_get_SequenceNumber_Field();
        
        this.test_ContentTuple_set_Payload_Field();
        this.test_ContentTuple_get_Payload_Field();
        
        /* now test matching */
        
        this.Result = this.CNT.match_on_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CNT.match_on_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CNT.match_on_ID_Field(null);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CNT.match_on_ID_Field(this.EmptyString);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CNT.match_on_SequenceNumber_Field(this.SampleSequenceNumber);
        System.out.println("executing match_on_SequenceNumber_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        this.Result = this.CNT.match_on_SequenceNumber_Field(null);
        System.out.println("executing match_on_SequenceNumber_Field() ");
        System.out.println("result is: " + this.Result);
        System.out.println("\n"); 
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple ");
        System.out.println("\n");
    }
    
     
    
}