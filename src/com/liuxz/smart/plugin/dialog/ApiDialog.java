package com.liuxz.smart.plugin.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.liuxz.smart.plugin.processor.api.ApiDocModel;

import javax.swing.*;
import java.awt.*;

public class ApiDialog extends JDialog {
    private Project project;
    private JPanel contentPanel;
    private JTextField uriValue;
    private JTextArea reqValue;
    private JTextArea resValue;


    public ApiDialog(Project project, ApiDocModel apiDocModel) {
        this.project = project;
        setContentPane(contentPanel);
        setModal(true);
        uriValue.setText(apiDocModel.getApiUrl());
        reqValue.setText(apiDocModel.getApiReq());
        resValue.setText(apiDocModel.getApiRes());
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
