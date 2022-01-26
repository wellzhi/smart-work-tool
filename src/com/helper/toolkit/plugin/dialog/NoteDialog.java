package com.helper.toolkit.plugin.dialog;

import cn.hutool.core.util.StrUtil;
import com.helper.toolkit.plugin.data.DataCenter;
import com.helper.toolkit.plugin.data.NoteData;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NoteDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea taCode;
    private JTextField tfTitle;
    private JTextArea taMark;
    private JLabel lbFileName;
    private boolean update = false;
    private Integer editIndex;

    private Project project;

    public NoteDialog(Project project) {
        this.project = project;
        editIndex = DataCenter.NOTE_LIST.size();
        String absoluteFilePath = DataCenter.ABSOLUTE_FILE_PATH;
        NoteData noteData = new NoteData("title", "note", DataCenter.SELECT_TEXT, DataCenter.FILE_NAME, null, absoluteFilePath);
        init(noteData);
    }

    public NoteDialog(Project project, Integer index, NoteData noteData) {
        this.project = project;
        init(noteData);
        this.editIndex = index;
        update = true;
    }

    private void init(NoteData noteData) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        lbFileName.setText(noteData.getFileName());
        String jsonStr = null;
        taCode.setText(jsonStr);
         taCode.setText(noteData.getContent());
        tfTitle.setText(noteData.getTitle());
        taMark.setText(noteData.getMark());

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (!update) {
            addNote(editIndex);
        } else {
            updateNote(editIndex);
        }
    }

    private void updateNote(Integer index) {
        String title = tfTitle.getText();
        String mark = taMark.getText();
        String code = taCode.getText();
        String fileType = StrUtil.subAfter(DataCenter.FILE_NAME, ".", true);
        NoteData noteData = new NoteData(title, mark, code, DataCenter.FILE_NAME, fileType, DataCenter.ABSOLUTE_FILE_PATH);
        DataCenter.update(index, noteData);
        dispose();
    }

    private void addNote(Integer index) {
        String title = tfTitle.getText();
        String mark = taMark.getText();
        String code = taCode.getText();
        String fileType = StrUtil.subAfter(DataCenter.FILE_NAME, ".", true);
        NoteData noteData = new NoteData(title, mark, code, DataCenter.FILE_NAME, fileType, DataCenter.ABSOLUTE_FILE_PATH);
        DataCenter.add(noteData);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    /**
     * 打开窗口
     */
    public void open() {
        pack();
        setTitle("Mark");
        setMinimumSize(new Dimension(800, 600));
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(this.project));
        setVisible(true);
    }
}
