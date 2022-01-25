package com.liuxz.smart.biz;

import cn.hutool.core.bean.BeanUtil;
import com.liuxz.smart.plugin.processor.api.ApiDocModel;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class GenUtil {
    public static void main(String[] args) throws IOException, TemplateException {
        String req = "{\n" +
                "\"isVipChannel\":\"${isVipChannel:Boolean}\" // 是否VIP渠道咨询\n" +
                "}\n";
        String res = "{\n" +
                "    \"conversationInfo\":\"${conversationInfo:InsuranceMessageConversationView}\" // 视图结构见下文\n" +
                "}\n";
        ApiDocModel docModel = new ApiDocModel();
        docModel.setApiUrl("/app/insurance/getInsuranceMessageConversationInfo");
        docModel.setApiReq(req);
        docModel.setApiRes(res);
        docModel.setApiDocName("Api-insurance-getInsuranceMessageConversationInfo.md");
        genApiDoc(docModel);
    }

    public static void genApiDoc(ApiDocModel docModel) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("ApiDocTemplate", docModel.getTemplateContent());
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("ApiDocTemplate");
        Map<String, Object> contentMap = BeanUtil.beanToMap(docModel);
        // Map contentMap = new HashMap();
        // contentMap.put("apiUrl", docModel.getApiUrl());
        // contentMap.put("apiReq", docModel.getApiReq());
        // contentMap.put("apiRes", docModel.getApiRes());
        File file = new File(docModel.getFilePath() + "/" + docModel.getApiDocName());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        template.process(contentMap, writer);
    }
}


