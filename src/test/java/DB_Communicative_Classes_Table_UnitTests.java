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
import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.implementation.DB_Dispatcher;
import edu.csu.lpm.DB.implementation.RecordDAO_implement;
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
public class DB_Communicative_Classes_Table_UnitTests 
{
    
    
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
    
    private final String COLUMN_APP_DESC = "icmp ping tool";
    private final String COLUMN_APP_PATH = "/bin/ping";
    private final String COLUMN_APP_PATH_INVALID = "/bin/x/ping";
    private final String COLUMN_POLICY_CLASS_ID = "1";
    private final String COLUMN_APP_CONTAINER_ID = "1";
    private final String COLUMN_STATUS = "1";
    private final String COLUMN_POLICY_CLASS_NAME = "web caching service";
    
    
    @Test
    public void test_Communicative_Classes_Table_create_drop() throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Communicative_Classes_Table_create_drop... ");

        int output = -1;
        boolean Out;
        
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.drop_Table_COMMC_DB();
	assertTrue("drop_Table_COMMC_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.create_Table_COMMC_DB();
	assertTrue("create_Table_COMMC_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Communicative_Classes_Table_create_drop... ");
    }
    
    
    @Test
    public void test_Communicative_Classes_Table_CRUD_operations() throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Communicative_Classes_Table_CRUD_operations... ");
        
        int output = -1;
        boolean Out;
        
        CommunicativeClassesTableRecord r = new CommunicativeClassesTableRecord();
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.drop_Table_COMMC_DB();
	assertTrue("drop_Table_COMMC_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.create_Table_COMMC_DB();
	assertTrue("create_Table_COMMC_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
       
        r.set_COLUMN_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.set_COLUMN_CLASS_NAME(this.COLUMN_POLICY_CLASS_NAME);
        r.set_COLUMN_STATUS(this.COLUMN_STATUS);
    
        r.set_COLUMN_STATUS_Active();
        
         
        output = db.count_Distinct_Communicative_Classes_Table_Records_on_CID();
	assertNotNull(output);
        System.out.println("count_Distinct_Communicative_Classes_Table_Records_on_CID: count is: " + output);
        
        CommunicativeClassesTableRecord[] recs = (CommunicativeClassesTableRecord[]) db.read_Communicative_Classes_Table_Records_On_CID(r);
        assertTrue("read_Communicative_Classes_Table_Records_On_CID: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("read_Communicative_Classes_Table_Records_On_CID: array value is: " + recs);
        
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Communicative_Classes_Table_CRUD_operations... ");
    }
}
