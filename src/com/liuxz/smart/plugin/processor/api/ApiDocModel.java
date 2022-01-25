package com.liuxz.smart.plugin.processor.api;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.liuxz.smart.biz.GenUtil;

import java.io.IOException;

/**
 * @author 15108
 */
public class ApiDocModel implements CommonModel {
    private String apiDocName;
    private String apiUrl;
    private String apiReq;
    private String apiRes;

    @Override
    public String getFilePath() {
        return "C:\\log\\";
    }

    @Override
    public String getTemplateContent() throws IOException {
        return UrlUtil.loadText(GenUtil.class.getResource("/template/apidoc.ftl"));
    }

    public String getApiDocName() {
        return apiDocName;
    }

    public void setApiDocName(String apiDocName) {
        this.apiDocName = apiDocName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiReq() {
        return apiReq;
    }

    public void setApiReq(String apiReq) {
        this.apiReq = apiReq;
    }

    public String getApiRes() {
        return apiRes;
    }

    public void setApiRes(String apiRes) {
        this.apiRes = apiRes;
    }
}
