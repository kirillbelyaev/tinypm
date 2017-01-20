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
