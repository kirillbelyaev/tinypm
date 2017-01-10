/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.lpm.DB.implementation;

import edu.csu.lpm.DB.interfaces.DB_Constants;
import java.sql.*;


public class ConnManager
{
    private static Driver driver = null;

    public synchronized Connection getConnection() throws SQLException
    {
        if (driver == null)
        {
            try
            {
                Class<?> jdbcDriverClass = Class.forName(DB_Constants.SQLITE_DRV );
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver( driver );
            }
            catch (Exception e)
            {
                System.out.println( "Failed to initialise JDBC driver" );
                e.printStackTrace();
            }
        }

	return DriverManager.getConnection(DB_Constants.DB_URI);
  
    }

	//preferred simpler method
	public synchronized Connection obtainConnection() throws SQLException
	{
		 Connection conn = null;
		try 
		{
			Class.forName(DB_Constants.SQLITE_DRV);
			conn = DriverManager.getConnection(DB_Constants.DB_URI);
		} catch (Exception e) { throw new SQLException( "Exception: " + e.getMessage(), e ); }	
		
		return conn;
	}	
    

	public void closeConnection(Connection conn)
	{
		try {
			if (conn != null) conn.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	public void close(PreparedStatement stmt)
	{
		try {
			if (stmt != null) stmt.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	public void close(ResultSet rs)
	{
		try {
			if (rs != null) rs.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}

	}

}
