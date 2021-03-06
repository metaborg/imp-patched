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
import org.eclipse.imp.services.IRefactoringContributor;
import org.eclipse.jface.action.IAction;

public class LPGRefactoringContributor implements IRefactoringContributor {
    public LPGRefactoringContributor() { }

    public IAction[] getEditorRefactoringActions(UniversalEditor editor) {
	return new IAction[] {
		new MakeNonEmptyRefactoringAction(editor),
		new MakeEmptyRefactoringAction(editor),
		new MakeLeftRecursiveRefactoringAction(editor),
		new InlineNonTerminalRefactoringAction(editor)
	};
    }
}
