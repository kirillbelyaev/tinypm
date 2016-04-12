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
import edu.csu.lpm.TSLib.implementation.PersistentTupleSpace_implement;
import edu.csu.lpm.TSLib.implementation.VolatileTupleSpace_implement;
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
public class TSLib_UnitTests_Tuples {
    
    public TSLib_UnitTests_Tuples() {
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

    String ValidLocation = System.getProperty("user.home");
    String InvalidLocation = "/non/existent/dir";
    String EmptyString = "";
    String RootDir = "/";
    
    StringBuffer Body = new StringBuffer();
    
    int IntValue = -1;
    
    boolean TerminateNow = false;
    
    PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
    ControlTuple_implement CLT = new ControlTuple_implement();
    ContentTuple_implement CNT = new ContentTuple_implement();
    
    @Test
    public void test_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple... ");
        System.out.println("\n"); 
        
        
        ControlTuple_implement ct = new ControlTuple_implement();
        String string_value = null;
        int int_value = -1;
        boolean result = false;
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_ID_Field(null);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_ID_Field("");
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("executing get_RequestMessage_Field() ");
        System.out.println("RequestMessage is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_RequestMessage_Field(this.FIELD_XML_CoordinationMessage);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("executing get_RequestMessage_Field() ");
        System.out.println("RequestMessage is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_RequestMessage_Field(null);
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("executing get_RequestMessage_Field() ");
        System.out.println("RequestMessage is: " + string_value);
        System.out.println("\n"); 
        
        int_value = ct.set_RequestMessage_Field("");
        System.out.println("executing set_RequestMessage_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("executing get_RequestMessage_Field() ");
        System.out.println("RequestMessage is: " + string_value);
        System.out.println("\n"); 
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("executing get_Type_Field() ");
        System.out.println("Type is: " + string_value);
        System.out.println("\n"); 
        
        ct.set_Type_Field_to_Collaboration();
        System.out.println("executing set_Type_Field_to_Collaboration() ");
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("executing get_Type_Field() ");
        System.out.println("Type is: " + string_value);
        System.out.println("\n"); 
        
        ct.set_Type_Field_to_Coordination();
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("executing get_Type_Field() ");
        System.out.println("Type is: " + string_value);
        System.out.println("\n"); 
        
        /* now test matching */
        
        result = ct.match_on_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_ID_Field(null);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_ID_Field("");
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        
        result = ct.match_on_Type_Field(Tuple.TupleTypes.COLLABORATION.toString());
        System.out.println("executing match_on_Type_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        
        result = ct.match_on_Type_Field(Tuple.TupleTypes.COORDINATION.toString());
        System.out.println("executing match_on_Type_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_Type_Field(" ");
        System.out.println("executing match_on_Type_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        
        result = ct.match_on_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("executing match_on_RequestMessage_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        
        result = ct.match_on_RequestMessage_Field(this.FIELD_XML_CoordinationMessage);
        System.out.println("executing match_on_RequestMessage_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_RequestMessage_Field(" ");
        System.out.println("executing match_on_RequestMessage_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple... ");
        System.out.println("\n"); 
    }
    
    
    @Test
    public void test_ContentTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ContentTuple... ");
        System.out.println("\n"); 
        
        
        ContentTuple_implement ct = new ContentTuple_implement();
        String string_value = null;
        int int_value = -1;
        boolean result = false;
        
        Integer sqn = 0;
        StringBuffer body_value = null; 
        StringBuffer body = new StringBuffer();
        body.append(this.FIELD_Payload);
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n"); 
        
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n");
        
        int_value = ct.set_ID_Field(null);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n");
        
        int_value = ct.set_ID_Field("");
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n");
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing set_ID_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("executing get_ID_Field() ");
        System.out.println("ID is: " + string_value);
        System.out.println("\n");
        
        
        sqn = ct.get_SequenceNumber_Field();
        assertNotNull(sqn);
        System.out.println("executing get_SequenceNumber_Field() ");
        System.out.println("SequenceNumber is: " + sqn);
        System.out.println("\n");
        
        int_value = ct.set_SequenceNumber_Field(null);
        System.out.println("executing set_SequenceNumber_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        sqn = ct.get_SequenceNumber_Field();
        assertNotNull(sqn);
        System.out.println("executing get_SequenceNumber_Field() ");
        System.out.println("SequenceNumber is: " + sqn);
        System.out.println("\n");
        
        
        int_value = ct.set_SequenceNumber_Field(0);
        System.out.println("executing set_SequenceNumber_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        sqn = ct.get_SequenceNumber_Field();
        assertNotNull(sqn);
        System.out.println("executing get_SequenceNumber_Field() ");
        System.out.println("SequenceNumber is: " + sqn);
        System.out.println("\n");
        
        int_value = ct.set_Payload_Field(null);
        System.out.println("executing set_Payload_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ct.set_Payload_Field(body);
        System.out.println("executing set_Payload_Field() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        body_value = ct.get_Payload_Field();
        assertNotNull(body_value);
        System.out.println("executing get_Payload_Field() ");
        System.out.println("Payload is: " + body_value);
        System.out.println("\n");
        
        
        /* now test matching */
        
        result = ct.match_on_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_ID_Field(null);
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_ID_Field("");
        System.out.println("executing match_on_ID() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_SequenceNumber_Field(1);
        System.out.println("executing match_on_SequenceNumber_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_SequenceNumber_Field(sqn);
        System.out.println("executing match_on_SequenceNumber_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        result = ct.match_on_SequenceNumber_Field(null);
        System.out.println("executing match_on_SequenceNumber_Field() ");
        System.out.println("result is: " + result);
        System.out.println("\n"); 
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple... ");
        System.out.println("\n");
    }
    
     
    
}
