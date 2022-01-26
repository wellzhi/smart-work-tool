package com.helper.toolkit.plugin.processor.viewJson;

import com.helper.toolkit.biz.GenUtil;
import com.helper.toolkit.plugin.processor.common.CommonModel;
import com.intellij.ide.fileTemplates.impl.UrlUtil;

import java.io.IOException;

public class ViewJsonModel implements CommonModel {
    private String fileName;
    private String fullFilePath;
    private String jsonValue;
    private String describe;

    public String getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFilePath() {
        return "C:\\log\\";
    }

    @Override
    public String getTemplateContent() throws IOException {
        return UrlUtil.loadText(GenUtil.class.getResource("/template/ViewJson.ftl"));
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFullFilePath() {
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }
}
