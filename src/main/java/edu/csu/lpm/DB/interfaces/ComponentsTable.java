/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

/*
 * Copyright (C) 2015 kirill.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
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
