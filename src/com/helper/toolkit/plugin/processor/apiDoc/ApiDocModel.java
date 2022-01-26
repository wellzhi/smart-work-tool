package com.helper.toolkit.plugin.processor.apiDoc;

import com.helper.toolkit.plugin.processor.common.CommonModel;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.helper.toolkit.biz.GenUtil;

import java.io.IOException;

/**
 * @author 15108
 */
public class ApiDocModel implements CommonModel {
    private String apiDocName;
    private String apiUrl;
    private String apiReq;
    private String apiRes;
    private String apiMethod;
    private String fullFilePath;
    private boolean needToken;

    @Override
    public String getFilePath() {
        return "C:\\log\\";
    }

    @Override
    public String getTemplateContent() throws IOException {
        return UrlUtil.loadText(GenUtil.class.getResource("/template/ApiDoc.ftl"));
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

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getFullFilePath() {
        //fullFilePath = getFilePath() + apiDocName;
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }

    public boolean isNeedToken() {
        return needToken;
    }

    public void setNeedToken(boolean needToken) {
        this.needToken = needToken;
    }
}
