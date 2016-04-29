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
    
    private String SourceID = "";
    private String DestinationID = "";
    private String Type = "";
    private String RequestMessage = "";
    
    @Override
    public String get_SourceID_Field()
    {
        return this.SourceID;
    }        
    
    @Override
    public String get_DestinationID_Field()
    {
        return this.DestinationID;
    }        
    
    /*
        provides extra info with int return type: 0 for success; -1 for error
    */
    @Override
    public int set_SourceID_Field(String id)
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
                    this.SourceID = id;
                    f = null;
                    return Tuple.INDICATE_OPERATION_SUCCESS;
                }    
                else {    
                        System.out.println("ControlTuple.set_SourceID(): app ID does not exist in the filesystem! ");
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                     }
            } else {
                        //System.out.println("ControlTuple.set_ID(): app ID is empty! ");  
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                   }    
            
        } else {
                    //System.out.println("ControlTuple.set_ID(): app ID is null! ");  
                    return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;     
               }        
    }  
    
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
                        System.out.println("ControlTuple.set_DestinationID(): app ID does not exist in the filesystem! ");
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                     }
            } else {
                        //System.out.println("ControlTuple.set_ID(): app ID is empty! ");  
                        return Tuple.INDICATE_CONDITIONAL_EXIT_STATUS;
                   }    
            
        } else {
                    //System.out.println("ControlTuple.set_ID(): app ID is null! ");  
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
    public boolean match_on_SourceID_Field(String id)
    {
        if (id != null)
        {
            if (!id.isEmpty())
            {
                if (this.SourceID.equals(id))
                    return true;
            }    
        }
        
        return false;
    }        
    
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
