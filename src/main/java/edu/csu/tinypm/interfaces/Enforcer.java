/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.interfaces;

import java.util.ArrayList;

/**
 *
 * @author kirill
 */
public interface Enforcer 
{
    final String SETCAP_EXE = "/sbin/setcap";
    
    public int executeCmd();
    
    public void buildEnforcerCMDParams (ArrayList<String> pl);
    
    
    
}
