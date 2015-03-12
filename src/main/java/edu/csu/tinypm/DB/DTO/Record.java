/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

/*
 Data transfer object
Data transfer object (DTO)[1][2] is an object that carries data between 
processes. The motivation for its use has to do with the fact that 
communication between processes is usually done resorting to remote interfaces.
 */
package edu.csu.tinypm.DB.DTO;

import java.io.Serializable;

/**
 *
 * @author kirill
 */
public class Record implements Serializable
{
    
    String UID = "";
    String APP_PATH = "";
    String CAP_ATTR = "";
    String STATUS = "";
    
public Record()
	{
	}
    

    public String getUID()
    {
        return this.UID;
    }        
    
    public void setUID(Integer uid)
    {
		if (uid != null) this.UID = uid.toString();
    }
    
    public void setUID(String uid)
    {
		if (uid != null)
                        if (!uid.isEmpty())
                            this.UID = uid;
    }
    
    
    
    public String getCAP_Attr()
    {
		return this.CAP_ATTR;
    }
	

    public void setCAP_Attr(String s)
    {
		if (s != null) this.CAP_ATTR = s;
    }
    
    
    public String getApp_PATH()
    {
		return this.APP_PATH;
    }
	

    public void setApp_PATH(String s)
    {
		if (s != null) this.APP_PATH = s;
    } 
    
    
    
    public String getStatus()
    {
		return this.STATUS;
    }
	

    public void setStatus(String s)
    {
	if (s != null) this.STATUS = s;
    }
    
    public void setStatusActive() 
    {
            Integer one = 1;
            this.STATUS = one.toString();
    }

    public void setStatusInactive() 
    {
            Integer zero = 0;
            this.STATUS = zero.toString();
    }
    
     
}
