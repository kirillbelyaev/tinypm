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
package edu.csu.lpm.TSL.interfaces;

/**
 *
 * @author kirill
 */
public interface ControlTuple extends Tuple 
{
    String ID = null; /* application ID - in fact full path to its executable */
    
    String Type = null;
    
    String RequestMessage = null; /* could incorporate collaboration/coordination request. 
    For coordination String datatype could still hold a small XML payload if necessary */
    
    public String get_ID();
    
    public String set_ID(String id);
    
    public String get_Type();
    
    public String set_Type(String type);
    
    public String get_RequestMessage();
    
    public String set_RequestMessage(String rqm);
    
    //String Type = TupleTypes.COLLABORATION.toString();
    
    //TupleTypes types = null;
    
}
