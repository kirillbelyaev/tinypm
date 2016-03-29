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
public interface Tuple 
{
    /* we use macros to indicate the general method exit codes within the tuple
    implementation */
    public final int INDICATE_CONDITIONAL_EXIT_STATUS = -1;
    public final int INDICATE_OPERATION_SUCCESS = 0;
    
  
    public enum TupleTypes /* we deal with two types of tuples */
    {
        COORDINATION,
        COLLABORATION
    }
    
    public enum TupleTypesNames /* we deal with two types of tuples */
    {
        COORDINATION_TUPLE, /* provides instructions */
        COLLABORATION_TUPLE /* data sharing mechanism */
    }
    
    
}
