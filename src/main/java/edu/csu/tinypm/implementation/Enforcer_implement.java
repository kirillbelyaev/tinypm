/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.implementation;

import edu.csu.tinypm.interfaces.Enforcer;
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
    
    private List<String> cmd = null;

    public List<String> getCmd() 
    {
        return cmd;
    }

    @Override
    public int executeCmd()
{
	String s = null;
	try {   
            ProcessBuilder pb = new ProcessBuilder(this.cmd);
            Process p = pb.start();
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Enforcer:executeCmd(): Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) 
            {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Enforcer:executeCmd(): Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) 
            {
                System.out.println(s);
            }
            
            try 
            {
            	if (p.waitFor() != 0)
            	{
            	System.out.println("Enforcer:executeCmd(): Wait for subprocess termination returned non zero!");
            	return -1; //indicate an error
            	}
            } catch (InterruptedException ie) {System.out.println("Enforcer:executeCmd(): Wait for subprocess termination has been interrupted - exception happened."); return -1;} //indicate an error
            
            if (p.exitValue() != 0) //proper way of checking for execution error 
            {	
            	System.out.println("Enforcer:executeCmd(): command execution error occurred!");
            	return -1; //indicate an error
            }
        } catch (IOException e) 
        {
            System.out.println("Enforcer:executeCmd(): exception happened - here's what I know: ");
            e.printStackTrace();
            return -1; //indicate an error
        }
			return 0;
}
    

    @Override
    public void buildEnforcerCMDParams (ArrayList<String> pl)
{
    if (pl == null) return;
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
        
        //System.out.println("enforcer.buildEnforcerCMDParams caps is: " + caps);

        command.add(caps);
        command.add(pl.get(pl.size()-1));
        this.cmd = command;
        
        //System.out.println("enforcer.buildEnforcerCMDParams cmd is: " + this.getCmd());
    }
    
    if (pl.size() == 1) /* no cap argument present - only app string is returned by return_modified_app_policies()
        - the case when a single capability is removed and no more capabilities are left */
    {
        caps = caps.concat(all);
        caps = caps.concat(rep);
        
        //System.out.println("enforcer.buildEnforcerCMDParams caps is: " + caps);
        
        command.add(caps);
        command.add(pl.get(pl.size()-1));
        this.cmd = command;
        
        //System.out.println("enforcer.buildEnforcerCMDParams cmd is: " + this.getCmd());
        
    }    
} 

    
}
