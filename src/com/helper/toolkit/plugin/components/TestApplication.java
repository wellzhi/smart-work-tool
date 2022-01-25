package com.helper.toolkit.plugin.components;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

public class TestApplication implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        boolean open = project.isOpen();
        System.out.println("启动1");
        if (open) {
            System.out.println("启动2");
            // 错误提示弹窗
            // Messages.showErrorDialog(project,
            //         project.getBasePath(),
            //         "Input your name");
        }
        System.out.println("启动3");
    }
}
