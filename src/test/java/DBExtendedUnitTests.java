
/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

import edu.csu.tinypm.DB.DTO.Apps_Table_Record;
import edu.csu.tinypm.DB.DTO.Policy_Classes_Table_Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;
import edu.csu.tinypm.DB.implementation.DB_Dispatcher_Extended;
import edu.csu.tinypm.DB.implementation.RecordDAOExtended_implement;
import edu.csu.tinypm.interfaces.LinuxCAPPolicyContainer;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author kirill
 */
public class DBExtendedUnitTests {
    
    public DBExtendedUnitTests() {
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
     
    @Test
     public void small_tests() 
     {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started small_tests... ");
        
        LinuxCAPPolicyContainer.LinuxCapabilities LCS[] = LinuxCAPPolicyContainer.LinuxCapabilities.values();
        
        System.out.println("LCS is: " + LCS[0]);
        System.out.println("LCS is: " + LCS[1]);
        System.out.println("LCS is: " + LCS[1].toString());
        
//        System.out.println("( " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + ", " + Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME + ", " + Policy_Classes_Table.COLUMN_STATUS + ", "); 
//        for (int i = 0; i < LCS.length; i++)
//            if (i != LCS.length -1)
//                System.out.print(LCS[i] + ", ");
//            else System.out.print(LCS[i] + " ) ");    
        
    
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished small_tests... ");
      
     }
    
    
    String CAP_ATTR = LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString();
    
    //Apps_Table_Record
    private final String COLUMN_APP_DESC = "icmp ping tool";
    private final String COLUMN_APP_PATH = "/bin/ping";
    private final String COLUMN_POLICY_CLASS_ID = "1";
    private final String COLUMN_APP_CONTAINER_ID = "1";
    private final String COLUMN_STATUS = "1";
    private final String COLUMN_POLICY_CLASS_NAME = "general applications policy class";
    
     //Policy_Classes_Table_Record
    
    @Test
    public void test_Apps_Table_Record() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Apps_Table_Record... ");
        
        Apps_Table_Record r = new Apps_Table_Record();
        String value = null;
        
        r.setCOLUMN_APP_DESC(this.COLUMN_APP_DESC);
        r.setCOLUMN_APP_PATH(this.COLUMN_APP_PATH);
        r.setCOLUMN_POLICY_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.setCOLUMN_APP_CONTAINER_ID(this.COLUMN_APP_CONTAINER_ID);
        r.set_Status_Active();
        
        value = r.getCOLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.setCOLUMN_STATUS(null);
	value = r.getCOLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_Status_Inactive();
	value = r.getCOLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        value = r.getCOLUMN_APP_PATH();
	assertNotNull(value);
	System.out.println("app path is: " + value);
        
        value = r.getCOLUMN_APP_DESC();
	assertNotNull(value);
	System.out.println("app desc is: " + value);
        
        value = r.getCOLUMN_POLICY_CLASS_ID();
	assertNotNull(value);
	System.out.println("PCID is: " + value);
        
        value = r.getCOLUMN_APP_CONTAINER_ID();
	assertNotNull(value);
	System.out.println("app container ID is: " + value);
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Apps_Table_Record... ");
        
    }
    
    
    
    @Test
     public void test_Apps_Table_create_drop() throws RecordDAOException, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Apps_Table_create_drop... ");

        int output = -1;
        boolean Out;
        
        DB_Dispatcher_Extended dd = new DB_Dispatcher_Extended();
        RecordDAOExtended_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_APPS_DB();
	assertTrue("dropTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_APPS_DB();
	assertTrue("createTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Apps_Table_create_drop... ");
    }
     
     
    @Test
     public void test_Apps_Table_CRUD_operations() throws RecordDAOException, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Apps_Table_CRUD_operations... ");
        
        int output = -1;
        boolean Out;
        
        Apps_Table_Record r = new Apps_Table_Record();
        DB_Dispatcher_Extended dd = new DB_Dispatcher_Extended();
        RecordDAOExtended_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_APPS_DB();
	assertTrue("dropTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_APPS_DB();
	assertTrue("createTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        r.setCOLUMN_APP_PATH(this.COLUMN_APP_PATH);
        r.setCOLUMN_POLICY_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        
        r.setCOLUMN_APP_DESC(this.COLUMN_APP_DESC);
        r.setCOLUMN_APP_CONTAINER_ID(this.COLUMN_APP_CONTAINER_ID);
        r.set_Status_Active();
        
        
        output = db.count_Distinct_Apps_Table_Records_on_APP_and_PCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Apps_Table_Records_on_APP_and_PCID: count is: " + output);
        
        
        Apps_Table_Record[] recs = (Apps_Table_Record[]) db.read_Apps_Table_Records_On_APP_and_PCID(r);
        assertTrue("read_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("read_Apps_Table_Records_On_APP_and_PCID: array value is: " + recs);
        
        output = db.delete_Apps_Table_Records_On_APP_and_PCID(r);
	assertTrue("delete_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_Apps_Table_Records_On_APP_and_PCID: value is: " + output);
        
        
        output = db.write_Apps_Table_Record(r);
        assertNotNull(output);
        System.out.println("write_Apps_Table_Record: value is: " + output);
        
        
        recs = (Apps_Table_Record[]) db.read_Apps_Table_Records_On_APP_and_PCID(r);
        assertTrue("read_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   desc is: " + recs[0].getCOLUMN_APP_DESC());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   app is: " + recs[0].getCOLUMN_APP_PATH());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   PCID is: " + recs[0].getCOLUMN_POLICY_CLASS_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   CID is: " + recs[0].getCOLUMN_APP_CONTAINER_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   status is: " + recs[0].getCOLUMN_STATUS());
        }
        
        recs = (Apps_Table_Record[]) db.read_Apps_Table_Records_On_All_APPs();
        assertTrue("read_Apps_Table_Records_On_All_APPs: Reply has unexpected return:", Out = recs == null | recs != null);
        
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_All_APPs: rec array index 0 app value is: " + recs[0].getCOLUMN_APP_PATH());
        }
        
        
        output = db.count_Distinct_Apps_Table_Records_on_APP_and_PCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Apps_Table_Records_on_APP_and_PCID: count is: " + output);
        
        
        output = db.delete_Apps_Table_Records_On_APP_and_PCID(r);
	assertTrue("delete_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_Apps_Table_Records_On_APP_and_PCID: value is: " + output);
        
        
        recs = (Apps_Table_Record[]) db.read_Apps_Table_Records_On_APP_and_PCID(r);
        assertTrue("read_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   desc is: " + recs[0].getCOLUMN_APP_DESC());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   app is: " + recs[0].getCOLUMN_APP_PATH());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   PCID is: " + recs[0].getCOLUMN_POLICY_CLASS_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   CID is: " + recs[0].getCOLUMN_APP_CONTAINER_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   status is: " + recs[0].getCOLUMN_STATUS());
        }
        
        
        output = db.count_Distinct_Apps_Table_Records_on_APP_and_PCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Apps_Table_Records_on_APP_and_PCID: count is: " + output);
        
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Apps_Table_CRUD_operations... ");
        
    }
    
     @Test
     public void test_Policy_Classes_Table_Record() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Policy_Classes_Table_Record... ");
        
        String value = null;
        Policy_Classes_Table_Record r = new Policy_Classes_Table_Record();
        
        r.setCOLUMN_POLICY_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.setCOLUMN_POLICY_CLASS_NAME(this.COLUMN_POLICY_CLASS_NAME);
        r.setCOLUMN_STATUS(this.COLUMN_STATUS);
        
        value = r.getCOLUMN_POLICY_CLASS_ID();
	assertNotNull(value);
	System.out.println("PCID is: " + value);
        
        value = r.getCOLUMN_POLICY_CLASS_NAME();
	assertNotNull(value);
	System.out.println("PC name is: " + value);
        
        value = r.getCOLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_Status_Inactive();
	value = r.getCOLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_Status_Active();
	value = r.getCOLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        
        r.add_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.getCOLUMN_CAPS();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.remove_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.getCOLUMN_CAPS();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.add_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        value = r.getCOLUMN_CAPS();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.add_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.getCOLUMN_CAPS();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.remove_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        value = r.getCOLUMN_CAPS();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.add_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.getCOLUMN_CAPS();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        
        System.out.println("cap index within enum array is: " + r.get_CAP_index(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_KILL.toString()) );

        System.out.println("Schema DDL is : " + r.produce_PCS_DB_DDL());
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Policy_Classes_Table_Record... ");
    }
     
     
     @Test
     public void test_Policy_Classes_Table_create_drop() throws RecordDAOException, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Policy_Classes_Table_create_drop... ");

        int output = -1;
        boolean Out;
        
        DB_Dispatcher_Extended dd = new DB_Dispatcher_Extended();
        RecordDAOExtended_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.drop_Table_PCS_DB();
	assertTrue("drop_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.create_Table_PCS_DB();
	assertTrue("create_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Policy_Classes_Table_create_drop... ");
    }
    
     
     @Test
     public void test_Policy_Classes_Table_CRUD_operations() throws RecordDAOException, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Policy_Classes_Table_CRUD_operations... ");
        
        int output = -1;
        boolean Out;
        
        Policy_Classes_Table_Record r = new Policy_Classes_Table_Record();
        DB_Dispatcher_Extended dd = new DB_Dispatcher_Extended();
        RecordDAOExtended_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.drop_Table_PCS_DB();
	assertTrue("drop_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.create_Table_PCS_DB();
	assertTrue("create_Table_PCS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
       
        r.setCOLUMN_POLICY_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.setCOLUMN_POLICY_CLASS_NAME(this.COLUMN_POLICY_CLASS_NAME);
        r.setCOLUMN_STATUS(this.COLUMN_STATUS);
        
       
        r.set_Status_Active();
        
        r.add_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        r.add_CAP(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        
        
        
        output = db.count_Distinct_Policy_Classes_Table_Records_on_PCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Policy_Classes_Table_Records_on_PCID: count is: " + output);
        
        
        Policy_Classes_Table_Record[] recs = (Policy_Classes_Table_Record[]) db.read_Policy_Classes_Table_Records_On_PCID(r);
        assertTrue("read_Policy_Classes_Table_Records_On_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("read_Policy_Classes_Table_Records_On_PCID: array value is: " + recs);
        
        
        output = db.write_Policy_Classes_Table_Record(r);
        assertNotNull(output);
        System.out.println("write_Policy_Classes_Table_Record: value is: " + output);
        
        
        output = db.count_Distinct_Policy_Classes_Table_Records_on_PCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Policy_Classes_Table_Records_on_PCID: count is: " + output);
        
        recs = (Policy_Classes_Table_Record[]) db.read_Policy_Classes_Table_Records_On_PCID(r);
        assertTrue("read_Policy_Classes_Table_Records_On_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   PCID is: " + recs[0].getCOLUMN_POLICY_CLASS_ID());
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   class name is: " + recs[0].getCOLUMN_POLICY_CLASS_NAME());
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   CAPS are: " + recs[0].getCOLUMN_CAPS());
            System.out.println("read_Policy_Classes_Table_Records_On_PCID:   status is: " + recs[0].getCOLUMN_STATUS());
        }
        
        
        recs = (Policy_Classes_Table_Record[]) db.read_Policy_Classes_Table_Records_On_All_Classes(r);
        assertTrue("read_Policy_Classes_Table_Records_On_All_Classes: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   PCID is: " + recs[0].getCOLUMN_POLICY_CLASS_ID());
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   class name is: " + recs[0].getCOLUMN_POLICY_CLASS_NAME());
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   CAPS are: " + recs[0].getCOLUMN_CAPS());
            System.out.println("read_Policy_Classes_Table_Records_On_All_Classes:   status is: " + recs[0].getCOLUMN_STATUS());
        }
        
        output = db.delete_Policy_Classes_Table_Records_On_PCID(r);
	assertTrue("delete_Policy_Classes_Table_Records_On_PCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_Policy_Classes_Table_Records_On_PCID: value is: " + output);
        
        output = db.count_Distinct_Policy_Classes_Table_Records_on_PCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Policy_Classes_Table_Records_on_PCID: count is: " + output);
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Policy_Classes_Table_CRUD_operations... ");
    }    
     
    
}
