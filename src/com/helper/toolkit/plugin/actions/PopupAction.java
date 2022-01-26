package com.helper.toolkit.plugin.actions;


import com.helper.toolkit.plugin.data.DataCenter;
import com.helper.toolkit.plugin.dialog.NoteDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class PopupAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        DataCenter.SELECT_TEXT = selectedText;
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile();
        String name = virtualFile.getName();
        String path = virtualFile.getPath();
        DataCenter.FILE_NAME = name;
        DataCenter.ABSOLUTE_FILE_PATH = path;
        NoteDialog dialog = new NoteDialog(project);
        dialog.open();
    }
}
