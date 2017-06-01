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

package edu.csu.lpm.DB.implementation;

import edu.csu.lpm.DB.DAO.UserAuthDAO;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.factory.RecordDAO_Factory;
import edu.csu.lpm.DB.factory.UserAuthDAO_Factory;
import java.sql.SQLException;

/**
 *
 * @author kirill
 */

public class DB_Dispatcher 
{  
    private ConnManager cm = null;
    private RecordDAO_Factory factory = null;
    private RecordDAO_implement db = null;
    private UserAuthDAO_Factory userFactory = null;
    private UserAuthDAO userAuthDB = null;
    
    public RecordDAO_implement dispatch_DB_Access() throws SQLException
    {
        this.cm = new ConnManager();
        this.factory = new RecordDAO_Factory();
        
        if (this.cm == null && this.factory == null) return null;
        
        try 
        {
                this.db = this.factory.create(this.cm.obtainConnection());
                return this.db;
        } catch (SQLException e) 
        {
                throw new SQLException("Exception: " + e.getMessage(), e);
        } 
    }     
    
    public UserAuthDAO dispatch_userDB_Access() throws SQLException, RecordDAO_Exception
    {
        this.cm = new ConnManager();
        this.userFactory = new UserAuthDAO_Factory();
        
        if (this.cm == null && this.userFactory == null) return null;
        
        try 
        {
                this.userAuthDB = this.userFactory.create(this.cm.obtainConnection());
                return this.userAuthDB;
        } catch (SQLException e) 
        {
                throw new SQLException("Exception: " + e.getMessage(), e);
        } 
    }
    
}
