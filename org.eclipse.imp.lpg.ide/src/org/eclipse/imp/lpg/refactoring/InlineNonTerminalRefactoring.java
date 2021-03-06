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

package org.eclipse.imp.lpg.refactoring;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.imp.lpg.parser.ASTUtils;
import org.eclipse.imp.lpg.parser.LPGParser.ASTNode;
import org.eclipse.imp.lpg.parser.LPGParser.IASTNodeToken;
import org.eclipse.imp.lpg.parser.LPGParser.IsymWithAttrs;
import org.eclipse.imp.lpg.parser.LPGParser.nonTerm;
import org.eclipse.imp.lpg.parser.LPGParser.rule;
import org.eclipse.imp.lpg.parser.LPGParser.ruleList;
import org.eclipse.imp.lpg.parser.LPGParser.symWithAttrsList;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.swt.graphics.Point;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

public class InlineNonTerminalRefactoring extends Refactoring {
    private final IFile fGrammarFile;
    private final IParseController fParseController;
    private final ASTNode fNode;
    private nonTerm fNonTerm;
    private symWithAttrsList fRHS;
    private boolean fInlineAll;
    private boolean fDeleteOrig;

    public InlineNonTerminalRefactoring(UniversalEditor editor) {
	super();

	IEditorInput input= editor.getEditorInput();

	if (input instanceof IFileEditorInput) {
	    IFileEditorInput fileInput= (IFileEditorInput) input;

	    fGrammarFile= fileInput.getFile();
	    fNode= findNode(editor);
	} else {
	    fGrammarFile= null;
	    fNode= null;
	}
	fInlineAll= false;
	fDeleteOrig= false;
	fParseController= editor.getParseController();
    }

    public boolean getDeleteOrig() {
        return fDeleteOrig;
    }

    public void setDeleteOrig(boolean deleteOrig) {
        fDeleteOrig= deleteOrig;
    }

    private ASTNode findNode(UniversalEditor editor) {
	Point sel= editor.getSelection();
	IParseController parseController= editor.getParseController();
	ASTNode root= (ASTNode) parseController.getCurrentAst();
	ISourcePositionLocator locator= parseController.getSourcePositionLocator();

	return (ASTNode) locator.findNode(root, sel.x);
    }

    public String getName() {
	return "Inline Non-Terminal";
    }

    public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
	// Check parameters retrieved from editor context
	if (fNode instanceof nonTerm) {
	    fNonTerm= (nonTerm) fNode;
	    fInlineAll= true;
	} else if (fNode instanceof IsymWithAttrs) {
	    Object def= ASTUtils.findDefOf(((IASTNodeToken) fNode), ASTUtils.getRoot(fNode), fParseController);

	    if (!(def instanceof nonTerm))
		return RefactoringStatus.createFatalErrorStatus("Inline Non-Terminal is only valid for non-terminal symbols");
	    fNonTerm= (nonTerm) def;
	} else if (fNode instanceof IASTNodeToken) {
	    if (fNode.getParent() instanceof nonTerm) {
		fNonTerm= (nonTerm) fNode.getParent();
		fInlineAll= true;
	    } else
		return RefactoringStatus.createFatalErrorStatus("Inline Non-Terminal is only valid for non-terminal symbols");
	} else
	    return RefactoringStatus.createFatalErrorStatus("Inline Non-Terminal is only valid for non-terminal symbols");

	ruleList rules= fNonTerm.getruleList();

	if (rules.size() != 1)
	    return RefactoringStatus.createFatalErrorStatus("Inline Non-Terminal is only valid for non-terminals with a single production");

	final rule rule= (rule) rules.getElementAt(0);

	if (rule.getopt_action_segment() != null)
	    return RefactoringStatus.createFatalErrorStatus("Non-terminal to be inlined cannot have an action block");

	fRHS= rule.getsymWithAttrsList();
	return new RefactoringStatus();
    }

    public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException, OperationCanceledException {
	return new RefactoringStatus();
    }

    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
	// TODO Replace hand-written transform code with usage of SAFARI AST rewriter.
	List/*<IsymWithAttrs>*/ refs;

	if (fInlineAll)
	    refs= ASTUtils.findRefsOf(fNonTerm);
	else
	    refs= Collections.singletonList(fNode);

	StringBuffer buff= new StringBuffer();
	int N= fRHS.size();

	for(int i=0; i < N; i++) {
	    // TODO Don't gratuitously throw out the action code...
	    // TODO Check to see whether this sym has any annotations, and if so... do something about them... whatever that means
	    if (i > 0) buff.append(' ');
	    buff.append(fRHS.getElementAt(i).toString());
	}

	String nonTermString= buff.toString();
	TextFileChange tfc= new TextFileChange("Inline Non-Terminal", fGrammarFile);

	tfc.setEdit(new MultiTextEdit());

	for(Iterator iter= refs.iterator(); iter.hasNext(); ) {
	    IsymWithAttrs sym= (IsymWithAttrs) iter.next();
	    int startOffset= sym.getLeftIToken().getStartOffset();
	    int endOffset= sym.getLeftIToken().getEndOffset();

	    tfc.addEdit(new ReplaceEdit(startOffset, endOffset - startOffset + 1, nonTermString));
	}

	if (fDeleteOrig) {
	    final int startOffset= fNonTerm.getLeftIToken().getStartOffset();
	    final int endOffset= fNonTerm.getRightIToken().getEndOffset();
	    tfc.addEdit(new DeleteEdit(startOffset, endOffset - startOffset + 1));
	}
	return tfc;
    }

    public boolean getInlineAll() {
        return fInlineAll;
    }

    public void setInlineAll(boolean inlineAll) {
        fInlineAll= inlineAll;
    }
}
