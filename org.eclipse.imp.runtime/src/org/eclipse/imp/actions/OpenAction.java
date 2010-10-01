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

package org.eclipse.imp.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IPath;
import org.eclipse.imp.core.IMPMessages;
import org.eclipse.imp.editor.EditorUtility;
import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.imp.language.ServiceFactory;
import org.eclipse.imp.model.ICompilationUnit;
import org.eclipse.imp.model.ISourceEntity;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.runtime.RuntimePlugin;
import org.eclipse.imp.services.IReferenceResolver;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;

/**
 * This action opens an IMP editor on an IMP element or source file.
 * <p>
 * The action is applicable to selections containing elements of type <code>ICompilationUnit</code>, <code>IMember</code> or <code>IFile</code>.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 */
public class OpenAction extends SelectionDispatchAction {
    private UniversalEditor fEditor;
	private IReferenceResolver fResolver= null;

    /**
     * Creates a new <code>OpenAction</code>. The action requires that the selection provided by the site's selection provider is of type <code>
     * org.eclipse.jface.viewers.IStructuredSelection</code>.
     * 
     * @param site
     *            the site providing context information for this action
     */
    public OpenAction(IWorkbenchSite site) {
        super(site);
        setText(ActionMessages.OpenAction_label);
        setToolTipText(ActionMessages.OpenAction_tooltip);
        setDescription(ActionMessages.OpenAction_description);
//      PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.OPEN_ACTION);
    }

    /**
     * Note: This constructor is for internal use only. Clients should not call this constructor.
     * 
     * @param editor
     *            the Java editor
     */
    public OpenAction(UniversalEditor editor) {
        this(editor.getEditorSite());
        fEditor= editor;
        setText(ActionMessages.OpenAction_declaration_label);
        setEnabled(EditorUtility.getEditorInputModelElement(fEditor, false) != null);
    }

    /*
     * (non-Javadoc) Method declared on SelectionDispatchAction.
     */
    public void selectionChanged(ITextSelection selection) {
       	Object target = getSelectionTarget(selection);

       	setEnabled(target != null);
    }

    private Object getSelectionTarget(ITextSelection textSel) {
		IParseController pc= fEditor.getParseController();
	    ISourcePositionLocator nodeLocator = pc.getSourcePositionLocator();
	
		if (fResolver == null) {
	        fResolver = ServiceFactory.getInstance().getReferenceResolver(pc.getLanguage());
	    }
	
		if (fResolver == null) {
			return null;
		}
	
	    Object ast= pc.getCurrentAst();
	
	    if (ast == null) {
	    	return null;
	    }
	
	    Object sourceNode= nodeLocator.findNode(ast, textSel.getOffset());
	
	    if (sourceNode == null) {
	    	return null;
	    }
	
	   	return fResolver.getLinkTarget(sourceNode, pc);
    }

    /*
     * (non-Javadoc) Method declared on SelectionDispatchAction.
     */
    public void selectionChanged(IStructuredSelection selection) {
        setEnabled(checkEnabled(selection));
    }

    @SuppressWarnings("unchecked")
	private boolean checkEnabled(IStructuredSelection selection) {
        if (selection.isEmpty())
            return false;
        for(Iterator iter= selection.iterator(); iter.hasNext(); ) {
            Object element= iter.next();
            if (element instanceof ISourceEntity)
                continue;
            if (element instanceof IFile)
                continue;
            if (element instanceof IStorage)
                continue;
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc) Method declared on SelectionDispatchAction.
     */
    public void run(ITextSelection selection) {
    	Object target= getSelectionTarget(selection);

    	if (target != null) {
    	    ISourcePositionLocator locator= fEditor.getParseController().getSourcePositionLocator();
    		IPath path= locator.getPath(target);
    		int targetOffset= locator.getStartOffset(target);

    		try {
    			IEditorPart editor= EditorUtility.isOpenInEditor(path);
    			if (editor == null) {
    				editor= EditorUtility.openInEditor(path);
    			}
    			EditorUtility.revealInEditor(editor, targetOffset, 0);
    		} catch (PartInitException e) {
    			RuntimePlugin.getInstance().logException("Unable to open declaration", e);
    		}
        }
    }

    @SuppressWarnings("unused")
	private boolean isProcessable() {
    	ISourceEntity se= EditorUtility.getEditorInputModelElement(fEditor, false);
    	if (fEditor != null) {
    	    if (se instanceof ICompilationUnit /*&& !JavaModelUtil.isPrimary((ICompilationUnit) se)*/)
    	        return true; // can process non-primary working copies
    	}
    	return false;
    }


    /*
     * (non-Javadoc) Method declared on SelectionDispatchAction.
     */
    public void run(IStructuredSelection selection) {
        if (!checkEnabled(selection))
            return;
        run(selection.toArray());
    }

    /**
     * Note: this method is for internal use only. Clients should not call this method.
     * 
     * @param elements
     *            the elements to process
     */
    public void run(Object[] elements) {
        if (elements == null)
            return;
        for(int i= 0; i < elements.length; i++) {
            Object element= elements[i];
            try {
                boolean activateOnOpen= fEditor != null ? true : OpenStrategy.activateOnOpen();
                OpenActionUtil.open(element, activateOnOpen);
            } catch (PartInitException x) {
                String name= null;
                if (element instanceof ISourceEntity) {
                    name= ((ISourceEntity) element).getName();
                } else if (element instanceof IStorage) {
                    name= ((IStorage) element).getName();
                } else if (element instanceof IResource) {
                    name= ((IResource) element).getName();
                }
                if (name != null) {
                    MessageDialog.openError(getShell(), ActionMessages.OpenAction_error_messageProblems, IMPMessages.format(
                            ActionMessages.OpenAction_error_messageArgs, new String[] { name, x.getMessage() }));
                }
            }
        }
    }
}
