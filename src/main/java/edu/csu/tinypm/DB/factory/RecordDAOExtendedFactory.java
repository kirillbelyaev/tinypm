/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.tinypm.DB.factory;


import edu.csu.tinypm.DB.implementation.RecordDAOExtended_implement;
import java.sql.Connection;

public class RecordDAOExtendedFactory
{

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return UserDao
	 */
	public RecordDAOExtended_implement create(Connection conn)
	{
		return new RecordDAOExtended_implement( conn );
	}

}
