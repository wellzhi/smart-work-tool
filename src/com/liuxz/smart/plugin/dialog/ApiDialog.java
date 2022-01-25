package com.liuxz.smart.plugin.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;
import java.awt.*;

public class ApiDialog extends JDialog {
    private Project project;
    private JPanel contentPanel;
    private JTextField textField2;
    private JPanel uriPanel;
    private JButton 生成Button;
    private JButton 取消Button;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;


    public ApiDialog(Project project) {
        this.project = project;
        setContentPane(contentPanel);
        setModal(true);
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
