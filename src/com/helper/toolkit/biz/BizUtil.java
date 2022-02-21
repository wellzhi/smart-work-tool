package com.helper.toolkit.biz;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.helper.toolkit.plugin.processor.apiDoc.ApiDocModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BizUtil {
    public static final String QUOTES = "\"";

    public static String genViewJson(String allText, String selectedText) {
        List<String> rawStrList = StrUtil.splitTrim(allText, "\n");
        StringBuffer sb = new StringBuffer();
        int leftSize = 0, rightSize = 0;
        for (String str : rawStrList) {
            if (str.contains("serialVersionUID")
                    || str.contains("@")
                    || str.contains("package")
                    || str.contains("import")
                    || str.contains("||")
                    || str.contains("*")
                    || StrUtil.isBlank(str)
                    || str.trim().contains("public static final")
                    || str.trim().startsWith("//")
            ) {
                continue;
            }
            if (str.contains("{")) {
                leftSize++;
                if (sb.toString().contains("{")) {
                    // 嵌套内部类
                    // public static class InsuranceTestQuestionOptionView {
                    String classNameStr = StrUtil.subAfter(str, "class", true);
                    String innerClassName = StrUtil.subBefore(classNameStr, "{", true).trim();
                    sb.append(StrUtil.concat(false, QUOTES, innerClassName, QUOTES, ":", "{"));
                } else {
                    sb.append("{");
                }
            } else if (str.contains("}")) {
                rightSize++;
                if (leftSize > rightSize) {
                    sb.append("},");
                } else {
                    sb.append("}");
                }
            } else {
                String fieldStr = StrUtil.subBefore(str, ";", true);
                List<String> words = StrUtil.splitTrim(fieldStr, StrUtil.SPACE);
                if (words.size() == 3) {
                    sb.append(StrUtil.concat(false, QUOTES, words.get(2), QUOTES, ":", QUOTES, "${", words.get(2), ":", words.get(1), "}", QUOTES, ","));
                } else {
                    sb.append(StrUtil.concat(false, QUOTES, words.get(1), QUOTES, ":", QUOTES, "${", words.get(1), ":", words.get(0), "}", QUOTES, ","));
                }
            }
        }
        return JSONUtil.formatJsonStr(sb.toString());
    }

    public static ApiDocModel genApiDoc(String allText, String selectedText) {
        ApiDocModel docModel = new ApiDocModel();
        List<String> lineStrs = StrUtil.splitTrim(allText, "\n");
        //移除空白元素
        CollUtil.removeBlank(lineStrs);

        Optional<String> optional = lineStrs
                .stream()
                .filter(f -> f.contains("@RequestMapping"))
                .findFirst();
        String apiL2Name = null;
        String apiL3Name = null;
        String apiMethod = "POST";
        if (optional.isPresent()) {
            String reqStr = optional.get();
            apiL2Name = StrUtil.subBetween(reqStr, "\"", "\"");
            apiL2Name = apiL2Name.startsWith("/") ? apiL2Name : "/" + apiL2Name;

        }

        List<String> selectedLines = StrUtil.splitTrim(selectedText, "\n");
        //移除空白元素
        CollUtil.removeBlank(selectedLines);
        Optional<String> selectedOptional = selectedLines
                .stream()
                .filter(f -> f.contains("@PostMapping") || f.contains("@GetMapping"))
                .findFirst();
        if (selectedOptional.isPresent()) {
            String reqStr = selectedOptional.get();
            apiL3Name = StrUtil.subBetween(reqStr, "\"", "\"");
            apiL3Name = apiL3Name.startsWith("/") ? apiL3Name : "/" + apiL3Name;
            if (reqStr.contains("@GetMapping")) {
                apiMethod = "GET";
            }
        }
        docModel.setApiMethod(apiMethod);
        String apiUrl = apiL2Name + apiL3Name;
        docModel.setApiUrl(apiUrl);
        //  /insuranceCategory/saveInsuranceFirstCategory --> Api-insuranceCategory-saveInsuranceFirstCategory.md
        docModel.setApiDocName("Api" + apiUrl.replaceAll("/", "-") + ".md");
        System.out.println(apiL2Name + apiL3Name);

        List<String> apiParamStrs = selectedLines
                .stream()
                .filter(f -> StrUtil.isNotBlank(f))
                .filter(f -> !f.startsWith("//"))
                .filter(f -> (f.contains("=") && f.contains("node")) ||
                        (f.contains("=") && f.contains("params")))
                .collect(Collectors.toList());

        StringBuffer reqSb = new StringBuffer();
        StringBuffer initSb = new StringBuffer();
        reqSb.append(StrUtil.DELIM_START);
        initSb.append(StrUtil.DELIM_START);
        for (String apiParamStr : apiParamStrs) {
            String typeStr = StrUtil.trim(StrUtil.subBetween(apiParamStr, "=", "("));
            // 单个参数
            if (Const.PARAM_TYPE_MAP.containsKey(typeStr)) {
                String paramName = StrUtil.subBetween(apiParamStr, "\"", "\"");
                Object paramType = Const.PARAM_TYPE_MAP.get(typeStr);
                reqSb.append("\"").append(paramName).append("\"").append(":")
                        .append("\"").append("${").append(paramName).append(":")
                        .append(paramType).append("}").append("\"").append(",");

                initSb.append("\"").append(paramName).append("\"").append(":")
                        .append(Const.PARAM_INIT_VALUE_MAP.get(typeStr))
                        .append(",");
            }
            // 对象或对象列表
            if (apiParamStr.contains(Const.PARAM_OBJECTS) || apiParamStr.contains(Const.PARAM_OBJECT)) {
                // List<PageReadRecord> records = getObjects(params.get("records"), PageReadRecord.class);
                // List<String> targetIds = getObjects(params.get("targetIds"), String.class);
                // LoginView loginView = getObject(params.get("loginObj"), LoginView.class);
                String paramName = StrUtil.subBetween(apiParamStr, "\"", "\"");
                List<String> partStrList = StrUtil.splitTrim(apiParamStr, StrUtil.SPACE);
                String paramType = "";
                if (!partStrList.isEmpty()) {
                    paramType = partStrList.get(0);
                }
                reqSb.append("\"").append(paramName).append("\"").append(":")
                        .append("\"").append("${").append(paramName).append(":")
                        .append(paramType).append("}").append("\"").append(",");

                String paramTypeStr = StrUtil.trim(StrUtil.subBetween(apiParamStr, "=", "("));
                initSb.append("\"").append(paramName).append("\"").append(":")
                        .append(Const.PARAM_INIT_VALUE_MAP.get(paramTypeStr))
                        .append(",");

            }
        }
        reqSb.append(StrUtil.DELIM_END);
        initSb.append(StrUtil.DELIM_END);
        docModel.setApiReq(JSONUtil.formatJsonStr(reqSb.toString()));
        docModel.setApiReqInitValue(JSONUtil.formatJsonStr(initSb.toString()));
        docModel.setFullFilePath(docModel.getFilePath() + docModel.getApiDocName());
        docModel.setNeedToken(false);
        System.out.println(JSONUtil.formatJsonStr(reqSb.toString()));
        return docModel;
    }


}
