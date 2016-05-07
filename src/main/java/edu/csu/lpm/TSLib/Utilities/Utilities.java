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
    private ContentTuple_implement cnt = new ContentTuple_implement();
    
    /* This method is used by TS Controller to deliver the object replica to
    the requesting service component.
    We can not afford reading whole file into memory especially if tens of 
    such collaborative transactions are happening simulteneously. Therefore
    we need to read a file chunk-size at a time and create a corresponding 
    content tuple that has to be written into a corresponding TS. Only if such
    a content tuple is consumed (taken) by the requesting application we should
    advance to the next chunk of the file and prepare a subsequent content tuple. */
    public int create_ObjectReplica(String object_path, String ts_location, String id)
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
        
        System.out.println("estimated numberOfChunks is: " + numberOfChunks);
                          
        try 
        {
            /* start reading file object chunk size at a time */
            for (int bytesRead = sourceChannel.read(buffer); bytesRead != -1; bytesRead = sourceChannel.read(buffer))
            {
                totalBytesRead += bytesRead;
                
                System.out.println("\n");
                
                System.out.println(String.format("Read %d bytes from channel; total bytes read %d/%d ", bytesRead, totalBytesRead, sourceChannel.size()));
                
                System.out.println(String.format("chunk number is: %d", outputChunkNumber) );
                
                buffer.flip(); /* convert the buffer from writing data to buffer from disk to reading mode */
                
                int bytesWrittenFromBuffer = 0; /* number of bytes written from buffer */
                
                System.out.println("ENTERING WHILE LOOP.");
                
                while (buffer.hasRemaining())
                {
                  
                    if (Payload == null)
                    {    
                        Payload = new StringBuffer(); 

                        outputChunkNumber++;
                        outputChunkBytesWritten = 0;
                    }
                    
                    long chunkBytesFree = (chunkSize - outputChunkBytesWritten); // maxmimum free space in chunk
                    int bytesToWrite = (int) Math.min(buffer.remaining(), chunkBytesFree); // maximum bytes that should be read from current byte buffer
                   
                    System.out.println(String.format("Byte buffer has %d remaining bytes; chunk has %d bytes free; writing up to %d bytes to chunk", buffer.remaining(), chunkBytesFree, bytesToWrite));
                    
                    System.out.println(String.format("line 100:: bytesWrittenFromBuffer is: %d ; bytesToWrite is: %d ;", bytesWrittenFromBuffer, bytesToWrite));
                    
                    buffer.limit(bytesWrittenFromBuffer + bytesToWrite); // set limit in buffer up to where bytes can be read
 
                    /* append the buffer object as a char buffer to payload */
                    Payload.append(buffer.asCharBuffer());

                    /* set the corresponding content tuple fields */
                    if (this.cnt.set_ID_Field(id) != Tuple.INDICATE_OPERATION_SUCCESS)
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    
                    if (this.cnt.set_SequenceNumber_Field(outputChunkNumber) != Tuple.INDICATE_OPERATION_SUCCESS)
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                        
                    if (this.cnt.set_Payload_Field(Payload) != Tuple.INDICATE_OPERATION_SUCCESS)
                        return TransactionManager.INDICATE_CONDITIONAL_EXIT_STATUS;
                    
                    /* now attempt to append the assembled content tuple */
                    if (this.PTS.append_ContentTuple(this.cnt, ts_location) != PersistentTupleSpace.INDICATE_OPERATION_SUCCESS)
                        return TransactionManager.INDICATE_APPEND_CONTENT_TUPLE_FAILED_STATUS;
                    
                    //int bytesWritten = buffer.capacity();
                    //int bytesWritten = outputChannel.write(buffer);
                    
                    byte tmp []; /* temporary buffer */
                    tmp = new byte [bytesRead]; /* allocate the size of actual read bytes
                    to avoid buffer underwriting exception */
                    /* emulate the write operation using buffer get() to advance
                    the remaining bytes to zero without a need to create the unnecessary
                    output channel */
                    buffer.get(tmp);
                    
                    tmp = null; /* set to null immediately */
                    
                    /* character buffer reports the length that is twice smaller then 
                    byte buffer - therefore we have to multiply it by two */
                    int bytesWritten = this.cnt.get_Payload_Field().capacity() * 2; 
                    
                    System.out.println("bytesWritten is: " + bytesWritten);
                    System.out.println("Actual Payload field length is: " + this.cnt.get_Payload_Field().length());
                    
                    outputChunkBytesWritten += bytesWritten;
                    bytesWrittenFromBuffer += bytesWritten;
                    totalBytesWritten += bytesWritten;
                    
                    System.out.println(String.format("bytesWrittenFromBuffer is: %d ; bytesToWrite is: %d ;", bytesWrittenFromBuffer, bytesToWrite));
                    System.out.println(String.format("outputChunkBytesWritten is: %d ;", outputChunkBytesWritten));
                    System.out.println(String.format("Byte buffer has %d remaining bytes;", buffer.remaining()));
                    System.out.println(String.format("Bytes read is: %d ", bytesRead));
                    
                    buffer.limit(bytesRead); /* reset limit */
                    
                    /* break out of a while loop if all bytes in the file object are written into corresponding
                    chunks and its sum matches the original size of the file object */
                    if (totalBytesWritten == sourceChannel.size())
                    {
                        System.out.println("Finished writing last chunk");
                        Payload = null;
                        break;
                    /* if chunk size of bytes is written into payload of a content
                    tuple, indicate the need for a new payload string buffer by setting it to null */    
                    } else if (outputChunkBytesWritten == chunkSize)
                    {
                      System.out.println("Chunk at capacity; closing()");
                      Payload = null;
                      /* emulate the replica assembly for now */
                      this.PTS.take_ContentTuple(ts_location);
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
            return TransactionManager.INDICATE_EXCEPTION_OCCURRENCE_STATUS;
        }
        
        return TransactionManager.INDICATE_OPERATION_SUCCESS;
    }
    
}
