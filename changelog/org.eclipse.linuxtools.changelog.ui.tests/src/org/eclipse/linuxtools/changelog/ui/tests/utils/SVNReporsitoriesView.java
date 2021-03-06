/*******************************************************************************
 * Copyright (c) 2010, 2018 Red Hat Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.linuxtools.changelog.ui.tests.utils;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;

/**
 * SWTBot abstraction for the SVNRepositoriesView.
 */
public class SVNReporsitoriesView {

    private SWTWorkbenchBot bot;

    public SVNReporsitoriesView(SWTWorkbenchBot bot) {
        this.bot = bot;
    }

    /**
     * Open the SVNRepositoriesView
     */
    public SVNReporsitoriesView open() {
        bot.menu("Window").menu("Show View").menu("Other...").click();
        SWTBotShell shell = bot.shell("Show View");
        shell.activate();
        bot.tree().expandNode("SVN").select("SVN Repositories");
        bot.button("OK").click();
        return this;
    }

    /**
     * Select repository
     */
    public void discardRepository(String repo) {
        SWTBotView svnRepoView = bot.viewByTitle("SVN Repositories");
        svnRepoView.show();
        svnRepoView.setFocus();
        SWTBotTree tree = svnRepoView.bot().tree();
        tree.select(repo);
        clickOnDiscardRepo(tree); // discard
    }

    /**
     * Context menu click helper. Click on "Add to existing sources".
     *
     * @param Tree of Package Explorer view.
     * @throws Exception
     */
    private void clickOnDiscardRepo(SWTBotTree svnReposTree) {
        String menuItem = "Discard location";
        ContextMenuHelper.clickContextMenu(svnReposTree, menuItem);
    }
}
