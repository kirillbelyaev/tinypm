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
package edu.csu.lpm.TSL.implementation;

import edu.csu.lpm.TSL.interfaces.ContentTuple;
import edu.csu.lpm.TSL.interfaces.ControlTuple;
import edu.csu.lpm.TSL.interfaces.TupleSpace;
import java.util.ArrayList;

/**
 *
 * @author kirill
 */
public class TupleSpace_implement implements TupleSpace 
{
    
    final String TupleSpaceName = "TupleSpace";
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
        if (!this.TS.isEmpty()) return TupleSpace.INDICATE_TUPLE_SPACE_NOT_EMPTY;
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
    public ControlTuple read_ControlTuple() 
    {
        ControlTuple ct = null;
        
        int position = this.TS.indexOf(ct);
        
        if (position != -1)
        { return (ControlTuple) this.TS.get(position); }
        else { return null; }
    }

    @Override
    public ContentTuple read_ContentTuple() 
    {
        ContentTuple ct = null;
        
        int position = this.TS.indexOf(ct);
        
        if (position != -1)
        { return (ContentTuple) this.TS.get(position); }
        else { return null; }
    }

    @Override
    public int append_ControlTuple(ControlTuple ct) 
    {
        /* we limit a tuple space to a single control tuple */
        if (!TS.contains(ct))
        {    
            TS.add(ct);
            return TupleSpace.INDICATE_OPERATION_SUCCESS;
        } else { return TupleSpace.INDICATE_CONTROL_TUPLE_EXISTS_STATUS; }    
    }

    @Override
    public int append_ContentTuple(ContentTuple ct) 
    {
        /* we limit a tuple space to a single content tuple for DOS reasons */
        if (!TS.contains(ct))
        {    
            TS.add(ct);
            return TupleSpace.INDICATE_OPERATION_SUCCESS;
        } else { return TupleSpace.INDICATE_CONTENT_TUPLE_EXISTS_STATUS; } 
    }

    @Override
    public ControlTuple take_ControlTuple() 
    {
        ControlTuple ct = null;
        
        int position = this.TS.indexOf(ct);
        
        if (position != -1)
        {
            ct = (ControlTuple) this.TS.get(position);
            this.TS.remove(position); /* adhere to TS calculus and remove a tuple */
            //return (ControlTuple) this.TS.get(position);
            return ct;
        }
        else { return null; }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContentTuple take_ContentTuple() 
    {
        ContentTuple ct = null;
        
        int position = this.TS.indexOf(ct);
        
        if (position != -1)
        {
            ct = (ContentTuple) this.TS.get(position);
            this.TS.remove(position); /* adhere to TS calculus and remove a tuple */
            //return (ContentTuple) this.TS.get(position); 
            return ct;
        }
        else { return null; }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
