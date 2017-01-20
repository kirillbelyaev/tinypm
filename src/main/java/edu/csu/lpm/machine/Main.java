/*
 * Lightweight Policy Machine for Linux (LPM) Reference Monitor Prototype
 *   
 * Copyright (C) 2015-2017 Kirill A Belyaev
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

package edu.csu.lpm.machine;

import edu.csu.lpm.DB.interfaces.DB_Constants;
import java.io.File;

/**
 *
 * @author kirill
 */
public class Main 
{
    
public static void main(String[] args)  throws Exception 
{

File db = new File(DB_Constants.DB_NAME);
LPM_Shell sh = new LPM_Shell();

if (db.exists())
{
    sh.process_UserInput();
} else
{
    System.out.println("PM Database " + DB_Constants.DB_NAME + " does not exist! Exiting.");
}    


}//end of main

}
