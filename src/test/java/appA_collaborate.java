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

import edu.csu.lpm.TSLib.implementation.AgentTransactionManager_implement;
import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;

/**
 *
 * @author kirill
 */
public class appA_collaborate implements Runnable
{
    private final String BaseLocation = System.getProperty("user.home") + "/containers/";
    
    /* need absolute path without variable substitution with // */
    private final String FIELD_APP_PATH_A = "/s/chopin/b/grad/kirill/containers/container-1/bin/applicationA";
    private final String FIELD_APP_PATH_B = "/s/chopin/b/grad/kirill/containers/container-2/bin/applicationB";
    
    /*
    private final String FIELD_APP_PATH_A = BaseLocation + "/container-1/bin/applicationA";
    private final String FIELD_APP_PATH_B = BaseLocation + "/container-2/bin/applicationB";
    */

    private final String FIELD_CollaborationMessage = System.getProperty("user.home") + "/waters/logs/secure.log";
    

    @Override
    public void run() 
    {
        this.appA();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void appA()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        //System.out.println("app A started test of perform_ActivePersistentCoordinativeTransaction() ");
        System.out.println("app A started test of collaboration ");
        System.out.println("\n");
        
        int IntValue = -1;
        ControlTuple_implement CLT = new ControlTuple_implement();
        AgentTransactionManager_implement ATM = new AgentTransactionManager_implement();
        
        String AbsolutePathTSA = this.BaseLocation + "/container-1/";
        //String AbsolutePathB = this.BaseLocation + "/container-2/";
        
        String ReplicationLocation = System.getProperty("user.home") + "/waters/logs/secure.log.replica";
        
        System.out.println("app A TS AbsolutePath is:" + AbsolutePathTSA);
        
        CLT.set_SourceID_Field(this.FIELD_APP_PATH_A);
        CLT.set_DestinationID_Field(this.FIELD_APP_PATH_B);
        CLT.set_Type_Field_to_Collaboration();
        CLT.set_RequestMessage_Field(this.FIELD_CollaborationMessage);
        System.out.println("app A setting ControlTuple fields ");
        System.out.println("\n");
        
        IntValue = ATM.perform_PersistentCollaborativeTransaction(CLT, AbsolutePathTSA, ReplicationLocation);
        System.out.println("app A executing perform_PersistentCollaborativeTransaction() ");
        System.out.println("app A method return value is: " + IntValue);
        System.out.println("\n");
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("app A finished test of collaboration ");
        System.out.println("\n");
    }
    
}
