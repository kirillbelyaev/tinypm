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

package edu.csu.lpm.TSLib.implementation;

import edu.csu.lpm.TSLib.interfaces.ContentTuple;
import edu.csu.lpm.TSLib.interfaces.Tuple;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author kirill
 */
public class ContentTuple_implement implements ContentTuple, Serializable 
{
    private String DestinationID = ""; 
    private Integer SequenceNumber = -1;
    private StringBuffer Payload = null;

    @Override
    public String get_DestinationID_Field() 
    {
        return this.DestinationID;
    }

    /*
        provides extra info with int return type: 0 for success; -1 for error
    */
    @Override
    public int set_DestinationID_Field(String id)
    {
        if (id != null)
        {    
            if (!id.isEmpty())
            {    
                File f = new File(id);
                
                /* set only if an app is an actual file, does exist and not 
                a directory */
                if (f.isFile())
                {    
                    this.DestinationID = id;
                    f = null;
                    return Tuple.INDICATE_OPERATION_SUCCESS;
                }    
                else {    
                        System.out.println("ContentTuple.set_DestinationID(): app ID does not exist in the filesystem! ");
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                     }
            } else {
                        System.out.println("ContentTuple.set_DestinationID(): app ID is empty! ");  
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                   }    
            
        } else {
                    System.out.println("ContentTuple.set_DestinationID(): app ID is null! ");  
                    return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;     
               }      
    }  
    

    @Override
    public Integer get_SequenceNumber_Field() 
    {
        return this.SequenceNumber;
    }

    @Override
    public int set_SequenceNumber_Field(Integer sqn) 
    {
        if (sqn != null)
        {
            this.SequenceNumber = sqn;
            return Tuple.INDICATE_OPERATION_SUCCESS;
        } else {
                  System.out.println("ContentTuple.set_SequenceNumber(): SequenceNumber is null! ");  
                  return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
               }    
    }

    @Override
    public StringBuffer get_Payload_Field() 
    {
        return this.Payload;
    }

    
    /*
        provides extra info with int return type: 0 for success; -1 for error
    */
    @Override
    public int set_Payload_Field(StringBuffer payload) 
    {
        if (payload != null)
        {
            this.Payload = payload;
            return Tuple.INDICATE_OPERATION_SUCCESS;   
        } else {
                    System.out.println("ContentTuple.set_Payload(): Payload is null! ");  
                    return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
               }    
    }
    
    
    /* introduce template matching methods inside a tuple */
    
    @Override
    public boolean match_on_DestinationID_Field(String id)
    {
        if (id != null)
        {
            if (!id.isEmpty())
            {
                if (this.DestinationID.equals(id))
                    return true;
            }    
        }
        
        return false;
    }        
    
    
    @Override
    public boolean match_on_SequenceNumber_Field(Integer n)
    {
        if (n != null)
        {    
            if (this.SequenceNumber.equals(n))
                return true; 
        }
        
        return false;
    }
    
}
