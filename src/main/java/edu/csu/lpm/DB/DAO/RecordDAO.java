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

package edu.csu.lpm.DB.DAO;

import edu.csu.lpm.DB.DTO.ComponentsTableRecord;
import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
import edu.csu.lpm.DB.DTO.CommunicativeClassesTableRecord;
import edu.csu.lpm.DB.exceptions.RecordDAO_Exception;

/**
 *
 * @author kirill
 */

public interface RecordDAO extends DB_Base
{  
    /* we use macros to indicate the general method exit codes within the DAO implementation */
    final int EMPTY_RESULT = -8;
    final int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    final int INDICATE_EXECUTION_SUCCESS = 0;
    final int RECORD_EXISTS = 1;
    final int INDICATE_SQL_EXCEPTION = -2;

    final int INDICATE_CAPABILITIES_CLASS_RECORD_DOES_NOT_EXIST_STATUS = -3;
    final int INDICATE_COMMUNICATIVE_CLASS_RECORD_DOES_NOT_EXIST_STATUS = -4;
    
    
    public void closeConnection() throws RecordDAO_Exception;
    
    
    /* new API methods to support the policy class abstraction */
    
    /* Components table operations */
    
    public int createTable_Components_DB() throws RecordDAO_Exception;
    
    public int dropTable_Components_DB() throws RecordDAO_Exception;
    
    public ComponentsTableRecord[] read_Components_Table_Records_On_Component(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public ComponentsTableRecord[] read_Components_Table_Records_On_Component_and_CAPCID(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public ComponentsTableRecord[] read_Components_Table_Records_On_CAPCID(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public ComponentsTableRecord[] read_Components_Table_Records_On_COMCID(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public ComponentsTableRecord[] read_Components_Table_Records_On_All_Components() throws RecordDAO_Exception;
    
    public Integer count_Components_Table_Records_on_CAPCID(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public Integer count_Components_Table_Records_on_COMCID(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public int write_ComponentsTableRecord(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Components_Table_Records_On_Component_and_CAPCID_and_COMCID(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    public String get_ComponentsTableRecordsCOMCID_On_Component(ComponentsTableRecord r) throws RecordDAO_Exception;
    
    /* capabilities classes table operations */
    
    public int createTable_CAPC_DB() throws RecordDAO_Exception;
    
    public int dropTable_CAPC_DB() throws RecordDAO_Exception;
    
    public CapabilitiesClassesTableRecord[] read_Capabilities_Classes_Table_Records_On_CID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception;
    
    public CapabilitiesClassesTableRecord[] read_Capabilities_Classes_Table_Records_On_All_Classes() throws RecordDAO_Exception;
    
    public Integer count_Distinct_Capabilities_Classes_Table_Records_on_CID() throws RecordDAO_Exception;
    
    public int write_CapabilitiesClassesTableRecord(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Capabilities_Classes_Table_Records_On_CID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception;
    
    
    /* communicative class table operations */
    
    public int createTable_COMMC_DB() throws RecordDAO_Exception;
    
    public int dropTable_COMMC_DB() throws RecordDAO_Exception;
    
    public Integer count_Distinct_Communicative_Classes_Table_Records_on_CID() throws RecordDAO_Exception;
    
    public CommunicativeClassesTableRecord[] read_Communicative_Classes_Table_Records_On_CID(CommunicativeClassesTableRecord r) throws RecordDAO_Exception;
    
    public CommunicativeClassesTableRecord[] read_Communicative_Classes_Table_Records_On_All_Classes() throws RecordDAO_Exception;
    
    public int write_Communicative_Classes_Table_Record(CommunicativeClassesTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Communicative_Classes_Table_Records_On_CID(CommunicativeClassesTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Communicative_Classes_Table_Record_On_CollaborationRecord_And_CID(CommunicativeClassesTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Communicative_Classes_Table_Record_On_CoordinationRecord_And_CID(CommunicativeClassesTableRecord r) throws RecordDAO_Exception;
    
    /* new version taking into account the fact that duplicates are allowed on CID
    but with distinct coord/collab records */
    public int write_CommunicativeClassesTableRecord(CommunicativeClassesTableRecord r) throws RecordDAO_Exception; 
    
}
