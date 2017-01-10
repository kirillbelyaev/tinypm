/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
 */
package edu.csu.lpm.machine;

import edu.csu.lpm.implementation.Parser_implement;
import edu.csu.lpm.interfaces.Parser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PM_Shell {

    private Parser_implement p = Parser_implement.getInstance();
    public static DataOutputStream out;
    private DataInputStream in;

    public PM_Shell(DataOutputStream out, DataInputStream in) {
        this.out = out;
        this.in = in;
    }

    private void show_Prompt() throws IOException {
        out.writeUTF("LPM::<>");
    }

    public void verify() throws IOException, SQLException, Exception {

        out.writeUTF("Enter username: ");
        String username = in.readUTF();
        out.writeUTF("Enter password: ");
        String password = in.readUTF();
        if (!username.equals(p.getUserAuthDAO().getUsernameFromDB())
                || !password.equals(p.getUserAuthDAO().getPasswordFromDB())) {
            out.writeUTF("Invalid Details!!!! Unable to log in");
        } else {
            process_UserInput();
        }

    }

    private void process_UserInput() throws Exception {
        int x = -1;

        for (;;) { //shell loop starts

            show_Prompt();
            //TODO: pass out reference so that we can write 
            String cmd = in.readUTF().trim();
            x = p.parse_and_execute_Command(cmd);
            String errorMsg = p.get_ERROR_MESSAGE();
            ArrayList<String> arr = p.get_ResultOutput();
            if(x == Parser.INDICATE_IMMEDIATE_EXIT_STATUS) {
            	errorMsg = "exit";
            	out.writeUTF(errorMsg);
            	//ServerMain.removeCurrentClient();
            	break;
            }
            if(errorMsg!="") {
            	out.writeUTF(errorMsg);
            }
            if(!arr.isEmpty()) {
            	out.writeUTF(buildStringFromArray(arr));
            }
            
        } //end of for loop
        
    }
    
    private String buildStringFromArray(ArrayList<String> arr) {
    	StringBuilder builder = new StringBuilder();
    	for(String message: arr) {
    		builder.append(message+"\n");
    	}
    	
    	return builder.toString();
    }

}
