/*******************************************************************************
* Copyright (c) 2009 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.analysis.constraints;

/**
 * Roughly, a visitor that wants to visit the sub-structure of a given
 * {@link IConstraintTerm}. This is used, for example, to set up various
 * housekeeping data structures relating to the constraint graph.
 * @see IConstraint, ConstraintGraph
 * @author rfuhrer@watson.ibm.com
 */
public interface ITermProcessor {
    void processTerm(IConstraintTerm term);
}
