/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
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
