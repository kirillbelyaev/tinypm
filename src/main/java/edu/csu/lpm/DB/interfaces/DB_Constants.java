/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/


/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.lpm.DB.interfaces;

/**
 *
 * @author I829920
 */
public interface DB_Constants 
{
	final String initialNameTag = "initialNameTag";
	final String SSH_KEYGEN_CMD = "/usr/bin/ssh-keygen -t rsa -N '' -q -f  ";
	final String BASEDIR = "/var/tmp/";
	
	final String SQLITE_DRV = "org.sqlite.JDBC";
	final String DB_URI = "jdbc:sqlite:lpm.db";
        final String DB_NAME = "lpm.db";
	
	final String charSet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        
        final String COUNT = "COUNT";
        
        final String ALL = " * ";
        
        /* new API methods to support the policy class abstraction */
        
        /* apps table operations */
        
        final String APPS_DB_SCHEMA  = " (" + ComponentsTable.COLUMN_COMPONENT_DESC + "," + ComponentsTable.COLUMN_COMPONENT_PATH_ID + "," + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + "," + ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID + "," + ComponentsTable.COLUMN_STATUS + ") ";
        
        final String create_APPS_DB_SQL = "create table " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + APPS_DB_SCHEMA;
        
        final String drop_APPS_DB_SQL = "drop table if exists " + ComponentsTable.COMPONENTS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_ON_APP_SQL = "select * from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ?";
        
        final String SELECT_FROM_APPS_DB_ALL_APPS_SQL = "select distinct " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_COUNT_APPS_ON_PCID_SQL = "select count(*) as " + COUNT + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "select " + ALL + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME  + " where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID  + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_PCID_SQL = "select " + ALL + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME  + " where " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID  + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_AND_PCID_SET_PCID_SQL = "update " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " set " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ? "  + "where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_SET_PCID_SQL = "update " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " set " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ? "  + "where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ?";
        
        final String INSERT_INTO_APPS_DB_SQL = "insert into " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " values (?, ?, ?, ?, ?) ";
        
        final String DELETE_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "delete from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?";

        
        /* policy classes table operations */
        
        //Policy_Classes_Table_Record pctr = new Policy_Classes_Table_Record();
        
        //final String PCS_DB_SCHEMA = pctr.produce_PCS_DB_DDL();
        
        final String PCS_DB_SCHEMA = "( " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID + ", " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_NAME + ", " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_POLICIES + ", " + CapabilitiesClassesTable.COLUMN_STATUS + ") ";
        
        final String create_PCS_DB_SQL = "create table " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME + PCS_DB_SCHEMA;
        
        final String drop_PCS_DB_SQL = "drop table if exists " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME;
        
        final String SELECT_ALL_FROM_PCS_DB_ON_PCID_SQL = "select " + ALL + " from " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME  + " where " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String SELECT_ALL_FROM_CAPC_DB_SQL = "select distinct " + ALL + " from " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME;
        
        final String SELECT_FROM_PCS_DB_COUNT_POLICY_CLASSES_ON_PCID_SQL = "select distinct count(*) as " + COUNT + " from " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME;
        
        final String SELECT_FROM_PCS_DB_ON_PCID_SQL = "select " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID + " from " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME  + " where " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String INSERT_INTO_CAPC_DB_SQL = "insert into " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME + " values (?, ?, ?, ?) ";
        
        final String UPDATE_CAPC_DB_COLUMN_CAPS_ON_CID_SQL = "update " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME + " set " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_POLICIES + " = ? "  + "where " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String UPDATE_CAPC_DB_COLUMN_NAME_ON_CID_SQL = "update " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME + " set " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_NAME + " = ? "  + "where " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";
                
        final String DELETE_FROM_CAPC_DB_ON_CID_SQL = "delete from " + CapabilitiesClassesTable.PCS_DB_TABLE_NAME + " where " + CapabilitiesClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";

        
        /* communicative class operations */
        
        final String COMMC_DB_SCHEMA = "( " + CommunicativeClassesTable.COLUMN_CLASS_ID + ", " + CommunicativeClassesTable.COLUMN_CLASS_NAME + ", " + CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD + ", " + CommunicativeClassesTable.COLUMN_COORDINATION_RECORD + ", " + CommunicativeClassesTable.COLUMN_STATUS + ") ";
        
        final String create_COMMC_DB_SQL = "create table " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + COMMC_DB_SCHEMA;
        
        final String drop_COMMC_DB_SQL = "drop table if exists " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME;
        
        final String SELECT_ALL_FROM_COMMC_DB_ON_CID_SQL = "select " + ALL + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME  + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID  + " = ?";
        
        final String SELECT_ALL_FROM_COMMC_DB_SQL = "select distinct " + ALL + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME;
        
        final String SELECT_FROM_COMMC_DB_COUNT_CLASSES_ON_CID_SQL = "select distinct count(*) as " + COUNT + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME;
        
        final String SELECT_FROM_COMMC_DB_ON_CID_SQL = "select " + CommunicativeClassesTable.COLUMN_CLASS_ID + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME  + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
               
        final String INSERT_INTO_COMMC_DB_SQL = "insert into " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " values (?, ?, ?, ?, ?) ";
        
        final String DELETE_FROM_COMMC_DB_ON_CID_SQL = "delete from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";

        final String UPDATE_COMMC_DB_COLUMN_NAME_ON_CID_SQL = "update " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " set " + CommunicativeClassesTable.COLUMN_CLASS_NAME + " = ? "  + "where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String UPDATE_COMMC_DB_COLUMN_COLLABORATION_RECORD_ON_CID_SQL = "update " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " set " + CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD + " = ? "  + "where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String UPDATE_COMMC_DB_COLUMN_COORDINATION_RECORD_ON_CID_SQL = "update " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " set " + CommunicativeClassesTable.COLUMN_COORDINATION_RECORD + " = ? "  + "where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        
}
