package org.eclipse.imp.presentation.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import java.util.Hashtable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 110:  prefix_unary_op ::= ++
 *</b>
 */
public class prefix_unary_op3 extends ASTNodeToken implements Iprefix_unary_op
{
    public IToken getPLUSPLUS() { return leftIToken; }

    public prefix_unary_op3(IToken token) { super(token); initialize(); }

    public void accept(IAstVisitor v)
    {
        if (! v.preVisit(this)) return;
        enter((Visitor) v);
        v.postVisit(this);
    }

    public void enter(Visitor v)
    {
        v.visit(this);
        v.endVisit(this);
    }
}


