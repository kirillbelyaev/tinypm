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
