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
public class Apps_Table_Record implements Serializable
{
    
    private String COLUMN_APP_DESC = "";
    private String COLUMN_APP_PATH = "";
    private String COLUMN_POLICY_CLASS_ID = "";
    private String COLUMN_APP_CONTAINER_ID = "";
    private String COLUMN_STATUS = "";
    
public Apps_Table_Record()
	{
            
	}
    
    
    public String getCOLUMN_APP_DESC() {
        return this.COLUMN_APP_DESC;
    }

    public void setCOLUMN_APP_DESC(String COLUMN_APP_DESC) {
        this.COLUMN_APP_DESC = COLUMN_APP_DESC;
    }

    public String getCOLUMN_APP_PATH() {
        return this.COLUMN_APP_PATH;
    }

    public void setCOLUMN_APP_PATH(String COLUMN_APP_PATH) {
        this.COLUMN_APP_PATH = COLUMN_APP_PATH;
    }

    public String getCOLUMN_POLICY_CLASS_ID() {
        return this.COLUMN_POLICY_CLASS_ID;
    }

    public void setCOLUMN_POLICY_CLASS_ID(String COLUMN_POLICY_CLASS_ID) {
        this.COLUMN_POLICY_CLASS_ID = COLUMN_POLICY_CLASS_ID;
    }

    public String getCOLUMN_APP_CONTAINER_ID() {
        return this.COLUMN_APP_CONTAINER_ID;
    }

    public void setCOLUMN_APP_CONTAINER_ID(String COLUMN_APP_CONTAINER_ID) {
        this.COLUMN_APP_CONTAINER_ID = COLUMN_APP_CONTAINER_ID;
    }

    public String getCOLUMN_STATUS() {
        return this.COLUMN_STATUS;
    }

    public void setCOLUMN_STATUS(String COLUMN_STATUS) {
        this.COLUMN_STATUS = COLUMN_STATUS;
    }
    
    
    
     public void setStatusActive() 
    {
            Integer one = 1;
            this.COLUMN_STATUS = one.toString();
    }

    public void setStatusInactive() 
    {
            Integer zero = 0;
            this.COLUMN_STATUS = zero.toString();
    }

    
     
}
