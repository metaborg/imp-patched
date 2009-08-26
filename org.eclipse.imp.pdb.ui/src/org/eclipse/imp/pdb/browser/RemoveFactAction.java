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

package org.eclipse.imp.pdb.browser;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class RemoveFactAction implements IViewActionDelegate {
    private FactBrowserView factBrowser;

    public void init(IViewPart view) {
        factBrowser = (FactBrowserView) view;
    }

    public void run(IAction action) {
        factBrowser.removeCurrentSelection();
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
}
