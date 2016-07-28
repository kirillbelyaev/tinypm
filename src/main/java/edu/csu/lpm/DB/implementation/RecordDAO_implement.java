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
package edu.csu.lpm.DB.implementation;

import edu.csu.lpm.DB.DAO.RecordDAO;
import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;
import edu.csu.lpm.DB.interfaces.DB_Constants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import edu.csu.lpm.DB.interfaces.CapabilitiesClassesTable;
import edu.csu.lpm.DB.interfaces.CommunicativeClassesTable;
import edu.csu.lpm.DB.interfaces.ComponentsTable;

/**
 *
 * @author kirill
 */
public class RecordDAO_implement implements RecordDAO  
{
    
    private Connection conn = null;
    
    
    public RecordDAO_implement(Connection c) //constructor
    {
	conn = c;
    }
    
    
    @Override
    public void closeConnection() throws RecordDAO_Exception
    {
            if (this.conn == null) return;
        
            try 
            {
                    this.conn.close();
            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }	
    }
        
    private int check_If_Apps_Table_Record_Exists(ComponentsTableRecord r) throws RecordDAO_Exception //on app_path and PCID
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            try 
            {	
                    /* too specific selection - an app record with a particular pcid might surely not exist
                    but there might be a record with a different pcid thus creating several
                    records of an app belonging to different policy classes that is NOT what we want */     
                    //ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL);
                
                    /* we have to make sure that only a single app record with app_path column exists in the db */
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_APPS_DB_ON_APP_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
                    
                    //ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

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

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }    

                    return EMPTY_RESULT; //no entry exists
    }
        
        
    @Override
    public int write_Apps_Table_Record(ComponentsTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            
            CapabilitiesClassesTableRecord pcr = null;

            try
            {
                pcr = new CapabilitiesClassesTableRecord();
                
                pcr.set_COLUMN_POLICY_CLASS_ID(r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
                
                if (this.check_If_Policy_Classes_Table_Record_Exists(pcr) == EMPTY_RESULT) return INDICATE_CONDITIONAL_EXIT_STATUS; //no record exists
                
                if (this.check_If_Apps_Table_Record_Exists(r) == EMPTY_RESULT) //no record exists
                {	
                        if (this.insert_Apps_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;


                } else if (this.check_If_Apps_Table_Record_Exists(r) == RECORD_EXISTS) 
                {/* record exists */
                    /* if record exists - just update it */	
                    if (r.get_UPDATE_COLUMN().equals(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID)) /* check if the update column
                            is a PCID column */
                    {
                        if (this.update_Apps_Table_Record_on_APP_and_PCID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                }

            } catch (Exception e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }

    
    private int insert_Apps_Table_Record(ComponentsTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                    {
                            ps = this.conn.prepareStatement(DB_Constants.INSERT_INTO_APPS_DB_SQL);

                            int index = 1;

                            ps.setString(index++, r.get_COLUMN_COMPONENT_DESC());
                            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
                            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
                            ps.setString(index++, r.get_COLUMN_COMPONENT_CONTAINER_ID());
                            ps.setString(index++, r.get_COLUMN_STATUS());

                            ps.addBatch();
                            this.conn.setAutoCommit(false);
                            ps.executeBatch();
                            this.conn.setAutoCommit(true);

                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Apps_Table_Record_on_APP_and_PCID(ComponentsTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants.UPDATE_APPS_DB_ON_APP_SET_PCID_SQL);

                            int index = 1;
                            
                            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
                            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
                            
                            
                            /*
                            ps.setString(index++, r.getCOLUMN_APP_DESC());
                            ps.setString(index++, r.getCOLUMN_APP_CONTAINER_ID());
                            ps.setString(index++, r.getCOLUMN_STATUS());
                            */
                            

                            this.conn.setAutoCommit(false);
                            ps.executeUpdate();
                            this.conn.setAutoCommit(true);
                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int delete_Apps_Table_Records_On_APP_and_PCID(ComponentsTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
             if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            String statement = null;
            PreparedStatement ps = null;
            
            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_APPS_DB_ON_APP_AND_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
                    ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    ps.executeUpdate();
                    this.conn.setAutoCommit(true);

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
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
    public int createTable_APPS_DB() throws RecordDAO_Exception
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.create_APPS_DB_SQL);

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    } 
    
    
    @Override
    public int dropTable_APPS_DB() throws RecordDAO_Exception
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.drop_APPS_DB_SQL);
            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public ComponentsTableRecord[] read_Apps_Table_Records_On_APP(ComponentsTableRecord r) throws RecordDAO_Exception       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_APPS_DB_ON_APP_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                            {
                                    ComponentsTableRecord rec = new ComponentsTableRecord();
                                    
                                    rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));
                                    
                                    rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));
                                    
                                    rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));
                                    
                                    rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public ComponentsTableRecord[] read_Apps_Table_Records_On_APP_and_PCID(ComponentsTableRecord r) throws RecordDAO_Exception       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
                    
                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                            {
                                    ComponentsTableRecord rec = new ComponentsTableRecord();
                                    
                                    rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));
                                    
                                    rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));
                                    
                                    rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));
                                    
                                    rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    
    @Override
    public ComponentsTableRecord[] read_Apps_Table_Records_On_PCID(ComponentsTableRecord r) throws RecordDAO_Exception       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_APPS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                            {
                                    ComponentsTableRecord rec = new ComponentsTableRecord();
                                    
                                    rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));
                                    
                                    rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));
                                    
                                    rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));
                                    
                                    rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
   
    
    
    @Override
    public ComponentsTableRecord[] read_Apps_Table_Records_On_All_APPs() throws RecordDAO_Exception //table name, uid
    {
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_APPS_DB_ALL_APPS_SQL);

                    int index = 1;

                    //ps.setString(index++, DB_Constants.COLUMN_APP_PATH);

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                            {
                                    ComponentsTableRecord rec = new ComponentsTableRecord();
                                    
                                    rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
     
     
    @Override
    public Integer count_Distinct_Apps_Table_Records_on_PCID(ComponentsTableRecord r) throws RecordDAO_Exception
    {
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = -1;

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_APPS_DB_COUNT_APPS_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (ComponentsTable.COMPONENTS_DB_TABLE_NAME.equals(ComponentsTable.COMPONENTS_DB_TABLE_NAME))
                            {
                                    count = rs.getInt(DB_Constants.COUNT);
                            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        rs.close();

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    
    /* policy classes table operations */
    
    @Override
    public int create_Table_PCS_DB() throws RecordDAO_Exception
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.create_PCS_DB_SQL);

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    } 
       
       
    @Override
    public int drop_Table_PCS_DB() throws RecordDAO_Exception
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.drop_PCS_DB_SQL);
            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }
       
    
       
    @Override
    public CapabilitiesClassesTableRecord[] read_Capabilities_Classes_Table_Records_On_CID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <CapabilitiesClassesTableRecord> rows = new ArrayList<CapabilitiesClassesTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_FROM_PCS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                    
                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (CapabilitiesClassesTable.PCS_DB_TABLE_NAME.equals(CapabilitiesClassesTable.PCS_DB_TABLE_NAME))
                            {
                                    CapabilitiesClassesTableRecord rec = new CapabilitiesClassesTableRecord();
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_NAME(rs.getString(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_NAME));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_POLICIES(rs.getString(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_POLICIES));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    CapabilitiesClassesTableRecord [] array = new CapabilitiesClassesTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public CapabilitiesClassesTableRecord[] read_Policy_Classes_Table_Records_On_All_Classes() throws RecordDAO_Exception       
    {
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <CapabilitiesClassesTableRecord> rows = new ArrayList<CapabilitiesClassesTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_FROM_PCS_DB_SQL);
                    
                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (CapabilitiesClassesTable.PCS_DB_TABLE_NAME.equals(CapabilitiesClassesTable.PCS_DB_TABLE_NAME))
                            {
                                    CapabilitiesClassesTableRecord rec = new CapabilitiesClassesTableRecord();
                                    
                                    rec.set_COLUMN_POLICY_CLASS_ID(rs.getString(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_NAME(rs.getString(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_NAME));
                                    
                                    rec.set_COLUMN_POLICY_CLASS_POLICIES(rs.getString(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_POLICIES));
                                    
                                    rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                                    rows.add(rec);
                                    
                                    rec = null;	
                            } else return null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    CapabilitiesClassesTableRecord [] array = new CapabilitiesClassesTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public Integer count_Distinct_Capabilities_Classes_Table_Records_on_CID() throws RecordDAO_Exception
    {
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = -1;

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_PCS_DB_COUNT_POLICY_CLASSES_ON_PCID_SQL);

                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (CapabilitiesClassesTable.PCS_DB_TABLE_NAME.equals(CapabilitiesClassesTable.PCS_DB_TABLE_NAME))
                            {
                                    count = rs.getInt(DB_Constants.COUNT);
                            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    rs.close();

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    private int check_If_Policy_Classes_Table_Record_Exists(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception //on PCID
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_PCS_DB_ON_PCID_SQL);

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

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }    

                    return EMPTY_RESULT; //no entry exists
    }
    
    private int insert_Capabilities_Classes_Table_Record(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (CapabilitiesClassesTable.PCS_DB_TABLE_NAME.equals(CapabilitiesClassesTable.PCS_DB_TABLE_NAME))
                    {
                            ps = this.conn.prepareStatement(DB_Constants.INSERT_INTO_PCS_DB_SQL);

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

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Policy_Classes_Table_Record_on_PCID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (CapabilitiesClassesTable.PCS_DB_TABLE_NAME.equals(CapabilitiesClassesTable.PCS_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants.UPDATE_PCS_DB_ON_PCID_SQL);

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

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    private int update_Policy_Classes_Table_Record_on_PCID_and_POLICIES(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (CapabilitiesClassesTable.PCS_DB_TABLE_NAME.equals(CapabilitiesClassesTable.PCS_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants.UPDATE_PCS_DB_ON_PCID_AND_CAPS_SQL);

                            int index = 1;
                            
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_POLICIES());
                            
                            ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());
                            
                            
                            this.conn.setAutoCommit(false);
                            ps.executeUpdate();
                            this.conn.setAutoCommit(true);
                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int write_Capabilities_Classes_Table_Record(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try
            {	
                if (this.check_If_Policy_Classes_Table_Record_Exists(r) == EMPTY_RESULT) //no record exists
                {	
                        if (this.insert_Capabilities_Classes_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                } else if (this.check_If_Policy_Classes_Table_Record_Exists(r) == RECORD_EXISTS) //record exists
                {//if record exists - just update it	

                        if (r.get_UPDATE_COLUMN().equals(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_NAME)) /* check if the update column
                            is a name column */
                            if (this.update_Policy_Classes_Table_Record_on_PCID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                        if (r.get_UPDATE_COLUMN().equals(CapabilitiesClassesTable.COLUMN_POLICY_CLASS_POLICIES)) /* check if the update column
                            is policies column */
                            if (this.update_Policy_Classes_Table_Record_on_PCID_and_POLICIES(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
                }

            } catch (Exception e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int delete_Capabilities_Classes_Table_Records_On_CID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            String statement = null;
            PreparedStatement ps = null;
            
            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_PCS_DB_ON_PCID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    ps.executeUpdate();
                    this.conn.setAutoCommit(true);

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
            
            /*
            Possible refactoring to every method to obtain the int exit code. 
            but that defeats the purpose of using specialized exception class.
            
            } catch(SQLException e) { System.out.println("Exception: " + e.getMessage() ); return INDICATE_SQL_EXCEPTION;}
            */        
                    
                    return INDICATE_EXECUTION_SUCCESS;
    }

    
    /* communicative classes table operations */
    
    @Override
    public int create_Table_COMMC_DB() throws RecordDAO_Exception
    {
        Statement state = null;

        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        try 
        {
                state = this.conn.createStatement();
                state.executeUpdate(DB_Constants.create_COMMC_DB_SQL);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    } 
       
       
    @Override
    public int drop_Table_COMMC_DB() throws RecordDAO_Exception
    {
        Statement state = null;

        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        try 
        {
                state = this.conn.createStatement();
                state.executeUpdate(DB_Constants.drop_COMMC_DB_SQL);
        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public Integer count_Distinct_Communicative_Classes_Table_Records_on_CID()
    throws RecordDAO_Exception
    {
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;
            ResultSet rs = null;

            int count = -1;

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMMC_DB_COUNT_CLASSES_ON_CID_SQL);

                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME.equals(CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME))
                            {
                                    count = rs.getInt(DB_Constants.COUNT);
                            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    rs.close();

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    @Override
    public CommunicativeClassesTableRecord[] read_Communicative_Classes_Table_Records_On_CID(CommunicativeClassesTableRecord r)
    throws RecordDAO_Exception       
    {
            if (r == null) return null;
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <CommunicativeClassesTableRecord> rows = new ArrayList<CommunicativeClassesTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_FROM_COMMC_DB_ON_CID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_CLASS_ID());
                    
                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {                           
                        CommunicativeClassesTableRecord rec = new CommunicativeClassesTableRecord();

                        rec.set_COLUMN_CLASS_ID(rs.getString(CommunicativeClassesTable.COLUMN_CLASS_ID));

                        rec.set_COLUMN_CLASS_NAME(rs.getString(CommunicativeClassesTable.COLUMN_CLASS_NAME));

                        rec.set_COLUMN_COLLABORATION_RECORD(rs.getString(CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD));

                        rec.set_COLUMN_COORDINATION_RECORD(rs.getString(CommunicativeClassesTable.COLUMN_COORDINATION_RECORD));

                        rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                        rows.add(rec);

                        rec = null;
                    }
                        rs.close();
                        rs = null;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    if (rows.isEmpty()) return null;

                    CommunicativeClassesTableRecord [] array = new CommunicativeClassesTableRecord [ rows.size() ];
                    rows.toArray(array);
                    return array;
    }
    
    
    @Override
    public int delete_Communicative_Classes_Table_Records_On_CID(CommunicativeClassesTableRecord r) 
    throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            String statement = null;
            PreparedStatement ps = null;
            
            try 
            {	
                    ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_COMMC_DB_ON_CID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    ps.executeUpdate();
                    this.conn.setAutoCommit(true);

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
            
            /*
            Possible refactoring to every method to obtain the int exit code. 
            but that defeats the purpose of using specialized exception class.
            
            } catch(SQLException e) { System.out.println("Exception: " + e.getMessage() ); return INDICATE_SQL_EXCEPTION;}
            */        
                    
                    return INDICATE_EXECUTION_SUCCESS;
    }

    
    
    
}
