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

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.model.ModelFactory;
import org.eclipse.imp.model.ModelFactory.ModelException;
import org.eclipse.imp.pdb.PDBPlugin;
import org.eclipse.imp.pdb.analysis.AnalysisException;
import org.eclipse.imp.pdb.analysis.AnalysisManager;
import org.eclipse.imp.pdb.analysis.IAnalysisDescriptor;
import org.eclipse.imp.pdb.facts.db.FactBase;
import org.eclipse.imp.pdb.facts.db.FactKey;
import org.eclipse.imp.pdb.facts.db.IFactKey;
import org.eclipse.imp.pdb.facts.db.context.ProjectContext;
import org.eclipse.imp.pdb.facts.impl.reference.ValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class TriggerAnalysisMenuAction extends Action implements IMenuCreator {

    private Menu fMenu;

    public TriggerAnalysisMenuAction() {
        setMenuCreator(this);
        setText("Analyze");
        setToolTipText("Trigger an analysis");
    }

    public Menu getMenu(Control parent) {
        if (fMenu != null) {
            fMenu.dispose();
        }
        
        fMenu = new Menu(parent);
        
        Set<IAnalysisDescriptor> analyses = AnalysisManager.getInstance().getAnalysisDescriptorSet();
        
        for (IAnalysisDescriptor analysis : analyses) {
            MenuItem menuItem = new MenuItem(fMenu, SWT.PUSH);
            menuItem.setText(analysis.getName());
            menuItem.setData(analysis);
            menuItem.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                    IAnalysisDescriptor a = (IAnalysisDescriptor) e.widget.getData();
                    triggerAnalysis(a);
                }
            });
        }

        return fMenu;
    }

    protected void triggerAnalysis(IAnalysisDescriptor a) {
        for (Type type : a.getOutputDescriptors()) {
            if (type != null) {
                IProject project = SelectContextMenuAction.getInstance().getSelectedProject();
                try {
                    ISourceProject srcProject= ModelFactory.open(project);
                    IFactKey key = new FactKey(type, new ProjectContext(srcProject));
                    try {
                        FactBase.getInstance().getFact(key);
                    } catch (AnalysisException e) {
                        FactBase.getInstance().defineFact(new FactKey(TypeFactory.getInstance().stringType(), key.getContext()), ValueFactory.getInstance().string(e.getMessage()));
                        PDBPlugin.getInstance().logException(e.getMessage(), e);
                    }
                } catch (ModelException e) {
                    PDBPlugin.getInstance().logException(e.getMessage(), e);
                }
            }
            else {
                PDBPlugin.getInstance().writeErrorMsg("Analysis does not provide output descriptors: " + a.getName());
            }
        }
    }

    public void dispose() {
        if (fMenu != null) {
            fMenu.dispose();
        }
    }

    public Menu getMenu(Menu parent) {
        return null;
    }
}
