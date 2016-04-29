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
