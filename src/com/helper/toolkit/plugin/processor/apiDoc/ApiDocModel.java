package com.helper.toolkit.plugin.processor.apiDoc;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.helper.toolkit.plugin.Const;
import com.helper.toolkit.plugin.processor.common.CommonModel;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.helper.toolkit.biz.GenUtil;

import java.io.IOException;
import java.util.Date;

/**
 * @author 15108
 */
public class ApiDocModel implements CommonModel {
    private String apiDocName;
    private String apiUrl;
    private String apiReq;
    private String apiReqInitValue;
    private String apiRes;
    private String apiMethod;
    private String fullFilePath;
    private String desc;
    private boolean needToken;
    // 中间变量
    private String apiItemMd;


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

    public String getApiReqInitValue() {
        return apiReqInitValue;
    }

    public void setApiReqInitValue(String apiReqInitValue) {
        this.apiReqInitValue = apiReqInitValue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getApiItemMd() {
        String apiDocName = this.apiDocName;
        String todayStr = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
        // | insurance |[getInsurancePage](Api-insurance-getInsurancePage)|R|保险主页信息|:green_apple: |2022-01-07|
        // Api-insurance-getInsurancePage
        apiDocName = StrUtil.subBefore(apiDocName, Const.MD_SUFFIX, true);
        // insurance
        String apiNameL2 = StrUtil.subBetween(apiDocName, Const.API_DOC_PREFIX, "-");
        String apiNameL3 = StrUtil.subAfter(apiDocName, "-", true);
        return "|" + apiNameL2 + "|[" + apiNameL3 + "](" + apiDocName + ")|R|" + desc + "|:green_apple:|" + todayStr + "|";
    }

    public void setApiItemMd(String apiItemMd) {
        this.apiItemMd = apiItemMd;
    }
}
