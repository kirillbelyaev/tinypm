/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.DB.exceptions;

/**
 * @author I811194 (Qingtong Yan)
 *
 */

public class RecordDAOException extends DAOException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6234473813988894505L;

	/**
	 * Method 'UserDaoException'
	 * 
	 * @param message
	 */
	public RecordDAOException(String message)
	{
		super(message);
	}

	/**
	 * Method 'UserDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public RecordDAOException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
