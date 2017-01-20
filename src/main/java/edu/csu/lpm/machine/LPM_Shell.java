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

package edu.csu.lpm.machine;


import edu.csu.lpm.implementation.Parser_implement;
import edu.csu.lpm.interfaces.Parser;
import java.util.Scanner;

public class LPM_Shell 
{

private Parser_implement p = new Parser_implement();
    
private void show_Prompt()
{
    System.out.print("LPM::<>");
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