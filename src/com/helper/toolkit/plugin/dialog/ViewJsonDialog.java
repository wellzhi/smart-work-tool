package com.helper.toolkit.plugin.dialog;

import com.helper.toolkit.biz.GenUtil;
import com.helper.toolkit.plugin.processor.viewJson.ViewJsonModel;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;
import java.awt.*;

public class ViewJsonDialog extends JDialog {
    private Project project;
    private JPanel contentPanel;
    private JTextField fileNameValue;
    private JTextField filePathValue;
    private JTextArea jsonValue;
    private JButton generateBtn;
    private JButton cancelBtn;
    private JTextField describeValue;
    private JButton generate2FileBtn;

    public ViewJsonDialog(Project project, ViewJsonModel viewJsonModel) {
        this.project = project;
        setContentPane(contentPanel);
        setModal(true);
        fileNameValue.setText(viewJsonModel.getFileName());
        filePathValue.setText(viewJsonModel.getFilePath() + viewJsonModel.getFileName() + ".md");
        jsonValue.setText(viewJsonModel.getJsonValue());
        init();
    }

    private void init() {
        generateBtn.addActionListener(e -> {
            onGenerate();
        });
        cancelBtn.addActionListener(e -> {
            onCancel();
        });
        generate2FileBtn.addActionListener(e -> {
            onGenerate2File();
        });
    }

    private void onGenerate2File() {
        String fileNameValueText = fileNameValue.getText();
        String filePathValueText = filePathValue.getText();
        String jsonValueText = jsonValue.getText();
        String describeValueText = describeValue.getText();
        ViewJsonModel viewJsonModel = new ViewJsonModel();
        viewJsonModel.setFileName(fileNameValueText);
        viewJsonModel.setFullFilePath(filePathValueText);
        viewJsonModel.setJsonValue(jsonValueText);
        viewJsonModel.setDescribe(describeValueText);
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getBaseDir());
        if (virtualFile != null) {

            String path = virtualFile.getPath();
            String fileFullPath = path + "/" + fileNameValueText + ".md";
            viewJsonModel.setFullFilePath(fileFullPath);
            try {
                GenUtil.genViewJson(viewJsonModel);
                NotificationGroup notificationGroup = new NotificationGroup("markbook_id", NotificationDisplayType.BALLOON, true);
                Notification notification = notificationGroup.createNotification("Generate Document Success：" + fileFullPath, MessageType.INFO);
                Notifications.Bus.notify(notification);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void onCancel() {
        System.out.println("cancelBtn click");
        dispose();
    }

    private void onGenerate() {
        System.out.println("generateBtn click");
        String fileNameValueText = fileNameValue.getText();
        String filePathValueText = filePathValue.getText();
        String jsonValueText = jsonValue.getText();
        String describeValueText = describeValue.getText();

        ViewJsonModel viewJsonModel = new ViewJsonModel();
        viewJsonModel.setFileName(fileNameValueText);
        viewJsonModel.setFullFilePath(filePathValueText);
        viewJsonModel.setJsonValue(jsonValueText);
        viewJsonModel.setDescribe(describeValueText);
        GenUtil.genViewJson(viewJsonModel);
        System.out.println("generateBtn ok");
        dispose();
    }

    /**
     * 打开窗口
     */
    public void open() {
        pack();
        setTitle("ApiDoc显示窗口");
        setMinimumSize(new Dimension(800, 600));
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(this.project));
        setVisible(true);
    }
}
