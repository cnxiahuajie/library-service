package work.codehub.library.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 数字工具类 .<br>
 *
 * @author andy.sher
 * @date 2018/8/30 14:16
 */
public final class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

    /**
     * 汉语中数字大写
     */
    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

    /**
     * 汉语中货币单位大写，这样的设计类似于占位符
     */
    private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟",
            "兆", "拾", "佰", "仟" };

    /**
     * 特殊字符：整
     */
    private static final String CN_FULL = "整";

    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";

    /**
     * 金额的精度，默认值为2
     */
    private static final int MONEY_PRECISION = 2;

    /**
     * 特殊字符：零元整
     */
    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

    /**
     * 数字
     */
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 把输入的金额转换为汉语中人民币的大写 .
     *
     * @param numberOfMoney 金额
     * @return java.lang.String 金额中文字符串
     * @author andy.sher
     * @date 2018/8/30 14:20
     */
    public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
        StringBuffer sb = new StringBuffer();
        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
        // positive.
        int signum = numberOfMoney.signum();
        // 零元整的情况
        if (signum == 0) {
            return CN_ZEOR_FULL;
        }
        // 这里会进行金额的四舍五入
        long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
        // 得到小数点后两位值
        long scale = number % 100;
        int numUnit = 0;
        int numIndex = 0;
        boolean getZero = false;
        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
        if (scale <= 0) {
            numIndex = 2;
            number = number / 100;
            getZero = true;
        }
        if ((scale > 0) && (!(scale % 10 > 0))) {
            numIndex = 1;
            number = number / 10;
            getZero = true;
        }
        int zeroSize = 0;
        while (true) {
            if (number <= 0) {
                break;
            }
            // 每次获取到最后一个数
            numUnit = (int) (number % 10);
            if (numUnit > 0) {
                if ((numIndex == 9) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                if ((numIndex == 13) && (zeroSize >= 3)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                }
                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                getZero = false;
                zeroSize = 0;
            } else {
                ++zeroSize;
                if (!(getZero)) {
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                if (numIndex == 2) {
                    if (number > 0) {
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                    }
                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                getZero = true;
            }
            // 让number每次都去掉最后一个数
            number = number / 10;
            ++numIndex;
        }
        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
        if (scale <= 0) {
            sb.append(CN_FULL);
        }
        return sb.toString();
    }

    /**
     * 判断是否为整数 .
     *
     * @param str 字符串
     * @return boolean
     * @author andy.sher
     * @date 2018/9/18 18:57
     */
    public static boolean isInteger(String str) {
        return INTEGER_PATTERN.matcher(str).matches();
    }

    /**
     * 转换数字为千分位 .
     *
     * @param amt
     * @return java.lang.String
     * @author fu.tong
     * @date 2019/4/23 16:06
     */
    public static String doTransConversion(BigDecimal amt) {
        //保留两位位小数  
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return df.format(amt);
    }

}
