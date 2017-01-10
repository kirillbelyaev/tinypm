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

import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
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

public class DB_CommunicativeClassesTableRecord_UnitTests 
{
       
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

    private final String COLUMN_APP_DESC = "icmp ping tool";
    private final String COLUMN_APP_PATH = "/bin/ping";
    private final String COLUMN_APP_PATH_INVALID = "/bin/x/ping";
    private final String COLUMN_POLICY_CLASS_ID = "1";
    private final String COLUMN_APP_CONTAINER_ID = "1";
    private final String COLUMN_STATUS = "1";
    private final String COLUMN_POLICY_CLASS_NAME = "web caching service";
    
    
    @Test
    public void test_CommunicativeClassesTableRecord() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Communicative_Classes_Table_Record... ");
        
        final String separator = " ";
        final String emptyString = "";
        final String alpha = "abcd";
        final String alphanum = "100abcd";
         
        String component_1_ID = "/s/missouri/a/nobackup/kirill/containers/container-1/bin/componentA";
        String component_2_ID = "/s/missouri/a/nobackup/kirill/containers/container-2/bin/componentB";       
        String object_path = "/s/missouri/a/nobackup/kirill/logs/secure.log";
        
        String coord_record = component_2_ID + separator + component_1_ID; 
        
        String collab_record = component_1_ID + separator + object_path; 
        
        int intValue = -1;
        boolean boolValue = false;
        
        String value = null;
        
        CommunicativeClassesTableRecord r = new CommunicativeClassesTableRecord();
        
        System.out.println("set_COLUMN_CLASS_ID. ");
        intValue = r.set_COLUMN_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        System.out.println("set_COLUMN_CLASS_ID return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_CLASS_ID. ");
        value = r.get_COLUMN_CLASS_ID();
	assertNotNull(value);
	System.out.println("CID is: " + value);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_CLASS_ID. ");
        intValue = r.set_COLUMN_CLASS_ID(null);
        System.out.println("set_COLUMN_CLASS_ID return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_CLASS_ID. ");
        intValue = r.set_COLUMN_CLASS_ID(emptyString);
        System.out.println("set_COLUMN_CLASS_ID return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_CLASS_ID. ");
        intValue = r.set_COLUMN_CLASS_ID(separator);
        System.out.println("set_COLUMN_CLASS_ID return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_CLASS_ID. ");
        intValue = r.set_COLUMN_CLASS_ID(alpha);
        System.out.println("set_COLUMN_CLASS_ID return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_CLASS_ID. ");
        intValue = r.set_COLUMN_CLASS_ID(alphanum);
        System.out.println("set_COLUMN_CLASS_ID return code is: " + intValue);
        
        
        System.out.println("\n");
        System.out.println("get_COLUMN_CLASS_ID. ");
        value = r.get_COLUMN_CLASS_ID();
	assertNotNull(value);
	System.out.println("CID is: " + value);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_CLASS_NAME. ");
        r.set_COLUMN_CLASS_NAME(this.COLUMN_POLICY_CLASS_NAME);
        
        System.out.println("get_COLUMN_CLASS_NAME. ");
        value = r.get_COLUMN_CLASS_NAME();
	assertNotNull(value);
	System.out.println("Class name is: " + value);
        
        System.out.println("set_COLUMN_STATUS. ");
        r.set_COLUMN_STATUS(this.COLUMN_STATUS);
        
        System.out.println("get_COLUMN_STATUS. ");
        value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        System.out.println("set_COLUMN_STATUS to inactive ");
        r.set_COLUMN_STATUS_Inactive();
        
        System.out.println("get_COLUMN_STATUS. ");
	value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        System.out.println("set_COLUMN_STATUS to active ");
        r.set_COLUMN_STATUS_Active();
	
        System.out.println("get_COLUMN_STATUS. ");
        value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        /* set records columns */
        
        /* collaboration first */
        
        System.out.println("\n");
        System.out.println("set_COLUMN_COLLABORATION_RECORD. ");
        intValue = r.set_COLUMN_COLLABORATION_RECORD(component_2_ID, object_path);
        System.out.println("set_COLUMN_COLLABORATION_RECORD return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_COLLABORATION_RECORD. ");
        value = r.get_COLUMN_COLLABORATION_RECORD();
	assertNotNull(value);
	System.out.println("Collaboration record is: " + value);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_COLLABORATION_RECORD. (invalid input)");
        intValue = r.set_COLUMN_COLLABORATION_RECORD("", "");
        System.out.println("set_COLUMN_COLLABORATION_RECORD return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_COLLABORATION_RECORD. ");
        value = r.get_COLUMN_COLLABORATION_RECORD();
	assertNotNull(value);
	System.out.println("Collaboration record is: " + value);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_COLLABORATION_RECORD using a method with a single parameter. ");
        r.set_COLUMN_COLLABORATION_RECORD(collab_record);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_COLLABORATION_RECORD. ");
        value = r.get_COLUMN_COLLABORATION_RECORD();
	assertNotNull(value);
	System.out.println("Collaboration record is: " + value);
        
        /* now coordination */
        
        System.out.println("\n");
        System.out.println("set_COLUMN_COORDINATION_RECORD. ");
        intValue = r.set_COLUMN_COORDINATION_RECORD(component_1_ID, component_2_ID);
        System.out.println("set_COLUMN_COORDINATION_RECORD return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_COORDINATION_RECORD. ");
        value = r.get_COLUMN_COORDINATION_RECORD();
	assertNotNull(value);
	System.out.println("Coordination record is: " + value);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_COORDINATION_RECORD. (invalid input)");
        intValue = r.set_COLUMN_COORDINATION_RECORD("", "");
        System.out.println("set_COLUMN_COORDINATION_RECORD return code is: " + intValue);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_COORDINATION_RECORD. ");
        value = r.get_COLUMN_COORDINATION_RECORD();
	assertNotNull(value);
	System.out.println("Coordination record is: " + value);
        
        System.out.println("\n");
        System.out.println("set_COLUMN_COORDINATION_RECORD using a method with a single parameter. ");
        r.set_COLUMN_COORDINATION_RECORD(coord_record);
        
        System.out.println("\n");
        System.out.println("get_COLUMN_COORDINATION_RECORD. ");
        value = r.get_COLUMN_COORDINATION_RECORD();
	assertNotNull(value);
	System.out.println("Coordination record is: " + value);
        
        
        /* update column tests */
        System.out.println("UPDATE_COLUMN operations:");
        
        r.set_UPDATE_COLUMN_to_CLASS_ID();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        r.set_UPDATE_COLUMN_to_CLASS_NAME();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        r.set_UPDATE_COLUMN_to_STATUS();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        r.set_UPDATE_COLUMN_to_COLLABORATION_RECORD();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        r.set_UPDATE_COLUMN_to_COORDINATION_RECORD();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
          
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Communicative_Classes_Table_Record... ");
    }
}
