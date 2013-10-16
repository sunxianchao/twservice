/**
 * $Id: FileUtil.java,v 1.1 2012/03/23 11:46:32 xianchao.sun Exp $
 */
package com.gamephone.admin.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author jiayu.qiu@downjoy.com
 */
public class FileUtil {

    public static String getFileContent(File file) throws IOException {
        byte[] buf=getFileBinaryContent(file);
        return new String(buf);
    }

    public static String getFileContent(File file, String charSet) throws IOException {
        if(null == charSet || charSet.length() == 0) {
            return getFileContent(file);
        }
        byte[] buf=getFileBinaryContent(file);
        return new String(buf, charSet);
    }

    public static byte[] getFileBinaryContent(File file) throws IOException {
        byte[] buffer=new byte[1024];
        int len=-1;
        InputStream is=null;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            is=new FileInputStream(file);
            while((len=is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch(IOException ex) {
            throw ex;
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bos.toByteArray();
    }

    public static void appendToFile(String content, File file) {
        File dir=file.getParentFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }
        OutputStream fos=null;
        try {
            fos=new FileOutputStream(file, true);
            OutputStreamWriter write=new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter writer=new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(null != fos) {
                try {
                    fos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获取文件扩展�?
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        int ind=fileName.lastIndexOf('.');
        String ext=null;
        if(ind > 0) {
            ext=fileName.substring(ind).toLowerCase();
        }
        return ext;
    }
}
