package youbao.shopping.ninetop.update;

import android.os.Build;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: 字符串工具类         </p>
 * <p>Description: </p>
 * <p>包括如下功能�?/p>
 * <p>1. 判断字符串是否为�?/p>
 * <p>2. 判断是否手机号码</p>
 * <p>3. 判断密码是否可用</p>
 * <p>4. 比较字符串是否相�?/p>
 * <p>5. 判断是否为纯数字</p>
 * <p>6. HTML 编码</p>
 * <p>7. String转拼�?/p>
 * <p>8. 判断字符串是否为拼音</p>
 * <p>9. 判断字符串是否包含中�?/p>
 * <p>@author: Eric.wsd                </p>
 * <p>Copyright: Copyright (c) 2012    </p>
 * <p>Company: FFCS Co., Ltd.          </p>
 * <p>Create Time: 2012-6-13             </p>
 * <p>Update Time:                     </p>
 * <p>Updater:                         </p>
 * <p>Update Comments:                 </p>
 */
public class StringUtil {


    public static String join(Collection<String> s, String delimiter) {
        if (s.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (String str : s) {
            sb.append(str).append(delimiter);
        }
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s) || s.equals("null") || s.equals("");
    }

    /**
     * 判断字符串是否纯数字
     *
     * @param str 需要判断的字符串
     * @return true 是纯数字; false 不是纯数字
     */
    public static boolean isDigital(String str) {
        if (!isEmpty(str))
            return str.matches("[0-9]+");
        return false;
    }
    /**
     * 是否手机号码
     * @param mobiles
     * @return
     */
//	public static boolean isMobile(String mobiles) {
//		if (StringUtil.isEmpty(mobiles)) {
//			return false;
//		}
//		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Matcher m = p.matcher(mobiles);
//		return m.matches();
//	}

    /**
     * 密码是否可用
     * 密码�?���?0�?   panxd
     *
     * @param pwd
     * @return
     */
    public static boolean isPwdValid(String pwd) {
        Pattern p = Pattern.compile("[a-zA-Z0-9]{0,20}");
        Matcher m = p.matcher(pwd);

        return m.matches();
    }

    /**
     * 比较字符串是否相�?
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        return TextUtils.equals(a, b);
    }

    /**
     * Returns whether the given CharSequence contains only digits
     *
     * @param str
     * @return
     */
    public static boolean isDigitsOnly(CharSequence str) {
        return TextUtils.isDigitsOnly(str);
    }

    /**
     * html encode
     *
     * @param s
     * @return
     */
    public static String htmlEncode(String s) {
        return TextUtils.htmlEncode(s);
    }

    /**
     * 由全角转半角
     *
     * @param s
     * @return
     */
    public static String toSBC(String s) {
        if (StringUtil.isEmpty(s)) {
            return "";
        }
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字�?
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 分割一串数字，按照4位中间加个空格
     *
     * @param cardNo
     * @return
     */
    public static String splitBankCardNo(String cardNo) {
        if (TextUtils.isEmpty(cardNo) || cardNo.length() <= 4) {
            return cardNo;
        }
        int aLen = ((cardNo.length()) % 4 == 0) ? cardNo.length() / 4 : cardNo.length() / 4 + 1;
        String[] a = new String[aLen];
        String result = "";
        for (int i = 0; i < aLen; i++) {
            int begin = i * 4;
            int end = (i * 4 + 4) > cardNo.length() ? cardNo.length() : i * 4 + 4;
            a[i] = cardNo.substring(begin, end);
        }
        for (int i = 0; i < aLen; i++) {
            result = result + a[i] + " ";
        }
        return result;
    }

    /**
     * 是否为拼音字符串
     *
     * @param str
     * @return
     */
    public static boolean isPinYin(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean containCn(String str) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        return pattern.matcher(str).find();
    }

    public static boolean isJB() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isJB1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isL() {
        return Build.VERSION.SDK_INT >= 20;
    }

    /**
     * 比较指定格式版本号 XXX.XXX.XXX  （注意必须为类似1.0.1这样的格式）
     *
     * @param version1
     * @param version2
     * @return
     * @throws Exception
     */
    public static int cmpVersion(String version1, String version2) throws Exception {
        try {
            if (version1 == null) {
                throw new NullPointerException();
            }
            if (version2 == null) {
                throw new NullPointerException();
            }
            String[] array1 = version1.split("\\.");
            String[] array2 = version2.split("\\.");
            if (array1.length != 3 || array2.length != 3) {
                throw new Exception("版本号格式非法");
            }
            version1 = formatVersion(array1);
            version2 = formatVersion(array2);
            return version1.compareTo(version2);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    /**
     * 格式化字符串为3位
     *
     * @param array
     * @return
     */
    private static String formatVersion(String[] array) {
        String retValue = "";
        for (int i = 0; i < array.length; i++) {
            String simple = "000" + array[i].trim();
            retValue += simple.substring(simple.length() - 3, simple.length());
        }
        return retValue;
    }

    /**
     * @param t
     * @param length
     * @return
     * @前补足0至长度等于length
     */
    public static <T> String addHeadZero(T t, int length) {
        String src = t.toString();
        if (isEmpty(src) || src.length() >= length) {
            return src;
        }
        String rest = String.format("%1$0" + (length - src.length()) + "d", 0)
                + src;
        return rest;
    }

    /**
     * @param t
     * @param length
     * @return
     * @后补足0至长度等于length
     */
    public static <T> String addBackZero(T t, int length) {
        String src = t.toString();
        if (isEmpty(src) || src.length() >= length) {
            return src;
        }
        String rest = src
                + String.format("%1$0" + (length - src.length()) + "d", 0);
        return rest;
    }

    /**
     * @param t
     * @param length
     * @return
     * @后补空格至长度等于length
     */
    public static <T> String addBackSpace(T t, int length) {
        if (t == null) {
            return "";
        }
        String src = t.toString();
        int srclen = src.replaceAll("[^\\x00-\\xff]", "**").length(); // 中文占2位长度
        if (srclen >= length) {
            return src;
        }
        String rest = src + String.format("%1$" + (length - srclen) + "s", "");
        return rest;
    }

    /**
     * @return
     * @末尾填充FF长度满8字节整数倍
     */
    public static String fillBackChar(String src, char character) {
        if (isEmpty(src) || src.length() % 16 == 0) {
            return src;
        }
        StringBuffer sbf = new StringBuffer(src);
        while (sbf.length() % 16 != 0) {
            sbf.append(character);
        }
        return sbf.toString(); // 将补位后的值返回
    }

    /**
     * @用途：检查输入手机号码是否正确
     * @输入： s：字符串 返回： 如果通过验证返回true,否则返回false
     */
    public static boolean isMobile(String s) {
        // String regu="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String regu = "^[1][3-9][0-9]{9}$";
        return s != null ? s.matches(regu) : false;
    }

    /**
     * @return
     * @判断符合SN号规则
     */
    public static boolean isTermSN(String s) {
        String regu = "/[^a-zA-Z0-9]/g";
        return isEmpty(s) ? false : s.matches(regu);
    }

    /**
     * @return
     * @格式化金额转换
     */
    public static String unformatMount(String mount) {
        if (isEmpty(mount)) {
            return "0.00";
        }
        double money = Double.parseDouble(mount) * 0.01D;
        if (money > 0.0D) {
            DecimalFormat df = new DecimalFormat("##0.00");
            return df.format(money);
        }
        return "0.00";
    }

    /**
     * @param <T>
     * @return
     * @格式化金额转换
     */
    public static <T> String formatMount(T t) {
        DecimalFormat df = new DecimalFormat("##0.00");
        if (t instanceof String) {
            double d = Double.valueOf(t.toString());
            return df.format(d).replace(".", "");
        }
        if (t instanceof Integer || t instanceof Double || t instanceof Short
                || t instanceof Float || t instanceof Long) {
            return df.format(t).replace(".", "");
        }
        return t.toString();
    }

    /**
     * @param phoneno
     * @return
     * @格式化卡号
     * @显示前6位和后4位
     */
    public static String formatMobile(String phoneno) {
        if (isEmpty(phoneno) || phoneno.length() < 11) {
            return phoneno;
        }
        String midString = "*******************************".substring(0,
                phoneno.length() - 4);
        String preString = phoneno.substring(0, 3);
        String lasString = phoneno.substring(phoneno.length() - 4,
                phoneno.length());
        return preString + midString + lasString;
    }

    /**
     * @param cardno
     * @return
     * @格式化卡号
     * @显示前3位和后4位
     */
    public static String formatCardno(String cardno) {
        if (isEmpty(cardno) || cardno.length() < 12) {
            return cardno;
        }
        String midString = "*******************************".substring(0,
                cardno.length() - 10);
        String preString = cardno.substring(0, 6);
        String lasString = cardno.substring(cardno.length() - 4,
                cardno.length());
        return preString + midString + lasString;
    }


    /**
     * @return
     * @去除奇数长度时的末尾字符
     */
    public static String dislodgeLastLetter(String src) {
        if (isEmpty(src) || src.length() % 2 == 0) {
            return src;
        }
        return src.substring(0, src.length() - 1);
    }

    /**
     * 数字格式化（每三位加上一个逗号）。
     */
    public static String addComma(String number) {
        double result;
        try {
            result = Double.valueOf(number);
        } catch (Exception e) {
            result = 0.00;
        }
        return new DecimalFormat("#,##0.00").format(result);
    }


    /**
     * 打印map数据
     *
     * @param map
     * @return
     */
    public static String map2LineStr(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        if (map == null) {
            return "";
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + " :: " + entry.getValue() + " \r\n ");
        }
        return sb.toString();
    }


    /**
     * 方法名称:transMapToString
     * 传入参数:map
     * 返回值:String 形如 username'chenziwen^password'1234
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("'").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }


    /**
     * 方法名称:transStringToMap
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     * 返回值:Map
     */
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        if (TextUtils.isEmpty(mapString)) {
            return map;
        }
        StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }


}
