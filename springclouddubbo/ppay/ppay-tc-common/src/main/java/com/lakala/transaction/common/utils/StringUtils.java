package com.lakala.transaction.common.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2017/9/15.
 */
public class StringUtils {

    public static String toStrValue(Object item){
        if(item == null){
            return "";
        }
        return String.valueOf(item);
    }
    public static int toIntValue(Object item){
        if(item == null){
            return 0;
        }
        return Integer.valueOf(toStrValue(item));
    }

    public static Date toDateValue(Object value){
        if(value == null){
            return null;
        }
        if (value instanceof java.sql.Date) {
            java.sql.Date date = (java.sql.Date)value;
            return new Date(date.getTime());
        } else if (value instanceof java.sql.Timestamp) {
            java.sql.Timestamp date = (java.sql.Timestamp)value;
            return new Date(date.getTime());
        } else if (value instanceof java.util.Date) {
            Date date = (Date)value;
            return new Date(date.getTime());
        }  else {

            Date date = null;
            try{
                date = getYMD(toStrValue(value));
            } catch (ParseException ex){
                date = (Date) value;
            }
            return date;
        }
    }

    private static final String fmtYYYYMMDD = "yyyy-MM-dd HH:mm:ss";
    public static Date getYMD(String date) throws ParseException {
        Date fmtNow = new SimpleDateFormat(fmtYYYYMMDD).parse(date);
        return fmtNow;
    }

    public static byte[] toByteValue(Object item){
        if(item == null){
            return null;
        }
        if(item instanceof java.sql.Blob){
            return blobToBytes((Blob)item);
        }
        return toStrValue(item).getBytes();
    }

    /**
     * 把Blob类型转换为byte数组类型
     *
     * @param blob
     * @return
     */
    public static byte[] blobToBytes(Blob blob) {
        BufferedInputStream is = null;
        try {
            is = new BufferedInputStream(blob.getBinaryStream());
            byte[] bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;
            while (offset < len
                    && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                is.close();
                is = null;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static String getStr(Object... items){
        if(items == null || items.length == 0){
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for(Object item: items){
            buffer.append(toStrValue(item) + ",");
        }
        String result = buffer.toString();
        return result.substring(0, result.length() - 1);
    }
}
