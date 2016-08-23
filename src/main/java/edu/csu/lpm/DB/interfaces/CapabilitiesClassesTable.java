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
import edu.csu.lpm.interfaces.LinuxCapabilitiesPolicyContainer;

/**
 *
 * @author kirill
 */
public interface CapabilitiesClassesTable extends Record
{
    final String CAPC_DB_TABLE_NAME = "capc_db";
    final String CAPC_DB_NAME = "capc.db";
    
    /* no duplicates are allowed for this table - CID can only have a single record */
    final String COLUMN_CLASS_NAME = "class_name";
    
    final String COLUMN_CLASS_ID = "CID";
    
    final String COLUMN_CAPABILITIES = "capabilities";  
    
    final String COLUMN_STATUS = "status";
    
    LinuxCapabilitiesPolicyContainer.LinuxCapabilities LCS[] = LinuxCapabilitiesPolicyContainer.LinuxCapabilities.values();
    
}
