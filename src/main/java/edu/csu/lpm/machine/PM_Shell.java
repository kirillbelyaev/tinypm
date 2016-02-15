/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.lpm.machine;


import edu.csu.lpm.implementation.Parser_implement;
import edu.csu.lpm.interfaces.Parser;
import java.util.Scanner;

public class PM_Shell 
{

private Parser_implement p = new Parser_implement();
    
private void show_Prompt()
{
    System.out.print("tinyPM::<>");
}

public void process_UserInput() throws Exception 
{	
    int x = -1;
    Scanner keyboard = new Scanner(System.in);

    for(;;) 
    { //shell loop starts

        show_Prompt();
        x = p.parse_and_execute_Command(keyboard.nextLine().trim());
        System.out.println(p.get_ERROR_MESSAGE());
        System.out.println(p.get_ResultOutput());
        if (x == Parser.INDICATE_IMMEDIATE_EXIT_STATUS) break;        
    } //end of for loop
}

    
}