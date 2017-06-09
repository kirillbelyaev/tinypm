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

package edu.csu.lpm.DB.factory;

import edu.csu.lpm.DB.implementation.UserAuthDAO_implement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author maalv
 */

public class UserAuthDAO_Factory 
{
    
    public UserAuthDAO_implement create(Connection conn) throws SQLException
    {
            return new UserAuthDAO_implement(conn);
    }
    
}
