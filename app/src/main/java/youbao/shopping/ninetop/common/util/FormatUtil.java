package youbao.shopping.ninetop.common.util;

import youbao.shopping.ninetop.common.constant.TextConstant;

/**
 * Created by jill on 2016/12/21.
 */

public class FormatUtil {

    public static String format(double value) {
        return String.format("%.2f", value);
    }

    public static String formatMoney(float value){
        String stringValue = format(value);
        return formatMoney(stringValue);
    }

    public static String formatMoney(double value){
        String stringValue = format(value);
        return formatMoney(stringValue);
    }

    public static String formatMoney(String value){
        return TextConstant.MONEY + value;
    }
}
