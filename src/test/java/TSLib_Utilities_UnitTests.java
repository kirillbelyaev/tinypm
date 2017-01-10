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

import edu.csu.lpm.TSLib.Utilities.Utilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author kirill
 */
public class TSLib_Utilities_UnitTests 
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

    private final String DataObjectPath = "/s/missouri/a/nobackup/kirill/logs/secure.log";
    private final String DataObjectReplicaPath = "/s/missouri/a/nobackup/kirill/logs/secure.log.replica";
    private final String ts_location = "/s/missouri/a/nobackup/kirill/containers/";
    private final String FIELD_APP_PATH_A = "/s/missouri/a/nobackup/kirill/containers/container-1/bin/applicationA";
    /* private final String FIELD_APP_PATH_A = ts_location + "/container-1/bin/applicationA"; */
    
    private final String InvalidLocation = "/non/existent/dir";
    private final String EmptyString = "";
    private final String RootDir = "/";
    
    private int IntValue = -1;
    private boolean TerminateNow = false;
    
    private Utilities ut = new Utilities ();
        
    @Test
    private void test_create_ObjectReplica() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_create_ObjectReplica() ");
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplica(this.DataObjectPath, this.DataObjectReplicaPath, this.ts_location, this.FIELD_APP_PATH_A);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_create_ObjectReplica() ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.ut.create_ObjectReplica(null, null, null, null);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplica(this.InvalidLocation, this.InvalidLocation, this.EmptyString, this.EmptyString);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplica(this.InvalidLocation, this.InvalidLocation, this.RootDir, this.EmptyString);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplica(this.DataObjectPath, this.DataObjectReplicaPath, this.RootDir, this.FIELD_APP_PATH_A);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_create_ObjectReplica() ");
        System.out.println("\n");
    }
    
    
    @Test
    private void test_create_ObjectReplicaWithDebug() 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_create_ObjectReplica() ");
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplicaWithDebug(this.DataObjectPath, this.DataObjectReplicaPath, this.ts_location, this.FIELD_APP_PATH_A);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_create_ObjectReplica() ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.ut.create_ObjectReplicaWithDebug(null, null, null, null);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplicaWithDebug(this.InvalidLocation, this.InvalidLocation, this.EmptyString, this.EmptyString);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplicaWithDebug(this.InvalidLocation, this.InvalidLocation, this.RootDir, this.EmptyString);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.ut.create_ObjectReplicaWithDebug(this.DataObjectPath, this.DataObjectReplicaPath, this.RootDir, this.FIELD_APP_PATH_A);
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_create_ObjectReplica() ");
        System.out.println("\n");
    }
    
    
    @Test
    public void test_Replication()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_Utilities ");
        System.out.println("\n"); 
        
        /* set to direct the execution of test methods with valid input only */
        this.TerminateNow = true;
        
        ut.setDebug(true);
        
        /* add benchmark info */
        Runtime runtime = Runtime.getRuntime();
        final long MEGABYTE = 1024L * 1024L;
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_create_ObjectReplica: Used VM Memory (MB) before replication: " + usedMemoryBefore / MEGABYTE);
        
        long startTime = System.currentTimeMillis();
        
        this.test_create_ObjectReplica();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("test_create_ObjectReplica: replication took in seconds: " +  elapsedTime / 1000.0 );
        
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("test_create_ObjectReplica: Used VM Memory (MB) after replication: " + usedMemoryAfter / MEGABYTE);
        System.out.println("test_create_ObjectReplica: VM Memory usage (MB) increased at: " + (usedMemoryAfter - usedMemoryBefore) / MEGABYTE);
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_Utilities ");
        System.out.println("\n");   
    }
    
    @Test
    public void test_ReplicationWithDebug()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_UtilitiesWithDebug ");
        System.out.println("\n"); 
        
        /* set to direct the execution of test methods with valid input only */
        this.TerminateNow = true;
        
        ut.setDebug(true);
        
        this.test_create_ObjectReplicaWithDebug();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_UtilitiesWithDebug ");
        System.out.println("\n");   
    }
}
