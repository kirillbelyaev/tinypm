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
public interface ComponentsTable extends Record
{
    
    final String COMPONENTS_DB_TABLE_NAME = "components_db";
    
    final String COMPONENTS_DB_NAME = "components.db";
    
    final String COLUMN_COMPONENT_DESC = "component_description";
    
    final String COLUMN_COMPONENT_PATH_ID = "component_path_id";
    
    /* reserved for future use - could be used for alternative ID mechanisms */
    final String COLUMN_COMPONENT_ID = "component_id";
    
    /* reserved for possible use - indicates the component's tuple space location.
    technically this should be determined automatically based on the path_ID -
    TS should be located immediately at the 1st level of component's root
    directory */
    final String COLUMN_COMPONENT_TUPLE_SPACE_PATH = "component_tuple_space_path";
    
    final String COLUMN_COMPONENT_CAPABILITIES_CLASS_ID = "CAP_CID";
    
    final String COLUMN_COMPONENT_COMMUNICATIVE_CLASS_ID = "COM_CID";
    
    final String COLUMN_COMPONENT_CONTAINER_ID = "CCID";
    
    final String COLUMN_STATUS = "status";   
    
}
