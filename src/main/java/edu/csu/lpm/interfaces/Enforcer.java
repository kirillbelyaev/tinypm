/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
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
