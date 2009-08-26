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

package org.eclipse.imp.analysis.type.constraints.fastrep;

import org.eclipse.imp.analysis.type.constraints.bindings.ITypeBinding;
import org.eclipse.imp.core.Assert;

public class RawType<T> extends HierarchyType<T> {
    private GenericType fTypeDeclaration;

    protected RawType(TypeEnvironment environment) {
        super(environment);
    }

    protected void initialize(ITypeBinding binding, T typeRep) {
        Assert.isTrue(binding.isRawType());
        super.initialize(binding, typeRep);
        TypeEnvironment environment= getEnvironment();
        fTypeDeclaration= (GenericType) environment.create(binding.getTypeDeclaration());
    }

    public int getKind() {
        return RAW_TYPE;
    }

    public boolean doEquals(TType type) {
        return getTypeRepresentation().equals(((RawType) type).getTypeRepresentation());
    }

    public int hashCode() {
        return getTypeRepresentation().hashCode();
    }

    public TType getTypeDeclaration() {
        return fTypeDeclaration;
    }

    public TType getErasure() {
        return fTypeDeclaration;
    }

    /* package */GenericType getGenericType() {
        return fTypeDeclaration;
    }

    protected boolean doCanAssignTo(TType lhs) {
        int targetType= lhs.getKind();
        switch (targetType) {
        case NULL_TYPE:
            return false;
        case VOID_TYPE:
            return false;
        case PRIMITIVE_TYPE:
            return false;

        case ARRAY_TYPE:
            return false;

        case STANDARD_TYPE:
            return canAssignToStandardType((StandardType) lhs);
        case GENERIC_TYPE:
            return false;
        case PARAMETERIZED_TYPE:
            return isSubType((ParameterizedType) lhs);
        case RAW_TYPE:
            return isSubType((HierarchyType) lhs);

        case UNBOUND_WILDCARD_TYPE:
        case SUPER_WILDCARD_TYPE:
        case EXTENDS_WILDCARD_TYPE:
            return ((WildcardType) lhs).checkAssignmentBound(this);

        case TYPE_VARIABLE:
            return false;
        case CAPTURE_TYPE:
            return ((CaptureType) lhs).checkLowerBound(this);
        }
        return false;
    }

    protected boolean isTypeEquivalentTo(TType other) {
        int otherElementType= other.getKind();
        if (otherElementType == PARAMETERIZED_TYPE || otherElementType == GENERIC_TYPE)
            return getErasure().isTypeEquivalentTo(other.getErasure());
        return super.isTypeEquivalentTo(other);
    }

    public String getName() {
        return FastTypeAdapter.getInstance().getShortName(getTypeRepresentation());
    }

    protected String getPlainPrettySignature() {
        return FastTypeAdapter.getInstance().getFullyQualifiedName(getTypeRepresentation());
    }
}
