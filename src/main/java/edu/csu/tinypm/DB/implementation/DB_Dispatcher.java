/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.DB.implementation;

import edu.csu.tinypm.DB.factory.RecordDAOFactory;
import java.sql.SQLException;

/**
 *
 * @author kirill
 */
public class DB_Dispatcher 
{
    
    private ConnManager cm = null;
    private RecordDAOFactory factory = null;
    private RecordDAO_implement db = null;
    
    
    public RecordDAO_implement dispatch_DB_Access() throws SQLException
    {
        this.cm = new ConnManager();
        this.factory = new RecordDAOFactory();
        
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
    
}
