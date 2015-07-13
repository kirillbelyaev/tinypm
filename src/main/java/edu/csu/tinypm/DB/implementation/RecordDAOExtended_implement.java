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

import edu.csu.tinypm.DB.DAO.RecordDAOExtended;
import edu.csu.tinypm.DB.DTO.Apps_Table_Record;
import edu.csu.tinypm.DB.DTO.Policy_Classes_Table_Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;
import edu.csu.tinypm.DB.interfaces.Apps_Table;
import edu.csu.tinypm.DB.interfaces.DB_Constants_Extended;
import edu.csu.tinypm.DB.interfaces.Policy_Classes_Table;
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
    public void closeConnection() throws RecordDAOException
    {
            if (this.conn == null) return;
        
            try 
            {
                    this.conn.close();
            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }	
    }
        
    private int check_If_Apps_Table_Record_Exists(Apps_Table_Record r) throws RecordDAOException //on app_path and PCID
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_APP_PATH());
                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    if (rs.next())
                    {
                            rs.close();
                            rs = null;
                            return RECORD_EXISTS; //entry exists
                    }	

                    rs.close();
                    rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }    

                    return EMPTY_RESULT; //no entry exists
    }
        
        
    @Override
    public int write_Apps_Table_Record(Apps_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try
            {	
                if (this.check_If_Apps_Table_Record_Exists(r) == EMPTY_RESULT) //no record exists
                {	
                        if (this.insert_Apps_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;


                } else if (this.check_If_Apps_Table_Record_Exists(r) == RECORD_EXISTS) 
                {/* record exists */
                    /* if record exists - just update it */	
                    if (r.get_UPDATE_COLUMN().equals(Apps_Table.COLUMN_POLICY_CLASS_ID)) /* check if the update column
                            is a PCID column */
                        if (this.update_Apps_Table_Record_on_APP_and_PCID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
                }

            } catch (Exception e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }

    
    private int insert_Apps_Table_Record(Apps_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                    {
                            ps = this.conn.prepareStatement(DB_Constants_Extended.INSERT_INTO_APPS_DB_SQL);

                            int index = 1;

                            ps.setString(index++, r.get_COLUMN_APP_DESC());
                            ps.setString(index++, r.get_COLUMN_APP_PATH());
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                            ps.setString(index++, r.get_COLUMN_APP_CONTAINER_ID());
                            ps.setString(index++, r.get_COLUMN_STATUS());

                            ps.addBatch();
                            this.conn.setAutoCommit(false);
                            ps.executeBatch();
                            this.conn.setAutoCommit(true);

                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Apps_Table_Record_on_APP_and_PCID(Apps_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants_Extended.UPDATE_APPS_DB_ON_APP_AND_PCID_SET_PCID_SQL);

                            int index = 1;
                            
                            ps.setString(index++, r.get_COLUMN_APP_PATH());
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                            
                            /*
                            ps.setString(index++, r.getCOLUMN_APP_DESC());
                            ps.setString(index++, r.getCOLUMN_APP_CONTAINER_ID());
                            ps.setString(index++, r.getCOLUMN_STATUS());
                            */
                            

                            this.conn.setAutoCommit(false);
                            ps.executeUpdate();
                            this.conn.setAutoCommit(true);
                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int delete_Apps_Table_Records_On_APP_and_PCID(Apps_Table_Record r) throws RecordDAOException
    {
            if (r == null) return -1;
             if (this.conn == null) return -1;

            String statement = null;
            PreparedStatement ps = null;
            
            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants_Extended.DELETE_FROM_APPS_DB_ON_APP_AND_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_APP_PATH());
                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

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
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants_Extended.create_APPS_DB_SQL);

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    } 
    
    
    @Override
    public int dropTable_APPS_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants_Extended.drop_APPS_DB_SQL);
            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public Apps_Table_Record[] read_Apps_Table_Records_On_APP(Apps_Table_Record r) throws RecordDAOException       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Apps_Table_Record> rows = new ArrayList<Apps_Table_Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ON_APP_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_APP_PATH());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                            {
                                    Apps_Table_Record rec = new Apps_Table_Record();
                                    
                                    rec.set_COLUMN_APP_DESC(rs.getString(Apps_Table.COLUMN_APP_DESC));
                                    
                                    rec.set_COLUMN_APP_PATH(rs.getString(Apps_Table.COLUMN_APP_PATH));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(Apps_Table.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_APP_CONTAINER_ID(rs.getString(Apps_Table.COLUMN_APP_CONTAINER_ID));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(Apps_Table.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Apps_Table_Record [] array = new Apps_Table_Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public Apps_Table_Record[] read_Apps_Table_Records_On_APP_and_PCID(Apps_Table_Record r) throws RecordDAOException       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Apps_Table_Record> rows = new ArrayList<Apps_Table_Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_APP_PATH());
                    
                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                            {
                                    Apps_Table_Record rec = new Apps_Table_Record();
                                    
                                    rec.set_COLUMN_APP_DESC(rs.getString(Apps_Table.COLUMN_APP_DESC));
                                    
                                    rec.set_COLUMN_APP_PATH(rs.getString(Apps_Table.COLUMN_APP_PATH));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(Apps_Table.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_APP_CONTAINER_ID(rs.getString(Apps_Table.COLUMN_APP_CONTAINER_ID));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(Apps_Table.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Apps_Table_Record [] array = new Apps_Table_Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    
    @Override
    public Apps_Table_Record[] read_Apps_Table_Records_On_PCID(Apps_Table_Record r) throws RecordDAOException       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Apps_Table_Record> rows = new ArrayList<Apps_Table_Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                            {
                                    Apps_Table_Record rec = new Apps_Table_Record();
                                    
                                    rec.set_COLUMN_APP_DESC(rs.getString(Apps_Table.COLUMN_APP_DESC));
                                    
                                    rec.set_COLUMN_APP_PATH(rs.getString(Apps_Table.COLUMN_APP_PATH));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(Apps_Table.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_APP_CONTAINER_ID(rs.getString(Apps_Table.COLUMN_APP_CONTAINER_ID));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(Apps_Table.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Apps_Table_Record [] array = new Apps_Table_Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
   
    
    
    @Override
    public Apps_Table_Record[] read_Apps_Table_Records_On_All_APPs() throws RecordDAOException //table name, uid
    {
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Apps_Table_Record> rows = new ArrayList<Apps_Table_Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ALL_APPS_SQL);

                    int index = 1;

                    //ps.setString(index++, DB_Constants.COLUMN_APP_PATH);

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                            {
                                    Apps_Table_Record rec = new Apps_Table_Record();
                                    
                                    rec.set_COLUMN_APP_PATH(rs.getString(Apps_Table.COLUMN_APP_PATH));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Apps_Table_Record [] array = new Apps_Table_Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
     
     
    @Override
    public Integer count_Distinct_Apps_Table_Records_on_PCID(Apps_Table_Record r) throws RecordDAOException
    {
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = -1;

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_COUNT_APPS_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (Apps_Table.APPS_DB_TABLE_NAME.equals(Apps_Table.APPS_DB_TABLE_NAME))
                            {
                                    count = rs.getInt(DB_Constants_Extended.COUNT);
                            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        rs.close();

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    
    /* policy classes table operations */
    
    @Override
    public int create_Table_PCS_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants_Extended.create_PCS_DB_SQL);

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    } 
       
       
    @Override
    public int drop_Table_PCS_DB() throws RecordDAOException
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants_Extended.drop_PCS_DB_SQL);
            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }
       
    
       
    @Override
    public Policy_Classes_Table_Record[] read_Policy_Classes_Table_Records_On_PCID(Policy_Classes_Table_Record r) throws RecordDAOException       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Policy_Classes_Table_Record> rows = new ArrayList<Policy_Classes_Table_Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_ALL_FROM_PCS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                    
                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (Policy_Classes_Table.PCS_DB_TABLE_NAME.equals(Policy_Classes_Table.PCS_DB_TABLE_NAME))
                            {
                                    Policy_Classes_Table_Record rec = new Policy_Classes_Table_Record();
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(Policy_Classes_Table.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_NAME(rs.getString(Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_POLICIES(rs.getString(Policy_Classes_Table.COLUMN_POLICY_CLASS_POLICIES));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(Apps_Table.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Policy_Classes_Table_Record [] array = new Policy_Classes_Table_Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public Policy_Classes_Table_Record[] read_Policy_Classes_Table_Records_On_All_Classes() throws RecordDAOException       
    {
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <Policy_Classes_Table_Record> rows = new ArrayList<Policy_Classes_Table_Record>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_ALL_FROM_PCS_DB_SQL);
                    
                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (Policy_Classes_Table.PCS_DB_TABLE_NAME.equals(Policy_Classes_Table.PCS_DB_TABLE_NAME))
                            {
                                    Policy_Classes_Table_Record rec = new Policy_Classes_Table_Record();
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(Policy_Classes_Table.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_NAME(rs.getString(Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_POLICIES(rs.getString(Policy_Classes_Table.COLUMN_POLICY_CLASS_POLICIES));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(Apps_Table.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    Policy_Classes_Table_Record [] array = new Policy_Classes_Table_Record [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public Integer count_Distinct_Policy_Classes_Table_Records_on_PCID() throws RecordDAOException
    {
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = -1;

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_PCS_DB_COUNT_POLICY_CLASSES_ON_PCID_SQL);

                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (Policy_Classes_Table.PCS_DB_TABLE_NAME.equals(Policy_Classes_Table.PCS_DB_TABLE_NAME))
                            {
                                    count = rs.getInt(DB_Constants_Extended.COUNT);
                            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    rs.close();

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    private int check_If_Policy_Classes_Table_Record_Exists(Policy_Classes_Table_Record r) throws RecordDAOException //on PCID
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_PCS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    if (rs.next())
                    {
                            rs.close();
                            rs = null;
                            //System.out.println("check_If_Apps_Table_Record_Exists: entry exists!");
                            return RECORD_EXISTS; //entry exists
                    }	

                    rs.close();
                    rs = null;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }    

                    return EMPTY_RESULT; //no entry exists
    }
    
    private int insert_Policy_Classes_Table_Record(Policy_Classes_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (Policy_Classes_Table.PCS_DB_TABLE_NAME.equals(Policy_Classes_Table.PCS_DB_TABLE_NAME))
                    {
                            ps = this.conn.prepareStatement(DB_Constants_Extended.INSERT_INTO_PCS_DB_SQL);

                            int index = 1;

                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_NAME());
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_POLICIES());
                            ps.setString(index++, r.get_COLUMN_STATUS());

                            ps.addBatch();
                            this.conn.setAutoCommit(false);
                            ps.executeBatch();
                            this.conn.setAutoCommit(true);

                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Policy_Classes_Table_Record_on_PCID(Policy_Classes_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (Policy_Classes_Table.PCS_DB_TABLE_NAME.equals(Policy_Classes_Table.PCS_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants_Extended.UPDATE_PCS_DB_ON_PCID_SQL);

                            int index = 1;
                            
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_NAME());
                            
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                            
                            /*
                            ps.setString(index++, r.getCOLUMN_CAPS());
                            ps.setString(index++, r.getCOLUMN_STATUS());
                            */
                            
                            this.conn.setAutoCommit(false);
                            ps.executeUpdate();
                            this.conn.setAutoCommit(true);
                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    private int update_Policy_Classes_Table_Record_on_PCID_and_POLICIES(Policy_Classes_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (Policy_Classes_Table.PCS_DB_TABLE_NAME.equals(Policy_Classes_Table.PCS_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants_Extended.UPDATE_PCS_DB_ON_PCID_AND_CAPS_SQL);

                            int index = 1;
                            
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_POLICIES());
                            
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                            
                            
                            this.conn.setAutoCommit(false);
                            ps.executeUpdate();
                            this.conn.setAutoCommit(true);
                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int write_Policy_Classes_Table_Record(Policy_Classes_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try
            {	
                if (this.check_If_Policy_Classes_Table_Record_Exists(r) == EMPTY_RESULT) //no record exists
                {	
                        if (this.insert_Policy_Classes_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                } else if (this.check_If_Policy_Classes_Table_Record_Exists(r) == RECORD_EXISTS) //record exists
                {//if record exists - just update it	

                        if (r.get_UPDATE_COLUMN().equals(Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME)) /* check if the update column
                            is a name column */
                            if (this.update_Policy_Classes_Table_Record_on_PCID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (r.get_UPDATE_COLUMN().equals(Policy_Classes_Table.COLUMN_POLICY_CLASS_POLICIES)) /* check if the update column
                            is policies column */
                            if (this.update_Policy_Classes_Table_Record_on_PCID_and_POLICIES(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
                }

            } catch (Exception e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int delete_Policy_Classes_Table_Records_On_PCID(Policy_Classes_Table_Record r) throws RecordDAOException
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            String statement = null;
            PreparedStatement ps = null;
            
            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants_Extended.DELETE_FROM_PCS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    ps.executeUpdate();
                    this.conn.setAutoCommit(true);

            } catch(SQLException e) { throw new RecordDAOException( "Exception: " + e.getMessage(), e ); }
            
            /*
            Possible refactoring to every method to obtain the int exit code. 
            but that defeats the purpose of using specialized exception class.
            
            } catch(SQLException e) { System.out.println("Exception: " + e.getMessage() ); return INDICATE_SQL_EXCEPTION;}
            */        
                    
                    return INDICATE_EXECUTION_SUCCESS;
    }

    
    
}
