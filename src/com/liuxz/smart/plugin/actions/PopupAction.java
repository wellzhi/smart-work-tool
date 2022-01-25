package com.liuxz.smart.plugin.actions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.liuxz.smart.biz.BizUtil;
import com.liuxz.smart.plugin.data.DataCenter;
import com.liuxz.smart.plugin.dialog.NoteDialog;
import com.liuxz.smart.plugin.processor.api.ApiDocModel;

public class PopupAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // System.out.println("isFromActionToolbar："+ e.isFromActionToolbar());
        // System.out.println("isFromContextMenu："+ e.isFromContextMenu());
        // Editor data = e.getData(CommonDataKeys.EDITOR);
        // // 编辑器：当前编辑文件的文件内容 //TODO 取到编辑器内容，生成自定义wiki文档
        // String documentText = data.getDocument().getText();
        // System.out.println(documentText);


        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        SelectionModel selectionModel = editor.getSelectionModel();
        // 获取当前编辑窗口中的文本内容
        String allText = editor.getDocument().getText();
        // 获取选中的文本内容
        String selectedText = selectionModel.getSelectedText();
        DataCenter.SELECT_TEXT = selectedText;

        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile();
        String name = virtualFile.getName();
        String path = virtualFile.getPath();
        ApiDocModel docModel = BizUtil.genApiDoc(allText, selectedText);
        DataCenter.FILE_NAME = name;
        DataCenter.ABSOLUTE_FILE_PATH = path;
        NoteDialog dialog = new NoteDialog(project);
        dialog.open();
    }
}
