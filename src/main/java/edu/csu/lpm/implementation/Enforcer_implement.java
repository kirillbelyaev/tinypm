/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.lpm.implementation;

import edu.csu.lpm.interfaces.Enforcer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kirill
 */
public class Enforcer_implement implements Enforcer
{
    
    private List<String> CMD = null;

    @Override
    public List<String> get_CMD() 
    {
        return this.CMD;
    }
    
    
    @Override
    public void set_CMD(List <String> l) 
    {
        if (l != null) this.CMD = l;
    }
    

    @Override
    public int execute_CMD()
{
    if (this.get_CMD() == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
    
    String s = null;
    
    try 
    {   
        ProcessBuilder pb = new ProcessBuilder(this.get_CMD());
        Process p = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        // read the output from the command
        System.out.println("Enforcer:execute_Cmd(): Here is the standard output of the command:\n");
        
        while ((s = stdInput.readLine()) != null) 
        {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Enforcer:execute_Cmd(): Here is the standard error of the command (if any):\n");
        
        while ((s = stdError.readLine()) != null) 
        {
            System.out.println(s);
        }

        try 
        {
            if (p.waitFor() != INDICATE_EXECUTION_SUCCESS)
            {
            System.out.println("Enforcer:execute_Cmd(): Wait for subprocess termination returned non zero!");
            return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate an error
            }
        } catch (InterruptedException ie) {System.out.println("Enforcer:execute_Cmd(): Wait for subprocess termination has been interrupted - exception happened."); return INDICATE_CONDITIONAL_EXIT_STATUS;} //indicate an error

        if (p.exitValue() != INDICATE_EXECUTION_SUCCESS) //proper way of checking for execution error 
        {	
            System.out.println("Enforcer:execute_Cmd(): command execution error occurred!");
            return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate an error
        }
    } catch (IOException e) 
    {
        System.out.println("Enforcer:execute_Cmd(): exception happened - here's what I know: ");
        e.printStackTrace();
        return INDICATE_CONDITIONAL_EXIT_STATUS; //indicate an error
    }
                    return INDICATE_EXECUTION_SUCCESS;
}
    
/*
    @Override
    public int build_EnforcerCMD_Parameters (ArrayList<String> pl)
{
    if (pl == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
    ArrayList<String> command = null;
    
    String caps = ""; 
    String ep = "+ep";
    
    command = new ArrayList<String>();
    command.add(SETCAP_EXE);
    
    for (int i = 0; i < (pl.size()-1); i++)
    {
        String cap = pl.get(i);
        if (cap != null)
        {    
            caps = caps.concat(cap);
            caps = caps.concat(",");
            
        }
        //System.out.println("enforcer.buildEnforcerCMDParams caps is: " + caps);
    }
    
    caps = caps.substring(0, (caps.length()-1)); //remove the last , character
    caps = caps.concat(ep);
    System.out.println("enforcer.build_EnforcerCMDParams caps is: " + caps);
    
    command.add(caps);
    command.add(pl.get(pl.size()-1));
    
    this.set_CMD(command);
    System.out.println("enforcer.build_EnforcerCMDParams cmd is: " + this.get_CMD());
    
    return INDICATE_EXECUTION_SUCCESS;
} 
*/    
    
    @Override
public int build_EnforcerCMD_Parameters (ArrayList<String> pl)
{
    if (pl == null) return INDICATE_CONDITIONAL_EXIT_STATUS;
    ArrayList<String> command = null;
    String caps = ""; 
    String ep= "+ep";
    String rep= "-ep";
    String all = "ALL";
    command = new ArrayList<String>();
    command.add(SETCAP_EXE);
   
    
    if (pl.size() > 1) /* at least one cap is present besides the app string itself returned by return_modified_app_policies() */
    {
        for (int i = 0; i < (pl.size()-1); i++)
        {
            String cap = pl.get(i);
            if (cap != null)
            {    
                caps = caps.concat(cap);
                caps = caps.concat(",");

            }
            //System.out.println("enforcer.buildEnforcerCMDParams caps is: " + caps);
        }

        caps = caps.substring(0, (caps.length()-1)); //remove the last , character
        caps = caps.concat(ep);
        
        System.out.println("enforcer.build_EnforcerCMD_Parameters caps is: " + caps);

        command.add(caps);
        command.add(pl.get(pl.size()-1));
        this.set_CMD(command);
        
        System.out.println("enforcer.build_EnforcerCMD_Parameters cmd is: " + this.get_CMD());
    }
    
    if (pl.size() == 1) /* no cap argument present - only app string is returned
        by return_modified_app_policies() - the case when a single 
        capability is removed and no more capabilities are left */
    {
        caps = caps.concat(all);
        caps = caps.concat(rep);
        
        System.out.println("enforcer.build_EnforcerCMD_Parameters caps is: " + caps);
        
        command.add(caps);
        command.add(pl.get(pl.size()-1));
        this.set_CMD(command);
        
        System.out.println("enforcer.build_EnforcerCMD_Parameters cmd is: " + this.get_CMD());
        
    }
    
    return INDICATE_EXECUTION_SUCCESS;
} 
    

    

    
}
