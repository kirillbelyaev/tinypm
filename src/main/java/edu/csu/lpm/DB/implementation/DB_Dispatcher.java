/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.lpm.DB.implementation;

import edu.csu.lpm.DB.factory.RecordDAO_Factory;
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
    
}
