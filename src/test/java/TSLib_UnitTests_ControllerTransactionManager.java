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

import edu.csu.lpm.TSLib.implementation.ContentTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControllerTransactionManager_implement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author kirill
 */
public class TSLib_UnitTests_ControllerTransactionManager 
{
    
    public TSLib_UnitTests_ControllerTransactionManager() {
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
    
    /* globals */
    
    private final String FIELD_APP_PATH_VALID = "/bin/ping";
    private final String FIELD_APP_PATH_INVALID = "/bin/non_existent/ping";
    private final String FIELD_RequestMessage = "/usr/home/containers/container-b/data-pool/runtime-log.txt";
    private final String FIELD_XML_CoordinationMessage = "<XML_MESSAGE length=\"0000060\"><field>8</field></XML_MESSAGE>";
    private final String FIELD_Payload = 
    "[^[[32m  OK  ^[[0m] Started Show Plymouth Boot Screen.\n" +
    "[^[[32m  OK  ^[[0m] Reached target Paths.\n" +
    "[^[[32m  OK  ^[[0m] Reached target Basic System.\n" +
    "[^[[32m  OK  ^[[0m] Reached target Initrd Default Target.\n" +
    "Starting dracut pre-pivot and cleanup hook...\n" +
    "[^[[32m  OK  ^[[0m] Started dracut pre-pivot and cleanup hook.\n" +
    "Starting Cleaning Up and Shutting Down Daemons...\n" +
    "[^[[32m  OK  ^[[0m] Stopped target Timers.\n" +
    "Starting Plymouth switch root service...\n";

    private final String ValidLocation = System.getProperty("user.home");
    private final String InvalidLocation = "/non/existent/dir";
    private final String EmptyString = "";
    private final String RootDir = "/";
    
    private StringBuffer Body = new StringBuffer();
    
    private int IntValue = -1;
    private final int SampleSequenceNumber = 101;
    
    private boolean TerminateNow = false;
    
    private ControllerTransactionManager_implement CTM = new ControllerTransactionManager_implement();
    private ControlTuple_implement CLT = new ControlTuple_implement();
    private ContentTuple_implement CNT = new ContentTuple_implement();
    
    /* CTM tests */
    
//    @Test
//    public void test_facilitate_PersistentCoordinativeTransaction()
//    {
//        System.out.println("\n"); 
//        System.out.println("--------------------------------------");
//        System.out.println("started test_facilitate_PersistentCoordinativeTransaction ");
//        System.out.println("\n");
//        
//        this.IntValue = this.CTM.facilitate_UnidirectionalPersistentCoordinativeTransaction(this.ValidLocation);
//        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("method return value is: " + this.IntValue);
//        System.out.println("\n");
//        
//        /* terminate with valid execution only */
//        if (this.TerminateNow == true)
//        {    
//            System.out.println("\n"); 
//            System.out.println("--------------------------------------");
//            System.out.println("finished test_facilitate_PersistentCoordinativeTransaction ");
//            System.out.println("\n");
//            return;
//        }
//        
//        this.IntValue = this.CTM.facilitate_UnidirectionalPersistentCoordinativeTransaction(null);
//        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("method return value is: " + IntValue);
//        System.out.println("\n");
//        
//        this.IntValue = this.CTM.facilitate_UnidirectionalPersistentCoordinativeTransaction(this.InvalidLocation);
//        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("method return value is: " + this.IntValue);
//        System.out.println("\n");
//        
//        this.IntValue = this.CTM.facilitate_UnidirectionalPersistentCoordinativeTransaction(this.EmptyString);
//        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("method return value is: " + this.IntValue);
//        System.out.println("\n");
//        
//        this.IntValue = this.CTM.facilitate_UnidirectionalPersistentCoordinativeTransaction(this.RootDir);
//        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("method return value is: " + this.IntValue);
//        System.out.println("\n");
//        
//        this.IntValue = this.CTM.facilitate_UnidirectionalPersistentCoordinativeTransaction(this.ValidLocation);
//        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("method return value is: " + this.IntValue);
//        System.out.println("\n");
//        
//        System.out.println("\n"); 
//        System.out.println("--------------------------------------");
//        System.out.println("finished test_facilitate_PersistentCoordinativeTransaction ");
//        System.out.println("\n");
//    }    
    

    @Test
    public void test_facilitate_BidirectionalPersistentCoordinativeTransaction()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_facilitate_BidirectionalPersistentCoordinativeTransaction ");
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(this.ValidLocation, this.RootDir);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_facilitate_BidirectionalPersistentCoordinativeTransaction ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(this.ValidLocation, this.ValidLocation);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
               
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(null, null);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(this.InvalidLocation, this.ValidLocation);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(this.EmptyString, this.ValidLocation);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(this.RootDir, this.ValidLocation);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(this.ValidLocation, this.RootDir);
        System.out.println("executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_facilitate_BidirectionalPersistentCoordinativeTransaction ");
        System.out.println("\n");
    }    
    
    
    @Test
    public void test_ControllerTransactionManager()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_ControllerTransactionManager ");
        System.out.println("\n"); 
        
        /* set to direct the execution of test methods with valid input only */
        this.TerminateNow = true;
        
        this.test_facilitate_BidirectionalPersistentCoordinativeTransaction();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControllerTransactionManager ");
        System.out.println("\n");   
    }    
    
}
