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
public class TSLib_UnitTests_Coordination 
{
    
    public TSLib_UnitTests_Coordination() {
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
            Logger.getLogger(TSLib_UnitTests_Coordination.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Coordination() ");
        System.out.println("\n");   
    }    
    
}
