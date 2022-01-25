package com.liuxz.smart.plugin.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.liuxz.smart.plugin.dialog.ApiDialog;

public class ApiDocAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        ApiDialog apiDialog = new ApiDialog(project);
        apiDialog.open();
    }
}
