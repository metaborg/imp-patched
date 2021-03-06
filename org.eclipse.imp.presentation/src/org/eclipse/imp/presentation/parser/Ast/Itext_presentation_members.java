package org.eclipse.imp.presentation.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import java.util.Hashtable;
import java.util.Stack;

/**
 * is implemented by <b>text_presentation_memberList</b>
 */
public interface Itext_presentation_members
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


