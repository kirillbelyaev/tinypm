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

import edu.csu.lpm.TSL.implementation.ContentTuple_implement;
import edu.csu.lpm.TSL.implementation.ControlTuple_implement;
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
public class TSL_UnitTests {
    
    public TSL_UnitTests() {
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
    
    
    private final String FIELD_APP_PATH_VALID = "/bin/ping";
    private final String FIELD_APP_PATH_INVALID = "/bin/non_existent/ping";
    private final String FIELD_RequestMessage = "/usr/home/containers/container-b/data-pool/runtime-log.txt";
    private final String FIELD_XML_CoordinationMessage = "<XML_MESSAGE length=\"0000060\"><field>8</field></XML_MESSAGE>";
    private final String FIELD_Payload = "^[%G[^[[32m  OK  ^[[0m] Started Show Plymouth Boot Screen.\n" +
"[^[[32m  OK  ^[[0m] Reached target Paths.\n" +
"[^[[32m  OK  ^[[0m] Reached target Basic System.\n" +
"[^[[32m  OK  ^[[0m] Reached target Initrd Default Target.\n" +
"Starting dracut pre-pivot and cleanup hook...\n" +
"[^[[32m  OK  ^[[0m] Started dracut pre-pivot and cleanup hook.\n" +
"Starting Cleaning Up and Shutting Down Daemons...\n" +
"[^[[32m  OK  ^[[0m] Stopped target Timers.\n" +
"Starting Plymouth switch root service...\n";

    
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
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field(null);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field("");
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        
        int_value = ct.set_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        int_value = ct.set_RequestMessage_Field(this.FIELD_XML_CoordinationMessage);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        int_value = ct.set_RequestMessage_Field(null);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        int_value = ct.set_RequestMessage_Field("");
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("Type is: " + string_value);
        
        ct.set_Type_Field_to_Collaboration();
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("Type is: " + string_value);
        
        ct.set_Type_Field_to_Coordination();
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("Type is: " + string_value);
        
        
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
        Integer sqn = 0;
        StringBuffer body_value = null; 
        StringBuffer body = new StringBuffer();
        body.append(this.FIELD_Payload);
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field(null);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field("");
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        
        sqn = ct.get_SequenceNumber_Field();
        assertNotNull(sqn);
        System.out.println("SequenceNumber is: " + sqn);
        
        int_value = ct.set_SequenceNumber_Field(null);
        System.out.println("method return value is: " + int_value);
        
        sqn = ct.get_SequenceNumber_Field();
        assertNotNull(sqn);
        System.out.println("SequenceNumber is: " + sqn);
        
        
        int_value = ct.set_SequenceNumber_Field(0);
        System.out.println("method return value is: " + int_value);
        
        sqn = ct.get_SequenceNumber_Field();
        assertNotNull(sqn);
        System.out.println("SequenceNumber is: " + sqn);
        
        int_value = ct.set_Payload_Field(null);
        System.out.println("method return value is: " + int_value);
        
        int_value = ct.set_Payload_Field(body);
        System.out.println("method return value is: " + int_value);
        
        body_value = ct.get_Payload_Field();
        assertNotNull(body_value);
        System.out.println("Payload is: " + body_value);
        
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ContentTuple... ");
        System.out.println("\n");
    }
    
}
