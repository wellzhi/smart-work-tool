package com.helper.toolkit.plugin.actions;


import cn.hutool.core.util.StrUtil;
import com.helper.toolkit.biz.BizUtil;
import com.helper.toolkit.plugin.dialog.ViewJsonDialog;
import com.helper.toolkit.plugin.processor.viewJson.ViewJsonModel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class ViewJsonAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        String allText = editor.getDocument().getText();
        String selectedText = selectionModel.getSelectedText();

        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile();

        String viewJsonStr = BizUtil.genViewJson(allText, selectedText);
        ViewJsonModel viewJsonModel = new ViewJsonModel();
        String name = virtualFile.getName();
        String pureFileName = StrUtil.subBefore(name, ".", true);

        String path = virtualFile.getPath();
        String pureFilePath = StrUtil.subBefore(path, "/", true);


        viewJsonModel.setFileName("Struct-" + pureFileName);
        viewJsonModel.setFullFilePath(pureFilePath + "/" + pureFileName + ".md");
        viewJsonModel.setJsonValue(viewJsonStr);

        ViewJsonDialog viewJsonAction = new ViewJsonDialog(project, viewJsonModel);
        viewJsonAction.open();
    }
}
