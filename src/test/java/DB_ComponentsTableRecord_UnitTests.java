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
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;
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
public class DB_ComponentsTableRecord_UnitTests 
{ 
    private final String CAP_ATTR = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString();
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
    public void test_Components_Table_Record() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Components_Table_Record... ");
        
        ComponentsTableRecord r = new ComponentsTableRecord();
        String value = null;
        
        r.set_COLUMN_COMPONENT_DESC(this.COLUMN_APP_DESC);
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_APP_PATH);
        r.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.set_COLUMN_COMPONENT_CONTAINER_ID(this.COLUMN_APP_CONTAINER_ID);
        r.set_Status_Active();
        
        value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_COLUMN_STATUS(null);
	value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_Status_Inactive();
	value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        value = r.get_COLUMN_COMPONENT_PATH_ID();
	assertNotNull(value);
	System.out.println("app path is: " + value);
        
        r.set_COLUMN_COMPONENT_PATH_ID(this.COLUMN_APP_PATH_INVALID);
        
        value = r.get_COLUMN_COMPONENT_PATH_ID();
	assertNotNull(value);
	System.out.println("app path is: " + value);
        
        
        value = r.get_COLUMN_COMPONENT_DESC();
	assertNotNull(value);
	System.out.println("app desc is: " + value);
        
        value = r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID();
	assertNotNull(value);
	System.out.println("PCID is: " + value);
        
        value = r.get_COLUMN_COMPONENT_CONTAINER_ID();
	assertNotNull(value);
	System.out.println("app container ID is: " + value);
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Components_Table_Record... ");
        
    }
    
}
