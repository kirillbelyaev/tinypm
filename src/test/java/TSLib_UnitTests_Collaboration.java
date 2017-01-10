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
public class TSLib_UnitTests_Collaboration 
{
    
    public TSLib_UnitTests_Collaboration() {
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
    
    @Test
    public void test_Collaboration()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Collaboration() ");
        System.out.println("\n"); 
        
        Runnable a = new ComponentA_collaborate();
        Thread appA = new Thread (a);
    
        Runnable c = new Controller_collaborate();
        Thread controller = new Thread (c);
        
        /* add benchmark info */
        Runtime runtime = Runtime.getRuntime();
        final long MEGABYTE = 1024L * 1024L;
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_collaboration(): Used VM Memory (MB) before replication: " + usedMemoryBefore / MEGABYTE);
        
        controller.start();
        
        appA.start();
        
        /* pause the main test thread until the working threads complete their work */
        try 
        {
            //Thread.sleep(90000); /* test for one minute or 30 seconds */
            /* add additional sleep periods for larger objects */
            Thread.sleep(90000*8); 
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(TSLib_UnitTests_Collaboration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_collaboration(): Used VM Memory (MB) after replication: " + usedMemoryAfter / MEGABYTE);
        System.out.println("test_collaboration(): VM Memory usage (MB) increased at: " + (usedMemoryAfter-usedMemoryBefore) / MEGABYTE);
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Collaboration() ");
        System.out.println("\n");   
    }    
    
}
