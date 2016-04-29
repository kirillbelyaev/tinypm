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

import edu.csu.lpm.TSLib.interfaces.TupleSpace;
import java.util.ArrayList;
import edu.csu.lpm.TSLib.interfaces.VolatileTupleSpace;

/**
 *
 * @author kirill
 */
public class VolatileTupleSpace_implement implements VolatileTupleSpace 
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
    public int count_Tuples() 
    {
        if (this.TS == null) return TupleSpace.INDICATE_TUPLE_SPACE_DOES_NOT_EXIST_STATUS;
        return this.TS.size();
    }

    @Override
    public int count_ControlTuples() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int count_ContentTuples() {
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
