/*******************************************************************************
 * (C) Copyright 2010, 2018 IBM Corp. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Thavidu Ranatunga (IBM) - Initial implementation.
 *******************************************************************************/
package org.eclipse.linuxtools.internal.perf.model;

public class PMDso extends TreeParent {
    private String path = null;
    private boolean kernel = false; //Is this DSO a kernel dso?

    public boolean isKernelDso() {
        return kernel;
    }

    public PMDso(String dsoName, boolean kernel) {
        super(dsoName, 0);
        this.kernel = kernel;
    }

    public PMFile getFile(String fileName) {
        //check if exists else make a new one.
        PMFile tmp = (PMFile) getChild(fileName);
        if (tmp != null) {
        } else {
            tmp = new PMFile(fileName);
            addChild(tmp);
        }
        return tmp;
    }

    public void setPath(String filePath) {
        path = filePath;
    }
    @Override
    public String toString() {
        String prefix = ""; //$NON-NLS-1$
        if (getPercent() != -1) {
            prefix += getPercent() + "% (" + getFormattedSamples() + " samples) in "; //$NON-NLS-1$ //$NON-NLS-2$
        }
        if (kernel) {
            prefix += "[k] "; //$NON-NLS-1$
        }
        if (path != null) {
            return prefix + getName() + " (at " + path + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        }
        return prefix + getName();
    }
    public String getPath() {
        return path;
    }
}
