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

import edu.csu.lpm.TSLib.interfaces.ControlTuple;
import edu.csu.lpm.TSLib.interfaces.Tuple;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author kirill
 */
public class ControlTuple_implement implements ControlTuple, Serializable 
{
    
    private String ID = "";
    private String Type = "";
    private String RequestMessage = "";
    
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
                        System.out.println("ControlTuple.set_ID(): app ID does not exist in the filesystem! ");
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                     }
            } else {
                        System.out.println("ControlTuple.set_ID(): app ID is empty! ");  
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                   }    
            
        } else {
                    System.out.println("ControlTuple.set_ID(): app ID is null! ");  
                    return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;     
               }        
    }  
    

    @Override
    public String get_Type_Field() 
    {
        return this.Type;
    }

    @Override
    public void set_Type_Field_to_Coordination() 
    {
        this.Type = Tuple.TupleTypes.COORDINATION.toString();
    }
    
    
    @Override
    public void set_Type_Field_to_Collaboration() 
    {
        this.Type = Tuple.TupleTypes.COLLABORATION.toString();
    }
    

    @Override
    public String get_RequestMessage_Field() 
    {
        return this.RequestMessage;
    }

    
    /*
        provides extra info with int return type: 0 for success; -1 for error
    */
    @Override
    public int set_RequestMessage_Field(String rqm) 
    {
        if (rqm != null)
        {
            if (!rqm.isEmpty())
            {    
                this.RequestMessage = rqm;
                return Tuple.INDICATE_OPERATION_SUCCESS;
            } else {
                        System.out.println("ControlTuple.set_RequestMessage(): message is empty! ");  
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                   } 
        } else {
                    System.out.println("ControlTuple.set_RequestMessage(): message is null! ");  
                    return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
               }    
    
    }
    
    /* introduce template matching methods inside a tuple */
    
    @Override
    public boolean match_on_ID_Field(String id)
    {
        if (id != null)
        {
            if (!id.isEmpty())
            {
                if (this.ID.equals(id))
                    return true;
            }    
        }
        
        return false;
    }        
    
    
    @Override
    public boolean match_on_Type_Field(String type)
    {
        if (type != null)
        {
            if (!type.isEmpty())
            {
                if (this.Type.equals(type))
                    return true;
            }    
        }
        
        return false;
    }
    
    
    @Override
    public boolean match_on_RequestMessage_Field(String rqm)
    {
        if (rqm != null)
        {
            if (!rqm.isEmpty())
            {
                if (this.RequestMessage.equals(rqm))
                    return true;
            }    
        }
        
        return false;
    }
    
}
