package com.tupperware.biz.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.webkit.CookieManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by dhunter on 2018/3/2.
 */

public class StringUtils {

    public static String MD5(String origString) {
        String md5String = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] resultByteArray = messageDigest.digest(origString.getBytes("UTF-8"));
            int val;
            StringBuffer buff = new StringBuffer("");
            for (int i = 0; i < resultByteArray.length; i++) {
                val = resultByteArray[i];
                if (val < 0)
                    val += 256;
                else if (val < 16)
                    buff.append("0");
                buff.append(Integer.toHexString(val));
            }
            md5String = buff.toString();
//            str.toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return md5String;
    }

    public static String getSessionId(String url){
        String sessionIdString = "";
        String cookies = CookieManager.getInstance().getCookie(url);
        if (null != cookies) {
            String[] resList = cookies.split(";");
            for (int i = 0; i < resList.length; i++) {
                String[] value = resList[i].trim().split("=");
                if (value[0].equalsIgnoreCase("PHPSESSID")) {
                    sessionIdString = value[1];
                    break;
                }
            }
        }

        return sessionIdString;
    }

    private static String encode(String path) {
        //decode to bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;

    }

    public static int StringChangeToInt(String string) {
        int a = 0;
        try{
            a = Integer.parseInt(string);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return a;
    }

    public static long StringChangeToLong(String string) {
        long a = 0;
        try{
            a = Long.parseLong(string);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return a;
    }

    public static String getTestCount() {
        Random random = new Random();
        return random.nextInt(100) + "";
    }

    /**
     * 将double转为数值，并最多保留num位小数。例如当num为2时，1.268为1.27，1.2仍为1.2；1仍为1，而非1.00;100.00则返回100。
     *
     * @param d
     * @param num 小数位数
     * @return
     */
    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);//保留两位小数
        nf.setGroupingUsed(false);//去掉数值中的千位分隔符

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!s2.substring(i - 1, i).equals("0")) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 将double转为数值，并最多保留num位小数。
     *
     * @param d
     * @param num 小数个数
     * @param defValue 默认值。当d为null时，返回该值。
     * @return
     */
    public static String double2String(Double d, int num, String defValue){
        if(d==null){
            return defValue;
        }

        return double2String(d,num);
    }

    /**
     * 去掉转义符号
     * @param str
     * @return
     * @throws IOException
     */
    public static String unescapeJava(String str) throws IOException
    {
        Writer out = new StringWriter();
        if (str != null)
        {
            int sz = str.length();
            StringBuilder unicode = new StringBuilder(4);
            boolean hadSlash = false;
            boolean inUnicode = false;

            for (int i = 0; i < sz; ++i)
            {
                char ch = str.charAt(i);
                if (inUnicode)
                {
                    unicode.append(ch);
                    if (unicode.length() == 4)
                    {
                        try
                        {
                            int nfe = Integer.parseInt(unicode.toString(), 16);
                            out.write((char) nfe);
                            unicode.setLength(0);
                            inUnicode = false;
                            hadSlash = false;
                        }
                        catch (NumberFormatException var9)
                        {
                        }
                    }
                }
                else if (hadSlash)
                {
                    hadSlash = false;
                    switch (ch)
                    {
                        case '\"':
                            out.write(34);
                            break;
                        case '\'':
                            out.write(39);
                            break;
                        case '\\':
                            out.write(92);
                            break;
                        case 'b':
                            out.write(8);
                            break;
                        case 'f':
                            out.write(12);
                            break;
                        case 'n':
                            out.write(10);
                            break;
                        case 'r':
                            out.write(13);
                            break;
                        case 't':
                            out.write(9);
                            break;
                        case 'u':
                            inUnicode = true;
                            break;
                        default:
                            out.write(ch);
                    }
                }
                else if (ch == 92)
                {
                    hadSlash = true;
                }
                else
                {
                    out.write(ch);
                }
            }

            if (hadSlash)
            {
                out.write(92);
            }

        }
        return out.toString();
    }

    /**
     *
     * map转换json.
     * <br>详细说明
     * @param map 集合
     * @return
     * @return String json字符串
     * @throws
     * @author slj
     */
    public static String mapToJson(Map<String, Object> map) {
        Set<String> keys = map.keySet();
        String key = "";
        Object value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            key = (String) it.next();
            value = map.get(key);
            jsonBuffer.append(key + ":" +"\""+ value+"\"");
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * 每隔8个字符换行
     */
    public static String insertNewLine(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String temp = str;
        int len = str.length();
        if(len <= 8) {
            return str;
        } else if(len < 16) {
            String before = str.substring(0,8);
            sb.append(before).append("\n").append(str.substring(8, len -1));
            return sb.toString();
        } else {
            String before = str.substring(0,8);
            sb.append(before).append("\n").append(str.substring(8, 14)).append("...");
            return sb.toString();
        }
    }

    /**
     * 插入方法
     *
     * @param num
     *            每隔几个字符插入一个字符串（中文字符）
     * @param splitStr
     *            待指定字符串
     * @param str
     *            原字符串
     * @return 插入指定字符串之后的字符串
     * @throws UnsupportedEncodingException
     */
    public static String addStr(int num, String splitStr, String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String temp = str;

        int len = str.length();
        while (len > 0) {
            int idx = getEndIndex(temp, num);
            sb.append(temp.substring(0, idx + 1)).append(splitStr);
            temp = temp.substring(idx + 1);
            len = temp.length();
        }

        return sb.toString();
    }

    /**
     * 两个数字/英文
     *
     * @param str
     *            字符串
     * @param num
     *            每隔几个字符插入一个字符串
     * @return int 最终索引
     * @throws UnsupportedEncodingException
     */
    public static int getEndIndex(String str, double num) throws UnsupportedEncodingException {
        int idx = 0;
        double val = 0.00;
        // 判断是否是英文/中文
        for (int i = 0; i < str.length(); i++) {
            if (String.valueOf(str.charAt(i)).getBytes("UTF-8").length >= 3) {
                // 中文字符或符号
                val += 1.00;
            } else {
                // 英文字符或符号
                val += 0.50;
            }
            if (val >= num) {
                idx = i;
                if (val - num == 0.5) {
                    idx = i - 1;
                }
                break;
            }
        }
        if (idx == 0) {
            idx = str.length() - 1;
        }
        return idx;
    }

}
