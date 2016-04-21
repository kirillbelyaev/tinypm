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
public interface ControlTuple extends Tuple 
{
    /* both source/destination ID fields are used for coordination since both fields are set to verify the flow of control messages
    between two endpoints.
    theoretically only source ID is required for collaboration since the requester can specify the request in the request message field.
    Another reason for that - collaboration is mediated through LPM without consent of the other endpoint in contrast to coordination. */
    String SourceID_Field = ""; /* source application ID - in fact full path to its executable */
    
    String DestinationID_Field = ""; /* destination application ID - in fact full path to its executable */
    
    String Type_Field = ""; /* indicates type of communication: coordination/collaboration  */
    
    String RequestMessage_Field = ""; /* could incorporate collaboration/coordination request. 
    For coordination String datatype could still hold a small XML payload if necessary */
    
    public String get_SourceID_Field();
    
    public int set_SourceID_Field(String id);
    
    public String get_DestinationID_Field();
    
    public int set_DestinationID_Field(String id);
    
    public String get_Type_Field();
    
    public void set_Type_Field_to_Coordination();
    
    public void set_Type_Field_to_Collaboration();
    
    public String get_RequestMessage_Field();
    
    public int set_RequestMessage_Field(String rqm);
    
    /* introduce template matching methods inside a tuple */
    public boolean match_on_SourceID_Field(String id);
    
    public boolean match_on_DestinationID_Field(String id);
    
    public boolean match_on_Type_Field(String type);
    
    public boolean match_on_RequestMessage_Field(String rqm);
    
}
