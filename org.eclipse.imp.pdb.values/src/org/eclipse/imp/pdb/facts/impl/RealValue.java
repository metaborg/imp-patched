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

package org.eclipse.imp.pdb.facts.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IReal;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

/*package*/ class RealValue extends Value implements IReal {
	private final BigDecimal fValue;

    /*package*/ RealValue(double value) {
        super(TypeFactory.getInstance().realType());
        fValue= BigDecimal.valueOf(value);
    }
    
    /*package*/ RealValue(float value) {
        super(TypeFactory.getInstance().realType());
        fValue= BigDecimal.valueOf(value);
    }
    
    /*package*/ RealValue(BigDecimal value) {
        super(TypeFactory.getInstance().realType());
        fValue= value;
    }
    
    public IReal negate() {
    	return new RealValue(fValue.negate());
    }
    
    public IReal add(IReal other) {
    	return new RealValue(fValue.add(((RealValue) other).fValue));
    }
    
    public IReal subtract(IReal other) {
    	return new RealValue(fValue.subtract(((RealValue) other).fValue));
    }
    
    public IReal multiply(IReal other) {
    	return new RealValue(fValue.multiply(((RealValue) other).fValue));
    }
    
    public IReal divide(IReal other, int precision) {
    	MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		return new RealValue(fValue.divide(((RealValue) other).fValue, mc));
    }
    
    public IReal round() {
    	return new RealValue(fValue.setScale(0, RoundingMode.HALF_UP));
    }
    
    public IReal floor() {
    	return new RealValue(fValue.setScale(0, RoundingMode.FLOOR));
    }
    
    public IInteger toInteger() {
    	return new IntegerValue(fValue.toBigInteger());
    }
    
    public IBool less(IReal other) {
    	return new BoolValue(compare(other) < 0);
    }
    
    public IBool lessEqual(IReal other) {
    	return new BoolValue(compare(other) <= 0);
    }
    
    public IBool greater(IReal other) {
    	return new BoolValue(compare(other) > 0);
    }
    
    public IBool greaterEqual(IReal other) {
    	return new BoolValue(compare(other) >= 0);
    }
    
    public int compare(IReal other) {
    	return fValue.compareTo(((RealValue) other).fValue);
    }
    
    @Override
    public boolean equals(Object o) {
    	if (getClass() == o.getClass()) {
    		return fValue.compareTo(((RealValue) o).fValue) == 0;
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	// Java BigDecimals have a bug, their even though 3.0 and 3.00 are equal,
    	// their hashCode() is not, which is against the equals/hashCode() contract.
    	// To work around this, we use this simple trick here which is correct but
    	// might lead to many collisions.
    	return Double.valueOf(fValue.doubleValue()).hashCode();
    }
    
    public <T> T accept(IValueVisitor<T> v) throws VisitorException {
    	return v.visitReal(this);
    }
    
    public String getStringRepresentation() {
    	StringBuilder sb = new StringBuilder();
		String decimalString = fValue.toString();
		sb.append(decimalString);
		if(decimalString.indexOf(".") == -1) sb.append(".");
		return sb.toString();
    }
    
    public double doubleValue() {
    	return fValue.doubleValue();
    }
    
    public float floatValue() {
    	return fValue.floatValue();
    }
    
    public int precision() {
    	return fValue.precision();
    }
    
    public int scale() {
    	return fValue.scale();
    }
    
    public IInteger unscaled() {
    	return new IntegerValue(fValue.unscaledValue());
    }
}
