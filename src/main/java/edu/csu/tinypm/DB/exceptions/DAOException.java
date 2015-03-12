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

public class DAOException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6697081603494519019L;
	protected Throwable throwable;

	/**
	 * Method 'DaoException'
	 * 
	 * @param message
	 */
	public DAOException(String message)
	{
		super(message);
	}

	/**
	 * Method 'DaoException'
	 * 
	 * @param message
	 * @param throwable
	 */
	public DAOException(String message, Throwable throwable)
	{
		super(message);
		this.throwable = throwable;
	}

	/**
	 * Method 'getCause'
	 * 
	 * @return Throwable
	 */
	public Throwable getCause()
	{
		return throwable;
	}

}
