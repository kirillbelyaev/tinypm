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

public class TSLib_TSC_SequentialCollaboration_UnitTests 
{
    private final Integer one = 1;
    private final Integer eight = 8;
    private final Integer sixteen = 16;
    private final Integer thirty_two = 32;
    private final Integer forty_eight = 48;
    private final Integer sixty_four = 64;
    
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
    public void test_SequentialCollaboration_1()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_SequentialCollaboration() ");
        System.out.println("\n"); 
        
        
        for (Integer i = 0; i < this.one; i++)
        {    
            Runnable c = new Controller_collaborate_Sequentially(i);
            Thread controller = new Thread (c);
            controller.start();
        }
        
        /* add benchmark info */
        /*
        Runtime runtime = Runtime.getRuntime();
        final long MEGABYTE = 1024L * 1024L;
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_SequentialCollaboration(): Used VM Memory (MB) before replication: " + usedMemoryBefore / MEGABYTE);
        */
        
        
        /* pause the main test thread until the working threads complete their work */
        try 
        {
            //Thread.sleep(90000); /* test for one minute or 30 seconds */
            /* add additional sleep periods for larger objects */
            Thread.sleep(90000*8); 
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(TSLib_TSC_SequentialCollaboration_UnitTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_SequentialCollaboration(): Used VM Memory (MB) after replication: " + usedMemoryAfter / MEGABYTE);
        System.out.println("test_SequentialCollaboration(): VM Memory usage (MB) increased at: " + (usedMemoryAfter-usedMemoryBefore) / MEGABYTE);
        */
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_SequentialCollaboration() ");
        System.out.println("\n");   
    }    
    
    
    @Test
    public void test_SequentialCollaboration_8()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_SequentialCollaboration() ");
        System.out.println("\n"); 
        
        
        for (Integer i = 0; i < this.eight; i++)
        {    
            Runnable c = new Controller_collaborate_Sequentially(i);
            Thread controller = new Thread (c);
            controller.start();
        }
        
        /* add benchmark info */
        /*
        Runtime runtime = Runtime.getRuntime();
        final long MEGABYTE = 1024L * 1024L;
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_SequentialCollaboration(): Used VM Memory (MB) before replication: " + usedMemoryBefore / MEGABYTE);
        */
        
        
        /* pause the main test thread until the working threads complete their work */
        try 
        {
            //Thread.sleep(90000); /* test for one minute or 30 seconds */
            /* add additional sleep periods for larger objects */
            Thread.sleep(90000*8); 
        } catch (InterruptedException ex) 
        {
            Logger.getLogger(TSLib_TSC_SequentialCollaboration_UnitTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_SequentialCollaboration(): Used VM Memory (MB) after replication: " + usedMemoryAfter / MEGABYTE);
        System.out.println("test_SequentialCollaboration(): VM Memory usage (MB) increased at: " + (usedMemoryAfter-usedMemoryBefore) / MEGABYTE);
        */
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_SequentialCollaboration() ");
        System.out.println("\n");   
    }
    
}
