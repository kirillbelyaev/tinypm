/*
 * Lightweight Policy Machine for Linux (LPM) Reference Monitor Prototype
 *   
 * Copyright (C) 2015-2017 Kirill A Belyaev
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

package edu.csu.lpm.DB.interfaces;

import edu.csu.lpm.DB.DTO.Record;

/**
 *
 * @author kirill
 */
public interface CommunicativeClassesTable extends Record
{
    
    final String COMMUNICATIVE_CLASSES_DB_TABLE_NAME = "commc_db";
    
    final String COMMUNICATIVE_CLASSES_DB_NAME = "commc.db";
    
    /* duplicates are allowed for this table - CID can have multiple records */
    final String COLUMN_CLASS_ID = "CID";
    
    final String COLUMN_CLASS_NAME = "class_name";
    
    /* a biple - component_1 ID; component_2 ID
    if such a record exists - that serves as a permission for coordination */
    final String COLUMN_COORDINATION_RECORD = "coordination_record";
    
    /* a biple - component ID; data_object_path
    if such a record exists - that serves as a permission for collaboration */
    final String COLUMN_COLLABORATION_RECORD = "collaboration_record";
    
    
    final String COLUMN_STATUS = "status";   
    
}
