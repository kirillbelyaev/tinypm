/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.machine;

import edu.csu.tinypm.interfaces.DB_Constants;
import java.io.File;

/**
 *
 * @author kirill
 */
public class Main 
{
    
public static void main(String[] args)  throws Exception 
{

File lcdb = new File(DB_Constants.LC_DB_NAME);
PM_Shell sh = new PM_Shell();

if(lcdb.exists())
{
    sh.process_UserInput();
} else
{
    System.out.println("PM Database " + DB_Constants.LC_DB_NAME + " does not exist! Exiting.");
}    


}//end of main

}
