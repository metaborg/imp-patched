
////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2007 IBM Corporation.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
//Contributors:
//    Philippe Charles (pcharles@us.ibm.com) - initial API and implementation

////////////////////////////////////////////////////////////////////////////////

package org.eclipse.imp.asl.parser.ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;

/**
 *<b>
 *<li>Rule 97:  intersectExpr ::= value intersect value
 *</b>
 */
public class intersectExpr extends ASTNode implements IintersectExpr
{
    private Ivalue _value;
    private ASTNodeToken _intersect;
    private Ivalue _value3;

    public Ivalue getvalue() { return _value; }
    public ASTNodeToken getintersect() { return _intersect; }
    public Ivalue getvalue3() { return _value3; }

    public intersectExpr(IToken leftIToken, IToken rightIToken,
                         Ivalue _value,
                         ASTNodeToken _intersect,
                         Ivalue _value3)
    {
        super(leftIToken, rightIToken);

        this._value = _value;
        ((ASTNode) _value).setParent(this);
        this._intersect = _intersect;
        ((ASTNode) _intersect).setParent(this);
        this._value3 = _value3;
        ((ASTNode) _value3).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_value);
        list.add(_intersect);
        list.add(_value3);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof intersectExpr)) return false;
        if (! super.equals(o)) return false;
        intersectExpr other = (intersectExpr) o;
        if (! _value.equals(other._value)) return false;
        if (! _intersect.equals(other._intersect)) return false;
        if (! _value3.equals(other._value3)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_value.hashCode());
        hash = hash * 31 + (_intersect.hashCode());
        hash = hash * 31 + (_value3.hashCode());
        return hash;
    }

    public void accept(IAstVisitor v)
    {
        if (! v.preVisit(this)) return;
        enter((Visitor) v);
        v.postVisit(this);
    }

    public void enter(Visitor v)
    {
        boolean checkChildren = v.visit(this);
        if (checkChildren)
        {
            _value.accept(v);
            _intersect.accept(v);
            _value3.accept(v);
        }
        v.endVisit(this);
    }
}


