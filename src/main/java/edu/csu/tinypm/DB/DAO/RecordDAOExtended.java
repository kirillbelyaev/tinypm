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

package edu.csu.tinypm.DB.DAO;

import edu.csu.tinypm.DB.DTO.Apps_Table_Record;
import edu.csu.tinypm.DB.DTO.Policy_Classes_Table_Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;

/**
 *
 * @author kirill
 */

public interface RecordDAOExtended extends DB_Base
{      
    public void closeConnection() throws RecordDAOException;
    
    
    /* new API methods to support the policy class abstraction */
    
    /* apps table operations */
    
    public int createTable_APPS_DB() throws RecordDAOException;
    
    public int dropTable_APPS_DB() throws RecordDAOException;
    
    public Apps_Table_Record[] read_Apps_Table_Records_On_APP(Apps_Table_Record r) throws RecordDAOException;
    
    public Apps_Table_Record[] read_Apps_Table_Records_On_APP_and_PCID(Apps_Table_Record r) throws RecordDAOException;
    
    public Apps_Table_Record[] read_Apps_Table_Records_On_All_APPs() throws RecordDAOException;
    
    public Integer count_Distinct_Apps_Table_Records_on_APP_and_PCID(Apps_Table_Record r) throws RecordDAOException;
     
    public int write_Apps_Table_Record(Apps_Table_Record r) throws RecordDAOException;
    
    public int delete_Apps_Table_Records_On_APP_and_PCID(Apps_Table_Record r) throws RecordDAOException;
    
    
     /* policy classes table operations */
    
    public int create_Table_PCS_DB() throws RecordDAOException;
    
    public int drop_Table_PCS_DB() throws RecordDAOException;
    
    public Policy_Classes_Table_Record[] read_Policy_Classes_Table_Records_On_PCID(Policy_Classes_Table_Record r) throws RecordDAOException;
    
    public Integer count_Distinct_Policy_Classes_Table_Records_on_PCID(Policy_Classes_Table_Record r) throws RecordDAOException;
    
    public int write_Policy_Classes_Table_Record(Policy_Classes_Table_Record r) throws RecordDAOException;
    
     
}
