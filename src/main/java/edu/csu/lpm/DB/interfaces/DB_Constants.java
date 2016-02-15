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
	final String DB_URI = "jdbc:sqlite:tinypm.db";
        final String DB_NAME = "tinypm.db";
	
	final String charSet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        
        final String COUNT = "COUNT";
        
        final String ALL = " * ";
        
        /* new API methods to support the policy class abstraction */
        
        /* apps table operations */
        
        final String APPS_DB_SCHEMA  = " (" + AppsTable.COLUMN_APP_DESC + "," + AppsTable.COLUMN_APP_PATH + "," + AppsTable.COLUMN_POLICY_CLASS_ID + "," + AppsTable.COLUMN_APP_CONTAINER_ID + "," + AppsTable.COLUMN_STATUS + ") ";
        
        final String create_APPS_DB_SQL = "create table " + AppsTable.APPS_DB_TABLE_NAME + APPS_DB_SCHEMA;
        
        final String drop_APPS_DB_SQL = "drop table if exists " + AppsTable.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_ON_APP_SQL = "select * from " + AppsTable.APPS_DB_TABLE_NAME + " where " + AppsTable.COLUMN_APP_PATH + " = ?";
        
        final String SELECT_FROM_APPS_DB_ALL_APPS_SQL = "select distinct " + AppsTable.COLUMN_APP_PATH + " from " + AppsTable.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_COUNT_APPS_ON_PCID_SQL = "select count(*) as " + COUNT + " from " + AppsTable.APPS_DB_TABLE_NAME + " where " + AppsTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "select " + ALL + " from " + AppsTable.APPS_DB_TABLE_NAME  + " where " + AppsTable.COLUMN_APP_PATH + " = ? and " + AppsTable.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_PCID_SQL = "select " + ALL + " from " + AppsTable.APPS_DB_TABLE_NAME  + " where " + AppsTable.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_AND_PCID_SET_PCID_SQL = "update " + AppsTable.APPS_DB_TABLE_NAME + " set " + AppsTable.COLUMN_POLICY_CLASS_ID + " = ? "  + "where " + AppsTable.COLUMN_APP_PATH + " = ? and " + AppsTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_SET_PCID_SQL = "update " + AppsTable.APPS_DB_TABLE_NAME + " set " + AppsTable.COLUMN_POLICY_CLASS_ID + " = ? "  + "where " + AppsTable.COLUMN_APP_PATH + " = ?";
        
        final String INSERT_INTO_APPS_DB_SQL = "insert into " + AppsTable.APPS_DB_TABLE_NAME + " values (?, ?, ?, ?, ?) ";
        
        final String DELETE_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "delete from " + AppsTable.APPS_DB_TABLE_NAME + " where " + AppsTable.COLUMN_APP_PATH + " = ? and " + AppsTable.COLUMN_POLICY_CLASS_ID + " = ?";

        
        /* policy classes table operations */
        
        //Policy_Classes_Table_Record pctr = new Policy_Classes_Table_Record();
        
        //final String PCS_DB_SCHEMA = pctr.produce_PCS_DB_DDL();
        
        final String PCS_DB_SCHEMA = "( " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + ", " + PolicyClassesTable.COLUMN_POLICY_CLASS_NAME + ", " + PolicyClassesTable.COLUMN_POLICY_CLASS_POLICIES + ", " + PolicyClassesTable.COLUMN_STATUS + ") ";
        
        final String create_PCS_DB_SQL = "create table " + PolicyClassesTable.PCS_DB_TABLE_NAME + PCS_DB_SCHEMA;
        
        final String drop_PCS_DB_SQL = "drop table if exists " + PolicyClassesTable.PCS_DB_TABLE_NAME;
        
        final String SELECT_ALL_FROM_PCS_DB_ON_PCID_SQL = "select " + ALL + " from " + PolicyClassesTable.PCS_DB_TABLE_NAME  + " where " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String SELECT_ALL_FROM_PCS_DB_SQL = "select distinct " + ALL + " from " + PolicyClassesTable.PCS_DB_TABLE_NAME;
        
        final String SELECT_FROM_PCS_DB_COUNT_POLICY_CLASSES_ON_PCID_SQL = "select distinct count(*) as " + COUNT + " from " + PolicyClassesTable.PCS_DB_TABLE_NAME;
        
        final String SELECT_FROM_PCS_DB_ON_PCID_SQL = "select " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + " from " + PolicyClassesTable.PCS_DB_TABLE_NAME  + " where " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String INSERT_INTO_PCS_DB_SQL = "insert into " + PolicyClassesTable.PCS_DB_TABLE_NAME + " values (?, ?, ?, ?) ";
        
        final String UPDATE_PCS_DB_ON_PCID_AND_CAPS_SQL = "update " + PolicyClassesTable.PCS_DB_TABLE_NAME + " set " + PolicyClassesTable.COLUMN_POLICY_CLASS_POLICIES + " = ? "  + "where " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String UPDATE_PCS_DB_ON_PCID_SQL = "update " + PolicyClassesTable.PCS_DB_TABLE_NAME + " set " + PolicyClassesTable.COLUMN_POLICY_CLASS_NAME + " = ? "  + "where " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";
        
        
        final String DELETE_FROM_PCS_DB_ON_PCID_SQL = "delete from " + PolicyClassesTable.PCS_DB_TABLE_NAME + " where " + PolicyClassesTable.COLUMN_POLICY_CLASS_ID + " = ?";

        
        
        
        
        
        
        
        
        
}
