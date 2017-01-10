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
    
    /* on component_path_ID */ 
    private int check_If_ComponentsTableRecord_Exists(ComponentsTableRecord r) 
    throws RecordDAO_Exception 
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try 
        {	
            /* too specific selection - a component record with a particular cid might surely not exist
            but there might be a record with a different cid thus creating several
            records of a component belonging to different policy classes that is NOT what we want */     
            
            //ps = this.conn.prepareStatement(DB_Constants_Extended.SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL);

            /* we have to make sure that only a single component record with
            component_path_ID column exists in the db */
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_ON_COMPONENT_SQL);

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
    public int write_ComponentsTableRecord(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        CapabilitiesClassesTableRecord capr = null;
        CommunicativeClassesTableRecord comr = null;
        
        try
        {
            capr = new CapabilitiesClassesTableRecord();

            comr = new CommunicativeClassesTableRecord();
            
            capr.set_COLUMN_CLASS_ID(r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

            /* check for valid CID */
            if (comr.set_COLUMN_CLASS_ID(r.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID()) != RecordDAO.INDICATE_EXECUTION_SUCCESS) return RecordDAO.INDICATE_CONDITIONAL_EXIT_STATUS;
            
            
            if (this.check_If_ComponentsTableRecord_Exists(r) == EMPTY_RESULT) //no record exists
            {	
                if (this.insert_ComponentsTableRecord(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;


            } else if (this.check_If_ComponentsTableRecord_Exists(r) == RECORD_EXISTS) 
            {/* record exists */
                /* if record exists - just update it */	
                if (r.get_UPDATE_COLUMN().equals(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID)) /* check if the update column
                        is a CAP CID column */
                {
                    /* we have to make sure a specified CID exists */
                    if (this.check_If_CapabilitiesClassesTableCID_Exists(capr) == EMPTY_RESULT) return RecordDAO.INDICATE_CAPABILITIES_CLASS_RECORD_DOES_NOT_EXIST_STATUS; //no record exists

                    if (this.update_Components_Table_Record_CAPCID_on_Component(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
                } 
                
                if (r.get_UPDATE_COLUMN().equals(ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID)) /* check if the update column
                        is a COM CID column */
                {
                    /* we have to make sure a specified CID exists */
                    if (this.check_If_Communicative_Classes_Table_CID_Exists(comr) == EMPTY_RESULT) return RecordDAO.INDICATE_COMMUNICATIVE_CLASS_RECORD_DOES_NOT_EXIST_STATUS; //no record exists
            
                    if (this.update_Components_Table_Record_COMCID_on_Component(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
                }    
            }

        } catch (Exception e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
        
                return INDICATE_EXECUTION_SUCCESS;
    }

    
    private int insert_ComponentsTableRecord(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {            
            ps = this.conn.prepareStatement(DB_Constants.INSERT_INTO_COMPONENTS_DB_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_DESC());
            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_CONTAINER_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_TUPLE_SPACE_PATH());
            ps.setString(index++, r.get_COLUMN_STATUS());

            ps.addBatch();
            this.conn.setAutoCommit(false);
            ps.executeBatch();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Components_Table_Record_CAPCID_on_Component(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {   	
            ps = this.conn.prepareStatement(DB_Constants.UPDATE_COMPONENTS_DB_ON_COMPONENT_SET_CAPCID_SQL);

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

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Components_Table_Record_COMCID_on_Component(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {   	
            ps = this.conn.prepareStatement(DB_Constants.UPDATE_COMPONENTS_DB_ON_COMPONENT_SET_COMCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());


            /*
            ps.setString(index++, r.getCOLUMN_APP_DESC());
            ps.setString(index++, r.getCOLUMN_APP_CONTAINER_ID());
            ps.setString(index++, r.getCOLUMN_STATUS());
            */


            this.conn.setAutoCommit(false);
            ps.executeUpdate();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    @Override
    public int delete_Components_Table_Records_On_Component_and_CAPCID_and_COMCID(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        String statement = null;
        PreparedStatement ps = null;

        try 
        {	
            ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_COMPONENTS_DB_ON_COMPONENT_AND_CAPCID_AND_COMCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());

            this.conn.setAutoCommit(false);
            ps.executeUpdate();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }


    @Override
    public String getCurrentDatetime() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCurrentDate() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /* new API methods to support the policy class abstraction */
    
    @Override
    public int createTable_Components_DB() throws RecordDAO_Exception
    {
        Statement state = null;
        
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        try 
        {
            state = this.conn.createStatement();
            state.executeUpdate(DB_Constants.create_COMPONENTS_DB_SQL);
            
        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    } 
    
    
    @Override
    public int dropTable_Components_DB() throws RecordDAO_Exception
    {
        Statement state = null;

        if (this.conn == null) {
            return INDICATE_CONDITIONAL_EXIT_STATUS;
        }

        try 
        {
            state = this.conn.createStatement();
            state.executeUpdate(DB_Constants.drop_COMPONENTS_DB_SQL);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
        
                return INDICATE_EXECUTION_SUCCESS;
    }
    
    /* should return a single components record with a distinct pathID */
    @Override
    public ComponentsTableRecord[] read_Components_Table_Records_On_Component(ComponentsTableRecord r)
    throws RecordDAO_Exception
    {
        if (r == null) return null;
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_ON_COMPONENT_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            while (rs.next()) 
            {     
                ComponentsTableRecord rec = new ComponentsTableRecord();

                /* technically all the set operations should be checked on the
                success return value. However in this case this might not be necessary
                because fields are set using records that are read from the store */
                
                rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));

                rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));

                rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));
                
                rec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID));

                rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                
                rec.set_COLUMN_COMPONENT_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_ID));
                
                rec.set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(rs.getString(ComponentsTable.COLUMN_COMPONENT_TUPLE_SPACE_PATH));

                rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                rows.add(rec);

                rec = null;
            }
                rs.close();
                rs = null;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        if (rows.isEmpty()) return null;

        ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
        rows.toArray(array);
        return array;
    }
    
    /* 
    should return CID for a distinct component path ID.
    returns null if not found.
    */
    @Override
    public String get_ComponentsTableRecordsCOMCID_On_Component(ComponentsTableRecord r)
    throws RecordDAO_Exception
    {
        if (r == null) return null;
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_COMCID_FROM_COMPONENTS_DB_ON_COMPONENT_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            if (rs == null) return null;
            
            while (rs.next()) 
            {     
                ComponentsTableRecord rec = new ComponentsTableRecord();

                /* technically all the set operations should be checked on the
                success return value. However in this case this might not be necessary
                because fields are set using records that are read from the store */
                
                rec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID));
                
                rs.close();
                rs = null;
                
                return rec.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID();     
            }
            
            rs.close();
            rs = null;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); } 

        return null;
    }
    
    /* might not be that useful in practice */
    @Override
    public ComponentsTableRecord[] read_Components_Table_Records_On_Component_and_CAPCID(ComponentsTableRecord r) 
    throws RecordDAO_Exception       
    {
        if (r == null) return null;
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_ON_COMPONENT_AND_CAPCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_PATH_ID());

            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            while (rs.next()) 
            {               
                ComponentsTableRecord rec = new ComponentsTableRecord();
                
                /* technically all the set operations should be checked on the
                success output value. However in this case this might not be necessary
                because fields are set using records that are read from the store */

                rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));

                rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));

                rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));
                
                rec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID));

                rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));

                rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                rows.add(rec);

                rec = null;
            }
                rs.close();
                rs = null;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        if (rows.isEmpty()) return null;

        ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
        rows.toArray(array);
        return array;
    }
    
    
    /* should return all components records that belong to a distinct CAPCID */
    @Override
    public ComponentsTableRecord[] read_Components_Table_Records_On_CAPCID(ComponentsTableRecord r) 
    throws RecordDAO_Exception       
    {
        if (r == null) return null;
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_ON_CAPCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            while (rs.next()) 
            {
                ComponentsTableRecord rec = new ComponentsTableRecord();
                
                /* technically all the set operations should be checked on the
                success output value. However in this case this might not be necessary
                because fields are set using records that are read from the store */

                rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));

                rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));

                rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));

                rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                
                rec.set_COLUMN_COMPONENT_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_ID));
                
                rec.set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(rs.getString(ComponentsTable.COLUMN_COMPONENT_TUPLE_SPACE_PATH));

                rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                rows.add(rec);

                rec = null;
            }
                rs.close();
                rs = null;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        if (rows.isEmpty()) return null;

        ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
        rows.toArray(array);
        return array;
    }
   
    /* should return all components records that belong to a distinct COMCID */
    @Override
    public ComponentsTableRecord[] read_Components_Table_Records_On_COMCID(ComponentsTableRecord r) 
    throws RecordDAO_Exception       
    {
        if (r == null) return null;
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_ON_COMCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            while (rs.next()) 
            {
                ComponentsTableRecord rec = new ComponentsTableRecord();
                
                /* technically all the set operations should be checked on the
                success output value. However in this case this might not be necessary
                because fields are set using records that are read from the store */

                rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));

                rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));

                rec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID));

                rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                
                rec.set_COLUMN_COMPONENT_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_ID));
                
                rec.set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(rs.getString(ComponentsTable.COLUMN_COMPONENT_TUPLE_SPACE_PATH));

                rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                rows.add(rec);

                rec = null;
            }
                rs.close();
                rs = null;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        if (rows.isEmpty()) return null;

        ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
        rows.toArray(array);
        return array;
    }
    
    /* should return all components records in the components table */
    @Override
    public ComponentsTableRecord[] read_Components_Table_Records_On_All_Components() 
    throws RecordDAO_Exception //table name, pathID
    {
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList <ComponentsTableRecord> rows = new ArrayList<ComponentsTableRecord>();

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_ALL_COMPONENTS_ALL_COLUMNS_SQL);

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            while (rs.next()) 
            {        
                ComponentsTableRecord rec = new ComponentsTableRecord();
                
                /* technically all the set operations should be checked on the
                success output value. However in this case this might not be necessary
                because fields are set using records that are read from the store */

                rec.set_COLUMN_COMPONENT_DESC(rs.getString(ComponentsTable.COLUMN_COMPONENT_DESC));
                
                rec.set_COLUMN_COMPONENT_PATH_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_PATH_ID));
                
                rec.set_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID));
                
                rec.set_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID));

                rec.set_COLUMN_COMPONENT_CONTAINER_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID));
                
                rec.set_COLUMN_COMPONENT_ID(rs.getString(ComponentsTable.COLUMN_COMPONENT_ID));
                
                rec.set_COLUMN_COMPONENT_TUPLE_SPACE_PATH(rs.getString(ComponentsTable.COLUMN_COMPONENT_TUPLE_SPACE_PATH));

                rec.set_COLUMN_STATUS(rs.getString(ComponentsTable.COLUMN_STATUS));

                rows.add(rec);

                rec = null;
            }
                rs.close();
                rs = null;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        if (rows.isEmpty()) return null;

        ComponentsTableRecord [] array = new ComponentsTableRecord [ rows.size() ];
        rows.toArray(array);
        return array;
    }
     
    /* should return a count of all components records that belong to a distinct CAPCID */ 
    @Override
    public Integer count_Components_Table_Records_on_CAPCID(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = -1;

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_COUNT_COMPONENTS_ON_CAPCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_CAPABILITIES_CLASS_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            //rs = state.executeQuery(statement);

            while (rs.next()) 
            {              
                count = rs.getInt(DB_Constants.COUNT);
            }
                rs.close();

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        return count;
    }
    
    /* should return a count of all components records that belong to a distinct COMCID */ 
    @Override
    public Integer count_Components_Table_Records_on_COMCID(ComponentsTableRecord r) 
    throws RecordDAO_Exception
    {
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;
        ResultSet rs = null;

        int count = -1;

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_COMPONENTS_DB_COUNT_COMPONENTS_ON_COMCID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            //rs = state.executeQuery(statement);

            while (rs.next()) 
            {              
                count = rs.getInt(DB_Constants.COUNT);
            }
                rs.close();

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

        return count;
    }
    
    /* capabilities classes table operations */
    
    @Override
    public int createTable_CAPC_DB() throws RecordDAO_Exception
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.create_CAPC_DB_SQL);

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    } 
       
       
    @Override
    public int dropTable_CAPC_DB() throws RecordDAO_Exception
    {
            Statement state = null;
            
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            try 
            {
                    state = this.conn.createStatement();
                    state.executeUpdate(DB_Constants.drop_CAPC_DB_SQL);
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
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_FROM_CAPC_DB_ON_CID_SQL);

                    int index = 1;

                    ps.setString(index++, r.get_COLUMN_CLASS_ID());
                    
                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (CapabilitiesClassesTable.CAPC_DB_TABLE_NAME.equals(CapabilitiesClassesTable.CAPC_DB_TABLE_NAME))
                            {
                                    CapabilitiesClassesTableRecord rec = new CapabilitiesClassesTableRecord();
                                    
                                    rec.set_COLUMN_CLASS_ID(rs.getString(CapabilitiesClassesTable.COLUMN_CLASS_ID));
                                    
                                    rec.set_COLUMN_CLASS_NAME(rs.getString(CapabilitiesClassesTable.COLUMN_CLASS_NAME));
                                    
                                    rec.set_COLUMN_CAPABILITIES(rs.getString(CapabilitiesClassesTable.COLUMN_CAPABILITIES));
                                    
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
    public CapabilitiesClassesTableRecord[] read_Capabilities_Classes_Table_Records_On_All_Classes() throws RecordDAO_Exception       
    {
            if (this.conn == null) return null;

            PreparedStatement ps = null;
            ResultSet rs = null;

            ArrayList <CapabilitiesClassesTableRecord> rows = new ArrayList<CapabilitiesClassesTableRecord>();

            try 
            {
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_FROM_CAPC_DB_SQL);
                    
                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    while (rs.next()) 
                    {
                            if (CapabilitiesClassesTable.CAPC_DB_TABLE_NAME.equals(CapabilitiesClassesTable.CAPC_DB_TABLE_NAME))
                            {
                                    CapabilitiesClassesTableRecord rec = new CapabilitiesClassesTableRecord();
                                    
                                    rec.set_COLUMN_CLASS_ID(rs.getString(CapabilitiesClassesTable.COLUMN_CLASS_ID));
                                    
                                    rec.set_COLUMN_CLASS_NAME(rs.getString(CapabilitiesClassesTable.COLUMN_CLASS_NAME));
                                    
                                    rec.set_COLUMN_CAPABILITIES(rs.getString(CapabilitiesClassesTable.COLUMN_CAPABILITIES));
                                    
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
                    ps = this.conn.prepareStatement(DB_Constants.SELECT_FROM_CAPC_DB_COUNT_CAPABILITIES_ON_CID_SQL);

                    int index = 1;

                    //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

                    this.conn.setAutoCommit(false);
                    rs = ps.executeQuery();
                    this.conn.setAutoCommit(true);

                    //rs = state.executeQuery(statement);

                    while (rs.next()) 
                    {
                            if (CapabilitiesClassesTable.CAPC_DB_TABLE_NAME.equals(CapabilitiesClassesTable.CAPC_DB_TABLE_NAME))
                            {
                                    count = rs.getInt(DB_Constants.COUNT);
                            } else return INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    rs.close();

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return count;
    }
    
    private int check_If_CapabilitiesClassesTableCID_Exists(CapabilitiesClassesTableRecord r)
    throws RecordDAO_Exception //on CID
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try 
        {	
                ps = this.conn.prepareStatement(DB_Constants.SELECT_CID_FROM_CAPC_DB_ON_CID_SQL);

                int index = 1;

                ps.setString(index++, r.get_COLUMN_CLASS_ID());

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
                    if (CapabilitiesClassesTable.CAPC_DB_TABLE_NAME.equals(CapabilitiesClassesTable.CAPC_DB_TABLE_NAME))
                    {
                            ps = this.conn.prepareStatement(DB_Constants.INSERT_INTO_CAPC_DB_SQL);

                            int index = 1;

                            ps.setString(index++, r.get_COLUMN_CLASS_ID());
                            ps.setString(index++, r.get_COLUMN_CLASS_NAME());
                            ps.setString(index++, r.get_COLUMN_CAPABILITIES());
                            ps.setString(index++, r.get_COLUMN_STATUS());

                            ps.addBatch();
                            this.conn.setAutoCommit(false);
                            ps.executeBatch();
                            this.conn.setAutoCommit(true);

                    } else return INDICATE_CONDITIONAL_EXIT_STATUS;

            } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                    return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Capabilities_Classes_Table_Record_Column_Name_on_CID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
            if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
            if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

            PreparedStatement ps = null;

            try 
            {
                    if (CapabilitiesClassesTable.CAPC_DB_TABLE_NAME.equals(CapabilitiesClassesTable.CAPC_DB_TABLE_NAME))
                    {	
                            ps = this.conn.prepareStatement(DB_Constants.UPDATE_CAPC_DB_COLUMN_NAME_ON_CID_SQL);

                            int index = 1;
                            
                            ps.setString(index++, r.get_COLUMN_CLASS_NAME());
                            
                            ps.setString(index++, r.get_COLUMN_CLASS_ID());
                            
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
    
    private int update_Capabilities_Classes_Table_Record_Column_Capabilities_on_CID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {
                if (CapabilitiesClassesTable.CAPC_DB_TABLE_NAME.equals(CapabilitiesClassesTable.CAPC_DB_TABLE_NAME))
                {	
                        ps = this.conn.prepareStatement(DB_Constants.UPDATE_CAPC_DB_COLUMN_CAPS_ON_CID_SQL);

                        int index = 1;

                        ps.setString(index++, r.get_COLUMN_CAPABILITIES());

                        ps.setString(index++, r.get_COLUMN_CLASS_ID());


                        this.conn.setAutoCommit(false);
                        ps.executeUpdate();
                        this.conn.setAutoCommit(true);
                } else return INDICATE_CONDITIONAL_EXIT_STATUS;

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    /* no duplicates are allowed for this table - CID can only have a single record */
    @Override
    public int write_CapabilitiesClassesTableRecord(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        try
        {	
            if (this.check_If_CapabilitiesClassesTableCID_Exists(r) == EMPTY_RESULT) //no record exists
            {	
                    if (this.insert_Capabilities_Classes_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

            } else if (this.check_If_CapabilitiesClassesTableCID_Exists(r) == RECORD_EXISTS) //record exists
            {//if record exists - just update it	

                    if (r.get_UPDATE_COLUMN().equals(CapabilitiesClassesTable.COLUMN_CLASS_NAME)) /* check if the update column
                        is a name column */
                        if (this.update_Capabilities_Classes_Table_Record_Column_Name_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                    if (r.get_UPDATE_COLUMN().equals(CapabilitiesClassesTable.COLUMN_CAPABILITIES)) /* check if the update column
                        is policies column */
                        if (this.update_Capabilities_Classes_Table_Record_Column_Capabilities_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
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
                ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_CAPC_DB_ON_CID_SQL);

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

    
    /* communicative classes table operations */
    
    @Override
    public int createTable_COMMC_DB() throws RecordDAO_Exception
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
    public int dropTable_COMMC_DB() throws RecordDAO_Exception
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
                count = rs.getInt(DB_Constants.COUNT);       
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

                /* technically all the set operations should be checked on the
                success return value. However in this case this might not be necessary
                because fields are set using records that are read from the store */
                
                rec.set_COLUMN_CLASS_ID(rs.getString(CommunicativeClassesTable.COLUMN_CLASS_ID));

                rec.set_COLUMN_CLASS_NAME(rs.getString(CommunicativeClassesTable.COLUMN_CLASS_NAME));

                rec.set_COLUMN_COLLABORATION_RECORD(rs.getString(CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD));

                rec.set_COLUMN_COORDINATION_RECORD(rs.getString(CommunicativeClassesTable.COLUMN_COORDINATION_RECORD));

                rec.set_COLUMN_STATUS(rs.getString(CommunicativeClassesTable.COLUMN_STATUS));

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

    
    @Override
    public int delete_Communicative_Classes_Table_Record_On_CollaborationRecord_And_CID(CommunicativeClassesTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        String statement = null;
        PreparedStatement ps = null;

        try 
        {	
            ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_COMMC_DB_ON_COLLABORATION_RECORD_AND_CID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_COLLABORATION_RECORD());

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
    
    @Override
    public int delete_Communicative_Classes_Table_Record_On_CoordinationRecord_And_CID(CommunicativeClassesTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        String statement = null;
        PreparedStatement ps = null;

        try 
        {	
            ps = this.conn.prepareStatement(DB_Constants.DELETE_FROM_COMMC_DB_ON_COORDINATION_RECORD_AND_CID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_COORDINATION_RECORD());

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
    
    
    private int insert_Communicative_Classes_Table_Record(CommunicativeClassesTableRecord r)
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.INSERT_INTO_COMMC_DB_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_CLASS_ID());
            ps.setString(index++, r.get_COLUMN_CLASS_NAME());
            ps.setString(index++, r.get_COLUMN_COLLABORATION_RECORD());
            ps.setString(index++, r.get_COLUMN_COORDINATION_RECORD());
            ps.setString(index++, r.get_COLUMN_STATUS());

            ps.addBatch();
            this.conn.setAutoCommit(false);
            ps.executeBatch();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    /* obsolete now */
    @Override
    public int write_Communicative_Classes_Table_Record(CommunicativeClassesTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        try
        {	
            if (this.check_If_Communicative_Classes_Table_CID_Exists(r) == EMPTY_RESULT) //no record exists
            {	
                if (this.insert_Communicative_Classes_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

            } else if (this.check_If_Communicative_Classes_Table_CID_Exists(r) == RECORD_EXISTS) //record exists
            {//if record exists - just update it	

                if (r.get_UPDATE_COLUMN().equals(CommunicativeClassesTable.COLUMN_CLASS_NAME)) /* check if the update column
                    is a name column */
                    if (this.update_Communicative_Classes_Table_Record_Column_Name_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                if (r.get_UPDATE_COLUMN().equals(CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD)) /* check if the update column
                    is collaboration policy column */
                    if (this.update_Communicative_Classes_Table_Record_Column_Collaboration_Record_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

                if (r.get_UPDATE_COLUMN().equals(CommunicativeClassesTable.COLUMN_COORDINATION_RECORD)) /* check if the update column
                    is coordination policy column */
                    if (this.update_Communicative_Classes_Table_Record_Column_Coordination_Record_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

        } catch (Exception e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                return INDICATE_EXECUTION_SUCCESS;
    }
    
    /* new version taking into account the fact that duplicates are allowed on CID
    but with distinct coord/collab records */
    @Override
    public int write_CommunicativeClassesTableRecord(CommunicativeClassesTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        try
        {	
            if (this.check_If_CommunicativeClassesTableCID_Exists(r) == EMPTY_RESULT) //no record exists
            {	
                if (this.insert_Communicative_Classes_Table_Record(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;

            } else if (this.check_If_CommunicativeClassesTableCID_Exists(r) == RECORD_EXISTS) //record exists
            {/* no updates are allowed - only inserts and deletes based on coord/collab records */
               
                return RECORD_EXISTS;
//if record exists - just update it	

//                if (r.get_UPDATE_COLUMN().equals(CommunicativeClassesTable.COLUMN_CLASS_NAME)) /* check if the update column
//                    is a name column */
//                    if (this.update_Communicative_Classes_Table_Record_Column_Name_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
//
//                if (r.get_UPDATE_COLUMN().equals(CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD)) /* check if the update column
//                    is collaboration policy column */
//                    if (this.update_Communicative_Classes_Table_Record_Column_Collaboration_Record_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
//
//                if (r.get_UPDATE_COLUMN().equals(CommunicativeClassesTable.COLUMN_COORDINATION_RECORD)) /* check if the update column
//                    is coordination policy column */
//                    if (this.update_Communicative_Classes_Table_Record_Column_Coordination_Record_on_CID(r) != INDICATE_EXECUTION_SUCCESS) return INDICATE_CONDITIONAL_EXIT_STATUS;
            }

        } catch (Exception e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }
                return INDICATE_EXECUTION_SUCCESS;
    }
    
    /* less strict one */
    private int check_If_Communicative_Classes_Table_CID_Exists(CommunicativeClassesTableRecord r)
    throws RecordDAO_Exception //on CID
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try 
        {	
            ps = this.conn.prepareStatement(DB_Constants.SELECT_CID_FROM_COMMC_DB_ON_CID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_CLASS_ID());

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
    
    /* new version taking into account the fact that duplicates are allowed on CID
    but with distinct coord/collab records */
    private int check_If_CommunicativeClassesTableCID_Exists(CommunicativeClassesTableRecord r)
    throws RecordDAO_Exception //on CID
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate error
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try 
        {	
            ps = this.conn.prepareStatement(DB_Constants.SELECT_CID_FROM_COMMC_DB_ON_ALL_MAIN_COLUMNS_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_CLASS_ID());
            
            //ps.setString(index++, r.get_COLUMN_CLASS_NAME());

            ps.setString(index++, r.get_COLUMN_COLLABORATION_RECORD());
            
            ps.setString(index++, r.get_COLUMN_COORDINATION_RECORD());
            
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
    
    private int update_Communicative_Classes_Table_Record_Column_Name_on_CID(CommunicativeClassesTableRecord r) 
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {             	
            ps = this.conn.prepareStatement(DB_Constants.UPDATE_COMMC_DB_COLUMN_NAME_ON_CID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_CLASS_NAME());

            ps.setString(index++, r.get_COLUMN_CLASS_ID());

            /*
            ps.setString(index++, r.getCOLUMN_CAPS());
            ps.setString(index++, r.getCOLUMN_STATUS());
            */

            this.conn.setAutoCommit(false);
            ps.executeUpdate();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    
    private int update_Communicative_Classes_Table_Record_Column_Collaboration_Record_on_CID(CommunicativeClassesTableRecord r)
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.UPDATE_COMMC_DB_COLUMN_COLLABORATION_RECORD_ON_CID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COLLABORATION_RECORD());

            ps.setString(index++, r.get_COLUMN_CLASS_ID());

            this.conn.setAutoCommit(false);
            ps.executeUpdate();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    private int update_Communicative_Classes_Table_Record_Column_Coordination_Record_on_CID(CommunicativeClassesTableRecord r)
    throws RecordDAO_Exception
    {
        if (r == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
        if (this.conn == null) return INDICATE_CONDITIONAL_EXIT_STATUS;

        PreparedStatement ps = null;

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.UPDATE_COMMC_DB_COLUMN_COORDINATION_RECORD_ON_CID_SQL);

            int index = 1;

            ps.setString(index++, r.get_COLUMN_COORDINATION_RECORD());

            ps.setString(index++, r.get_COLUMN_CLASS_ID());

            this.conn.setAutoCommit(false);
            ps.executeUpdate();
            this.conn.setAutoCommit(true);

        } catch(SQLException e) { throw new RecordDAO_Exception( "Exception: " + e.getMessage(), e ); }

                return INDICATE_EXECUTION_SUCCESS;
    }
    
    @Override
    public CommunicativeClassesTableRecord[] read_Communicative_Classes_Table_Records_On_All_Classes() 
    throws RecordDAO_Exception       
    {
        if (this.conn == null) return null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList <CommunicativeClassesTableRecord> rows = new ArrayList<CommunicativeClassesTableRecord>();

        try 
        {
            ps = this.conn.prepareStatement(DB_Constants.SELECT_ALL_FROM_COMMC_DB_SQL);

            int index = 1;

            //ps.setString(index++, r.getCOLUMN_POLICY_CLASS_ID());

            this.conn.setAutoCommit(false);
            rs = ps.executeQuery();
            this.conn.setAutoCommit(true);

            while (rs.next()) 
            {             
                CommunicativeClassesTableRecord rec = new CommunicativeClassesTableRecord();
                
                /* technically all the set operations should be checked on the
                success return value. However in this case this might not be necessary
                because fields are set using records that are read from the store */

                rec.set_COLUMN_CLASS_ID(rs.getString(CommunicativeClassesTable.COLUMN_CLASS_ID));

                rec.set_COLUMN_CLASS_NAME(rs.getString(CommunicativeClassesTable.COLUMN_CLASS_NAME));

                rec.set_COLUMN_COLLABORATION_RECORD(rs.getString(CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD));

                rec.set_COLUMN_COORDINATION_RECORD(rs.getString(CommunicativeClassesTable.COLUMN_COORDINATION_RECORD));

                rec.set_COLUMN_STATUS(rs.getString(CommunicativeClassesTable.COLUMN_STATUS));

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
    
    
}
