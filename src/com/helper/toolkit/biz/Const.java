package com.helper.toolkit.biz;

import java.util.HashMap;
import java.util.Map;

public class Const {
    public final static Map PARAM_TYPE_MAP = new HashMap();
    static {
        PARAM_TYPE_MAP.put("getRequiredTextField", "String");
        PARAM_TYPE_MAP.put("getRequiredBigDecimalField", "BigDecimal");
        PARAM_TYPE_MAP.put("getRequiredBooleanField", "Boolean");
        PARAM_TYPE_MAP.put("getRequiredDoubleField", "Double");
        PARAM_TYPE_MAP.put("getRequiredIntField", "Integer");
        PARAM_TYPE_MAP.put("getRequiredLongField", "Long");
        PARAM_TYPE_MAP.put("getOptionalTextField", "String");
        PARAM_TYPE_MAP.put("getOptionalBooleanField", "Boolean");
        PARAM_TYPE_MAP.put("getOptionalDoubleField", "Double");
        PARAM_TYPE_MAP.put("getOptionalIntField", "Integer");
        PARAM_TYPE_MAP.put("getOptionalLongField", "Long");
        //String rString = getRequiredTextField(node, "rString");
        //BigDecimal rBigDecimal = getRequiredBigDecimalField(node, "rBigDecimal");
        //Boolean rBoolean = getRequiredBooleanField(node, "rBoolean");
        //Double rDouble = getRequiredDoubleField(node, "rDouble");
        //Integer rInteger = getRequiredIntField(node, "rInteger");
        //Long rLong = getRequiredLongField(node, "rLong");
        //String oString = getOptionalTextField(node, "oString");
        //Boolean oBoolean = getOptionalBooleanField(node, "oBoolean");
        //Double oDouble = getOptionalDoubleField(node, "oDouble");
        //Integer oInteger = getOptionalIntField(node, "oInteger");
        //Long oLong = getOptionalLongField(node, "oLong");
    }
}
