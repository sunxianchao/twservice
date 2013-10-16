/**
 * $Id: CSVCreater.java,v 1.2 2012/06/12 04:52:34 xianchao.sun Exp $
 */
package com.gamephone.admin.common.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author xianchao.sun@downjoy.com 
 * 生成csv文件的处理类
 */
public class CSVCreater {

    private OutputStream out=null;

    private StringBuilder sb=null;

    private boolean convertFlag=false;

    public static final String DEL_CHAR=",";

    public static final String AV_CHAR="\"";

    public CSVCreater(String arg) throws IOException {
        out=new FileOutputStream(arg, false);
        sb=new StringBuilder();
    }

    public CSVCreater(OutputStream outStream) throws IOException {
        this.out=outStream;
        sb=new StringBuilder();
    }

    public CSVCreater setData(String data) {
        if(convertFlag) {
            data=CSVEncode(data);
        }
        sb.append(AV_CHAR);
        sb.append(data);
        sb.append(AV_CHAR);
        sb.append(DEL_CHAR);
        return this;
    }

    public void setConvertFlag(boolean b) {
        convertFlag=b;
    }

    public void writeLine() {
        if(sb.charAt(sb.length() - 1) == ',') {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("\r\n");
    }

    public void writeDataByLine(String[] args) {
        for(int i=0; i < args.length; i++) {
            setData(args[i]);
        }
        writeLine();
    }

    public void writeAndClose() throws IOException {
        InputStream in=null;
        try {
            in=new ByteArrayInputStream(sb.toString().getBytes("GB2312"));
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
            sb=null;
        } catch(IOException e) {
            throw e;
        } finally {
            in.close();
            out.close();
        }
    }

    public static String CSVEncode(String in) {
        String out="";
        if(in == null) {
            return out;
        }
        out=in.replaceAll("&", "&amp;");
        out=out.replaceAll("\"", "&quot;");
        return out;
    }

    public static String CSVDecode(String in) {
        String out="";
        if(in == null) {
            return out;
        }
        out=in.replaceAll("&quot;", "\"");
        out=out.replaceAll("&amp;", "&");
        return out;
    }

    public static void main(String[] args) {
        try {
            CSVCreater csvCre=new CSVCreater("C:\\test.csv");
            csvCre.setConvertFlag(false);
            csvCre.setData("aaa");
            csvCre.setData("aa,a");
            csvCre.writeLine();
            csvCre.setData("aa\"a");
            csvCre.setData("aa,a");
            csvCre.setData("aa,a");
            csvCre.writeLine();
            csvCre.setData("aa\"a");
            csvCre.setData("aa,\"a");
            csvCre.setData("aa,\"a");
            csvCre.setData("aa,\"a");
            csvCre.setData("aa,\"a");
            csvCre.writeLine();
            csvCre.writeAndClose();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}