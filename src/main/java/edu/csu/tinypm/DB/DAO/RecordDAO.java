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

import edu.csu.tinypm.DB.DTO.Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;

/**
 *
 * @author kirill
 */
public interface RecordDAO extends DB_Base
{   
    public Record[] readRecordsOnAPP(Record r) throws RecordDAOException;
    
    public int deleteRecordsOnAPPandCAP(Record r) throws RecordDAOException;
    
    public int writeRecord(Record r) throws RecordDAOException;
    
    public int createTable_LC_DB() throws RecordDAOException;
    
    public int dropTable_LC_DB() throws RecordDAOException;
    
    public void closeConnection() throws RecordDAOException;
    
    public Integer countDistinctAppCapRecords(Record r) throws RecordDAOException;
     
}
