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

package org.eclipse.imp.asl.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.imp.asl.ASLPlugin;
import org.eclipse.imp.builder.ProjectNatureBase;
import org.eclipse.imp.runtime.IPluginLog;
import org.eclipse.imp.smapifier.builder.SmapiProjectNature;

public class ASLNature extends ProjectNatureBase {
    // SMS 28 Mar 2007:  plugin class now totally parameterized
    public static final String k_natureID= ASLPlugin.kPluginID + ".asl.nature";

    public String getNatureID() {
        return k_natureID;
    }

    public String getBuilderID() {
        return ASLBuilder.BUILDER_ID;
    }

    public void addToProject(IProject project) {
        super.addToProject(project);
        new SmapiProjectNature("ASL").addToProject(project);
    };

    protected void refreshPrefs() {
        // TODO implement preferences and hook in here
    }

    public IPluginLog getLog() {
        // SMS 28 Mar 2007:  plugin class now totally parameterized
        return ASLPlugin.getInstance();
    }

    protected String getDownstreamBuilderID() {
        // TODO Auto-generated method stub
        return null;
    }
}
