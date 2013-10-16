/**
 * $Id: JSONUtil.java,v 1.2 2012/04/16 06:48:00 jiayu.qiu Exp $
 */
package com.gamephone.admin.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 * JSON工具
 * @author jiayu.qiu@downjoy.com
 */
public class JSONUtil {

    /**
     * 将Java对象转化为JSON字符�?
     * @param obj
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static String getJSON(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        if(null == obj) {
            return "";
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String jsonStr=mapper.writeValueAsString(obj);
        return jsonStr;
    }

    /**
     * 将JSON字符串转化为Java对象
     * @param obj
     * @return
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object getObj(String json, Class clazz) throws JsonParseException, JsonMappingException, IOException {
        if(null == json || json.length() == 0) {
            return null;
        }
        ObjectMapper mapper=new ObjectMapper();
        mapper.getDeserializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if(json.startsWith("[")) {
            List result=mapper.readValue(json, TypeFactory.collectionType(ArrayList.class, clazz));
            return result;
        }
        return mapper.readValue(json, clazz);
    }
}
