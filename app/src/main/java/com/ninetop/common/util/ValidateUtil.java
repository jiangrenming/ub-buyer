package com.ninetop.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jill on 2016/11/10.
 */

public class ValidateUtil {

    public static boolean isEmail(String Str) {
        boolean tag = true;
        final String patternStr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher mat = pattern.matcher(Str);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    public static boolean isMobilePhone(String str) {
        if ("".equals(str)) {
          ToastUtils.showCenter("手机号不能为空");
        }
        final String patternStr = "^1[3|4|5|7|8][0-9]\\d{8}$";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isIDCard(String str) {
        String patternStr = "\\d{14}[[0-9],0-9xX]";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFloat(String str) {
        return str.matches("^(|[+-]?(0|([1-9]\\d*)|((0|([1-9]\\d*))?\\.\\d{1,2})){1,1})$");
    }

    public static boolean isFreeca(String str) {
        return str.matches("^\\d{16}$");
    }

    public static boolean isMoney(String money) {

        return money.matches("^(([0-9]|([1-9][0-9]{0,9}))((\\.[0-9]{1,2})?))$");
    }
}
