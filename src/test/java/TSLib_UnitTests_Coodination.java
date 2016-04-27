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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author kirill
 */
public class TSLib_UnitTests_Coodination 
{
    
    public TSLib_UnitTests_Coodination() {
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
    
    /* necessary globals */
    private final String BaseLocation = System.getProperty("user.home") + "/containers/";
    
    private final String FIELD_CoordinationMessage1 = "<wscoor:CoordinationContext>" +
    "<wsu:Expires>2012-04-22T00:00:00.0000000-07:00</wsu:Expires>\n" +
    "<wsu:Identifier>\n" +
    "uuid:0f05758b-1f0d-4248-a911-90f7bd18ae52\n" +
    "</wsu:Identifier>\n" +
    "<wscoor:CoordinationType>\n" +
    "http://schemas.xmlsoap.org/ws/2003/09/wsat\n" +
    "</wscoor:CoordinationType> <wscoor:RegistrationService>\n" +
    "<wsa:Address>\n" +
    "http://Business456.com/MyCoordinationService/RegistrationCoordinator\n" +
    "</wsa:Address>\n" +
    "<wsa:ReferenceProperties>\n" +
    "<mstx:ex xmlns:mstx=http://schemas.microsoft.com/wsat/extensibility\n" +
    "mstx:transactionId=\"uuid:cfb01dc0-5073-405a-a3aea6038ecc476e\"/>\n" +
    "</wsa:ReferenceProperties>\n" +
    "</wscoor:RegistrationService>\n" +
    "</wscoor:CoordinationContext>";
            
    private final String FIELD_CoordinationMessage2 = "<wscoor:CoordinationContext>" +
    "<wsu:Expires>2012-04-22T00:00:00.0000000-07:00</wsu:Expires>\n" +
    "<wsu:Identifier>\n" +
    "uuid:0f05758b-1f0d-4248-a911-90f7bd18ae52\n" +
    "</wsu:Identifier>\n" +
    "<wscoor:CoordinationType>\n" +
    "http://schemas.xmlsoap.org/ws/2003/09/wsat\n" +
    "</wscoor:CoordinationType> <wscoor:RegistrationService>\n" +
    "<wsa:Address>\n" +
    "http://Business455.com/MyCoordinationService/RegistrationCoordinator\n" +
    "</wsa:Address>\n" +
    "<wsa:ReferenceProperties>\n" +
    "<mstx:ex xmlns:mstx=http://schemas.microsoft.com/wsat/extensibility\n" +
    "mstx:transactionId=\"uuid:cfb01dc0-5073-405a-a3aea6038ecc476e\"/>\n" +
    "</wsa:ReferenceProperties>\n" +
    "</wscoor:RegistrationService>\n" +
    "</wscoor:CoordinationContext>";
    
    @Test
    public void test_Coordination()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Coordination() ");
        System.out.println("\n"); 
        
        Runnable a = new appA();
        Thread appA = new Thread (a);
    
        Runnable b = new appB();
        Thread appB = new Thread (b);
        
        Runnable c = new controller();
        Thread controller = new Thread (c);
        
        controller.start();
        
        appA.start();
        
        appB.start();
        
        /* pause the main test thread until the working threads complete their work */
        try 
        {
            Thread.sleep(30000); /* test for one minute or 30 seconds */
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(TSLib_UnitTests_Coodination.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Coordination() ");
        System.out.println("\n");   
    }    
    
}
