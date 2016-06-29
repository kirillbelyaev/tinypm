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

import edu.csu.lpm.TSLib.implementation.ControllerTransactionManager_implement;

/**
 *
 * @author kirill
 */
public class controller_coordinate implements Runnable
{
    private final String BaseLocation = System.getProperty("user.home") + "/containers/";
    
    @Override
    public void run() 
    {
        this.controller();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void controller()
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("controller started test of facilitate_BidirectionalPersistentCoordinativeTransaction() ");
        System.out.println("\n");
        
        int IntValue = -1;
        ControllerTransactionManager_implement CTM = new ControllerTransactionManager_implement();
        
        /* in reality obtained via TBD CPC persistent layer */
        String AbsolutePathTSA = this.BaseLocation + "/container-1/";
        String AbsolutePathTSB = this.BaseLocation + "/container-2/";
        
        System.out.println("controller TS A AbsolutePath is:" + AbsolutePathTSA);
        System.out.println("controller TS B AbsolutePath is:" + AbsolutePathTSB);
        
        while (true) /* run forever */
        {            
            IntValue = CTM.facilitate_BidirectionalPersistentCoordinativeTransaction(AbsolutePathTSA, AbsolutePathTSB);
            System.out.println("controller executing facilitate_BidirectionalPersistentCoordinativeTransaction() ");
            System.out.println("controller method return value is: " + IntValue);
            System.out.println("\n");
        }
        
//        System.out.println("\n"); 
//        System.out.println("--------------------------------------");
//        System.out.println("controller finished test facilitate_PersistentCoordinativeTransaction() ");
//        System.out.println("\n");
    }
    
}
