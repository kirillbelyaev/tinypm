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

/**
 *
 * @author kirill
 */

public class Controller_collaborate_Sequentially implements Runnable
{
    /*
    private final String BaseLocation = System.getProperty("user.home") + "/containers/";
    */
    /*
    private final String BaseLocation = "/s/oranges/a/nobackup/kirill/containers";
    */
    
    private final String DataObjectPath = "/s/apples/a/nobackup/kirill/logs/secure.log";
    private final String DataObjectReplicaPath = "/s/apples/a/nobackup/kirill/logs/secure.log.replica";
    private final String BASE_TS_LOCATION = "/s/apples/a/nobackup/kirill/containers/tuple_spaces/";
    private final String FIELD_COMP_PATH_A = "/s/apples/a/nobackup/kirill/containers/container-1/bin/componentA";
    /* private final String FIELD_APP_PATH_A = ts_location + "/container-1/bin/applicationA"; */
    
    private final String Dot = ".";
    private final String Root = "/";
    
    private int IntValue = -1;
    private Integer TID = -1;
    
    private Utilities ut = new Utilities ();
    
    /*
    use constructor to pass the thread ID
    */
    Controller_collaborate_Sequentially (Integer i)
    {
        this.TID = i;
    }        
    
    @Override
    public void run() 
    {
        this.create_ObjectReplica(this.TID);
    }
    
    
    public void create_ObjectReplica(Integer i) 
    {
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("started create_ObjectReplica() ");
        System.out.println("\n");
        
        long startTime = System.currentTimeMillis();
        
        this.IntValue = this.ut.create_ObjectReplica(this.DataObjectPath, this.DataObjectReplicaPath + this.Dot + i.toString(),
        this.BASE_TS_LOCATION + i.toString() + this.Root, this.FIELD_COMP_PATH_A);
        
        System.out.println("executing create_ObjectReplica() ");
        System.out.println("method return value is: " + this.IntValue);
        System.out.println("\n");
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Thread with ID " + this.TID.toString() + ": "
        + "create_ObjectReplica: replication took in seconds: " +  elapsedTime / 1000.0 );
        
        System.out.println("\n"); 
        System.out.println("--------------------------------------");
        System.out.println("finished create_ObjectReplica() ");
        System.out.println("\n");
    }
    
}
