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

package org.eclipse.imp.analysis.type.constraints.variables;

import org.eclipse.imp.analysis.ICompilationUnitRange;
import org.eclipse.imp.analysis.constraints.ITermProcessor;
import org.eclipse.imp.analysis.type.constraints.bindings.BindingKeyFactory;
import org.eclipse.imp.analysis.type.constraints.bindings.BindingKeyFactory.BindingKey;
import org.eclipse.imp.analysis.type.constraints.fastrep.TType;

/**
 * Constraint variable that represents a formal parameter of a method.
 */
public class MethodParameterTypeVariable extends TypeConstraintVariable {
    private final BindingKey fMethodKey;
    private final int fIndex;

    public MethodParameterTypeVariable(BindingKey methodKey, int index, TType declaredType, ICompilationUnitRange range, BindingKeyFactory keyFactory) {
        super(keyFactory.getKeyForMethodArgument(methodKey, index), declaredType, range);
        fMethodKey= methodKey;
        fIndex= index;
    }

    /**
     * @return Returns the index.
     */
    public int getIndex() {
        return fIndex;
    }

    /**
     * @return Returns the methodKey.
     */
    public BindingKey getMethodKey() {
        return fMethodKey;
    }

    public boolean isComplexTerm() {
        return false;
    }

    public void processTerms(ITermProcessor processor) { }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Arg(" + fMethodKey.toString() + "," + fIndex + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
