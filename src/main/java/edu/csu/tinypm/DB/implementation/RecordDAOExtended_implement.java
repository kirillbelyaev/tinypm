/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/


/*
Data access object
data access object (DAO) is an object that provides an abstract interface to 
some type of database or other persistence mechanism. By mapping application 
calls to the persistence layer, DAO provide some specific data operations 
without exposing details of the database.
 */
package edu.csu.tinypm.DB.implementation;

import edu.csu.tinypm.interfaces.DB_Constants;
import edu.csu.tinypm.DB.DAO.RecordDAOExtended;
import edu.csu.tinypm.DB.DTO.Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;
import edu.csu.tinypm.interfaces.DB_Constants_Extended;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author kirill
 */
public class RecordDAOExtended_implement implements RecordDAOExtended  
{
    
    private Connection conn = null;
    
    
    public RecordDAOExtended_implement(Connection c) //constructor
    {
	conn = c;
    }
    
    
    @Override
    public int createTable_LC_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return -1;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.create_LC_DB_SQL);

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return 0;
    }
    
    @Override
    public int dropTable_LC_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return -1;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.drop_LC_DB_SQL);
            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
                    return 0;
    }
    
    
    @Override
    public void closeConnection() throws RecordDAOException
    {
            if (this.conn == null) return;
        
            try 
            {
                    this.conn.close();
            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }	
    }
    
    
    @Override
    public Integer countDistinctAppCapRecords(Record r) throws RecordDAOException //table name, uid
    {
            if (this.conn == null) return -1;
            if (r == null) return -1;

            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = -1;

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_COUNT_APP_CAP_SQL);

                    int index = 1;

                    ps.setString(index++, r.getApp_PATH());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (DB_Constants.LC_DB_TABLE_NAME.equals(DB_Constants.LC_DB_TABLE_NAME)) 
                            {
                                    count = rs.getInt(DB_Constants.COUNT);
                            } else return -1;
                    }
                        rs.close();

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    
    @Override
    public Record[] readRecordsOnAPP(Record r) throws RecordDAOException //table name, uid
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Record> rows = new ArrayList<Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ON_APP_SQL);

                    int index = 1;

                    ps.setString(index++, r.getApp_PATH());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (DB_Constants.LC_DB_TABLE_NAME.equals(DB_Constants.LC_DB_TABLE_NAME))
                            {
                                    Record rec = new Record();
                                    rec.setUID(rs.getString(DB_Constants.COLUMN_UID));
                                    rec.setApp_PATH(rs.getString(DB_Constants.COLUMN_APP_PATH));
                                    rec.setCAP_Attr(rs.getString(DB_Constants.COLUMN_CAP_ATTR));
                                    rec.setStatus(rs.getString(DB_Constants.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Record [] array = new Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    @Override
    public Record[] readRecordsOnAllAPPs() throws RecordDAOException //table name, uid
    {
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Record> rows = new ArrayList<Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_APPS_SQL);

                    int index = 1;

                    //ps.setString(index++, DB_Constants.COLUMN_APP_PATH);

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (DB_Constants.LC_DB_TABLE_NAME.equals(DB_Constants.LC_DB_TABLE_NAME))
                            {
                                    Record rec = new Record();
                                    rec.setApp_PATH(rs.getString(DB_Constants.COLUMN_APP_PATH));
                                    
                                    //rec.setUID(rs.getString(DB_Constants.COLUMN_UID));
                                    //rec.setCAP_Attr(rs.getString(DB_Constants.COLUMN_CAP_ATTR));
                                    //rec.setStatus(rs.getString(DB_Constants.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Record [] array = new Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    
	private int checkIfRecordExists(Record r) throws RecordDAOException	
	{
		if (r == null) return -1; //indicate error
                if (this.conn == null) return -1;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{	
			ps = this.conn.prepareStatement(DB_Constants.SELECT_ON_APP_AND_CAP_SQL);
			
			int index = 1;
				
			ps.setString(index++, r.getApp_PATH());
			ps.setString(index++, r.getCAP_Attr());
				
			this.conn.setAutoCommit(false);
			rs = ps.executeQuery();
			this.conn.setAutoCommit(true);
			
			if (rs.next())
			{
				rs.close();
                                rs = null;
				System.out.println("checkRecordExists: entry exists!");
				return 1; //entry exists
			}	
			
			rs.close();
                        rs = null;
			
		} catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }    
			
			return 0; //no entry exists
	}
        
    @Override
        public int writeRecord(Record r) throws RecordDAOException
	{
		if (r == null) return -1;
		
		try
		{	
		if (this.checkIfRecordExists(r) == 0) //no record exists
		{	
			if (this.insertRecord(r) != 0) return -1;
			
		} else {//if record exists - just update it	
			if (this.updateRecord(r) != 0) return -2;
		}
			
		} catch (Exception e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
			return 0;
	}
        
    
        private int insertRecord(Record r) throws RecordDAOException
	{
		if (r == null) return -1;
                if (this.conn == null) return -1;
		
		PreparedStatement ps = null;
		
		try 
		{
                        if (DB_Constants.LC_DB_TABLE_NAME.equals(DB_Constants.LC_DB_TABLE_NAME))
                        {
				ps = this.conn.prepareStatement(DB_Constants.INSERT_LC_DB_SQL);

				int index = 1;
				
				ps.setString(index++, r.getUID());
				ps.setString(index++, r.getApp_PATH());
				ps.setString(index++, r.getCAP_Attr());
				ps.setString(index++, r.getStatus());
				
				ps.addBatch();
				this.conn.setAutoCommit(false);
				ps.executeBatch();
				this.conn.setAutoCommit(true);
		
			} else return -1;
			
		} catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
			
			return 0;
	}
	
    
    private int updateRecord(Record r) throws RecordDAOException
    {
            if (r == null) return -1;
            if (this.conn == null) return -1;

            PreparedStatement ps = null;

            try 
            {
                    if (DB_Constants.LC_DB_TABLE_NAME.equals(DB_Constants.LC_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants.UPDATE_LC_DB_SQL);

                            int index = 1;

                            //ps.setString(index++, r.getUID());
                            ps.setString(index++, r.getApp_PATH());
                            ps.setString(index++, r.getCAP_Attr());
                            //ps.setString(index++, r.getStatus());

                            this.conn.setAutoCommit(false);
                            ps.executeUpdate();
                            this.conn.setAutoCommit(true);
                    } else return -1;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return 0;
    }    

    
    
    
    @Override
    public int deleteRecordsOnAPPandCAP(Record r) throws RecordDAOException
    {
            if (r == null) return -1;
             if (this.conn == null) return -1;

            String statement = null;
            PreparedStatement ps = null;
            
            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants.DELETE_ON_APP_AND_CAP_SQL);

                    int index = 1;

                    ps.setString(index++, r.getApp_PATH());
                    ps.setString(index++, r.getCAP_Attr());

                    this.conn.setAutoCommit(false);
                    ps.executeUpdate();
                    this.conn.setAutoCommit(true);

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return 0;
    }

    

    @Override
    public String getCurrentDatetime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCurrentDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* new API methods to support the policy class abstraction */
    
    @Override
    public int createTable_APPS_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return -1;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants_Extended.create_APPS_DB_SQL);

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return 0;
    } 
    
    
    @Override
    public int dropTable_APPS_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return -1;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants_Extended.drop_APPS_DB_SQL);
            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
                    return 0;
    }
    
    
}
