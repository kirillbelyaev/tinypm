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

import edu.csu.lpm.TSLib.interfaces.TupleSpace;
import java.util.ArrayList;

/**
 *
 * @author kirill
 */
public class TupleSpace_implement implements TupleSpace 
{
    //final String TupleSpaceName = "ts";
    /* TS is a list that can hold different types of objects */
    private ArrayList <Object> TS = null;

    @Override
    public String get_TupleSpaceName() 
    {
        return this.TupleSpaceName;
    }

    @Override
    public int create_TupleSpace() 
    {
        if (this.TS == null)
        {    
            TS = new ArrayList <Object>();
            return TupleSpace.INDICATE_OPERATION_SUCCESS;
        } else { return TupleSpace.INDICATE_TUPLE_SPACE_EXISTS_STATUS; }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete_TupleSpace() 
    {
        if (this.TS == null) return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
        
        /* allow deletion only if ts is empty by TS calculus definition */
        if (!this.TS.isEmpty()) return TupleSpace.INDICATE_TUPLE_SPACE_NOT_EMPTY_STATUS;
        else
        {    
            this.TS.clear(); /* not really needed since it should be empty - more
            like a symbolic gesture*/
            TS = null;
            return TupleSpace.INDICATE_OPERATION_SUCCESS;
        }
    }

//    @Override
//    public int delete_TupleSpace(String name) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public int countTuples() 
    {
        if (this.TS == null) return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
        return this.TS.size();
    }

    @Override
    public int countControlTuples() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countContentTuples() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ControlTuple_implement read_ControlTuple() 
    {
        if (this.TS == null) return null;
        //ControlTuple_implement ct = null;
        /* we need to instanciate the class */
        ControlTuple_implement ct = new ControlTuple_implement();
        
        /* iterate over TS */
        for (Object o : this.TS)
        {   
            if (o != null)
            {  
                /* does not work - we need the actual instanciated class parameter */
                //if (o.getClass().isInstance(ControlTuple_implement.class))
                /* since equals() can not make object comparison of 
                different objects we need to identify a class type based on
                the instance type check - the only possible solution */
                if (o.getClass().isInstance(ct))    
                {
                    //System.out.println("read_ControlTuple(): object class match found! ");
                    /* reuse the existing class instance */
                    ct = (ControlTuple_implement) o;
                    //return (ControlTuple_implement) o;
                    return ct;
                }               
            }    
        }
     
        return null;
    }

    @Override
    public ContentTuple_implement read_ContentTuple() 
    {
        if (this.TS == null) return null;
        //ContentTuple_implement ct = null;
        /* we need to instanciate the class */
        ContentTuple_implement ct = new ContentTuple_implement();
        
        /* iterate over TS */
        for (Object o : this.TS)
        {   
            if (o != null)
            {  
                /* does not work - we need the actual instanciated class parameter */
                //if (o.getClass().isInstance(ContentTuple_implement.class))
                /* since equals() can not make object comparison of 
                different objects we need to identify a class type based on
                the instance type check - the only possible solution */
                if (o.getClass().isInstance(ct))    
                {
                    //System.out.println("read_ControlTuple(): object class match found! ");
                    /* reuse the existing class instance */
                    ct = (ContentTuple_implement) o;
                    //return (ControlTuple_implement) o;
                    return ct;
                }               
            }    
        }
     
        return null;   
    }

    @Override
    public int append_ControlTuple(ControlTuple_implement ct) 
    {
        if (ct == null) return TupleSpace.INDICATE_TUPLE_IS_NULL_STATUS;
        if (this.TS == null) return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
        /* we limit a tuple space to a single control tuple */
        if (!TS.contains(ct))
        {    
            TS.add(ct);
            return TupleSpace.INDICATE_OPERATION_SUCCESS;
            
        } else { return TupleSpace.INDICATE_CONTROL_TUPLE_EXISTS_STATUS; }    
    }

    @Override
    public int append_ContentTuple(ContentTuple_implement ct) 
    {
        if (ct == null) return TupleSpace.INDICATE_TUPLE_IS_NULL_STATUS;
        if (this.TS == null) return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
        /* we limit a tuple space to a single content tuple for DOS reasons */
        if (!TS.contains(ct))
        {    
            TS.add(ct);
            return TupleSpace.INDICATE_OPERATION_SUCCESS;
            
        } else { return TupleSpace.INDICATE_CONTENT_TUPLE_EXISTS_STATUS; } 
    }

    @Override
    public ControlTuple_implement take_ControlTuple() 
    {
        if (this.TS == null) return null;
        //ControlTuple_implement ct = null;
        /* we need to instanciate the class */
        ControlTuple_implement ct = new ControlTuple_implement();
        
        int position = 0;
        
        /* iterate over TS */
        for (Object o : this.TS)
        {   
            if (o != null)
            {  
                /* does not work - we need the actual instanciated class parameter */
                //if (o.getClass().isInstance(ControlTuple_implement.class))
                /* since equals() can not make object comparison of 
                different objects we need to identify a class type based on
                the instance type check - the only possible solution */
                if (o.getClass().isInstance(ct))    
                {
                    //System.out.println("read_ControlTuple(): object class match found! ");
                    /* reuse the existing class instance */
                    ct = (ControlTuple_implement) o;
                    //return (ControlTuple_implement) o;
                    this.TS.remove(position); /* adhere to TS calculus and remove a tuple */
                    return ct;
                }               
            }
            
            position = position+1;
        }
        
        return null;
    }

    @Override
    public ContentTuple_implement take_ContentTuple() 
    {
        if (this.TS == null) return null;
        //ContentTuple_implement ct = null;
        /* we need to instanciate the class */
        ContentTuple_implement ct = new ContentTuple_implement();
        
        int position = 0;
        
        /* iterate over TS */
        for (Object o : this.TS)
        {   
            if (o != null)
            {  
                /* does not work - we need the actual instanciated class parameter */
                //if (o.getClass().isInstance(ContentTuple_implement.class))
                /* since equals() can not make object comparison of 
                different objects we need to identify a class type based on
                the instance type check - the only possible solution */
                if (o.getClass().isInstance(ct))    
                {
                    //System.out.println("read_ControlTuple(): object class match found! ");
                    /* reuse the existing class instance */
                    ct = (ContentTuple_implement) o;
                    //return (ControlTuple_implement) o;
                    this.TS.remove(position); /* adhere to TS calculus and remove a tuple */
                    return ct;
                }               
            }
            
            position = position+1;
        }
     
        return null;
    }
    
}
