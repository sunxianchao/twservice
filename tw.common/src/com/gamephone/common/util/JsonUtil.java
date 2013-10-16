package com.gamephone.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

public class JsonUtil {

    private static ObjectMapper mapper=new ObjectMapper();
    static {
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
//        mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String Object2Json(Object o) {
        String s=null;
        try {
            if(o != null) {
                s=mapper.writeValueAsString(o);
            }
        } catch(JsonGenerationException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> T Json2Object(String json, Class<T> c) {
        T t=null;
        try {
            if(json != null) {
                t=mapper.readValue(json, c);
            }
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T Json2Object(String json, TypeReference<T> tr) {
        T t=null;
        try {
            if(json != null) {
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                t=(T)mapper.readValue(json, tr);
            }
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return (T)t;
    }
}
