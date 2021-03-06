/*******************************************************************************
 * Copyright (c) 2011, 2018 IBM Corporation
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Daniel H Barboza <danielhb@br.ibm.com> - initial API and implementation
 *******************************************************************************/

package org.eclipse.linuxtools.internal.valgrind.helgrind;

public final class HelgrindLaunchConstants {
    // LaunchConfiguration attributes
    public static final String ATTR_HELGRIND_LOCKORDERS = HelgrindPlugin.PLUGIN_ID
            + ".HELGRIND_LOCKORDERS"; //$NON-NLS-1$
    public static final String ATTR_HELGRIND_HISTORYLEVEL = HelgrindPlugin.PLUGIN_ID
            + ".HELGRIND_HISTORYLEVEL"; //$NON-NLS-1$
    public static final String ATTR_HELGRIND_CACHESIZE = HelgrindPlugin.PLUGIN_ID
            + ".HELGRIND_CACHESIZE"; //$NON-NLS-1$

    // default values
    public static final String HISTORY_NONE = "none"; //$NON-NLS-1$
    public static final String HISTORY_APPROX = "approx"; //$NON-NLS-1$
    public static final String HISTORY_FULL = "full"; //$NON-NLS-1$

    public static final boolean DEFAULT_HELGRIND_LOCKORDERS = true;
    public static final String DEFAULT_HELGRIND_HISTORYLEVEL = HISTORY_FULL;
    public static final int DEFAULT_HELGRIND_CACHESIZE = 1000000;

}
