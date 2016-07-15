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

package edu.csu.lpm.DB.DAO;

import edu.csu.lpm.DB.DTO.AppsTableRecord;
import edu.csu.lpm.DB.DTO.CapabilitiesClassesTableRecord;
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
    
    public void closeConnection() throws RecordDAO_Exception;
    
    
    /* new API methods to support the policy class abstraction */
    
    /* apps table operations */
    
    public int createTable_APPS_DB() throws RecordDAO_Exception;
    
    public int dropTable_APPS_DB() throws RecordDAO_Exception;
    
    public AppsTableRecord[] read_Apps_Table_Records_On_APP(AppsTableRecord r) throws RecordDAO_Exception;
    
    public AppsTableRecord[] read_Apps_Table_Records_On_APP_and_PCID(AppsTableRecord r) throws RecordDAO_Exception;
    
    public AppsTableRecord[] read_Apps_Table_Records_On_PCID(AppsTableRecord r) throws RecordDAO_Exception;
    
    public AppsTableRecord[] read_Apps_Table_Records_On_All_APPs() throws RecordDAO_Exception;
    
    public Integer count_Distinct_Apps_Table_Records_on_PCID(AppsTableRecord r) throws RecordDAO_Exception;
     
    public int write_Apps_Table_Record(AppsTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Apps_Table_Records_On_APP_and_PCID(AppsTableRecord r) throws RecordDAO_Exception;
    
    
     /* policy classes table operations */
    
    public int create_Table_PCS_DB() throws RecordDAO_Exception;
    
    public int drop_Table_PCS_DB() throws RecordDAO_Exception;
    
    public CapabilitiesClassesTableRecord[] read_Policy_Classes_Table_Records_On_PCID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception;
    
    public CapabilitiesClassesTableRecord[] read_Policy_Classes_Table_Records_On_All_Classes() throws RecordDAO_Exception;
    
    public Integer count_Distinct_Policy_Classes_Table_Records_on_PCID() throws RecordDAO_Exception;
    
    public int write_Policy_Classes_Table_Record(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception;
    
    public int delete_Policy_Classes_Table_Records_On_PCID(CapabilitiesClassesTableRecord r) throws RecordDAO_Exception;
     
}
