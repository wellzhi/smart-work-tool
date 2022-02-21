package com.helper.toolkit.plugin.dialog;

import com.helper.toolkit.biz.GenUtil;
import com.helper.toolkit.plugin.processor.apiDoc.ApiDocModel;
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

public class ApiDialog extends JDialog {
    private Project project;
    private JPanel contentPanel;
    private JTextField uriValue;
    private JTextArea reqValue;
    private JTextArea reqInitValue;
    private JTextArea resValue;
    private JButton generateBtn;
    private JButton cancelBtn;
    private JTextField docNameValue;
    private JTextField apiMethodValue;
    private JTextField fullFilePathValue;
    private JCheckBox tokenCheckBox;
    private JButton generate2FileBtn;


    public ApiDialog(Project project, ApiDocModel apiDocModel) {
        this.project = project;
        setContentPane(contentPanel);
        setModal(true);
        uriValue.setText(apiDocModel.getApiUrl());
        reqValue.setText(apiDocModel.getApiReq());
        reqInitValue.setText(apiDocModel.getApiReqInitValue());
        resValue.setText(apiDocModel.getApiRes());
        docNameValue.setText(apiDocModel.getApiDocName());
        apiMethodValue.setText(apiDocModel.getApiMethod());
        fullFilePathValue.setText(apiDocModel.getFullFilePath());
        tokenCheckBox.setSelected(false);
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
        String uriValueText = uriValue.getText();
        String reqValueText = reqValue.getText();
        String resValueText = resValue.getText();
        String docNameValueText = docNameValue.getText();
        String methodValueText = apiMethodValue.getText();
        String fullFilePathValueText = fullFilePathValue.getText();
        boolean tokenCheckBoxSelected = tokenCheckBox.isSelected();
        ApiDocModel apiDocModel = new ApiDocModel();
        apiDocModel.setApiUrl(uriValueText);
        apiDocModel.setApiReq(reqValueText);
        apiDocModel.setApiRes(resValueText);
        apiDocModel.setApiDocName(docNameValueText);
        apiDocModel.setApiMethod(methodValueText);
        apiDocModel.setFullFilePath(fullFilePathValueText);
        apiDocModel.setNeedToken(tokenCheckBoxSelected);
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getBaseDir());
        if (virtualFile != null) {
            String path = virtualFile.getPath();
            String fileFullPath = path + "/" + docNameValueText;
            apiDocModel.setFullFilePath(fileFullPath);
            try {
                GenUtil.genApiDoc(apiDocModel);
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
        String uriValueText = uriValue.getText();
        String reqValueText = reqValue.getText();
        String resValueText = resValue.getText();
        String docNameValueText = docNameValue.getText();
        String methodValueText = apiMethodValue.getText();
        String fullFilePathValueText = fullFilePathValue.getText();
        boolean tokenCheckBoxSelected = tokenCheckBox.isSelected();
        ApiDocModel apiDocModel = new ApiDocModel();
        apiDocModel.setApiUrl(uriValueText);
        apiDocModel.setApiReq(reqValueText);
        apiDocModel.setApiRes(resValueText);
        apiDocModel.setApiDocName(docNameValueText);
        apiDocModel.setApiMethod(methodValueText);
        apiDocModel.setFullFilePath(fullFilePathValueText);
        apiDocModel.setNeedToken(tokenCheckBoxSelected);
        GenUtil.genApiDoc(apiDocModel);
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

    public JTextArea getReqInitValue() {
        return reqInitValue;
    }

    public void setReqInitValue(JTextArea reqInitValue) {
        this.reqInitValue = reqInitValue;
    }
}
