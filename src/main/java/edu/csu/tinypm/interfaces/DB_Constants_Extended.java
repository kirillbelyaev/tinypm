/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/


/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.tinypm.interfaces;

import edu.csu.tinypm.DB.interfaces.Apps_Table;

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
        
        final int EMPTY_RESULT = -8;
        
        final String COUNT = "COUNT";
        
        /* new API methods to support the policy class abstraction */
        
        final String APPS_DB_SCHEMA  = " (" + Apps_Table.COLUMN_APP_DESC + "," + Apps_Table.COLUMN_APP_PATH + "," + Apps_Table.COLUMN_POLICY_CLASS_ID + "," + Apps_Table.COLUMN_APP_CONTAINER_ID + "," + Apps_Table.COLUMN_STATUS + ") ";
        final String create_APPS_DB_SQL = "create table " + Apps_Table.APPS_DB_TABLE_NAME + APPS_DB_SCHEMA;
        final String drop_APPS_DB_SQL = "drop table if exists " + Apps_Table.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_ON_APP_SQL = "select * from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_APP_PATH + " = ?";
        
        final String SELECT_FROM_APPS_DB_ALL_APPS_SQL = "select distinct " + Apps_Table.COLUMN_APP_PATH + " from " + Apps_Table.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_COUNT_APPS_ON_PCID_SQL = "select count(*) as " + COUNT + " from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String SELECT_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "select " + Apps_Table.COLUMN_APP_PATH + " from " + Apps_Table.APPS_DB_TABLE_NAME  + " where " + Apps_Table.COLUMN_APP_PATH + " = ? and " + Apps_Table.COLUMN_POLICY_CLASS_ID  + " = ?";
        
        final String UPDATE_APPS_DB_ON_APP_AND_PCID_SQL = "update " + Apps_Table.APPS_DB_TABLE_NAME + " set " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ? "  + "where " + Apps_Table.COLUMN_APP_PATH + " = ? and " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ?";
        
        final String INSERT_APPS_DB_SQL = "insert into " + Apps_Table.APPS_DB_TABLE_NAME + " values (?, ?, ?, ?, ?) ";
        
        final String DELETE_FROM_APPS_DB_ON_APP_AND_PCID_SQL = "delete from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_APP_PATH + " = ? and " + Apps_Table.COLUMN_POLICY_CLASS_ID + " = ?";

        
}
