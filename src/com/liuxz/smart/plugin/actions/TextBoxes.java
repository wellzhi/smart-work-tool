package com.liuxz.smart.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class TextBoxes extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        // // 输入型弹窗
        // Messages.showInputDialog(
        //         project,
        //         "What is your name?",
        //         "Input your name",
        //         Messages.getQuestionIcon());
        //
        // // 错误提示弹窗
        // Messages.showErrorDialog(project,
        //         "What is your name?",
        //         "Input your name");
    }
}