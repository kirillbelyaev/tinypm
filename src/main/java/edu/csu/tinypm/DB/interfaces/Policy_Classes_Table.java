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
package edu.csu.tinypm.DB.interfaces;

import edu.csu.tinypm.interfaces.LinuxCAPPolicyContainer;

/**
 *
 * @author kirill
 */
public interface Policy_Classes_Table 
{
    final String APPS_DB_TABLE_NAME = "pcs_db";
    final String APPS_DB_NAME = "pcs.db";
    
    
    final String COLUMN_POLICY_CLASS_NAME = "pc_name";
    final String COLUMN_POLICY_CLASS_ID = "PCID";
       
    final String COLUMN_STATUS = "status";
    
    LinuxCAPPolicyContainer.LinuxCapabilities LCS[] = LinuxCAPPolicyContainer.LinuxCapabilities.values();
    
}
