package com.liuxz.smart.plugin.processor.api;

import java.io.IOException;

/**
 * @author 15108
 */
public interface CommonModel {
    /**
     * 获取文件生成路径
     *
     * @return
     */
    String getFilePath();

    /**
     * 获取模板文件路径
     *
     * @return
     */
    String getTemplateContent() throws IOException;
}
