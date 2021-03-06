package org.eclipse.imp.presentation.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import java.util.Hashtable;
import java.util.Stack;

/**
 * is implemented by <b>tree_presentation_attributeList</b>
 */
public interface Itree_presentation_attributes
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


