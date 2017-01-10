/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.lpm.DB.exceptions;

/**
 * @author I811194 (Qingtong Yan)
 *
 */

public class RecordDAO_Exception extends DAO_Exception
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
	public RecordDAO_Exception(String message)
	{
		super(message);
	}

	/**
	 * Method 'UserDaoException'
	 * 
	 * @param message
	 * @param cause
	 */
	public RecordDAO_Exception(String message, Throwable cause)
	{
		super(message, cause);
	}

}
