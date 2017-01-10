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

package edu.csu.lpm.TSLib.Utilities;

import edu.csu.lpm.TSLib.implementation.ContentTuple_implement;
import edu.csu.lpm.TSLib.implementation.PersistentTupleSpace_implement;
import edu.csu.lpm.TSLib.interfaces.PersistentTupleSpace;
import edu.csu.lpm.TSLib.interfaces.TransactionManager;
import edu.csu.lpm.TSLib.interfaces.Tuple;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kirill
 */
public class Utilities 
{
    private PersistentTupleSpace_implement PTS = new PersistentTupleSpace_implement();
    
    /* we reuse the same content tuple both for controller and the requester operations */
    private ContentTuple_implement CNT = new ContentTuple_implement();
    /* keep the sequence number state here */
    private int CurrentSequenceNumber = -1;
    
    
    private boolean Debug = false;
    
    public void setDebug(boolean state)
    {
        this.Debug = state;
    }        
    
    /* This method is a REFERENCE method to emulate the collaborative transaction
    conducted between TS Controller and the requesting service component
    
    Here we emulate the workflow used by TS Controller/requester to deliver the object replica to
    the requesting service component.
    We can not afford reading whole file into memory especially if tens of 
    such collaborative transactions are happening simulteneously. Therefore
    we need to read a file chunk-size at a time and create a corresponding 
    content tuple that has to be written into a corresponding TS. Only if such
    a content tuple is consumed (taken) by the requesting application we should
    advance to the next chunk of the file and prepare a subsequent content tuple.
    
    object_path - absolute path to the data object indicated in the control tuple request field.
    ts_location - base directory where TS of the requester is located.
    id - ID of the requester
    */
    public int create_ObjectReplicaWithDebug(String object_path_in, String object_path_out, String ts_location, String id)
    {
        if (object_path_in == null || object_path_out == null || ts_location == null || id == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (object_path_in.isEmpty() || object_path_out.isEmpty() || ts_location.isEmpty() || id.isEmpty() ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        /* make sure source and destination are not the same */
        if (object_path_in.compareTo(object_path_out) == 0 ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        final int chunkSize = 1024 * 1024; /* 1 MB */
        final int bufferSize = 1024 * 1024; /* 1 MB */
        FileChannel sourceChannel = null;
        StringBuffer Payload = null;
        
        try 
        {
            sourceChannel = new FileInputStream(object_path_in).getChannel();
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
        
        long totalBytesRead = 0; // total bytes read from channel
        long totalBytesWritten = 0; // total bytes written to output
        
        double numberOfChunks = 0;
        
        int outputChunkNumber = 0; // the split file / chunk number
        long outputChunkBytesWritten = 0; // number of bytes written to chunk so far
        
        try 
        {
           numberOfChunks = Math.ceil(sourceChannel.size() / (double) chunkSize);
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        if (this.Debug == true)
        {    
            System.out.println("DEBUG: create_ObjectReplica: estimated numberOfChunks is: " + numberOfChunks);
        } 
        
        try 
        {
            /* start reading file object chunk size at a time */
            for (int bytesRead = sourceChannel.read(buffer); bytesRead != -1; bytesRead = sourceChannel.read(buffer))
            {
                totalBytesRead += bytesRead;
                
                if (this.Debug == true)
                {
                    System.out.println("\n");

                    System.out.println(String.format("DEBUG: create_ObjectReplica: Read %d bytes from channel; total bytes read %d/%d ", bytesRead, totalBytesRead, sourceChannel.size()));

                    System.out.println(String.format("DEBUG: create_ObjectReplica: chunk number is: %d", outputChunkNumber) );
                }
                
                buffer.flip(); /* convert the buffer from writing data to buffer from disk to reading data from buffer mode */
                
                int bytesWrittenFromBuffer = 0; /* number of bytes written from buffer */
                
                if (this.Debug == true)
                {
                    System.out.println("DEBUG: create_ObjectReplica: ENTERING WHILE LOOP.");
                }
                
                while (buffer.hasRemaining())
                {
                  
                    if (Payload == null)
                    {    
                        Payload = new StringBuffer(); /* allocate storage space for a payload field */

                        outputChunkNumber++; /* increment the chunk number */
                        outputChunkBytesWritten = 0;
                    }
                    
                    long chunkBytesFree = (chunkSize - outputChunkBytesWritten); // maxmimum free space in chunk
                    int bytesToWrite = (int) Math.min(buffer.remaining(), chunkBytesFree); // maximum bytes that should be read from current byte buffer
                   
                    if (this.Debug == true)
                    {
                        System.out.println(String.format("DEBUG: create_ObjectReplica: Byte buffer has %d remaining bytes; chunk has %d bytes free; writing up to %d bytes to chunk", buffer.remaining(), chunkBytesFree, bytesToWrite));

                        System.out.println(String.format("DEBUG: create_ObjectReplica: line 100:: bytesWrittenFromBuffer is: %d ; bytesToWrite is: %d ;", bytesWrittenFromBuffer, bytesToWrite));
                    }
                    
                    /* set limit in buffer up to where bytes can be read */
                    buffer.limit(bytesWrittenFromBuffer + bytesToWrite);
 
                    /* append the buffer object as a char buffer to payload */
                    Payload.append(buffer.asCharBuffer());

                    /* set the corresponding content tuple fields */
                    if (this.CNT.set_DestinationID_Field(id) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* set the sequence number to (current - 1) since we prematurely
                    incremented it already when allocated storage space for payload
                    field */
                    if (this.CNT.set_SequenceNumber_Field(outputChunkNumber-1) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    if (this.CNT.set_Payload_Field(Payload) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                    
                    /* now attempt to append the assembled content tuple */
                    if (this.PTS.count_ContentTuples(ts_location) == 0)
                    {    
                        if (this.PTS.append_ContentTuple(this.CNT, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                        }
                        
                    } else
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                    }
                    
                    byte tmp []; /* temporary buffer */
                    tmp = new byte [bytesRead]; /* allocate the size of actual read bytes
                    to avoid buffer underwriting exception */
                    /* emulate the write operation using buffer get() to advance
                    the remaining bytes to zero without a need to create the unnecessary
                    output channel */
                    buffer.get(tmp);
                    
                    tmp = null; /* set to null immediately */
                    
                    /* character buffer reports the length that is twice smaller then 
                    byte buffer - therefore we have to multiply it by two since char takes two bytes */
                    int bytesWritten = this.CNT.get_Payload_Field().capacity() * 2; 
                    
                    if (this.Debug == true)
                    {
                        System.out.println("DEBUG: create_ObjectReplica: bytesWritten is: " + bytesWritten);
                        System.out.println("DEBUG: create_ObjectReplica: Actual Payload field length is: " + this.CNT.get_Payload_Field().length());
                    }
                    
                    outputChunkBytesWritten += bytesWritten;
                    bytesWrittenFromBuffer += bytesWritten;
                    totalBytesWritten += bytesWritten;
                    
                    if (this.Debug == true)
                    {
                        System.out.println(String.format("DEBUG: create_ObjectReplica: bytesWrittenFromBuffer is: %d ; bytesToWrite is: %d ;", bytesWrittenFromBuffer, bytesToWrite));
                        System.out.println(String.format("DEBUG: create_ObjectReplica: outputChunkBytesWritten is: %d ;", outputChunkBytesWritten));
                        System.out.println(String.format("DEBUG: create_ObjectReplica: Byte buffer has %d remaining bytes;", buffer.remaining()));
                        System.out.println(String.format("DEBUG: create_ObjectReplica: Bytes read is: %d ", bytesRead));
                    }
                    
                    buffer.limit(bytesRead); /* reset limit */
                    
                    /* break out of a while loop if all bytes in the file object are written into corresponding
                    chunks and its sum matches the original size of the file object */
                    if (totalBytesWritten == sourceChannel.size())
                    {
                        if (this.Debug == true)
                        {
                            System.out.println("DEBUG: create_ObjectReplica: Finished writing last chunk");
                        }
                        
                        Payload = null;
                        
                        /* emulate the replica assembly for now */
                        System.out.println("\n");
                        System.out.println("assemble_ObjectReplica return value is: " + this.assemble_ObjectReplicaWithDebug(object_path_out, ts_location, id));
     
                        StringBuffer EOF = new StringBuffer();
                        
                        if (this.Debug == true)
                        {
                            System.out.println("DEBUG: create_ObjectReplica: Preparing to append EOF content tuple ");
                        }
                        
                        /* Now, since we have already written all the bytes into replica
                        we have to append one extra content tuple with special meaning to 
                        indicate the EOF (End of File) for the requester. 
                        So let us set the sequence number to -1. 
                        Since we can not set payload to null because the set method does not allow that
                        we can set it to empty string at least to avoid excessive byte transfer */
                        if (this.CNT.set_SequenceNumber_Field(-1) != Tuple.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        }

                        if (this.CNT.set_Payload_Field(EOF) != Tuple.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        }    

                        /* now attempt to append the assembled content tuple */
                        if (this.PTS.count_ContentTuples(ts_location) == 0)
                        {    
                            if (this.PTS.append_ContentTuple(this.CNT, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                            {
                                sourceChannel.close();
                                sourceChannel = null;
                                return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                            }

                        } else
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                        }
                        
                        if (this.Debug == true)
                        {
                            System.out.println("DEBUG: create_ObjectReplica: Finished appending EOF content tuple ");
                        }
                        
                        EOF = null;
                        
                        /* take the EOF content tuple as well */
                        System.out.println("\n");
                        System.out.println("assemble_ObjectReplica return value is: " + this.assemble_ObjectReplicaWithDebug(object_path_out, ts_location, id));
     
                        break;
                        
                    /* if chunk size of bytes is written into payload of a content
                    tuple, indicate the need for a new payload string buffer by setting it to null */    
                    } else if (outputChunkBytesWritten == chunkSize)
                    {
                      if (this.Debug == true)
                      {  
                        System.out.println("DEBUG: create_ObjectReplica: Chunk at capacity; closing()");
                      }
                      
                      Payload = null;
                      
                      /* emulate the replica assembly for now by the requester */
                      System.out.println("\n");
                      System.out.println("assemble_ObjectReplica return value is: " + this.assemble_ObjectReplicaWithDebug(object_path_out, ts_location, id));
                      
                      //return 100; /* test: terminate execution to get the 1st chunk and compare */
                    }
                
                } /* end of while loop */
                    /* clear the buffer for the next iteration of the for loop */
                    buffer.clear();
            } /* end of for loop */
            
            sourceChannel.close();
            sourceChannel = null;
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            
            try 
            {
                sourceChannel.close();
                sourceChannel = null;
            } catch (IOException ex1) 
            {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex1);
                return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
            }
            
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        return TransactionManager.INDICATE_OPERATION_SUCCESS;
    }
    
    /* This method is a REFERENCE method to emulate the collaborative transaction
    conducted between TS Controller and the requesting service component
    
    Here we emulate the workflow used by TS Controller/requester to deliver the object replica to
    the requesting service component.
    We can not afford reading whole file into memory especially if tens of 
    such collaborative transactions are happening simulteneously. Therefore
    we need to read a file chunk-size at a time and create a corresponding 
    content tuple that has to be written into a corresponding TS. Only if such
    a content tuple is consumed (taken) by the requesting application we should
    advance to the next chunk of the file and prepare a subsequent content tuple.
    
    object_path - absolute path to the data object indicated in the control tuple request field.
    ts_location - base directory where TS of the requester is located.
    id - ID of the requester
    */
    public int create_ObjectReplica(String object_path_in, String object_path_out, String ts_location, String id)
    {
        if (object_path_in == null || object_path_out == null || ts_location == null || id == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (object_path_in.isEmpty() || object_path_out.isEmpty() || ts_location.isEmpty() || id.isEmpty() ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        /* make sure source and destination are not the same */
        if (object_path_in.compareTo(object_path_out) == 0 ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        final int chunkSize = 1024 * 1024; /* 1 MB */
        final int bufferSize = 1024 * 1024; /* 1 MB */
        FileChannel sourceChannel = null;
        StringBuffer Payload = null; 
        
        try 
        {
            sourceChannel = new FileInputStream(object_path_in).getChannel();
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
        
        long totalBytesRead = 0; // total bytes read from channel
        long totalBytesWritten = 0; // total bytes written to output
        
        double numberOfChunks = 0;
        
        int outputChunkNumber = 0; // the split file / chunk number
        long outputChunkBytesWritten = 0; // number of bytes written to chunk so far
        
        try 
        {
           numberOfChunks = Math.ceil(sourceChannel.size() / (double) chunkSize);
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        try 
        {
            /* start reading file object chunk size at a time */
            for (int bytesRead = sourceChannel.read(buffer); bytesRead != -1; bytesRead = sourceChannel.read(buffer))
            {
                totalBytesRead += bytesRead;
                
                buffer.flip(); /* convert the buffer from writing data to buffer from disk to reading data from buffer mode */
                
                int bytesWrittenFromBuffer = 0; /* number of bytes written from buffer */
                
                while (buffer.hasRemaining())
                {
                  
                    if (Payload == null)
                    {    
                        Payload = new StringBuffer(); /* allocate storage space for a payload field */

                        outputChunkNumber++; /* increment the chunk number */
                        outputChunkBytesWritten = 0;
                    }
                    
                    long chunkBytesFree = (chunkSize - outputChunkBytesWritten); // maxmimum free space in chunk
                    int bytesToWrite = (int) Math.min(buffer.remaining(), chunkBytesFree); // maximum bytes that should be read from current byte buffer
                   
                    /* set limit in buffer up to where bytes can be read */
                    buffer.limit(bytesWrittenFromBuffer + bytesToWrite);
 
                    /* append the buffer object as a char buffer to payload */
                    Payload.append(buffer.asCharBuffer());

                    /* set the corresponding content tuple fields */
                    if (this.CNT.set_DestinationID_Field(id) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* set the sequence number to (current - 1) since we prematurely
                    incremented it already when allocated storage space for payload
                    field */
                    if (this.CNT.set_SequenceNumber_Field(outputChunkNumber-1) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    if (this.CNT.set_Payload_Field(Payload) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                    
                    /* now attempt to append the assembled content tuple */
                    if (this.PTS.count_ContentTuples(ts_location) == 0)
                    {    
                        if (this.PTS.append_ContentTuple(this.CNT, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                        }
                        
                    } else
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                    }
                    
                    byte tmp []; /* temporary buffer */
                    tmp = new byte [bytesRead]; /* allocate the size of actual read bytes
                    to avoid buffer underwriting exception */
                    /* emulate the write operation using buffer get() to advance
                    the remaining bytes to zero without a need to create the unnecessary
                    output channel */
                    buffer.get(tmp);
                    
                    tmp = null; /* set to null immediately */
                    
                    /* character buffer reports the length that is twice smaller then 
                    byte buffer - therefore we have to multiply it by two since char takes two bytes */
                    int bytesWritten = this.CNT.get_Payload_Field().capacity() * 2; 
                     
                    outputChunkBytesWritten += bytesWritten;
                    bytesWrittenFromBuffer += bytesWritten;
                    totalBytesWritten += bytesWritten;
                    
                    buffer.limit(bytesRead); /* reset limit */
                    
                    /* break out of a while loop if all bytes in the file object are written into corresponding
                    chunks and its sum matches the original size of the file object */
                    if (totalBytesWritten == sourceChannel.size())
                    {   
                        Payload = null;
                        
                        /* emulate the replica assembly for now by the requester */
                        this.assemble_ObjectReplica(object_path_out, ts_location, id);
                        
                        StringBuffer EOF = new StringBuffer();
                        
                        /* Now, since we have already written all the bytes into replica
                        we have to append one extra content tuple with special meaning to 
                        indicate the EOF (End of File) for the requester. 
                        So let us set the sequence number to -1. 
                        Since we can not set payload to null because the set method does not allow that
                        we can set it to empty string at least to avoid excessive byte transfer */
                        if (this.CNT.set_SequenceNumber_Field(-1) != Tuple.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        }

                        if (this.CNT.set_Payload_Field(EOF) != Tuple.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        }    

                        /* now attempt to append the assembled content tuple */
                        if (this.PTS.count_ContentTuples(ts_location) == 0)
                        {    
                            if (this.PTS.append_ContentTuple(this.CNT, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                            {
                                sourceChannel.close();
                                sourceChannel = null;
                                return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                            }

                        } else
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                        }
                        
                        EOF = null;
                        
                        /* take the EOF content tuple as well */
                        this.assemble_ObjectReplica(object_path_out, ts_location, id);
     
                        break;
                        
                    /* if chunk size of bytes is written into payload of a content
                    tuple, indicate the need for a new payload string buffer by setting it to null */    
                    } else if (outputChunkBytesWritten == chunkSize)
                    { 
                      Payload = null;
                      
                      /* emulate the replica assembly for now by the requester */ 
                      this.assemble_ObjectReplica(object_path_out, ts_location, id);
                    }
                
                } /* end of while loop */
                    /* clear the buffer for the next iteration of the for loop */
                    buffer.clear();
            } /* end of for loop */
            
            sourceChannel.close();
            sourceChannel = null;
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            
            try 
            {
                sourceChannel.close();
                sourceChannel = null;
            } catch (IOException ex1) 
            {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex1);
                return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
            }
            
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        return TransactionManager.INDICATE_OPERATION_SUCCESS;
    }
    
    
    /* This method is used in the collaborative transaction
    by the TS Controller to deliver the object replica to the requesting
    service component.
    We can not afford reading whole file into memory especially if tens of 
    such collaborative transactions are happening simulteneously. Therefore
    we need to read a file chunk-size at a time and create a corresponding 
    content tuple that has to be written into a corresponding TS. Only if such
    a content tuple is consumed (taken) by the requesting application we should
    advance to the next chunk of the file and prepare a subsequent content tuple.
    
    object_path - absolute path to the data object indicated in the control tuple request field.
    ts_location - base directory where TS of the requester is located.
    id - ID of the requester
    */
    public int fragment_ObjectReplica(String object_path, String ts_location, String id)
    {
        if (object_path == null || ts_location == null || id == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (object_path.isEmpty() || ts_location.isEmpty() || id.isEmpty() ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        final int chunkSize = 1024 * 1024; /* 1 MB */
        final int bufferSize = 1024 * 1024; /* 1 MB */
        FileChannel sourceChannel = null;
        StringBuffer Payload = null;
        
        try 
        {
            sourceChannel = new FileInputStream(object_path).getChannel();
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
        
        long totalBytesRead = 0; // total bytes read from channel
        long totalBytesWritten = 0; // total bytes written to output
        
        double numberOfChunks = 0;
        
        int outputChunkNumber = 0; // the split file / chunk number
        long outputChunkBytesWritten = 0; // number of bytes written to chunk so far
        
        try 
        {
           numberOfChunks = Math.ceil(sourceChannel.size() / (double) chunkSize);
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        try 
        {
            /* start reading file object chunk size at a time */
            for (int bytesRead = sourceChannel.read(buffer); bytesRead != -1; bytesRead = sourceChannel.read(buffer))
            {
                totalBytesRead += bytesRead;
                
                buffer.flip(); /* convert the buffer from writing data to buffer from disk to reading data from buffer mode */
                
                int bytesWrittenFromBuffer = 0; /* number of bytes written from buffer */
                
                while (buffer.hasRemaining())
                {
                  
                    if (Payload == null)
                    {    
                        Payload = new StringBuffer(); /* allocate storage space for a payload field */

                        outputChunkNumber++; /* increment the chunk number */
                        outputChunkBytesWritten = 0;
                    }
                    
                    long chunkBytesFree = (chunkSize - outputChunkBytesWritten); // maxmimum free space in chunk
                    int bytesToWrite = (int) Math.min(buffer.remaining(), chunkBytesFree); // maximum bytes that should be read from current byte buffer
                   
                    /* set limit in buffer up to where bytes can be read */
                    buffer.limit(bytesWrittenFromBuffer + bytesToWrite);
 
                    /* append the buffer object as a char buffer to payload */
                    Payload.append(buffer.asCharBuffer());

                    /* set the corresponding content tuple fields */
                    if (this.CNT.set_DestinationID_Field(id) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                    /* set the sequence number to (current - 1) since we prematurely
                    incremented it already when allocated storage space for payload
                    field */
                    if (this.CNT.set_SequenceNumber_Field(outputChunkNumber-1) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }
                        
                    if (this.CNT.set_Payload_Field(Payload) != Tuple.INDICATE_OPERATION_SUCCESS)
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    }    
                    
                    /* SLEEP to allow the requester to consume the content tuple
                    Note - we introduce the sleep interval for the controller
                    but do not use it in the assemble_ObjectReplica() for the
                    requester since we want to maximize the throughput for
                    requester without introducing further delays in the 
                    transactional flow */
                    try 
                    {
                        Thread.sleep(TransactionManager.SHORT_SLEEP_INTERVAL); /* 5 milliseconds */
                    } catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    /* now attempt to append the assembled content tuple */
                    if (this.PTS.count_ContentTuples(ts_location) == 0)
                    {    
                        if (this.PTS.append_ContentTuple(this.CNT, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                        }
                        
                    } else
                    {
                        sourceChannel.close();
                        sourceChannel = null;
                        return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                    }
                    
                    byte tmp []; /* temporary buffer */
                    tmp = new byte [bytesRead]; /* allocate the size of actual read bytes
                    to avoid buffer underwriting exception */
                    /* emulate the write operation using buffer get() to advance
                    the remaining bytes to zero without a need to create the unnecessary
                    output channel */
                    buffer.get(tmp);
                    
                    tmp = null; /* set to null immediately */
                    
                    /* character buffer reports the length that is twice smaller then 
                    byte buffer - therefore we have to multiply it by two since char takes two bytes */
                    int bytesWritten = this.CNT.get_Payload_Field().capacity() * 2; 
                     
                    outputChunkBytesWritten += bytesWritten;
                    bytesWrittenFromBuffer += bytesWritten;
                    totalBytesWritten += bytesWritten;
                    
                    buffer.limit(bytesRead); /* reset limit */
                    
                    /* break out of a while loop if all bytes in the file object are written into corresponding
                    chunks and its sum matches the original size of the file object */
                    if (totalBytesWritten == sourceChannel.size())
                    {   
                        Payload = null;
                        
                        StringBuffer EOF = new StringBuffer();
                        
                        /* Now, since we have already written all the bytes into replica
                        we have to append one extra content tuple with special meaning to 
                        indicate the EOF (End of File) for the requester. 
                        So let us set the sequence number to -1. 
                        Since we can not set payload to null because the set method does not allow that
                        we can set it to empty string at least to avoid excessive byte transfer */
                        if (this.CNT.set_SequenceNumber_Field(-1) != Tuple.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        }

                        if (this.CNT.set_Payload_Field(EOF) != Tuple.INDICATE_OPERATION_SUCCESS)
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        }    

                        /* SLEEP to allow the requester to consume the content tuple
                        Note - we introduce the sleep interval for the controller
                        but do not use it in the assemble_ObjectReplica() for the
                        requester since we want to maximize the throughput for
                        requester without introducing further delays in the 
                        transactional flow */
                        try 
                        {
                            Thread.sleep(TransactionManager.SHORT_SLEEP_INTERVAL); /* 5 milliseconds */
                        } catch (InterruptedException ex) 
                        {
                            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        /* now attempt to append the assembled content tuple */
                        if (this.PTS.count_ContentTuples(ts_location) == 0)
                        {    
                            if (this.PTS.append_ContentTuple(this.CNT, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                            {
                                sourceChannel.close();
                                sourceChannel = null;
                                return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                            }

                        } else
                        {
                            sourceChannel.close();
                            sourceChannel = null;
                            return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                        }
                        
                        EOF = null;
                        
                        break;
                        
                    /* if chunk size of bytes is written into payload of a content
                    tuple, indicate the need for a new payload string buffer by setting it to null */    
                    } else if (outputChunkBytesWritten == chunkSize)
                    { 
                      Payload = null;
                    }
                
                } /* end of while loop */
                    /* clear the buffer for the next iteration of the for loop */
                    buffer.clear();
            } /* end of for loop */
            
            sourceChannel.close();
            sourceChannel = null;
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            
            try 
            {
                sourceChannel.close();
                sourceChannel = null;
            } catch (IOException ex1) 
            {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex1);
                return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
            }
            
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        return TransactionManager.INDICATE_OPERATION_SUCCESS;
    }
    
  
    /* This method is used by the service component requesting the object replica
    from the TS Controller.
    replica is recreated by assembling all chunks in received content tuples    
    
    object_path - absolute path to the location where replica of the requested 
    data object indicated in the control tuple request field should be assembled.
    ts_location - base directory where TS of the requester is located.
    id - ID of the requester.
    */
    public int assemble_ObjectReplicaWithDebug(String object_path, String ts_location, String id)
    {
        if (object_path == null || ts_location == null || id == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (object_path.isEmpty() || ts_location.isEmpty() || id.isEmpty() ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        final int chunkSize = 1024 * 1024; /* 1 MB */
        FileChannel outputChannel = null;
        
        try 
        {
            /* indicate the append mode for the file by setting second boolean parameter to true */
            outputChannel = new FileOutputStream(object_path, true).getChannel();
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(chunkSize);        
                          
        try 
        {
            /* start reading replica object chunk size at a time from appended content tuples */
            for (;;)
            {
            /* now attempt to take the appended content tuple */
                if (this.PTS.count_ContentTuples(ts_location) == 1)
                {   
                    this.CNT = this.PTS.take_ContentTuple(ts_location);
                    if (this.CNT == null)
                    {
                        outputChannel.close();
                        outputChannel = null;
                        return TransactionManager.INDICATE_CONTENT_TUPLE_NOT_PRESENT_STATUS;
                    }
                    
                    if (this.Debug == true)
                    {
                        System.out.println("DEBUG: assemble_ObjectReplica: CNT is not null. ");
                    }
                    
                } else
                {
                    outputChannel.close();
                    outputChannel = null;
                    return TransactionManager.INDICATE_CONTENT_TUPLE_NOT_PRESENT_STATUS;
                }
                
                /* if sequence number = -1 - TS controller indicates the EOF */
                if (this.CNT.match_on_SequenceNumber_Field(-1))
                {
                    
                    if (this.Debug == true)
                    {
                        System.out.println("DEBUG: assemble_ObjectReplica: SequenceNumber is: " + this.CNT.get_SequenceNumber_Field());
                        System.out.println("DEBUG: assemble_ObjectReplica: Received EOF content tuple. Exiting the loop. ");
                    }
                    
                    break; /* exit for loop*/
                }
                    
                if (this.Debug == true)
                {
                    System.out.println("DEBUG: assemble_ObjectReplica: SequenceNumber is: " + this.CNT.get_SequenceNumber_Field());
                }
                
                /* compare the current sequence number with the one in the content
                tuple - whether it is exactly a successive value */
                if (this.CNT.get_SequenceNumber_Field() == this.CurrentSequenceNumber+1)
                {   
                    /* update the sequence number state */
                    this.CurrentSequenceNumber = this.CNT.get_SequenceNumber_Field();
                
                /* if sequence number is not successive - terminate execution 
                we might be dealing with incorrectly ordered replica data stream */    
                } else 
                {
                    outputChannel.close();
                    outputChannel = null;
                    return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS;
                }
                
                /* if IDs do not match - terminate execution */
                if (this.CNT.match_on_DestinationID_Field(id) != true)
                {
                    outputChannel.close();
                    outputChannel = null;
                    return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS;
                }
                
                char ctmp []; /* temporary char buffer */
                ctmp = new char [this.CNT.get_Payload_Field().length()]; /* allocate the size of Payload field */
                
                /* write characters to allocated char buffer from payload field */    
                this.CNT.get_Payload_Field().getChars(0, this.CNT.get_Payload_Field().length(), ctmp, 0);
                
                if (this.Debug == true)
                {
                    System.out.println("DEBUG: assemble_ObjectReplica: ctmp length is: " + ctmp.length);
                }
                
                /* copy characters from character buffer to byte buffer */
                for (int c = 0; c < ctmp.length; c++)
                {    
                    buffer.putChar(ctmp[c]);
                }
               
                ctmp = null;
                
                buffer.flip(); /* convert the buffer from writing data to buffer from disk to reading data from buffer mode */
                
                if (this.Debug == true)
                {
                    System.out.println(String.format("DEBUG: assemble_ObjectReplica: Byte buffer has %d remaining bytes;", buffer.remaining()));
                }
                
                /* character buffer reports the length that is twice smaller then 
                byte buffer - therefore we have to multiply it by two */
                int bytesToWrite = this.CNT.get_Payload_Field().capacity() * 2; 
                
                if (this.Debug == true)
                {
                    System.out.println("DEBUG: assemble_ObjectReplica: bytesToWrite is: " + bytesToWrite);
                }
                
                /* set limit in buffer up to where bytes can be read */
                buffer.limit(bytesToWrite);
                
                /* write to file */
                int bytesWritten = outputChannel.write(buffer);
                
                if (this.Debug == true)
                {
                    System.out.println("DEBUG: assemble_ObjectReplica: bytesWritten is: " + bytesWritten);
                    System.out.println("DEBUG: assemble_ObjectReplica: Actual Payload field length is: " + this.CNT.get_Payload_Field().length());
                    System.out.println(String.format("DEBUG: assemble_ObjectReplica: Byte buffer has %d remaining bytes;", buffer.remaining()));
                }
                
                /* clear the buffer for the next iteration of the for loop */
                buffer.clear();
        
            } /* end of for loop */
            
            outputChannel.close();
            outputChannel = null;
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            try 
            {
                outputChannel.close();
                outputChannel = null;
            } catch (IOException ex1) 
            {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex1);
                return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
            }
            
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        return TransactionManager.INDICATE_OPERATION_SUCCESS;
    }
    
    /* This method is used by the service component requesting the object replica
    from the TS Controller.
    replica is recreated by assembling all chunks in received content tuples    
    
    object_path - absolute path to the location where replica of the requested 
    data object indicated in the control tuple request field should be assembled.
    ts_location - base directory where TS of the requester is located.
    id - ID of the requester.
    */
    public int assemble_ObjectReplica(String object_path, String ts_location, String id)
    {
        if (object_path == null || ts_location == null || id == null) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        if (object_path.isEmpty() || ts_location.isEmpty() || id.isEmpty() ) return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
        
        final int chunkSize = 1024 * 1024; /* 1 MB */
        FileChannel outputChannel = null;
        
        try 
        {
            /* indicate the append mode for the file by setting second boolean parameter to true */
            outputChannel = new FileOutputStream(object_path, true).getChannel();
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(chunkSize);        
                          
        try 
        {
            /* start reading replica object chunk size at a time from appended content tuples */
            for (;;)
            {
            /* now attempt to take the appended content tuple */
                if (this.PTS.count_ContentTuples(ts_location) == 1)
                {   
                    this.CNT = this.PTS.take_ContentTuple(ts_location);
                    
                    if (this.CNT == null)
                    {
                        outputChannel.close();
                        outputChannel = null;
                        return TransactionManager.INDICATE_CONTENT_TUPLE_NOT_PRESENT_STATUS;
                    }
                    
                } else
                {
                    outputChannel.close();
                    outputChannel = null;
                    return TransactionManager.INDICATE_CONTENT_TUPLE_NOT_PRESENT_STATUS;
                }
                
                /* if sequence number = -1 - TS controller indicates the EOF */
                if (this.CNT.match_on_SequenceNumber_Field(-1))
                {    
                    break; /* exit for loop*/
                }
                
                /* compare the current sequence number with the one in the content
                tuple - whether it is exactly a successive value */
                if (this.CNT.get_SequenceNumber_Field() == this.CurrentSequenceNumber+1)
                {   
                    /* update the sequence number state */
                    this.CurrentSequenceNumber = this.CNT.get_SequenceNumber_Field();
                
                /* if sequence number is not successive - terminate execution 
                we might be dealing with incorrectly ordered replica data stream */    
                } else 
                {
                    outputChannel.close();
                    outputChannel = null;
                    return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS;
                }
                
                /* if destination IDs do not match - terminate execution */
                if (this.CNT.match_on_DestinationID_Field(id) != true)
                {
                    outputChannel.close();
                    outputChannel = null;
                    return TransactionManager.INDICATE_FIELDS_VALIDATION_FAILED_STATUS;
                }
                
                char ctmp []; /* temporary char buffer */
                ctmp = new char [this.CNT.get_Payload_Field().length()]; /* allocate the size of Payload field */
                
                /* write characters to allocated char buffer from payload field */    
                this.CNT.get_Payload_Field().getChars(0, this.CNT.get_Payload_Field().length(), ctmp, 0);
                
                /* copy characters from character buffer to byte buffer */
                for (int c = 0; c < ctmp.length; c++)
                {    
                    buffer.putChar(ctmp[c]);
                }
               
                ctmp = null;
                
                buffer.flip(); /* convert the buffer from writing data to buffer from disk to reading data from buffer mode */
                
                /* character buffer reports the length that is twice smaller then 
                byte buffer - therefore we have to multiply it by two */
                int bytesToWrite = this.CNT.get_Payload_Field().capacity() * 2; 
                
                /* set limit in buffer up to where bytes can be read */
                buffer.limit(bytesToWrite);
                
                /* write to file */
                int bytesWritten = outputChannel.write(buffer);
                
                /* clear the buffer for the next iteration of the for loop */
                buffer.clear();
        
            } /* end of for loop */
            
            outputChannel.close();
            outputChannel = null;
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            try 
            {
                outputChannel.close();
                outputChannel = null;
            } catch (IOException ex1) 
            {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex1);
                return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
            }
            
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        return TransactionManager.INDICATE_OPERATION_SUCCESS;
    }
    
}
