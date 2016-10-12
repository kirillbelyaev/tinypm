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

import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.implementation.DB_Dispatcher;
import edu.csu.lpm.DB.implementation.RecordDAO_implement;
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;
import java.sql.SQLException;
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
public class DB_CapabilitiesClassesTable_UnitTests 
{   
    private final String COLUMN_APP_DESC = "icmp ping tool";
    private final String COLUMN_APP_PATH = "/bin/ping";
    private final String COLUMN_APP_PATH_INVALID = "/bin/x/ping";
    private final String COLUMN_POLICY_CLASS_ID = "1";
    private final String COLUMN_APP_CONTAINER_ID = "1";
    private final String COLUMN_STATUS = "1";
    private final String COLUMN_POLICY_CLASS_NAME = "general applications policy class";
    
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

    @Test
    public void test_Capabilities_Classes_Table_create_drop() throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Capabilities_Classes_Table_create_drop... ");

        int output = -1;
        boolean Out;
        
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_CAPC_DB();
	assertTrue("drop_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_CAPC_DB();
	assertTrue("create_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Capabilities_Classes_Table_create_drop... ");
    }
    
    @Test
    public void test_Capabilities_Classes_Table_CRUD_operations() throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Capabilities_Classes_Table_CRUD_operations... ");
        
        int output = -1;
        boolean Out;
        
        CapabilitiesClassesTableRecord r = new CapabilitiesClassesTableRecord();
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_CAPC_DB();
	assertTrue("drop_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_CAPC_DB();
	assertTrue("create_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
       
        r.set_COLUMN_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.set_COLUMN_CLASS_NAME(this.COLUMN_POLICY_CLASS_NAME);
        r.set_COLUMN_STATUS(this.COLUMN_STATUS);
        
       
        r.set_COLUMN_STATUS_Active();
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        
        
        
        output = db.count_Distinct_Capabilities_Classes_Table_Records_on_CID();
	assertNotNull(output);
        System.out.println("count_Distinct_Policy_Classes_Table_Records_on_PCID: count is: " + output);
        
        
        CapabilitiesClassesTableRecord[] recs = (CapabilitiesClassesTableRecord[]) db.read_Capabilities_Classes_Table_Records_On_CID(r);
        assertTrue("read_Policy_Classes_Table_Records_On_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("read_Policy_Classes_Table_Records_On_PCID: array value is: " + recs);
        
        
        output = db.write_CapabilitiesClassesTableRecord(r);
        assertNotNull(output);
        System.out.println("write_Policy_Classes_Table_Record: value is: " + output);
        
        
        output = db.count_Distinct_Capabilities_Classes_Table_Records_on_CID();
	assertNotNull(output);
        System.out.println("count_Distinct_Policy_Classes_Table_Records_on_PCID: count is: " + output);
        
        recs = (CapabilitiesClassesTableRecord[]) db.read_Capabilities_Classes_Table_Records_On_CID(r);
        assertTrue("read_Policy_Classes_Table_Records_On_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   PCID is: " + recs[0].get_COLUMN_CLASS_ID());
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   class name is: " + recs[0].get_COLUMN_CLASS_NAME());
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   CAPS are: " + recs[0].get_COLUMN_CAPABILITIES());
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   status is: " + recs[0].get_COLUMN_STATUS());
        }
        
        
        recs = (CapabilitiesClassesTableRecord[]) db.read_Capabilities_Classes_Table_Records_On_All_Classes();
        assertTrue("read_Policy_Classes_Table_Records_On_All_Classes: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   PCID is: " + recs[0].get_COLUMN_CLASS_ID());
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   class name is: " + recs[0].get_COLUMN_CLASS_NAME());
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   CAPS are: " + recs[0].get_COLUMN_CAPABILITIES());
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   status is: " + recs[0].get_COLUMN_STATUS());
        }
        
        output = db.delete_Capabilities_Classes_Table_Records_On_CID(r);
	assertTrue("delete_Policy_Classes_Table_Records_On_PCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_Policy_Classes_Table_Records_On_PCID: value is: " + output);
        
        output = db.count_Distinct_Capabilities_Classes_Table_Records_on_CID();
	assertNotNull(output);
        System.out.println("count_Distinct_Policy_Classes_Table_Records_on_PCID: count is: " + output);
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Capabilities_Classes_Table_CRUD_operations... ");
    }    
    
}
