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
package edu.csu.lpm.TSLib.interfaces;

/**
 *
 * @author kirill
 */
public interface ContentTuple extends Tuple 
{
    String ID_Field = ""; /* application ID - in fact full path to its executable */
    
    Integer SequenceNumber_Field = null; /* sequence number for the part of the 
    data object. Made Integer instead of an int to be able to hold info about
    chunks for potentially large data objects */
    
    StringBuffer Payload_Field = null; /* could incorporate data object chunks */
    
    public String get_ID_Field();
    
    public int set_ID_Field(String id);
    
    public Integer get_SequenceNumber_Field();
    
    public int set_SequenceNumber_Field(Integer sqn);
    
    public StringBuffer get_Payload_Field();
    
    public int set_Payload_Field(StringBuffer payload);
    
    /* introduce template matching methods inside a tuple */
    public boolean match_on_ID_Field(String id);
    
    public boolean match_on_SequenceNumber_Field(Integer n);
    
}
