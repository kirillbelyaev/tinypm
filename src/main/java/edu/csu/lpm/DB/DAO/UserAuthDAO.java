/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
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

package edu.csu.lpm.DB.DAO;

import java.sql.SQLException;

/**
 * This is responsible for checking and storing user authentication credentials
 * @author maalv
 */

public interface UserAuthDAO
{
    final String USER_AUTH_DB_TABLE_NAME = "auth_db";
    String USERNAME = "username";
    String PASSWORD = "password";
    
    public int createTable_AuthDB() throws SQLException;
    
    public int insert_defaultAuthCredentials() throws SQLException;
    
    public String getUsernameFromDB() throws SQLException;
    
    public String getPasswordFromDB() throws SQLException;
    
    public int updateAuthPassword(String newPassword) throws SQLException;
}
