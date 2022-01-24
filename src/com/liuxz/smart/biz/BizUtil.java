package com.liuxz.smart.biz;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static void main(String[] args) {
        String str = "@RequestMapping(value = \"/admin\")";
        if (str.contains("@RequestMapping")) {
            String apiL2Name = StrUtil.subBetween(str, "\"", "\"");
            if (StrUtil.contains(str, "/")) {
                System.out.println(apiL2Name);
            } else {
                System.out.println("/" + apiL2Name);
            }
        }

        System.out.println(DateUtil.format(DateUtil.date(1642666827824L),"yyyy-MM-dd hh:mm:ss"));

    }


    /**
     * 选中Api参数代码，生成wiki文档requestBody json
     * 所在代码文件提取api相对路径
     */
    public static String genApiDoc(String selectedText, String filePath) {
        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            String apiL2Name = null;
            String apiL3Name = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains("@RequestMapping")) {
                    apiL2Name = StrUtil.subBetween(str, "\"", "\"");
                    if (!StrUtil.contains(apiL2Name, "/")) {
                        apiL2Name = "/" + apiL2Name;
                    }
                }
            }
            List<String> lineStrList = StrUtil.splitTrim(selectedText, "\n");
            for (String lineStr : lineStrList) {
                if (StrUtil.isNotBlank(lineStr)) {
                    if (lineStr.contains("@PostMapping") || lineStr.contains("@GetMapping")) {
                        apiL3Name = StrUtil.subBetween(lineStr, "\"", "\"");
                        if (!StrUtil.contains(apiL3Name, "/")) {
                            apiL3Name = "/" + apiL3Name;
                        }
                    }
                }
            }
            System.out.println("/app" + apiL2Name + apiL3Name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}
