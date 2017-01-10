/*
 * Linux Policy Machine (LPM) Prototype
 *   
 * Copyright (C) 2015-2016  Kirill A Belyaev
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
package edu.csu.lpm.TSLib.factory;

import edu.csu.lpm.TSLib.implementation.AgentTransactionManager_implement;
import edu.csu.lpm.TSLib.implementation.ContentTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControlTuple_implement;
import edu.csu.lpm.TSLib.implementation.ControllerTransactionManager_implement;

/**
 *
 * @author kirill
 */
public class TSL_Factory 
{
    
    
public ControlTuple_implement obtain_ControlTuple()
{
    return new ControlTuple_implement();
}        
   

public ContentTuple_implement obtain_ContentTuple()
{
    return new ContentTuple_implement();
}


public AgentTransactionManager_implement obtain_AgentTransactionManager()
{
    return new AgentTransactionManager_implement();
}

public ControllerTransactionManager_implement obtain_ControllerTransactionManager()
{
    return new ControllerTransactionManager_implement();
}
    
}
