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

/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.lpm.DB.factory;


import edu.csu.lpm.DB.implementation.RecordDAO_implement;
import java.sql.Connection;

public class RecordDAO_Factory
{

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return UserDao
	 */
	public RecordDAO_implement create(Connection conn)
	{
		return new RecordDAO_implement( conn );
	}

}
