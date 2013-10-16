package com.gamephone.common.context;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gamephone.common.util.FilterUtil;

public class SystemProperties {

    final static Logger loger=Logger.getLogger(SystemProperties.class);

    private static Properties props;

    /**
     * 加载指定的多个资源文件。其中资源文件是以"a.properities \n b.properities 形式给出
     * @param filename
     */
    public static void loadPropertyFile(String filename) {
        loger.info("Loading system initialize rules file from:" + filename);
        props=new Properties();
        InputStream in=null;
        try {
            List<String> filenames=FilterUtil.spliteUrlPatterns(filename);

            if(filenames != null) {
                for(String filename1: filenames) {
                    in=SystemProperties.class.getClassLoader().getResourceAsStream(filename1);
                    props.load(new InputStreamReader(in, "UTF-8"));
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            loger.error("Fail to load system initialize rules file.", ex);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(java.io.IOException e) {
                    e.printStackTrace();
                    loger.error("I/O Exception at close InputStream.");
                }
            }
        }
    }

    /**
     * 加载指定的多个资源文件。
     * @param filename
     */
    public static void loadPropertyFiles(List<String> filenames) {
        props=new Properties();
        InputStream in=null;
        try {
            if(filenames != null) {
                for(String filename1: filenames) {
                    in=SystemProperties.class.getClassLoader().getResourceAsStream(filename1);
                    props.load(new InputStreamReader(in, "UTF-8"));
                }
            }
        } catch(Exception ex) {
            loger.error("Fail to load system initialize rules file.", ex);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(java.io.IOException e) {
                    loger.error("I/O Exception at close InputStream.");
                }
            }
        }
    }

    /**
     * Get the value in system property file by the key
     * @param key key String
     * @return String
     */
    public static String getProperty(String key) {
        Object obj=props.get(key);
        if(obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    /**
     * Get the value in system property file by the key, with a default value,once the value is not set.
     * @param key key String
     * @param defaultValue defaultValue String
     * @return String
     */
    public static String getProperty(String key, String defaultValue) {
        if(props == null)
            return defaultValue;
        Object obj=props.get(key);
        if(obj != null) {
            return obj.toString();
        } else {
            return defaultValue;
        }
    }
}
