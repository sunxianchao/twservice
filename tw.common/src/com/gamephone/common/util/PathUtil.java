/**
 * $Id: PathUtil.java,v 1.6 2012/04/05 08:37:20 xianchao.sun Exp $
 */
package com.gamephone.common.util;

/**
 * @author jiayu.qiu@downjoy.com
 */
public class PathUtil {

    private static PathUtil instance=new PathUtil();

    private static boolean isLinux;
    static {
        String os=System.getProperty("os.name");
        if(os != null && os.toUpperCase().indexOf("LINUX") > -1) {
            isLinux=true;
        } else {
            isLinux=false;
        }
    }

    private PathUtil() {
    }

    public static PathUtil getInstance() {
        return instance;
    }

    /**
     * 访问日志路径
     * @param dateStr
     * @return String
     */
    public String getAccessLogPath(String dateStr) {
        if(isLinux) {
            return "/home/downjoy/smartngcore/access_log_" + dateStr + ".txt";
        } else {
            return "D:/dev/smartngcore/access_log_" + dateStr + ".txt";
        }
    }

    public String getUserAvatarPath() {
        if(isLinux) {
            return "/usr/local/apache/htdocs/websmartng/upload/";
        } else {
            return "E:/workspace/web_smart_ng_op_platform/websmartng/upload/";
        }
    }

    public String getGoodsPicturePath() {
        if(isLinux) {
            return "/usr/local/apache/htdocs/websmartng/upload/goods/";
        } else {
            return "E:/workspace/web_smart_ng_op_platform/websmartng/upload/goodspic/";
        }
    }
    
    public String getAdvPicturePath(){
        if(isLinux) {
            return "/usr/local/apache/htdocs/websmartng/upload/advs/";
        } else {
            return "E:/workspace/web_smart_ng_op_platform/websmartng/upload/advs/";
        } 
    }
}
