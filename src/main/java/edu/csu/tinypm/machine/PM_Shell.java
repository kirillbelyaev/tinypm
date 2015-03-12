/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.machine;


import edu.csu.tinypm.DB.implementation.Parser_implement;
import edu.csu.tinypm.interfaces.Parser;
import java.util.Scanner;

public class PM_Shell 
{

private Parser_implement p = new Parser_implement();
    
private void showPrompt()
{
    System.out.print("tinyPM::<>");
}

public void processUserInput() throws Exception 
{	
    int x = -1;
    Scanner keyboard = new Scanner(System.in);

    for(;;) 
    { //shell loop starts

        showPrompt();
        x = p.parse_and_execute_Command(keyboard.nextLine());
        System.out.println(p.getERROR_MESSAGE());
        System.out.println(p.getResultOutput());
        if (x == Parser.INDICATE_IMMEDIATE_EXIT_STATUS) break;        
    } //end of for loop
}

    
}