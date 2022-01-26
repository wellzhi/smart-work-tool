package com.helper.toolkit.biz;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.helper.toolkit.plugin.processor.api.ApiDocModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BizUtil {
    public static final String QUOTES = "\"";
    private final static List<String> keywords = Arrays.asList(
            "char", "Char",
            "byte", "Byte",
            "int", "Integer",
            "boolean", "Boolean",
            "long", "Long",
            "Set", "List", "Map", "class", "{", "}"
    );

    public static String file2WikiJson(String filePath) {
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> strLines = new ArrayList<>();
            String str;
            int leftSize = 0, rightSize = 0;
            while ((str = bufferedReader.readLine()) != null) {
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
                    if (strLines.contains("{")) {
                        // 嵌套内部类
                        // public static class InsuranceTestQuestionOptionView {
                        String classNameStr = StrUtil.subAfter(str, "class", true);
                        String innerClassName = StrUtil.subBefore(classNameStr, "{", true).trim();
                        strLines.add(StrUtil.concat(false, QUOTES, innerClassName, QUOTES, ":", "{"));
                    } else {
                        strLines.add("{");
                    }
                } else if (str.contains("}")) {
                    rightSize++;
                    if (leftSize > rightSize) {
                        strLines.add("},");
                    } else {
                        strLines.add("}");
                    }
                } else {
                    String fieldStr = StrUtil.subBefore(str, ";", true);
                    List<String> words = StrUtil.splitTrim(fieldStr, StrUtil.SPACE);
                    if (words.size() == 3) {
                        strLines.add(StrUtil.concat(false, QUOTES, words.get(2), QUOTES, ":", QUOTES, "${", words.get(2), ":", words.get(1), "}", QUOTES, ","));
                    } else {
                        strLines.add(StrUtil.concat(false, QUOTES, words.get(1), QUOTES, ":", QUOTES, "${", words.get(1), ":", words.get(0), "}", QUOTES, ","));
                    }
                }
            }
            String resultStr = "";
            for (String f : strLines) {
                resultStr += f;
            }
            return JSONUtil.formatJsonStr(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
                .filter(f -> f.contains("=") && f.contains("node"))
                .collect(Collectors.toList());

        StringBuffer reqSb = new StringBuffer();
        reqSb.append(StrUtil.DELIM_START);
        for (String apiParamStr : apiParamStrs) {
            String typeStr = StrUtil.trim(StrUtil.subBetween(apiParamStr, "=", "("));
            if (Const.PARAM_TYPE_MAP.containsKey(typeStr)) {
                String paramName = StrUtil.subBetween(apiParamStr, "\"", "\"");
                Object paramType = Const.PARAM_TYPE_MAP.get(typeStr);
                reqSb.append("\"").append(paramName).append("\"").append(":")
                        .append("\"").append("${").append(paramName).append(":")
                        .append(paramType).append("}").append("\"").append(",");
            }
        }
        reqSb.append(StrUtil.DELIM_END);
        docModel.setApiReq(JSONUtil.formatJsonStr(reqSb.toString()));
        docModel.setFullFilePath(docModel.getFilePath() + docModel.getApiDocName());
        docModel.setNeedToken(false);
        System.out.println(JSONUtil.formatJsonStr(reqSb.toString()));
        return docModel;
    }
}
