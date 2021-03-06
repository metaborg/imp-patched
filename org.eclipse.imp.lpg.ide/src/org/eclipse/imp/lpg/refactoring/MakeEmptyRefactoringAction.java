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

import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.imp.refactoring.RefactoringStarter;
import org.eclipse.ui.texteditor.TextEditorAction;

public class MakeEmptyRefactoringAction extends TextEditorAction {
//    private final UniversalEditor fEditor;

    public MakeEmptyRefactoringAction(UniversalEditor editor) {
	super(RefactoringResources.ResBundle, "makeEmpty.", editor);
//	fEditor= editor;
    }

    public void run() {
	final MakeEmptyRefactoring refactoring= new MakeEmptyRefactoring((UniversalEditor) this.getTextEditor());

	if (refactoring != null)
		new RefactoringStarter().activate(refactoring, new MakeEmptyWizard(refactoring, "Make Empty"), this.getTextEditor().getSite().getShell(), "Make Empty", false);
    }
}
