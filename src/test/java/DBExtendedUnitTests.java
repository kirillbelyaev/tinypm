
/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

import edu.csu.tinypm.DB.DTO.Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;
import edu.csu.tinypm.DB.factory.RecordDAOFactory;
import edu.csu.tinypm.DB.implementation.ConnManager;
import edu.csu.tinypm.DB.implementation.DB_Dispatcher;
import edu.csu.tinypm.DB.implementation.DB_Dispatcher_Extended;
import edu.csu.tinypm.DB.implementation.RecordDAOExtended_implement;
import edu.csu.tinypm.implementation.Parser_implement;
import edu.csu.tinypm.DB.implementation.RecordDAO_implement;
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
    // @Test
    // public void hello() {}
    
    Integer UID = 805;
    String APP_PATH = "/bin/ping";
    String CAP_ATTR = LinuxCAPPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString();
    String STATUS = "1";
    
    @Ignore
    public void testRecord() 
    {
        Record r = new Record();
        String value = null;
        
        r.setApp_PATH(this.APP_PATH);
        r.setCAP_Attr(this.CAP_ATTR);
        r.setUID(this.UID);
        r.setStatusActive();
        
        value = r.getStatus();
	assertNotNull(value);
	System.out.println("value is: " + value);
        
        r.setStatus(null);
	value = r.getStatus();
	assertNotNull(value);
	System.out.println("value is: " + value);
        
        r.setStatusInactive();
	value = r.getStatus();
	assertNotNull(value);
	System.out.println("value is: " + value);
        
        value = r.getApp_PATH();
	assertNotNull(value);
	System.out.println("value is: " + value);
        
        value = r.getCAP_Attr();
	assertNotNull(value);
	System.out.println("value is: " + value);
        
        value = r.getUID();
	assertNotNull(value);
	System.out.println("value is: " + value);
        
        
    }
    
    
    @Ignore
    public void testCRUD() throws RecordDAOException, SQLException 
    {
        Record r = new Record();
        int output = -1;
        boolean Out;
        
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;
        
        r.setApp_PATH(this.APP_PATH);
        r.setCAP_Attr(this.CAP_ATTR);
        r.setUID(this.UID);
        r.setStatusActive();
        
//        ConnManager cm = new ConnManager();
//	assertNotNull(cm);
//    
//        
//        RecordDAOFactory factory = new RecordDAOFactory();
//	assertNotNull(factory);
//
//	RecordDAO_implement db = null;
//        
//        try 
//        {
//                db = factory.create(cm.obtainConnection());
//        } catch (SQLException e) 
//        {
//                throw new SQLException("Exception: " + e.getMessage(), e);
//        }

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_LC_DB();
	assertTrue("dropTable: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_LC_DB();
	assertTrue("createTable_LC_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.countDistinctAppCapRecords(r);
	assertNotNull(output);
        System.out.println("value is: " + output);
        
        
        Record[] recs = (Record[]) db.readRecordsOnAPP(r);
        assertTrue("readRecordsOnAPP: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("array value is: " + recs);
        
        
        output = db.deleteRecordsOnAPPandCAP(r);
	assertTrue("deleteRecordsOnAPPandCAP: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("value is: " + output);
        
        
        
        output = db.writeRecord(r);
        assertNotNull(output);
        System.out.println("writeRecord: value is: " + output);
        
        
        recs = (Record[]) db.readRecordsOnAPP(r);
        assertTrue("readRecordsOnAPP: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("readRecordsOnAPP:   array value is: " + recs);
        if (recs != null)
        {    
            System.out.println("readRecordsOnAPP:   UID is: " + recs[0].getUID());
            System.out.println("readRecordsOnAPP:   APP is: " + recs[0].getApp_PATH());
            System.out.println("readRecordsOnAPP:   CAP_ATTR is: " + recs[0].getCAP_Attr());
            System.out.println("readRecordsOnAPP:   Status is: " + recs[0].getStatus());
        }
        
        //output = db.checkIfRecordExists(r);
        //System.out.println("checkIfRecordExists: value is: " + output);
        
        recs = (Record[]) db.readRecordsOnAllAPPs();
        assertTrue("readRecordsOnAllAPPs: Reply has unexpected return:", Out = recs == null | recs != null);
        if (recs != null)
        {    
            System.out.println("readRecordsOnAllAPPs: rec array index 0 app value is: " + recs[0].getApp_PATH());
        }
        
        db.closeConnection();
        
        
    }
    
    @Test
     public void testApps_Table_create_drop() throws RecordDAOException, SQLException 
    {
        //Record r = new Record();
        int output = -1;
        boolean Out;
        
        DB_Dispatcher_Extended dd = new DB_Dispatcher_Extended();
        RecordDAOExtended_implement db = null;
        
//        r.setApp_PATH(this.APP_PATH);
//        r.setCAP_Attr(this.CAP_ATTR);
//        r.setUID(this.UID);
//        r.setStatusActive();
        
//        ConnManager cm = new ConnManager();
//	assertNotNull(cm);
//    
//        
//        RecordDAOFactory factory = new RecordDAOFactory();
//	assertNotNull(factory);
//
//	RecordDAO_implement db = null;
//        
//        try 
//        {
//                db = factory.create(cm.obtainConnection());
//        } catch (SQLException e) 
//        {
//                throw new SQLException("Exception: " + e.getMessage(), e);
//        }

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_APPS_DB();
	assertTrue("dropTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_APPS_DB();
	assertTrue("createTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
//        output = db.countDistinctAppCapRecords(r);
//	assertNotNull(output);
//        System.out.println("value is: " + output);
//        
//        
//        Record[] recs = (Record[]) db.readRecordsOnAPP(r);
//        assertTrue("readRecordsOnAPP: Reply has unexpected return:", Out = recs == null | recs != null);
//	System.out.println("array value is: " + recs);
//        
//        
//        output = db.deleteRecordsOnAPPandCAP(r);
//	assertTrue("deleteRecordsOnAPPandCAP: Reply has unexpected return:", Out = output == 0 | output == -1);
//        System.out.println("value is: " + output);
//        
//        
//        
//        output = db.writeRecord(r);
//        assertNotNull(output);
//        System.out.println("writeRecord: value is: " + output);
//        
//        
//        recs = (Record[]) db.readRecordsOnAPP(r);
//        assertTrue("readRecordsOnAPP: Reply has unexpected return:", Out = recs == null | recs != null);
//	System.out.println("readRecordsOnAPP:   array value is: " + recs);
//        if (recs != null)
//        {    
//            System.out.println("readRecordsOnAPP:   UID is: " + recs[0].getUID());
//            System.out.println("readRecordsOnAPP:   APP is: " + recs[0].getApp_PATH());
//            System.out.println("readRecordsOnAPP:   CAP_ATTR is: " + recs[0].getCAP_Attr());
//            System.out.println("readRecordsOnAPP:   Status is: " + recs[0].getStatus());
//        }
//        
//        //output = db.checkIfRecordExists(r);
//        //System.out.println("checkIfRecordExists: value is: " + output);
//        
//        recs = (Record[]) db.readRecordsOnAllAPPs();
//        assertTrue("readRecordsOnAllAPPs: Reply has unexpected return:", Out = recs == null | recs != null);
//        if (recs != null)
//        {    
//            System.out.println("readRecordsOnAllAPPs: rec array index 0 app value is: " + recs[0].getApp_PATH());
//        }
        
        db.closeConnection();
        
        
    }
    
    
}
