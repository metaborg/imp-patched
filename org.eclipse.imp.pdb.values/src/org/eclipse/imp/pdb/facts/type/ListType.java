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

package org.eclipse.imp.pdb.facts.type;

import java.util.Map;

import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;

/*package*/ final class ListType extends Type {
	private final Type fEltType;
	
	/*package*/ ListType(Type eltType) {
		fEltType = eltType;
	}

	@Override
	public Type getElementType() {
		return fEltType;
	}
	
	@Override
	public boolean isListType() {
		return true;
	}
	
	@Override
	public boolean isSubtypeOf(Type other) {
		if (other.isListType()) {
			return fEltType.isSubtypeOf(other.getElementType());
		}
		
		return super.isSubtypeOf(other);
	}

	@Override
	public Type lub(Type o) {
		if (o.isListType()) {
			return TypeFactory.getInstance().listType(fEltType.lub(o.getElementType()));
		}
		
		return super.lub(o);
	}
	
	@Override
	public Type carrier() {
		return TypeFactory.getInstance().setType(fEltType);
	}
	
	@Override
	public String toString() {
		return "list[" + fEltType + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ListType) {
			ListType other = (ListType) o;
			return fEltType == other.fEltType;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 75703 + 104543 * fEltType.hashCode();
	}
	
	@Override
	public <T> T accept(ITypeVisitor<T> visitor) {
		return visitor.visitList(this);
	}

	@Override
	public IValue make(IValueFactory f) {
		return f.list(fEltType);
	}

	@Override
	public IValue make(IValueFactory f, IValue... elems) {
		return f.list(elems);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IListWriter writer(IValueFactory f) {
		return f.listWriter(fEltType);
	}
	
	@Override
	public void match(Type matched, Map<Type, Type> bindings)
			throws FactTypeUseException {
		super.match(matched, bindings);
		getElementType().match(matched.getElementType(), bindings);
	}
	
	@Override
	public Type instantiate(TypeStore store, Map<Type, Type> bindings) {
		return TypeFactory.getInstance().listType(getElementType().instantiate(store, bindings));
	}
}
