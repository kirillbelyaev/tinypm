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
	final String DB_URI = "jdbc:sqlite:lc.db";
	final String LC_DB_TABLE_NAME = "lc_db";
        final String LC_DB_NAME = "lc.db";
	
	final String charSet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        
        //schema fields
        final String COLUMN_UID = "uid";
        final String COLUMN_APP_PATH = "app_path";
        final String COLUMN_CAP_ATTR = "cap_attr";
        final String COLUMN_STATUS = "status";
        
        
        final int EMPTY_RESULT = -8;
        
        final String COUNT = "COUNT";
        final String LC_DB_SCHEMA  = " (" + COLUMN_UID + "," + COLUMN_APP_PATH + "," + COLUMN_CAP_ATTR + "," + COLUMN_STATUS + ") ";
        final String create_LC_DB_SQL = "create table " + LC_DB_TABLE_NAME + LC_DB_SCHEMA;
        final String drop_LC_DB_SQL = "drop table if exists " + LC_DB_TABLE_NAME;
        final String SELECT_COUNT_SQL = "select count(*) as " + COUNT + " from " + LC_DB_TABLE_NAME;
        final String SELECT_COUNT_APP_CAP_SQL = "select count(*) as " + COUNT + " from " + LC_DB_TABLE_NAME + " where " + COLUMN_APP_PATH + " = ?";
        final String SELECT_ON_APP_SQL = "select * from " + LC_DB_TABLE_NAME + " where " + COLUMN_APP_PATH + " = ?";
        final String SELECT_ON_APP_AND_CAP_SQL = "select " + COLUMN_APP_PATH + " from " + LC_DB_TABLE_NAME + " where " + COLUMN_APP_PATH + " = ? and " + COLUMN_CAP_ATTR + " = ?";
        final String SELECT_ALL_APPS_SQL = "select distinct " + COLUMN_APP_PATH + " from " + LC_DB_TABLE_NAME;
        final String INSERT_LC_DB_SQL = "insert into " + LC_DB_TABLE_NAME + " values (?, ?, ?, ?) ";
        
        //final String UPDATE_LC_DB_SQL = "update " + LC_DB_TABLE_NAME + " set " + COLUMN_UID + " = ?, " + COLUMN_APP_PATH + " = ?, " + COLUMN_CAP_ATTR + " = ?, " + COLUMN_STATUS + " = ? " + "where " +  COLUMN_APP_PATH + " = ? and " + COLUMN_CAP_ATTR + " = ?";
        final String UPDATE_LC_DB_SQL = "update " + LC_DB_TABLE_NAME + " set " + COLUMN_CAP_ATTR + " = ? "  + "where " + COLUMN_APP_PATH + " = ? and " + COLUMN_CAP_ATTR + " = ?";
        
        
        final String DELETE_ON_APP_AND_CAP_SQL = "delete from " + LC_DB_TABLE_NAME + " where " + COLUMN_APP_PATH + " = ? and " + COLUMN_CAP_ATTR + " = ?";

        
        /* new API methods to support the policy class abstraction */
        
        final String APPS_DB_SCHEMA  = " (" + Apps_Table.COLUMN_APP_DESC + "," + Apps_Table.COLUMN_APP_PATH + "," + Apps_Table.COLUMN_POLICY_CLASS_ID + "," + Apps_Table.COLUMN_APP_CONTAINER_ID + "," + Apps_Table.COLUMN_STATUS + ") ";
        final String create_APPS_DB_SQL = "create table " + Apps_Table.APPS_DB_TABLE_NAME + APPS_DB_SCHEMA;
        final String drop_APPS_DB_SQL = "drop table if exists " + Apps_Table.APPS_DB_TABLE_NAME;
        
        final String SELECT_FROM_APPS_DB_ON_APP_SQL = "select * from " + Apps_Table.APPS_DB_TABLE_NAME + " where " + Apps_Table.COLUMN_APP_PATH + " = ?";
        
        final String SELECT_FROM_APPS_DB_ALL_APPS_SQL = "select distinct " + Apps_Table.COLUMN_APP_PATH + " from " + Apps_Table.APPS_DB_TABLE_NAME;
        
}
