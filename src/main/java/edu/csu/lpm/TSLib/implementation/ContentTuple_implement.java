/*
 * Copyright (C) 2016 kirill.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.csu.lpm.TSLib.implementation;

import edu.csu.lpm.TSLib.interfaces.ContentTuple;
import edu.csu.lpm.TSLib.interfaces.Tuple;
import java.io.File;

/**
 *
 * @author kirill
 */
public class ContentTuple_implement implements ContentTuple 
{
    private String ID = ""; 
    private Integer SequenceNumber = -1;
    private StringBuffer Payload = null;

    @Override
    public String get_ID_Field() 
    {
        return this.ID;
    }

    /*
        provides extra info with int return type: 0 for success; -1 for error
    */
    @Override
    public int set_ID_Field(String id)
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
                    this.ID = id;
                    f = null;
                    return Tuple.INDICATE_OPERATION_SUCCESS;
                }    
                else {    
                        System.out.println("ContentTuple.set_ID(): app ID does not exist in the filesystem! ");
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                     }
            } else {
                        System.out.println("ContentTuple.set_ID(): app ID is empty! ");  
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                   }    
            
        } else {
                    System.out.println("ContentTuple.set_ID(): app ID is null! ");  
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
    
}
