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
import edu.csu.lpm.TSLib.interfaces.TransactionManager;

/**
 *
 * @author kirill
 */
public class appB implements Runnable
{
    private final String FIELD_APP_PATH_A = "/s/chopin/b/grad/kirill/containers/container-1/bin/applicationA";
    private final String FIELD_APP_PATH_B = "/s/chopin/b/grad/kirill/containers/container-2/bin/applicationB";
     
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
    "http://Business455.com/MyCoordinationService/RegistrationCoordinator\n" +
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
    "http://Business456.com/MyCoordinationService/RegistrationCoordinator\n" +
    "</wsa:Address>\n" +
    "<wsa:ReferenceProperties>\n" +
    "<mstx:ex xmlns:mstx=http://schemas.microsoft.com/wsat/extensibility\n" +
    "mstx:transactionId=\"uuid:cfb01dc0-5073-405a-a3aea6038ecc476e\"/>\n" +
    "</wsa:ReferenceProperties>\n" +
    "</wscoor:RegistrationService>\n" +
    "</wscoor:CoordinationContext>";
    

    @Override
    public void run() 
    {
        this.appB();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void appB()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        //System.out.println("app B started test of perform_PassivePersistentCoordinativeTransaction() ");
        System.out.println("app B started test of coordination ");
        System.out.println("\n");
        
        int IntValue = -1;
        ControlTuple_implement CLT = new ControlTuple_implement();
        AgentTransactionManager_implement ATM = new AgentTransactionManager_implement();
        
        //String AbsolutePathA = this.BaseLocation + "/container-1/";
        String AbsolutePathB = this.BaseLocation + "/container-2/";
        
        System.out.println("app B TS AbsolutePath is:" + AbsolutePathB);
        
        IntValue = ATM.perform_PassivePersistentCoordinativeTransaction(AbsolutePathB);
        System.out.println("app B executing perform_PassivePersistentCoordinativeTransaction() ");
        System.out.println("app B method return value is: " + IntValue);
        System.out.println("\n");
        
        if (IntValue == TransactionManager.INDICATE_OPERATION_SUCCESS)
        {
            CLT = ATM.get_ReplyControlTuple();
            
            System.out.println("app B received control tuple with sourceID: " + CLT.get_SourceID_Field());
            System.out.println("app B received control tuple with destinationID: " + CLT.get_DestinationID_Field());
            System.out.println("app B received control tuple with type: " + CLT.get_Type_Field());
            System.out.println("app B received control tuple with message: " + CLT.get_RequestMessage_Field());    
        }
        
        CLT.set_SourceID_Field(this.FIELD_APP_PATH_B);
        CLT.set_DestinationID_Field(this.FIELD_APP_PATH_A);
        CLT.set_Type_Field_to_Coordination();
        CLT.set_RequestMessage_Field(this.FIELD_CoordinationMessage2);
        System.out.println("app B setting ControlTuple fields ");
        System.out.println("\n");
        
        IntValue = ATM.perform_ActivePersistentCoordinativeTransaction(CLT, AbsolutePathB);
        System.out.println("app B executing perform_ActivePersistentCoordinativeTransaction() ");
        System.out.println("app B method return value is: " + IntValue);
        System.out.println("\n");
        
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        //System.out.println("app B finished test of perform_PassivePersistentCoordinativeTransaction() ");
        System.out.println("app B finished test of coordination ");
        System.out.println("\n");
    }
    
}
