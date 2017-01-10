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
public class DB_CapabilitiesClassesTableRecord_UnitTests 
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
    public void test_Capabilities_Classes_Table_Record() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Capabilities_Classes_Table_Record... ");
        
        String caps = " CAP_CHOWN CAP_AUDIT_WRITE CAP_AUDIT_CONTROL ";
        String morecaps = "CAP_CHOWN CAP_AUDIT_WRITE CAP_AUDIT_CONTROL";
        String cap = " CAP_CHOWN ";
        String invalidcap = " CAP_CHOW ";
        String morecap = "CAP_CHOWN";

        int intValue = -1;
        boolean boolValue = false;
        
        String value = null;
        CapabilitiesClassesTableRecord r = new CapabilitiesClassesTableRecord();
        
        r.set_COLUMN_CLASS_ID(this.COLUMN_POLICY_CLASS_ID);
        r.set_COLUMN_CLASS_NAME(this.COLUMN_POLICY_CLASS_NAME);
        r.set_COLUMN_STATUS(this.COLUMN_STATUS);
        
        
        value = r.get_COLUMN_CLASS_ID();
	assertNotNull(value);
	System.out.println("PCID is: " + value);
        
        value = r.get_COLUMN_CLASS_NAME();
	assertNotNull(value);
	System.out.println("PC name is: " + value);
        
        value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_COLUMN_STATUS_Inactive();
	value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_COLUMN_STATUS_Active();
	value = r.get_COLUMN_STATUS();
	assertNotNull(value);
	System.out.println("status is: " + value);
        
        r.set_UPDATE_COLUMN_to_CLASS_ID();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        r.set_UPDATE_COLUMN_to_CLASS_NAME();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        r.set_UPDATE_COLUMN_to_CAPABILITIES();
        
        value = r.get_UPDATE_COLUMN();
	assertNotNull(value);
	System.out.println("update column is: " + value);
        
        
        boolValue = r.check_if_COLUMN_CAPABILITIES_is_Empty();
	assertNotNull(boolValue);
	System.out.println("check_if_COLUMN_POLICY_CLASS_POLICIES_is_Empty: boolean value is: " + boolValue);
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        boolValue = r.check_if_COLUMN_CAPABILITIES_is_Empty();
	assertNotNull(boolValue);
	System.out.println("check_if_COLUMN_POLICY_CLASS_POLICIES_is_Empty: boolean value is: " + boolValue);
        
        
        r.remove_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        boolValue = r.check_if_COLUMN_CAPABILITIES_is_Empty();
	assertNotNull(boolValue);
	System.out.println("check_if_COLUMN_POLICY_CLASS_POLICIES_is_Empty: boolean value is: " + boolValue);
        
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        boolValue = r.check_if_COLUMN_CAPABILITIES_is_Empty();
	assertNotNull(boolValue);
	System.out.println("check_if_COLUMN_POLICY_CLASS_POLICIES_is_Empty: boolean value is: " + boolValue);
        
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.remove_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_AUDIT_WRITE.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.add_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_AUDIT_CONTROL.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.remove_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_AUDIT_WRITE.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        r.remove_CAPABILITY(LinuxCapabilitiesPolicyContainer.LinuxCapabilities.CAP_CHOWN.toString());
        value = r.get_COLUMN_CAPABILITIES();
	assertNotNull(value);
	System.out.println("caps are: " + value);
        
        
        intValue = r.check_if_Capability_is_valid(cap);
        System.out.println("return is: " + intValue);
        
        intValue = r.check_if_Capability_is_valid(invalidcap);
        System.out.println("return is: " + intValue);
        
        intValue = r.check_if_Capabilities_are_valid(caps);
        System.out.println("return is: " + intValue);
        
        intValue = r.check_if_Capabilities_are_valid(morecaps);
        System.out.println("return is: " + intValue);
        
        intValue = r.check_if_Capabilities_are_valid(morecap);
        System.out.println("return is: " + intValue);
        
        //System.out.println("cap index within enum array is: " + r.check_if_Capability_is_valid(LinuxCAPPolicyContainer.LinuxCapabilities.CAP_KILL.toString()) );

        //System.out.println("Schema DDL is : " + r.produce_PCS_DB_DDL());
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Capabilities_Classes_Table_Record... ");
    }
     
    
}
