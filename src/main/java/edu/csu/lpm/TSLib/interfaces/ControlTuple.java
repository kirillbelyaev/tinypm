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
public interface ControlTuple extends Tuple 
{
    /* both source/destination ID fields are used for coordination since both fields are set to verify the flow of control messages
    between two endpoints.
    theoretically only source ID is required for collaboration since the requester can specify the request in the request message field.
    Another reason for that - collaboration is mediated through LPM without consent of the other endpoint in contrast to coordination. */
    /* However for verifiability collaboration request requires the requesting 
    app to set both source and destination fields to prove that it knows the destination ID. */
    String SourceID_Field = ""; /* source application ID - in fact full path to its executable */
    
    /* However for verifiability collaboration request requires the requesting 
    app to set both source and destination fields to prove that it knows the destination ID. */
    String DestinationID_Field = ""; /* destination application ID - in fact full path to its executable - necessary for verifiable coordination */
    
    String Type_Field = ""; /* indicates type of communication: coordination/collaboration  */
    
    String RequestMessage_Field = ""; /* could incorporate collaboration/coordination request. 
    For coordination String datatype could still hold a small XML/JSON payload if necessary
    For collaboration a full valid path to the data object is required */
    
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
