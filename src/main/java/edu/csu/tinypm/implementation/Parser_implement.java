/*
tinyPM Prototype
Kirill Belyaev. Copyright (c) @2015 Colorado State University 
Department of Computer Science, Fort Collins, CO  80523-1873, USA
*/

package edu.csu.tinypm.implementation;

import edu.csu.tinypm.DB.DTO.Record;
import edu.csu.tinypm.DB.exceptions.RecordDAOException;
import edu.csu.tinypm.DB.implementation.DB_Dispatcher;
import edu.csu.tinypm.DB.implementation.RecordDAO_implement;
import edu.csu.tinypm.interfaces.DB_Constants;
import edu.csu.tinypm.interfaces.Parser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirill
 */
public class Parser_implement implements Parser   
{
    
    private StringTokenizer tokenizer = null;
    private ArrayList <String> commandParameters = null;
    private Record rec = null;
    private DB_Dispatcher dd = null;
    private RecordDAO_implement db = null;
    
    private ArrayList <String> ResultOutput = null;
    private int resultSize = -1;
    
    private String ERROR_MESSAGE = null;
    private Enforcer_implement ei = null;
    

    public Parser_implement() 
    {
        this.dd = new DB_Dispatcher();
        this.ResultOutput = new ArrayList();
        this.ERROR_MESSAGE = new String();
        this.ei = new Enforcer_implement();
    }
     
    public String getERROR_MESSAGE() {
        return this.ERROR_MESSAGE;
    }

    private void setERROR_MESSAGE(String m) {
        this.ERROR_MESSAGE = m;
    }
    
    private int getResultSize() 
    {
        return this.resultSize;
    }

    private void setResultSize(int n) 
    {
        this.resultSize = n;
    }
    
    @Override
    public ArrayList getResultOutput() 
    {
        return this.ResultOutput;
    }
    
    private void setResultOutput(ArrayList r) 
    {
        this.ResultOutput = r;
    }
    
    private void refillResultOutput(String r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        this.ResultOutput.add(r);
    }
    
    
    private void refillResultOutput_with_CAP_Attr(Record[] r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        for (int i=0; i < r.length; i++)
            this.ResultOutput.add(r[i].getCAP_Attr());
    }
    
    
    private void refillResultOutput_with_APP_PATH(Record[] r) 
    {
        if (r == null) return;
        this.ResultOutput.clear();
        for (int i=0; i < r.length; i++)
            this.ResultOutput.add(r[i].getApp_PATH());
    }
    
    
    private int obtain_DB_Handler()
    {
        if (this.db == null) //minimize the number of calls - do it only once
        {    
            if (this.dd != null)
            {    
                try 
                {
                        this.db = this.dd.dispatch_DB_Access();
                } catch (SQLException ex) 
                {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (this.db != null) return 0;
        else return -1;   
    }        
    
    
    @Override
    public int parse_and_execute_Command(String e)
    {
        if (e == null) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        
        if (this.obtain_DB_Handler() != 0) return -1;
        
        this.setERROR_MESSAGE("");
        this.refillResultOutput("");
        
        e = e.trim();
        
        if (e.isEmpty() || e.equals("")) 
        {
            this.refillResultOutput("");
            this.setERROR_MESSAGE("");
            return 0;
        } else if (e.equals("\n")) 
        {
            this.refillResultOutput("");
            this.setERROR_MESSAGE("");
               
        } else if (e.indexOf(PM_COMMANDS.COUNT_APP_POLICIES.toString()) == 0) 
        {
            if (this.parse_and_execute_COUNT_APP_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.setERROR_MESSAGE(PM_ERRORS.COUNT_APP_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return -1;
            }    
            
        } else if (e.indexOf(PM_COMMANDS.SHOW_APP_POLICIES.toString()) == 0) 
        {
            if (this.parse_and_execute_SHOW_APP_POLICIES(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.setERROR_MESSAGE(PM_ERRORS.SHOW_APP_POLICIES_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_1.toString());
                return -1;
            }    
            
        } else if (e.indexOf(PM_COMMANDS.SHOW_APPS.toString()) == 0) 
        {
            if (this.parse_and_execute_SHOW_APPS(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.setERROR_MESSAGE(PM_ERRORS.SHOW_APPS_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_NONE.toString());
                return -1;
            }    
        
        }    else if (e.indexOf(PM_COMMANDS.ADD_APP_POLICY.toString()) == 0) 
        {
            if (this.parse_and_execute_ADD_APP_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.setERROR_MESSAGE(PM_ERRORS.ADD_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return -1;
            }
            
        } else if (e.indexOf(PM_COMMANDS.DELETE_APP_POLICY.toString()) == 0) 
        {
            if (this.parse_and_execute_DELETE_APP_POLICY(e) == INDICATE_ARGUMENT_MISMATCH)
            {
                this.setERROR_MESSAGE(PM_ERRORS.DELETE_APP_POLICY_ERROR_NUMBER_OF_ARGUMENTS_SHOULD_BE_2.toString());
                return -1;
            }    
            
        }  else if (e.indexOf(PM_COMMANDS.HELP.toString()) == 0) 
        {
            this.parse_and_execute_HELP(e);
        } else if (e.indexOf(PM_COMMANDS.EXIT.toString()) == 0) 
        {
            return  Parser.INDICATE_IMMEDIATE_EXIT_STATUS;
        } else
        {
            this.parse_and_execute_HELP(e);
        }    
        
        return 0;
    }
    
    private Integer parse_and_execute_COUNT_APP_POLICIES(String e)
    {
        if (e == null || e.isEmpty()) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        Integer count = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                    this.rec.setApp_PATH(this.commandParameters.get(0));
                else return -1;
            } else return -1;
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    count = this.db.countDistinctAppCapRecords(this.rec);
                    this.setResultSize(count);
                    this.refillResultOutput(count.toString());
                    return 0;
                }    
            } catch (RecordDAOException ex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return -1;
    }        
    
    
    private Integer parse_and_execute_SHOW_APP_POLICIES(String e)
    {
        if (e == null || e.isEmpty()) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        Record[] ra = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 2)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 0)
                    this.rec.setApp_PATH(this.commandParameters.get(0));
                else return -1;
            } else return -1;
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    ra = this.db.readRecordsOnAPP(this.rec);
                    if (ra != null)
                    {    
                        this.setResultSize(ra.length);
                        this.refillResultOutput_with_CAP_Attr(ra);
                        return 0;
                    } else return DB_Constants.EMPTY_RESULT;
                }    
            } catch (RecordDAOException ex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return -1;
    }
    
    
    private Integer parse_and_execute_SHOW_APPS(String e)
    {
        if (e == null || e.isEmpty()) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        Record[] ra = null;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 1)
        { 
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    ra = this.db.readRecordsOnAllAPPs();
                    if (ra != null)
                    {    
                        this.setResultSize(ra.length);
                        this.refillResultOutput_with_APP_PATH(ra);
                        return 0;
                    } else return DB_Constants.EMPTY_RESULT;
                }    
            } catch (RecordDAOException ex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return -1;
    }
    
    
    
    private ArrayList<String> get_APP_POLICIES(String app)
    {
        Record[] ra = null;
        ArrayList<String> caps = null;

        if (app == null || app.isEmpty()) return null;

        this.rec.setApp_PATH(app.trim());

        try 
        {//execute the db layer
            if (this.db != null)
            {    
                ra = this.db.readRecordsOnAPP(this.rec);  
            }    
        } catch (RecordDAOException ex) 
        {
            Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (ra != null)
        {    
            caps = new ArrayList<String>();
            for (int i=0; i < ra.length; i++)
                caps.add(ra[i].getCAP_Attr());
        }
        
        return caps;
    }
    
    
    private int check_if_PolicyExists (String app, String p)
    {
        if (app == null || app.isEmpty() || p == null || p.isEmpty()) return -1;
        
        ArrayList<String> caps = this.get_APP_POLICIES(app.trim());
        
        if (caps != null)
            for (int i = 0; i < caps.size(); i++)
                if (caps.get(i).compareTo(p.trim()) == 0) return 0;
        
        return -1;
    }
    
    private ArrayList<String>  return_modified_app_policies (String app, String p, int mode)
    {
        if (app == null || app.isEmpty() || p == null || p.isEmpty()) return null;
        ArrayList<String> caps = null;
        
        if (mode == 1)//add policy
        {    
            if (this.check_if_PolicyExists(app.trim(), p.trim()) == 0) return null;
            caps = this.get_APP_POLICIES(app.trim());
            if (caps != null)
            {    
                caps.add(p.trim());
                caps.add(app.trim());//add the application entry last
            }    
            return caps;
            
        }
        
         if (mode == -1)//remove policy
        {    
            if (this.check_if_PolicyExists(app.trim(), p.trim()) != 0) return null;
            caps = this.get_APP_POLICIES(app.trim());
            if (caps != null) 
            {    
                caps.remove(p.trim());
                caps.add(app.trim());//add the application entry last
            }    
            return caps;
            
        }
    
        return caps;
    }
    
    
    
    private Integer parse_and_execute_ADD_APP_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.rec.setApp_PATH(this.commandParameters.get(1));
                    this.rec.setCAP_Attr(this.commandParameters.get(0));
                }    
                else return -1;
            } else return -1;
            
            if (this.check_if_PolicyExists(this.rec.getApp_PATH(), this.rec.getCAP_Attr()) == 0) return -1; /*//return if policy already exists*/
            
            this.ei.build_EnforcerCMDParameters(this.return_modified_app_policies(this.rec.getApp_PATH(), this.rec.getCAP_Attr(), 1)); //1 indicates add instruction
            
            if (this.ei.execute_CMD() != 0) return -1; //terminate if libcap execution involves error
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.writeRecord(this.rec) != 0) 
                        return -1;
                    else
                    {    
                        this.setResultSize(0);
                        this.refillResultOutput("");
                        return 0;
                    }
                }    
            } catch (RecordDAOException ex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return -1;
    }
    
    private Integer parse_and_execute_DELETE_APP_POLICY(String e)
    {
        if (e == null || e.isEmpty()) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        int num_tokens = this.tokenize_and_build_command_parameters(e.trim());
        //System.out.println("num_tokens is: " + num_tokens);
        if (num_tokens == 3)
        {    
            if (this.commandParameters != null)
            {
                if (this.commandParameters.size() > 1)
                {    
                    this.rec.setApp_PATH(this.commandParameters.get(1));
                    this.rec.setCAP_Attr(this.commandParameters.get(0));
                }    
                else return -1;
            } else return -1;
            
            if (this.check_if_PolicyExists(this.rec.getApp_PATH(), this.rec.getCAP_Attr()) == -1) return -1; //return if policy does not exist
            
            this.ei.build_EnforcerCMDParameters(this.return_modified_app_policies(this.rec.getApp_PATH(), this.rec.getCAP_Attr(), -1)); //-1 indicates remove instruction
            
            if (this.ei.execute_CMD() != 0) return -1; //terminate if libcap execution involves error
            
            try 
            {//execute the db layer
                if (this.db != null)
                {    
                    if (this.db.deleteRecordsOnAPPandCAP(this.rec) != 0) 
                        return -1;
                    else
                    {    
                        this.setResultSize(0);
                        this.refillResultOutput("");
                        return 0;
                    }
                }    
            } catch (RecordDAOException ex) 
            {
                Logger.getLogger(Parser_implement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  else return INDICATE_ARGUMENT_MISMATCH;
        
        return -1;
    }
    
    private void parse_and_execute_HELP(String e)
    {
        if (e == null || e.isEmpty()) return;
        this.setResultSize(0);
        this.refillResultOutput("");
        this.setERROR_MESSAGE(Parser.HELP_MESSAGE);
    }
    
    
    
    private int tokenize_and_build_command_parameters(String e)
    {
        if (e == null || e.isEmpty()) return Parser.INDICATE_INVALID_ARGUMENT_VALUE;
        int count = -1;
        
        if (this.commandParameters == null)
        {    
             this.commandParameters = new ArrayList <String>();
        } else
        {
            this.commandParameters.clear();
        }    
        
        if (this.rec == null) this.rec = new Record();
            
        
        this.tokenizer = new StringTokenizer(e, " ");
        
        count = this.tokenizer.countTokens(); //obtain the number of tokens before cycling through them
        
        this.tokenizer.nextToken();//skip the 1st token
        
        while (this.tokenizer.hasMoreTokens())
        {
            String field = this.tokenizer.nextToken();
            field = field.trim();
            this.commandParameters.add(field);
        }
        
        return count;
    }
    
    
    
    
    
    
    
    
}
