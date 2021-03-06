/*******************************************************************************
 * Copyright (c) 2004, 2018 Red Hat, Inc.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Keith Seitz <keiths@redhat.com> - initial API and implementation
 *    Kent Sebastian <ksebasti@redhat.com>
 *******************************************************************************/
package org.eclipse.linuxtools.internal.oprofile.core.opxml.checkevent;

import org.eclipse.linuxtools.internal.oprofile.core.opxml.XMLProcessor;

/**
 * XML handler class for opxml's "check-events".
 * 
 * @see org.eclipse.linuxtools.internal.oprofile.core.opxml.OpxmlRunner
 */
public class CheckEventsProcessor extends XMLProcessor {
	public static final int INVALID_UNKNOWN = 0; // unexpected error
	public static final int EVENT_OK = 1; // valid
	public static final int INVALID_UMASK = 3; // invalid unit mask value
	public static final int INVALID_COUNTER = 4; // invalid event for given counter number

	private static final String RESULT_TAG = "result"; //$NON-NLS-1$
	private static final String CHECK_EVENTS_TAG = "check-events"; //$NON-NLS-1$

	private static final String EVENT_OK_STR = "ok"; //$NON-NLS-1$
	private static final String INVALID_UMASK_STR = "invalid-um"; //$NON-NLS-1$
	private static final String INVALID_COUNTER_STR = "invalid-counter"; //$NON-NLS-1$

	private int result;

	@Override
	public void reset(Object callData) {
		result = INVALID_UNKNOWN;
	}

	@Override
	public void endElement(String name, Object callData) {
		if (name.equals(RESULT_TAG)) {
			if (characters.equals(EVENT_OK_STR)) {
				result = EVENT_OK;
			} else if (characters.equals(INVALID_UMASK_STR)) {
				result = INVALID_UMASK;
			} else if (characters.equals(INVALID_COUNTER_STR)) {
				result = INVALID_COUNTER;
			}
		} else if (name.equals(CHECK_EVENTS_TAG)) {
			int[] r = (int[]) callData;
			r[0] = result;
		}
	}
}
