/*******************************************************************************
 * Copyright (c) 2007, 2018 Red Hat, Inc.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Red Hat - initial API and implementation
 *******************************************************************************/

package org.eclipse.linuxtools.internal.rpm.ui.editor.detectors;

public class PatchNumberDetector implements IStrictWordDetector {

	@Override
	public boolean isWordPart(char c) {
		return Character.isDigit(c);
	}

	@Override
	public boolean isWordStart(char c) {
		return Character.isDigit(c);
	}

	@Override
	public boolean isEndingCharacter(char c) {
		return Character.isWhitespace(c);
	}

}
