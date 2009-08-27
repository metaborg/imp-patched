/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Jurgen Vinju (jurgen@vinju.org) - initial API and implementation

*******************************************************************************/
package org.eclipse.imp.pdb.facts.type;



/**
 * ExternalType facilitates a limited form of extensibility to the PDB's type system.
 * It can be used for example to add 'function types' to the PDB. Any such extension
 * to PDB must be a subclass of ExternalType and override isSubTypeOf() and lub().
 * <br>
 * Features such as (de)serialization are NOT supported for values that have an 
 * external type. However, such features will choose a non-failing default behavior,
 * such as silently not printing the value at all.
 * <br> 
 * Note that NORMAL USE OF THE PDB DOES NOT REQUIRE EXTENDING THIS CLASS
 */
public abstract class ExternalType extends Type {

	@Override
	public <T> T accept(ITypeVisitor<T> visitor) {
		return visitor.visitExternal(this);
	}
	
	@Override
	public boolean isExternalType() {
		return true;
	}
	
	@Override
	public boolean isSubtypeOf(Type other) {
		throw new AbstractMethodError("subclasses of ExternalType should override isSubtypeOf");
	}
	
	@Override
	public Type lub(Type other) {
		throw new AbstractMethodError("subclasses of ExternalType should override lub");
	}
	
	@Override
	public boolean equals(Object obj) {
		throw new AbstractMethodError("subclasses of ExternalType should override equals");
	}
	
	@Override
	public int hashCode() {
		throw new AbstractMethodError("subclasses of ExternalType should override hashCode");
	}
}