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
    
    @Test
    public void test_facilitate_PersistentCoordinativeTransaction()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started test_facilitate_PersistentCoordinativeTransaction ");
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_PersistentCoordinativeTransaction(this.ValidLocation);
        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        /* terminate with valid execution only */
        if (this.TerminateNow == true)
        {    
            System.out.println("\n"); 
            System.out.println("--------------------------------------");
            System.out.println("finished test_facilitate_PersistentCoordinativeTransaction ");
            System.out.println("\n");
            return;
        }
        
        this.IntValue = this.CTM.facilitate_PersistentCoordinativeTransaction(null);
        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_PersistentCoordinativeTransaction(this.InvalidLocation);
        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_PersistentCoordinativeTransaction(this.EmptyString);
        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_PersistentCoordinativeTransaction(this.RootDir);
        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        this.IntValue = this.CTM.facilitate_PersistentCoordinativeTransaction(this.ValidLocation);
        System.out.println("executing facilitate_PersistentCoordinativeTransaction() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_facilitate_PersistentCoordinativeTransaction ");
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
        
        this.test_facilitate_PersistentCoordinativeTransaction();
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished test_ControllerTransactionManager ");
        System.out.println("\n");   
    }    
    
}
