package com.helper.toolkit.plugin.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.helper.toolkit.biz.BizUtil;
import com.helper.toolkit.plugin.dialog.ApiDialog;
import com.helper.toolkit.plugin.processor.api.ApiDocModel;

public class ApiDocAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        // 获取当前编辑窗口中的文本内容
        String allText = editor.getDocument().getText();
        // 获取选中的文本内容
        String selectedText = selectionModel.getSelectedText();
        ApiDocModel apiDocModel = BizUtil.genApiDoc(allText, selectedText);

        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        ApiDialog apiDialog = new ApiDialog(project, apiDocModel);
        apiDialog.open();
    }
}
