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
    public void test_Coordination()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Coordination() ");
        System.out.println("\n"); 
        
        Runnable a = new ComponentA_coordinate();
        Thread compA = new Thread (a);
    
        Runnable b = new ComponentB_coordinate();
        Thread compB = new Thread (b);
        
        Runnable c = new Controller_coordinate();
        Thread controller = new Thread (c);
        
        controller.start();
        
        compA.start();
        
        compB.start();
        
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
