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

import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
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
public class DB_ComponentsTable_UnitTests 
{  
    private final String CAP_ATTR = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString();
    private final String COLUMN_COMPONENT_DESC = "icmp ping tool";
    private final String COLUMN_COMPONENT_PATH_ID = "/bin/ping";
    private final String COLUMN_COMPONENT_INVALID_PATH_ID = "/bin/x/ping";
    private final String COLUMN_COMPONENT_CAP_CLASS_ID = "1";
    private final String COLUMN_COMPONENT_COM_CLASS_ID = "1";
    private final String COLUMN_COMPONENT_CONTAINER_ID = "";
    private final String COLUMN_COMPONENT_ID = "";
    private final String COLUMN_COMPONENT_TUPLE_SPACE_PATH = "";
    private final String COLUMN_STATUS = "1";
    
    
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
    public void small_tests() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started small_tests... ");
        
        LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS[] = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.values();
        
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
    
    @Test
    public void test_Components_Table_create_drop() throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Components_Table_create_drop... ");

        int output = -1;
        boolean Out;
        
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_Components_DB();
	assertTrue("dropTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_Components_DB();
	assertTrue("createTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Components_Table_create_drop... ");
    }
     
     
    @Test
    public void test_Components_Table_CRUD_operations() throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Components_Table_CRUD_operations... ");
        
        int output = -1;
        boolean Out;
        
        ComponentsTableRecord r = new ComponentsTableRecord();
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_Components_DB();
	assertTrue("dropTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_Components_DB();
	assertTrue("createTable_APPS_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        r.set_COLUMN_COMPONENT_DESC(this.COLUMN_COMPONENT_DESC);
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_COMPONENT_PATH_ID);
        r.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.COLUMN_COMPONENT_CAP_CLASS_ID);
        r.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(this.COLUMN_COMPONENT_COM_CLASS_ID);
        r.set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(this.COLUMN_COMPONENT_TUPLE_SPACE_PATH);
        r.set_COLUMN_COMPONENT_ID(this.COLUMN_COMPONENT_ID);
        r.set_COLUMN_COMPONENT_CONTAINER_ID(this.COLUMN_COMPONENT_CONTAINER_ID);
        r.set_Status_Active();
        
        
        output = db.count_Distinct_Components_Table_Records_on_CID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Apps_Table_Records_on_APP_and_PCID: count is: " + output);
        
        
        ComponentsTableRecord[] recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_Component_and_CAPCID(r);
        assertTrue("read_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("read_Apps_Table_Records_On_APP_and_PCID: array value is: " + recs);
        
        output = db.delete_Components_Table_Records_On_Component_and_CAPCID_and_COMCID(r);
	assertTrue("delete_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_Apps_Table_Records_On_APP_and_PCID: value is: " + output);
        
        
        output = db.write_Components_Table_Record(r);
        assertNotNull(output);
        System.out.println("write_Apps_Table_Record: value is: " + output);
        
        
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_Component_and_CAPCID(r);
        assertTrue("read_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   desc is: " + recs[0].get_COLUMN_COMPONENT_DESC());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   app is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   PCID is: " + recs[0].get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   CID is: " + recs[0].get_COLUMN_COMPONENT_CONTAINER_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   status is: " + recs[0].get_COLUMN_STATUS());
        }
        
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_All_Components();
        assertTrue("read_Apps_Table_Records_On_All_APPs: Reply has unexpected return:", Out = recs == null | recs != null);
        
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_All_APPs: rec array index 0 app value is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
        }
        
        
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_CID(r);
        assertTrue("read_Apps_Table_Records_On_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
        
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_PCID: rec array index 0 app value is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
        }
        
        
        output = db.count_Distinct_Components_Table_Records_on_CID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Apps_Table_Records_on_APP_and_PCID: count is: " + output);
        
        
        output = db.delete_Components_Table_Records_On_Component_and_CAPCID_and_COMCID(r);
	assertTrue("delete_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_Apps_Table_Records_On_APP_and_PCID: value is: " + output);
        
        
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_Component_and_CAPCID(r);
        assertTrue("read_Apps_Table_Records_On_APP_and_PCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   desc is: " + recs[0].get_COLUMN_COMPONENT_DESC());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   app is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   PCID is: " + recs[0].get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   CID is: " + recs[0].get_COLUMN_COMPONENT_CONTAINER_ID());
            System.out.println("read_Apps_Table_Records_On_APP_and_PCID:   status is: " + recs[0].get_COLUMN_STATUS());
        }
        
        
        output = db.count_Distinct_Components_Table_Records_on_CID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_Apps_Table_Records_on_APP_and_PCID: count is: " + output);
        
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Components_Table_CRUD_operations... ");
        
    }

}
