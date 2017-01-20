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

package edu.csu.lpm.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kirill
 */
public interface Enforcer 
{
    /* we use macros to indicate the general method exit codes within the implementation */
    public int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public int INDICATE_EXECUTION_SUCCESS = 0;
    
    final String SETCAP_EXE = "/sbin/setcap";
    
    public List<String> get_CMD();
    
    public void set_CMD(List <String> l);
    
    public int execute_CMD();
    
    public int build_EnforcerCMD_Parameters (ArrayList<String> pl);
    
    
    
}
