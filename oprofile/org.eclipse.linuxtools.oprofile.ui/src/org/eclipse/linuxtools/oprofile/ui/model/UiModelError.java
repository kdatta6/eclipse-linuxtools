/*******************************************************************************
 * Copyright (c) 2009, 2018 Red Hat, Inc.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Kent Sebastian <ksebasti@redhat.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.linuxtools.oprofile.ui.model;

import org.eclipse.linuxtools.internal.oprofile.ui.OprofileUiMessages;
import org.eclipse.linuxtools.internal.oprofile.ui.OprofileUiPlugin;
import org.eclipse.swt.graphics.Image;

/**
 * This is an error element
 * @since 1.1
 */
public class UiModelError implements IUiModelElement {
    public static final UiModelError NO_SAMPLES_ERROR = new UiModelError(OprofileUiMessages.getString("root.error.nosamples")); //$NON-NLS-1$

    private String errorMessage;

    /**
     * Constructor to the UiModelError class
     * @param message Error message
     */
    public UiModelError(String message) {
        errorMessage = message;
    }

    /** IUiModelElement functions **/
    @Override
    public String getLabelText() {
        return errorMessage;
    }

    /**
     * Returns the children of this element.
     * @return An array of child elements or null
     */
    @Override
    public IUiModelElement[] getChildren() {
        return null;
    }

    /**
     * Return whether the element has any children
     * @return true if the element has any children, false otherwise
     */
    @Override
    public boolean hasChildren() {
        return false;
    }

    /**
     * Returns the element's parent.
     * @return The parent element or null
     */
    @Override
    public IUiModelElement getParent() {
        return null;
    }

    /**
     * Returns the Image to display next to the text in the tree viewer.
     * In this case it is an error icon.
     * @return an Image object of the icon
     */
    @Override
    public Image getLabelImage() {
        return OprofileUiPlugin.getImageDescriptor(OprofileUiPlugin.ERROR_ICON).createImage();
    }
}
