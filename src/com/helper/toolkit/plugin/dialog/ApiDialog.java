package com.helper.toolkit.plugin.dialog;

import com.helper.toolkit.biz.GenUtil;
import com.helper.toolkit.plugin.processor.api.ApiDocModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;
import java.awt.*;

public class ApiDialog extends JDialog {
    private Project project;
    private JPanel contentPanel;
    private JTextField uriValue;
    private JTextArea reqValue;
    private JTextArea resValue;
    private JButton generateBtn;
    private JButton cancelBtn;
    private JTextField docNameValue;
    private JTextField apiMethodValue;
    private JTextField fullFilePathValue;


    public ApiDialog(Project project, ApiDocModel apiDocModel) {
        this.project = project;
        setContentPane(contentPanel);
        setModal(true);
        uriValue.setText(apiDocModel.getApiUrl());
        reqValue.setText(apiDocModel.getApiReq());
        resValue.setText(apiDocModel.getApiRes());
        docNameValue.setText(apiDocModel.getApiDocName());
        apiMethodValue.setText(apiDocModel.getApiMethod());
        fullFilePathValue.setText(apiDocModel.getFullFilePath());
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        init();
    }

    private void init() {
        generateBtn.addActionListener(e -> {
            onGenerate();
        });
        cancelBtn.addActionListener(e -> {
            onCancel();
        });
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
        ApiDocModel apiDocModel = new ApiDocModel();
        apiDocModel.setApiUrl(uriValueText);
        apiDocModel.setApiReq(reqValueText);
        apiDocModel.setApiRes(resValueText);
        apiDocModel.setApiDocName(docNameValueText);
        apiDocModel.setApiMethod(methodValueText);
        apiDocModel.setFullFilePath(fullFilePathValueText);
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

}
