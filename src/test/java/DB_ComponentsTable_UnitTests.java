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
    private final String COLUMN_COMPONENT_DESC = "service component";
    private final String COLUMN_COMPONENT_1_PATH_ID = "/s/missouri/a/nobackup/kirill/containers/container-1/bin/componentA";
    private final String COLUMN_COMPONENT_2_PATH_ID = "/s/missouri/a/nobackup/kirill/containers/container-2/bin/componentB";
    private final String COLUMN_COMPONENT_INVALID_PATH_ID = "/bin/x/ping";
    private final String COLUMN_COMPONENT_CAP_CLASS_ID = "1";
    private final String COLUMN_COMPONENT_COM_CLASS_ID = "1";
    private final String COLUMN_COMPONENT_CONTAINER_ID = "";
    private final String COLUMN_COMPONENT_ID = "";
    private final String COLUMN_COMPONENT_TUPLE_SPACE_PATH = "";
    private final String COLUMN_STATUS = "1";
    
    
    @BeforeClass
    public static void setUpClass() 
    {
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
    }
    
    @Before
    public void setUp() 
    {
    }
    
    @After
    public void tearDown() 
    {
    }
     
    @Test
    public void test_LCS() 
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
    public void test_ComponentsTable_create_drop() throws RecordDAO_Exception, SQLException 
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
	assertTrue("dropTable_Components_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_Components_DB();
	assertTrue("createTable_Components_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Components_Table_create_drop... ");
    }
     
     
    @Test
    public void test_ComponentsTable_CRUD_operations() 
    throws RecordDAO_Exception, SQLException 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Components_Table_CRUD_operations... ");
        
        int output = -1;
        boolean Out;
        String svalue = null;
        
        ComponentsTableRecord r = new ComponentsTableRecord();
        DB_Dispatcher dd = new DB_Dispatcher();
        RecordDAO_implement db = null;

        db = dd.dispatch_DB_Access();
        
	assertNotNull(db);
        
        output = db.dropTable_Components_DB();
	assertTrue("dropTable_Components_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        output = db.createTable_Components_DB();
	assertTrue("createTable_Components_DB: Reply has unexpected return:", Out = output == 0 | output == -1);
        
        System.out.println("setting record fields. ");
        r.set_COLUMN_COMPONENT_DESC(this.COLUMN_COMPONENT_DESC);
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_COMPONENT_1_PATH_ID);
        r.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.COLUMN_COMPONENT_CAP_CLASS_ID);
        r.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(this.COLUMN_COMPONENT_COM_CLASS_ID);
        r.set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(this.COLUMN_COMPONENT_TUPLE_SPACE_PATH);
        r.set_COLUMN_COMPONENT_ID(this.COLUMN_COMPONENT_ID);
        r.set_COLUMN_COMPONENT_CONTAINER_ID(this.COLUMN_COMPONENT_CONTAINER_ID);
        r.set_Status_Active();
        
        System.out.println("\n"); 
        output = db.count_Components_Table_Records_on_COMCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_ComponentsTable_Records_on_COMCID: count is: " + output);
        
        System.out.println("\n"); 
        ComponentsTableRecord[] recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_Component_and_CAPCID(r);
        assertTrue("read_ComponentsTableRecords_On_Component_and_CAPCID: Reply has unexpected return:", Out = recs == null | recs != null);
	System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID: array value is: " + recs);
        
        System.out.println("\n"); 
        output = db.delete_Components_Table_Records_On_Component_and_CAPCID_and_COMCID(r);
	assertTrue("delete_ComponentsTableRecords_On_Component_and_CAPCID_and_COMCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_ComponentsTableRecords_On_Component_and_CAPCID_and_COMCID: value is: " + output);
        
        /*
        add 1st component
        */
        System.out.println("\n"); 
        output = db.write_ComponentsTableRecord(r);
        assertNotNull(output);
        System.out.println("write_ComponentsTableRecord: value is: " + output);
        
        System.out.println("\n"); 
        output = db.write_ComponentsTableRecord(r);
        assertNotNull(output);
        System.out.println("write_ComponentsTableRecord: value is: " + output);
        
        System.out.println("\n"); 
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_Component_and_CAPCID(r);
        assertTrue("read_ComponentsTableRecords_On_Component_and_CAPCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   desc is: " + recs[0].get_COLUMN_COMPONENT_DESC());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   Component Path ID is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   CAPCID is: " + recs[0].get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   COMCID is: " + recs[0].get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   status is: " + recs[0].get_COLUMN_STATUS());
        }
        
        System.out.println("\n"); 
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_All_Components();
        assertTrue("read_ComponentsTableRecords_On_All_Components: Reply has unexpected return:", Out = recs == null | recs != null);
        
        if (recs != null)
        {    
            System.out.println("read_ComponentsTableRecords_On_All_Components: rec array index 0 Component Path ID value is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
            System.out.println("read_ComponentsTableRecords_On_All_Components: rec array index 0 CAPCID is: " + recs[0].get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            System.out.println("read_ComponentsTableRecords_On_All_Components: rec array index 0 COMCID is: " + recs[0].get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());
        }
        
        System.out.println("\n"); 
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_COMCID(r);
        assertTrue("read_ComponentsTableRecords_On_COMCID: Reply has unexpected return:", Out = recs == null | recs != null);
        
        if (recs != null)
        {    
            System.out.println("read_ComponentsTableRecords_On_COMCID: rec array index 0 Components Path ID value is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
        }
        
        System.out.println("\n"); 
        output = db.count_Components_Table_Records_on_COMCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_ComponentsTableRecords_on_COMCID: count is: " + output);
        
        System.out.println("\n"); 
        svalue = db.get_ComponentsTableRecordsCOMCID_On_Component(r);
        System.out.println("get_ComponentsTableRecordsCOMCID_On_Component: value is: " + svalue);
        
        /*
        add 2nd component
        */
        
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_COMPONENT_2_PATH_ID);
        
        System.out.println("\n"); 
        output = db.write_ComponentsTableRecord(r);
        assertNotNull(output);
        System.out.println("write_ComponentsTableRecord: value is: " + output);
        
        System.out.println("\n"); 
        output = db.write_ComponentsTableRecord(r);
        assertNotNull(output);
        System.out.println("write_ComponentsTableRecord: value is: " + output);
        
        System.out.println("\n"); 
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_COMCID(r);
        assertTrue("read_ComponentsTableRecords_On_COMCID: Reply has unexpected return:", Out = recs == null | recs != null);
        
        if (recs != null)
        {    
            System.out.println("read_ComponentsTableRecords_On_COMCID: rec array index 0 Components Path ID value is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
            System.out.println("read_ComponentsTableRecords_On_COMCID: rec array index 1 Components Path ID value is: " + recs[1].get_COLUMN_COMPONENT_PATH_ID());
        }
        
        System.out.println("\n"); 
        output = db.count_Components_Table_Records_on_COMCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_ComponentsTableRecords_on_COMCID: count is: " + output);
        
        System.out.println("\n"); 
        svalue = db.get_ComponentsTableRecordsCOMCID_On_Component(r);
        System.out.println("get_ComponentsTableRecordsCOMCID_On_Component: value is: " + svalue);
        
        
        System.out.println("\n"); 
        output = db.delete_Components_Table_Records_On_Component_and_CAPCID_and_COMCID(r);
	assertTrue("delete_ComponentsTableRecords_On_Component_and_CAPCID_and_COMCID: Reply has unexpected return:", Out = output == 0 | output == -1);
        System.out.println("delete_ComponentsTableRecords_On_Component_and_CAPCID_and_COMCID: value is: " + output);
        
        System.out.println("\n"); 
        recs = (ComponentsTableRecord[]) db.read_Components_Table_Records_On_Component_and_CAPCID(r);
        assertTrue("read_ComponentsTableRecords_On_Component_and_CAPCID: Reply has unexpected return:", Out = recs == null | recs != null);
	
        if (recs != null)
        {    
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   desc is: " + recs[0].get_COLUMN_COMPONENT_DESC());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   Component Path ID is: " + recs[0].get_COLUMN_COMPONENT_PATH_ID());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   CAPCID is: " + recs[0].get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   COMCID is: " + recs[0].get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());
            System.out.println("read_ComponentsTableRecords_On_Component_and_CAPCID:   status is: " + recs[0].get_COLUMN_STATUS());
        }
        
        System.out.println("\n"); 
        output = db.count_Components_Table_Records_on_COMCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_ComponentsTableRecords_on_COMCID: count is: " + output);
        
        
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_COMPONENT_1_PATH_ID);
        
        System.out.println("\n"); 
        output = db.write_ComponentsTableRecord(r);
        assertNotNull(output);
        System.out.println("write_ComponentsTableRecord: value is: " + output);
        
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_COMPONENT_2_PATH_ID);
        
        System.out.println("\n"); 
        output = db.write_ComponentsTableRecord(r);
        assertNotNull(output);
        System.out.println("write_ComponentsTableRecord: value is: " + output);
        
        System.out.println("\n"); 
        output = db.count_Components_Table_Records_on_COMCID(r);
	assertNotNull(output);
        System.out.println("count_Distinct_ComponentsTableRecords_on_COMCID: count is: " + output);
        
        db.closeConnection();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Components_Table_CRUD_operations... ");
        
    }

}
