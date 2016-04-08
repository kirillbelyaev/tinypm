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
public class TSLib_UnitTests {
    
    public TSLib_UnitTests() {
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
    int IntValue = -1;
    
    PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
    ControlTuple_implement CLT = new ControlTuple_implement();
    
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
    
    
    @Test
    public void test_TupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_TupleSpace... ");
        System.out.println("\n"); 
     
     
        VolatileTupleSpace_implement ts = new VolatileTupleSpace_implement();
        String string_value = null;
        int int_value = -1;
        
        ControlTuple_implement ct = new ControlTuple_implement();
        ct.set_Type_Field_to_Coordination();
        
        ContentTuple_implement ctt = new ContentTuple_implement();
        ctt.set_SequenceNumber_Field(100);
        
        
        string_value = ts.get_TupleSpaceName();
        assertNotNull(string_value);
        System.out.println("executing get_TupleSpaceName() ");
        System.out.println("TupleSpaceName is: " + string_value);
        System.out.println("\n");
        
        int_value = ts.create_TupleSpace();
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.create_TupleSpace();
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.delete_TupleSpace();
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.delete_TupleSpace();
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.create_TupleSpace();
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.append_ControlTuple(ct);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        ct = null;
        ct = ts.read_ControlTuple();
        System.out.println("executing read_ControlTuple() ");
        if (ct != null) 
            System.out.println("tuple type is: " + ct.get_Type_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        int_value = ts.append_ControlTuple(ct);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        ctt = null;
        ctt = ts.read_ContentTuple();
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null) 
            System.out.println("content tuple seq_num is: " + ctt.get_SequenceNumber_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");

        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.append_ContentTuple(ctt);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        ctt = new ContentTuple_implement();
        ctt.set_SequenceNumber_Field(100);
        
        int_value = ts.append_ContentTuple(ctt);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        ctt = null;
        ctt = ts.read_ContentTuple();
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null) 
            System.out.println("content tuple seq_num is: " + ctt.get_SequenceNumber_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");

        int_value = ts.append_ContentTuple(ctt);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        ct = null;
        ct = ts.take_ControlTuple();
        System.out.println("executing take_ControlTuple() ");
        if (ct != null) 
            System.out.println("tuple type is: " + ct.get_Type_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        ct = null;
        ct = ts.take_ControlTuple();
        System.out.println("executing take_ControlTuple() ");
        if (ct != null) 
            System.out.println("tuple type is: " + ct.get_Type_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        ctt = null;
        ctt = ts.take_ContentTuple();
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null) 
            System.out.println("content tuple seq_num is: " + ctt.get_SequenceNumber_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");

        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        ctt = null;
        ctt = ts.take_ContentTuple();
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null) 
            System.out.println("content tuple seq_num is: " + ctt.get_SequenceNumber_Field());
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");

        
        int_value = ts.count_Tuples();
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        int_value = ts.delete_TupleSpace();
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        int_value = ts.delete_TupleSpace();
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_TupleSpace... ");
        System.out.println("\n");
    }
    
    /* PTS */
    
    @Test
    public void test_PersistenceTupleSpace_create_TupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceTupleSpace_create_TupleSpace... ");
        System.out.println("\n");
                
        this.IntValue = this.PTS.create_TupleSpace(this.ValidLocation);
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.create_TupleSpace(this.InvalidLocation);
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.create_TupleSpace(this.EmptyString);
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.create_TupleSpace(this.RootDir);
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.create_TupleSpace(null);
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.create_TupleSpace(this.ValidLocation);
        System.out.println("executing create_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
    
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceTupleSpace_create_TupleSpace... ");
        System.out.println("\n");
    }


    @Test
    public void test_PersistenceTupleSpace_delete_TupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceTupleSpace_delete_TupleSpace... ");
        System.out.println("\n");
        
        this.IntValue = this.PTS.delete_TupleSpace(this.ValidLocation);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.delete_TupleSpace(this.InvalidLocation);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.delete_TupleSpace(this.EmptyString);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.delete_TupleSpace(this.RootDir);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.delete_TupleSpace(null);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.delete_TupleSpace(this.ValidLocation);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceTupleSpace_delete_TupleSpace... ");
        System.out.println("\n");
    }    
    
    
    @Test
    public void test_PersistenceTupleSpace_count_Tuples()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceTupleSpace_count_Tuples... ");
        System.out.println("\n");
        
        this.IntValue = this.PTS.count_Tuples(this.ValidLocation);
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.count_Tuples(this.InvalidLocation);
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.count_Tuples(this.EmptyString);
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.count_Tuples(this.RootDir);
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.count_Tuples(null);
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.count_Tuples(this.ValidLocation);
        System.out.println("executing count_Tuples() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceTupleSpace_count_Tuples... ");
        System.out.println("\n");
    }    
    
    
    @Test
    public void test_PersistenceTupleSpace_append_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceTupleSpace_append_ControlTuple... ");
        System.out.println("\n");
        
        if (this.CLT == null) this.CLT = new ControlTuple_implement();
        
        this.CLT.set_ID_Field(this.FIELD_APP_PATH_VALID);
        this.CLT.set_Type_Field_to_Collaboration();
        this.CLT.set_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("setting ControlTuple fields ");
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(this.CLT, this.ValidLocation);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(null, this.ValidLocation);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(this.CLT, this.InvalidLocation);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(null, this.InvalidLocation);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(this.CLT, this.EmptyString);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(null, this.EmptyString);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(this.CLT, this.RootDir);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(null, this.RootDir);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(this.CLT, null);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(null, null);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.PTS.append_ControlTuple(this.CLT, this.ValidLocation);
        System.out.println("executing append_ControlTuple() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceTupleSpace_append_ControlTuple... ");
        System.out.println("\n");
    }    
    
    @Test
    public void test_PersistenceTupleSpace_read_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceTupleSpace_read_ControlTuple... ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.read_ControlTuple(this.ValidLocation);
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.read_ControlTuple(this.InvalidLocation);
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.read_ControlTuple(this.EmptyString);
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.read_ControlTuple(this.RootDir);
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.read_ControlTuple(null);
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.read_ControlTuple(this.ValidLocation);
        System.out.println("executing read_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceTupleSpace_read_ControlTuple... ");
        System.out.println("\n");
    }    
    
    
    @Test
    public void test_PersistenceTupleSpace_take_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceTupleSpace_take_ControlTuple... ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.take_ControlTuple(this.ValidLocation);
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.take_ControlTuple(this.InvalidLocation);
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.take_ControlTuple(this.EmptyString);
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.take_ControlTuple(this.RootDir);
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.take_ControlTuple(null);
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        this.CLT = null;
        this.CLT = this.PTS.take_ControlTuple(this.ValidLocation);
        System.out.println("executing take_ControlTuple() ");
        if (this.CLT != null)
        {    
            System.out.println("tuple Type field is: " + this.CLT.get_Type_Field());
            System.out.println("tuple ID field is: " + this.CLT.get_ID_Field());
            System.out.println("tuple Request field is: " + this.CLT.get_RequestMessage_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceTupleSpace_take_ControlTuple... ");
        System.out.println("\n");
    }    
    
    
    @Test
    public void test_PersistenceManager()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_PersistenceManager... ");
        System.out.println("\n"); 
        
        String valid_location = System.getProperty("user.home");
        String invalid_location = "/non/existent/dir";
        
        PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
        ControlTuple_implement CLT = new ControlTuple_implement();
        ContentTuple_implement ctt = new ContentTuple_implement();
        
        StringBuffer body_value = null; 
        StringBuffer body = new StringBuffer();
        body.append(this.FIELD_Payload);
        
        
        String string_value = null;
        int int_value = -1;
        boolean result = false;
        
        
        ctt.set_ID_Field(this.FIELD_APP_PATH_VALID);
        ctt.set_SequenceNumber_Field(101);
        ctt.set_Payload_Field(body);
        System.out.println("setting ContentTuple fields ");
        System.out.println("\n");
        
        int_value = PTS.append_ContentTuple(ctt, valid_location);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = PTS.append_ContentTuple(ctt, invalid_location);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = PTS.append_ContentTuple(null, valid_location);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = PTS.append_ContentTuple(null, null);
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = PTS.append_ContentTuple(null, "");
        System.out.println("executing append_ContentTuple() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        int_value = PTS.count_Tuples(valid_location);
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        ctt = null;
        ctt = PTS.read_ContentTuple(valid_location);
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        ctt = null;
        ctt = PTS.read_ContentTuple(invalid_location);
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        ctt = null;
        ctt = PTS.read_ContentTuple("");
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        ctt = null;
        ctt = PTS.read_ContentTuple(null);
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        ctt = null;
        ctt = PTS.read_ContentTuple("/");
        System.out.println("executing read_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        ctt = null;
        ctt = PTS.take_ContentTuple(valid_location);
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        ctt = null;
        ctt = PTS.take_ContentTuple(invalid_location);
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        ctt = null;
        ctt = PTS.take_ContentTuple("");
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        ctt = null;
        ctt = PTS.take_ContentTuple("/");
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        ctt = null;
        ctt = PTS.take_ContentTuple(null);
        System.out.println("executing take_ContentTuple() ");
        if (ctt != null)
        {    
            System.out.println("tuple ID field is: " + ctt.get_ID_Field());
            System.out.println("tuple Suquence Number field is: " + ctt.get_SequenceNumber_Field());
            System.out.println("tuple Payload field is: " + ctt.get_Payload_Field());
        }    
        else
            System.out.println("NULL - no match ");
        System.out.println("\n");
        
        
        int_value = PTS.count_Tuples(valid_location);
        System.out.println("executing countTuples() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        int_value = PTS.delete_TupleSpace(valid_location);
        System.out.println("executing delete_TupleSpace() ");
        System.out.println("method return value is: " + int_value);
        System.out.println("\n");
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_PersistenceManager... ");
        System.out.println("\n");
        
    }    
    
}
