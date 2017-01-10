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


/**
 *
 * @author I829920 Kirill Belyaev @2013 SAP Labs LLC, Palo Alto, CA
 */

package edu.csu.lpm.DB.interfaces;

import edu.csu.lpm.DB.DAO.UserAuthDAO;

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
        
        /* COMPONENTS table operations */
        
        final String COMPONENTS_DB_SCHEMA  = " (" + ComponentsTable.COLUMN_COMPONENT_DESC 
        + "," + ComponentsTable.COLUMN_COMPONENT_PATH_ID 
        + "," + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID 
        + "," + ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID 
        + "," + ComponentsTable.COLUMN_COMPONENT_CONTAINER_ID 
        + "," + ComponentsTable.COLUMN_COMPONENT_ID 
        + "," + ComponentsTable.COLUMN_COMPONENT_TUPLE_SPACE_PATH 
        + "," + ComponentsTable.COLUMN_STATUS + ") ";
        
        final String create_COMPONENTS_DB_SQL = "create table " 
        + ComponentsTable.COMPONENTS_DB_TABLE_NAME + COMPONENTS_DB_SCHEMA;        
        
        
        final String create_USER_AUTH_DB_SQL = "create table if not exists " 
        + UserAuthDAO.USER_AUTH_DB_TABLE_NAME + UserAuthDAO.USER_AUTH_DB_SCHEMA;
        
        final String drop_COMPONENTS_DB_SQL = "drop table if exists " 
        + ComponentsTable.COMPONENTS_DB_TABLE_NAME;
        
        final String SELECT_FROM_USER_AUTH = "select " + ALL 
        + " from " + UserAuthDAO.USER_AUTH_DB_TABLE_NAME;
        
        final String UPDATE_PASSWORD_IN_USER_AUTH = "update " + 
                UserAuthDAO.USER_AUTH_DB_TABLE_NAME + " set " + 
                UserAuthDAO.PASSWORD + " = ? "  + "where " + 
                UserAuthDAO.USERNAME+ " = ? ";
        
        final String SELECT_FROM_COMPONENTS_DB_ON_COMPONENT_SQL = "select " + ALL 
        + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " 
        + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ?";
        
        final String SELECT_COMCID_FROM_COMPONENTS_DB_ON_COMPONENT_SQL = "select "
        + ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID 
        + " from " 
        + ComponentsTable.COMPONENTS_DB_TABLE_NAME 
        + " where " 
        + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ?";
        
        final String SELECT_FROM_COMPONENTS_DB_ALL_COMPONENTS_SQL = "select distinct " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME;
        
        final String SELECT_FROM_COMPONENTS_DB_ALL_COMPONENTS_ALL_COLUMNS_SQL = "select " + ALL + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME;
        
        final String SELECT_FROM_COMPONENTS_DB_COUNT_COMPONENTS_ON_CAPCID_SQL = "select count(*) as " + COUNT + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?";
        
        final String SELECT_FROM_COMPONENTS_DB_COUNT_COMPONENTS_ON_COMCID_SQL = "select count(*) as " + COUNT + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID + " = ?";
        
        final String SELECT_FROM_COMPONENTS_DB_ON_COMPONENT_AND_CAPCID_SQL = "select " + ALL + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME  + " where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID  + " = ?";
        
        final String SELECT_FROM_COMPONENTS_DB_ON_CAPCID_SQL = "select " + ALL + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME  + " where " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID  + " = ?";
        
        final String SELECT_FROM_COMPONENTS_DB_ON_COMCID_SQL = "select " + ALL + " from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME  + " where " + ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID  + " = ?";
        
        final String UPDATE_COMPONENTS_DB_ON_COMPONENT_AND_CID_SET_CID_SQL = "update " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " set " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ? "  + "where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?";
        
        final String UPDATE_COMPONENTS_DB_ON_COMPONENT_SET_CAPCID_SQL = "update " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " set " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ? "  + "where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ?";
        
        final String UPDATE_COMPONENTS_DB_ON_COMPONENT_SET_COMCID_SQL = "update " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " set " + ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID + " = ? "  + "where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ?";
        
        final String INSERT_INTO_COMPONENTS_DB_SQL = "insert into " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " values (?, ?, ?, ?, ?, ?, ?, ?) ";
        
        final String INSERT_INTO_USER_AUTH_DB_SQL = "insert into " + UserAuthDAO.USER_AUTH_DB_TABLE_NAME + " values (?, ?) ";
        
        final String DELETE_FROM_COMPONENTS_DB_ON_COMPONENT_AND_CAPCID_SQL = "delete from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?";

        final String DELETE_FROM_COMPONENTS_DB_ON_COMPONENT_AND_CAPCID_AND_COMCID_SQL = "delete from " + ComponentsTable.COMPONENTS_DB_TABLE_NAME + " where " + ComponentsTable.COLUMN_COMPONENT_PATH_ID + " = ? and " + ComponentsTable.COLUMN_COMPONENT_CAPABILITIES_CLASS_ID + " = ?"
        + " and " + ComponentsTable.COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID + " = ?";

        
        /* capabilities classes table operations */
        
        //Policy_Classes_Table_Record pctr = new Policy_Classes_Table_Record();
        
        //final String PCS_DB_SCHEMA = pctr.produce_PCS_DB_DDL();
        
        final String CAPC_DB_SCHEMA = "( " + CapabilitiesClassesTable.COLUMN_CLASS_ID + ", " + CapabilitiesClassesTable.COLUMN_CLASS_NAME + ", " + CapabilitiesClassesTable.COLUMN_CAPABILITIES + ", " + CapabilitiesClassesTable.COLUMN_STATUS + ") ";
        
        final String create_CAPC_DB_SQL = "create table " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME + CAPC_DB_SCHEMA;
        
        final String drop_CAPC_DB_SQL = "drop table if exists " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME;
        
        final String SELECT_ALL_FROM_CAPC_DB_ON_CID_SQL = "select " + ALL + " from " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME  + " where " + CapabilitiesClassesTable.COLUMN_CLASS_ID  + " = ?";
        
        final String SELECT_ALL_FROM_CAPC_DB_SQL = "select distinct " + ALL + " from " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME;
        
        final String SELECT_FROM_CAPC_DB_COUNT_CAPABILITIES_ON_CID_SQL = "select distinct count(*) as " + COUNT + " from " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME;
        
        final String SELECT_CID_FROM_CAPC_DB_ON_CID_SQL = "select " 
        + CapabilitiesClassesTable.COLUMN_CLASS_ID + " from " 
        + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME  + " where " 
        + CapabilitiesClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String INSERT_INTO_CAPC_DB_SQL = "insert into " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME + " values (?, ?, ?, ?) ";
        
        final String UPDATE_CAPC_DB_COLUMN_CAPS_ON_CID_SQL = "update " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME + " set " + CapabilitiesClassesTable.COLUMN_CAPABILITIES + " = ? "  + "where " + CapabilitiesClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String UPDATE_CAPC_DB_COLUMN_NAME_ON_CID_SQL = "update " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME + " set " + CapabilitiesClassesTable.COLUMN_CLASS_NAME + " = ? "  + "where " + CapabilitiesClassesTable.COLUMN_CLASS_ID + " = ?";
                
        final String DELETE_FROM_CAPC_DB_ON_CID_SQL = "delete from " + CapabilitiesClassesTable.CAPC_DB_TABLE_NAME + " where " + CapabilitiesClassesTable.COLUMN_CLASS_ID + " = ?";

        
        /* communicative class operations */
        
        final String COMMC_DB_SCHEMA = "( " + CommunicativeClassesTable.COLUMN_CLASS_ID 
        + ", " + CommunicativeClassesTable.COLUMN_CLASS_NAME + ", " 
        + CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD + ", " 
        + CommunicativeClassesTable.COLUMN_COORDINATION_RECORD + ", " 
        + CommunicativeClassesTable.COLUMN_STATUS + ") ";
        
        final String create_COMMC_DB_SQL = "create table " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME 
        + COMMC_DB_SCHEMA;
        
        final String drop_COMMC_DB_SQL = "drop table if exists " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME;
        
        final String SELECT_ALL_FROM_COMMC_DB_ON_CID_SQL = "select " + ALL 
        + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME  
        + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID  + " = ?";
        
        final String SELECT_ALL_FROM_COMMC_DB_SQL = "select distinct " + ALL 
        + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME;
        
        final String SELECT_FROM_COMMC_DB_COUNT_CLASSES_ON_CID_SQL = "select distinct count(*) as " 
        + COUNT + " from " + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME;
        
        final String SELECT_CID_FROM_COMMC_DB_ON_CID_SQL = "select " 
        + CommunicativeClassesTable.COLUMN_CLASS_ID
        + " from " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME  
        + " where " 
        + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String SELECT_CID_FROM_COMMC_DB_ON_ALL_MAIN_COLUMNS_SQL = "select " 
        + CommunicativeClassesTable.COLUMN_CLASS_ID 
        + " from "
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME  
        + " where "
        + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?" + " and "
        + CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD + " = ?" + " and "     
        + CommunicativeClassesTable.COLUMN_COORDINATION_RECORD + " = ?";
        
        
        final String INSERT_INTO_COMMC_DB_SQL = "insert into " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME 
        + " values (?, ?, ?, ?, ?) ";
        
        final String DELETE_FROM_COMMC_DB_ON_CID_SQL = "delete from " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME 
        + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String DELETE_FROM_COMMC_DB_ON_COLLABORATION_RECORD_AND_CID_SQL = "delete from " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME 
        + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?" + " and "
        + CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD + " = ?";
               
        final String DELETE_FROM_COMMC_DB_ON_COORDINATION_RECORD_AND_CID_SQL = "delete from " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME 
        + " where " + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?" + " and "
        + CommunicativeClassesTable.COLUMN_COORDINATION_RECORD + " = ?";


        final String UPDATE_COMMC_DB_COLUMN_NAME_ON_CID_SQL = "update " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " set " 
        + CommunicativeClassesTable.COLUMN_CLASS_NAME + " = ? "  + "where " 
        + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String UPDATE_COMMC_DB_COLUMN_COLLABORATION_RECORD_ON_CID_SQL = "update " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " set " 
        + CommunicativeClassesTable.COLUMN_COLLABORATION_RECORD + " = ? "  + "where " 
        + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        final String UPDATE_COMMC_DB_COLUMN_COORDINATION_RECORD_ON_CID_SQL = "update " 
        + CommunicativeClassesTable.COMMUNICATIVE_CLASSES_DB_TABLE_NAME + " set " 
        + CommunicativeClassesTable.COLUMN_COORDINATION_RECORD + " = ? "  + "where " 
        + CommunicativeClassesTable.COLUMN_CLASS_ID + " = ?";
        
        
}
