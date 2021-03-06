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
 *    Elliott Baron <ebaron@redhat.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.linuxtools.internal.valgrind.massif;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MassifPidMenuAction extends Action implements IMenuCreator {

    private Menu menu;
    private MassifViewPart view;
    private Integer[] pids;

    public MassifPidMenuAction(MassifViewPart view) {
        super(Messages.getString("MassifPidMenuAction.Select_Process_ID"), IAction.AS_DROP_DOWN_MENU); //$NON-NLS-1$
        this.view = view;

        setToolTipText(Messages.getString("MassifPidMenuAction.Select_Process_ID")); //$NON-NLS-1$
        setImageDescriptor(DebugUITools.getImageDescriptor(IDebugUIConstants.IMG_OBJS_THREAD_RUNNING));
        setMenuCreator(this);
    }

    @Override
    public void dispose() {
        if (menu != null) {
            menu.dispose();
        }
    }

    @Override
    public Menu getMenu(Control parent) {
        if (menu == null) {
            if (pids != null) {
                menu = new Menu(parent);
                for (int i = 0; i < pids.length; i++) {
                    final Integer pid = pids[i];
                    ActionContributionItem item = new ActionContributionItem(new Action("PID " + pids[i], IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
                        @Override
                        public void run() {
                            MenuItem[] items = menu.getItems();
                            for (int j = 0; j < items.length; j++) {
                                IAction action = ((ActionContributionItem) items[j].getData()).getAction();
                                action.setChecked(false);
                            }
                            setChecked(true);
                            view.setPid(pid);
                            Display.getDefault().syncExec(() -> view.refreshView());
                        }
                    });
                    item.fill(menu, -1);
                }
            }
            // Check first item
            ActionContributionItem item = (ActionContributionItem) menu.getItem(0).getData();
            item.getAction().setChecked(true);
        }
        return menu;
    }

    @Override
    public Menu getMenu(Menu parent) {
        return null;
    }

    public void setPids(Integer[] pids) {
        this.pids = pids;
    }

}
