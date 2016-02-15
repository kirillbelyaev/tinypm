
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

/**
 *
 * @author kirill
 */
public interface DB_Base 
{
    
    public String getCurrentDatetime();
    public String getCurrentDate();
   
}
