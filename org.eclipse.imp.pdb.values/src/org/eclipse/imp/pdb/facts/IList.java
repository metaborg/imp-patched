/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation

*******************************************************************************/

package org.eclipse.imp.pdb.facts;

import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.type.Type;

public interface IList extends Iterable<IValue>, IValue {
	/**
	 * @return the type of the elements in the list
	 */
    public Type getElementType();
    
    /**
     * @return the number of elements in the list
     */
    public int length();
    
    /**
     * @return a new list with all elements in reverse order
     */
    public IList reverse();
    
    /**
     * Appends an element to the end of the list
     * 
     * @param e the new element
     * @return a new list with the element at the end
     */
    public IList append(IValue e);
    
    /**
     * Insers an element in front of the list
     * @param e the new element
     * @return a new list with the element in front
     */
    public IList insert(IValue e);
    
    /**
     * Concatenates this list with another
     * @param o another list
     * @return a concatenated list with the elements of the 
     *         receiver before the elements of o.
     */
    public IList concat(IList o);
    
    /**
     * Replaces the value of the ith element in the list with a new value
     * @param i index to replace a value at.
     * @param e the new value to replace the old one
     * @return a new list with the element replaced
     * @throws FactTypeUseException when the type of the element is not a subtype of the element type
     * @throws IndexOutOfBoundsException when the i < 0 or i >= IList.length()
     */
    public IList put(int i, IValue e) throws FactTypeUseException, IndexOutOfBoundsException;
    
    /**
     * Return the ith element of the list.
     * @param i
     * @return the ith element of the list
     * @throws IndexOutOfBoundsException when i < 0 or i >= IList.length
     */
    public IValue get(int i) throws IndexOutOfBoundsException;
    
    /**
     * Compute a sublist.
     * 
     * @param offset inclusive start index of the sublist
     * @param length number of elements in the resulting list
     * @return a new list that contains this[offset] until this[offset+length-1].
     */
    public IList sublist(int offset, int length);
    
    /**
     * @return true iff the list is non-empty
     */
    public boolean isEmpty();
    
    /**
     * @param e
     * @return true iff e is an element of the list
     */
    public boolean contains(IValue e);
    
    /**
     * Removes the first occurrence of an element, i.e. the
     * element with the lowest index that is present in the list,
     * if present at all.
     * @param e
     * @return a new list, with one element removed.
     */
    public IList delete(IValue e);
    
    /**
     * Removes the element at index <code>i</code>.
     * 
     * @param i
     * @return a new list with one element removed.
     */
    public IList delete(int i);
}
