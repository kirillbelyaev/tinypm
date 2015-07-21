/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/


/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.tinypm.DB.interfaces;

/**
 *
 * @author I829920
 */
public interface DB_Constants_Extended 
{
	final String initialNameTag = "initialNameTag";
	final String SSH_KEYGEN_CMD = "/usr/bin/ssh-keygen -t rsa -N '' -q -f  ";
	final String BASEDIR = "/var/tmp/";
	
	final String SQLITE_DRV = "org.sqlite.JDBC";
	final String DB_URI = "jdbc:sqlite:" + Apps_Table.APPS_DB_NAME;
	
	final String charSet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        
        final String COUNT = "COUNT";
        
        final String ALL = " * ";
        
        /* new API methods to support the policy class abstraction */
        
        /* apps table operations */
        
        final String APPS_DB_SCHEMA  = " (" + Apps_Table.COLUMN_APP_DESC + "," + Apps_Table.COLUMN_APP_PATH + "," + Apps_Table.COLUMN_POLICY_CLASS_ID + "," + Apps_Table.COLUMN_APP_CONTAINER_ID + "," + Apps_Table.COLUMN_STATUS + ") ";
        
        final String create_APPS_DB_SQL = "create table " + Apps_Table.APPS_DB_TABLE_NAME + APPS_DB_SCHEMA;
        
        final String drop_APPS_DB_SQL = "drop table if exists " + Apps_Table.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_ON_APP_SQL = "select * from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_APP_PATH + " = ?";
        
        final String SELECT_FROM_APPS_DB_ALL_APPS_SQL = "select distinct " + Apps_Table.COLUMN_APP_PATH + " from " + Apps_Table.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_COUNT_APPS_ON_PCID_SQL = "select count(*) as " + COUNT + " from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "select " + ALL + " from " + Apps_Table.APPS_DB_TABLE_NAME  + " where " + Apps_Table.COLUMN_APP_PATH + " = ? and " + Apps_Table.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_PCID_SQL = "select " + ALL + " from " + Apps_Table.APPS_DB_TABLE_NAME  + " where " + Apps_Table.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_AND_PCID_SET_PCID_SQL = "update " + Apps_Table.APPS_DB_TABLE_NAME + " set " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ? "  + "where " + Apps_Table.COLUMN_APP_PATH + " = ? and " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_SET_PCID_SQL = "update " + Apps_Table.APPS_DB_TABLE_NAME + " set " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ? "  + "where " + Apps_Table.COLUMN_APP_PATH + " = ?";
        
        final String INSERT_INTO_APPS_DB_SQL = "insert into " + Apps_Table.APPS_DB_TABLE_NAME + " values (?, ?, ?, ?, ?) ";
        
        final String DELETE_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "delete from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_APP_PATH + " = ? and " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ?";

        
        /* policy classes table operations */
        
        //Policy_Classes_Table_Record pctr = new Policy_Classes_Table_Record();
        
        //final String PCS_DB_SCHEMA = pctr.produce_PCS_DB_DDL();
        
        final String PCS_DB_SCHEMA = "( " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + ", " + Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME + ", " + Policy_Classes_Table.COLUMN_POLICY_CLASS_POLICIES + ", " + Policy_Classes_Table.COLUMN_STATUS + ") ";
        
        final String create_PCS_DB_SQL = "create table " + Policy_Classes_Table.PCS_DB_TABLE_NAME + PCS_DB_SCHEMA;
        
        final String drop_PCS_DB_SQL = "drop table if exists " + Policy_Classes_Table.PCS_DB_TABLE_NAME;
        
        final String SELECT_ALL_FROM_PCS_DB_ON_PCID_SQL = "select " + ALL + " from " + Policy_Classes_Table.PCS_DB_TABLE_NAME  + " where " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String SELECT_ALL_FROM_PCS_DB_SQL = "select distinct " + ALL + " from " + Policy_Classes_Table.PCS_DB_TABLE_NAME;
        
        final String SELECT_FROM_PCS_DB_COUNT_POLICY_CLASSES_ON_PCID_SQL = "select distinct count(*) as " + COUNT + " from " + Policy_Classes_Table.PCS_DB_TABLE_NAME;
        
        final String SELECT_FROM_PCS_DB_ON_PCID_SQL = "select " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + " from " + Policy_Classes_Table.PCS_DB_TABLE_NAME  + " where " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String INSERT_INTO_PCS_DB_SQL = "insert into " + Policy_Classes_Table.PCS_DB_TABLE_NAME + " values (?, ?, ?, ?) ";
        
        final String UPDATE_PCS_DB_ON_PCID_AND_CAPS_SQL = "update " + Policy_Classes_Table.PCS_DB_TABLE_NAME + " set " + Policy_Classes_Table.COLUMN_POLICY_CLASS_POLICIES + " = ? "  + "where " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String UPDATE_PCS_DB_ON_PCID_SQL = "update " + Policy_Classes_Table.PCS_DB_TABLE_NAME + " set " + Policy_Classes_Table.COLUMN_POLICY_CLASS_NAME + " = ? "  + "where " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + " = ?";
        
        
        final String DELETE_FROM_PCS_DB_ON_PCID_SQL = "delete from " + Policy_Classes_Table.PCS_DB_TABLE_NAME + " where " + Policy_Classes_Table.COLUMN_POLICY_CLASS_ID + " = ?";

        
        
        
        
        
        
        
        
        
}
