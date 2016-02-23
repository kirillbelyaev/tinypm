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

import edu.csu.lpm.TSL.implementation.ContentTuple_implement;
import edu.csu.lpm.TSL.implementation.ControlTuple_implement;
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
public class TSL_UnitTests {
    
    public TSL_UnitTests() {
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
    
    
    private final String FIELD_APP_PATH_VALID = "/bin/ping";
    private final String FIELD_APP_PATH_INVALID = "/bin/non_existent/ping";
    private final String FIELD_RequestMessage = "/usr/home/containers/container-b/data-pool/runtime-log.txt";
    private final String FIELD_XML_CoordinationMessage = "<XML_MESSAGE length=\"0000060\"><field>8</field></XML_MESSAGE>";
    
    @Test
    public void test_ControlTuple()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControlTuple... ");
        System.out.println("\n"); 
        
        
        ControlTuple_implement ct = new ControlTuple_implement();
        String string_value = null;
        int int_value = -1;
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_INVALID);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field(null);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field("");
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        int_value = ct.set_ID_Field(this.FIELD_APP_PATH_VALID);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_ID_Field();
        assertNotNull(string_value);
        System.out.println("ID is: " + string_value);
        
        
        int_value = ct.set_RequestMessage_Field(this.FIELD_RequestMessage);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        int_value = ct.set_RequestMessage_Field(this.FIELD_XML_CoordinationMessage);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        int_value = ct.set_RequestMessage_Field(null);
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        int_value = ct.set_RequestMessage_Field("");
        System.out.println("method return value is: " + int_value);
        
        string_value = ct.get_RequestMessage_Field();
        assertNotNull(string_value);
        System.out.println("RequestMessage is: " + string_value);
        
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("Type is: " + string_value);
        
        ct.set_Type_Field_to_Collaboration();
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("Type is: " + string_value);
        
        ct.set_Type_Field_to_Coordination();
        
        string_value = ct.get_Type_Field();
        assertNotNull(string_value);
        System.out.println("Type is: " + string_value);
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControlTuple... ");
        System.out.println("\n"); 
    }
    
    
}
