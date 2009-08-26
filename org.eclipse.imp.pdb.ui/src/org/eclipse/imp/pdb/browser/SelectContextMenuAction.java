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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * Provides a menu to select a context for the next analysis 
 * 
 * @author jurgenv
 *
 */
public class SelectContextMenuAction extends Action implements IMenuCreator {
    private Menu fMenu;
    protected IProject fCurrentProject;
    private static SelectContextMenuAction fInstance;

    private SelectContextMenuAction() {
        setMenuCreator(this);
        setText("Context");
        setToolTipText("Select a context for the next analysis");
        getProjects();
    }
    
    public static SelectContextMenuAction getInstance() {
        if (fInstance == null) {
            fInstance = new SelectContextMenuAction();
        }
        
        return fInstance;
    }

    public Menu getMenu(Control parent) {
        if (fMenu != null) {
            fMenu.dispose();
        }
        
        fMenu = new Menu(parent);
        
        IProject[] projects = getProjects();
        
        for (IProject project : projects) {
            final MenuItem menuItem = new MenuItem(fMenu, SWT.RADIO);
            menuItem.setText("Project: " + project.getName());
            menuItem.setData(project);
            if (project.equals(fCurrentProject)) {
                menuItem.setSelection(true);
            }
            else {
                menuItem.setSelection(false);
            }
            menuItem.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                   fCurrentProject = (IProject) e.widget.getData();
                   unselectOthers();
                   menuItem.setSelection(true);
                }

                private void unselectOthers() {
                    for (MenuItem item : fMenu.getItems()) {
                        item.setSelection(false);
                    }
                }
            });
        }

        return fMenu;
    }

    private IProject[] getProjects() {
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        
        if (fCurrentProject == null && projects.length > 0) {
            fCurrentProject = projects[0];
        }
        return projects;
    }

    public void dispose() {
        if (fMenu != null) {
            fMenu.dispose();
        }
    }

    public Menu getMenu(Menu parent) {
        return null;
    }
    
    public IProject getSelectedProject() {
        return fCurrentProject;
    }
}
