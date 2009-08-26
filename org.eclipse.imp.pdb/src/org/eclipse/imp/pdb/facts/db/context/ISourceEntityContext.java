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

/**
 * 
 */
package org.eclipse.imp.pdb.facts.db.context;

import org.eclipse.imp.model.ISourceEntity;
import org.eclipse.imp.pdb.facts.db.IFactContext;

public interface ISourceEntityContext extends IFactContext {
    ISourceEntity getEntity();
}