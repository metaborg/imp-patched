/*******************************************************************************
* Copyright (c) CWI 2008 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Jurgen Vinju (jurgenv@cwi.nl) - initial API and implementation

*******************************************************************************/

package org.eclipse.imp.pdb.facts;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;


/**
 * Untyped node representation (a container that has a limited amount of children and a name), 
 * can iterate over the list of children. This data construct can be used to make trees by applying
 * it recursively.
 */
public interface INode extends IValue, Iterable<IValue> {
	/**
	 * Get a child
	 * @param i the zero based index of the child
	 * @return a value
	 */
	public IValue get(int i) throws IndexOutOfBoundsException;
	
	/**
	 * Change this tree to have a different child at a certain position.
	 * 
	 * @param i            the zero based index of the child to be replaced
	 * @param newChild     the new value for the child
	 * @return an untyped tree with the new child at position i, if the receiver was untyped,
	 *         or a typed tree node if it was typed.
	 */
	public INode set(int i, IValue newChild) throws IndexOutOfBoundsException;
	
	/**
	 * @return the (fixed) number of children of this node
	 */
	public int arity();
	
	/**
	 * @return the name of this node (an identifier)
	 */
	public String getName();
	
	/**
	 * @return an iterator over the direct children, equivalent to 'this'.
	 */
	public Iterable<IValue> getChildren();
	
	/**
	 * @return an iterator over the direct children.
	 */
	public Iterator<IValue> iterator();
	
	/**
	 * Get the value of an annotation
	 * 
	 * @param label identifies the annotation
	 * @return a value if the annotation has a value on this node or null otherwise
	 */
	public IValue  getAnnotation(String label) throws FactTypeUseException;
	
	/**
	 * Set the value of an annotation
	 * 
	 * @param label identifies the annotation
	 * @param newValue the new value for the annotation
	 * @return a value if the annotation has a value on this node or null otherwise
	 * @throws FactTypeUseException when the type of the new value is not comparable to the old annotation value
	 */
	public INode   setAnnotation(String label, IValue newValue) throws FactTypeUseException;

	/**
	 * Check whether a certain annotation is set.
	 * 
	 * @param label identifies the annotation
	 * @return true iff the annotation has a value on this node
	 * @throws FactTypeUseException when no annotation with this label is defined for this type of node.
	 */
	public boolean hasAnnotation(String label) throws FactTypeUseException;

	/**
	 * Check whether any annotations are present.
	 */
	public boolean hasAnnotations();
	
	/**
	 * @return a map of annotation labels to annotation values.
	 */
	public Map<String,IValue> getAnnotations();
	
	/**
	 * Overwrites all annotations by replacing them with new ones.
	 * @param annotations 
	 * @return a new node with new annotations
	 */
	public INode setAnnotations(Map<String, IValue> annotations); 
	
	/**
	 * Adds a number of annotations to this value. The given annotations
	 * will overwrite existing ones if the keys are the same.
	 * 
	 * @param annotations 
	 * @return a new node with new annotations
	 */
	public INode joinAnnotations(Map<String, IValue> annotations); 
	
	/**
	 * Remove the annotation identified by key
	 * @param key
	 * @return a new node without the given annotation
	 */
	public INode removeAnnotation(String key);
	
	/**
	 * Remove all annotations on this node
	 * @return a new node without any annotations
	 */
	public INode removeAnnotations();
}
