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

package org.eclipse.imp.preferences;

import org.eclipse.swt.graphics.Font;

public class PreferenceCache {
    public static boolean emitMessages= false;

    public static int tabWidth= 4; // LK: sane default tab width

    public static Font sourceFont;

    public static boolean dumpTokens= false;

    private PreferenceCache() { }
}
