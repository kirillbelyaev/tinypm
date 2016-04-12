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
public class TSLib_UnitTests_VolatileTupleSpace {
    
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
    
    
    /* VTS tests */
    
    @Test
    public void test_VolatileTupleSpace()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_VolatileTupleSpace ");
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
        System.out.println("finished test_VolatileTupleSpace ");
        System.out.println("\n");
    } 
    
}
