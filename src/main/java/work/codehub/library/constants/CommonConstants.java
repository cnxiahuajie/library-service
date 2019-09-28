package work.codehub.library.constants;

public class CommonConstants {

    /**
     * 存储模式[redis]
     */
    public static final String STORE_REDIS = "redis";

    /**
     * 存储模式[数据源]
     */
    public static final String STORE_MYSQL = "mysql";

    /**
     * 版本
     */
    public static final String VERSION = "version";

    /**
     * enable
     */
    public static final String ENABLE = "E";

    /**
     * disable
     */
    public static final String DISABLE = "D";

    /**
     * ownerId
     */
    public static final String OWNER_ID = "ownerId";

    /**
     * id
     */
    public static final String DATA_ID = "id";

    /**
     * yes
     */
    public static final String YES = "Y";

    /**
     * no
     */
    public static final String NO = "N";

    /**
     * swagger2框架常量
     */
    public final class Swagger2 {

        /**
         * 数据类型[String]
         */
        public static final String DATA_TYPE_STRING = "string";

        /**
         * 数据类型[Integer]
         */
        public static final String DATA_TYPE_INTEGER = "int";

        /**
         * 参数类型[路径]
         */
        public static final String PARAM_TYPE_PATH = "path";

        /**
         * 参数类型[查询]
         */
        public static final String PARAM_TYPE_QUERY = "query";

        /**
         * 参数类型[消息体]
         */
        public static final String PARAM_TYPE_BODY = "body";

        /**
         * 参数类型[请求头]
         */
        public static final String PARAM_TYPE_HEADER = "header";

        /**
         * 参数类型[表单]
         */
        public static final String PARAM_TYPE_FORM = "form";

    }

    /**
     * 英文字母表
     */
    public final class EnglishAlphabet {

        public static final String A = "A";
        public static final String B = "B";
        public static final String C = "C";
        public static final String D = "D";
        public static final String E = "E";
        public static final String F = "F";
        public static final String G = "G";
        public static final String H = "H";
        public static final String I = "I";
        public static final String J = "J";
        public static final String K = "K";
        public static final String L = "L";
        public static final String M = "M";
        public static final String N = "N";
        public static final String O = "O";
        public static final String P = "P";
        public static final String Q = "Q";
        public static final String R = "R";
        public static final String S = "S";
        public static final String T = "T";
        public static final String U = "U";
        public static final String V = "V";
        public static final String W = "W";
        public static final String X = "X";
        public static final String Y = "Y";
        public static final String Z = "Z";

    }

    /**
     * 阿拉伯字母
     */
    public final class ArabicNumeral {

        public static final String N0 = "0";
        public static final String N1 = "1";
        public static final String N2 = "2";
        public static final String N3 = "3";
        public static final String N4 = "4";
        public static final String N5 = "5";
        public static final String N6 = "6";
        public static final String N7 = "7";
        public static final String N8 = "8";
        public static final String N9 = "9";

    }

    /**
     * 协议
     */
    public final class Protocol {

        public static final String HTTP = "http";

        public static final String HTTPS = "https";

        public static final String WS = "ws";

        public static final String WSS = "wss";

    }

    /**
     * 分页
     */
    public final class Pagination {

        public static final String PAGE_SIZE = "10";

    }

    /**
     * 字符编码
     */
    public final class Character {

        /**
         * 万国码
         */
        public static final String UTF_8 = "UTF-8";

        /**
         * 汉字内码扩展规范
         */
        public static final String GBK = "GBK";

        /**
         * 汉字内码扩展规范
         */
        public static final String GB2312 = "GB2312";

        /**
         * ASCII
         */
        public static final String ASCII = "ASCII";
    }

    /**
     * 日期格式常量类 .
     *
     * @author andy.sher
     * @date 2018/8/14 14:27
     */
    public final class DateFormat {
        public static final String TO_MONTH = "yyyy-MM";

        public static final String TO_DATE = "yyyy-MM-dd";

        public static final String TO_MINUTE = "yyyy-MM-dd HH:mm";

        public static final String TO_SECOND = "yyyy-MM-dd HH:mm:ss";

        public static final String TO_MILLISECOND = "yyyy-MM-dd HH:mm:ss SSSS";

        public static final String TO_MONTHDATE = "MM-dd";

        public static final String TO_DATE_N = "yyyyMMdd";

        public static final String TO_HOUR_N = "yyyyMMddHH";

        public static final String TO_MINUTE_N = "yyyyMMddHHmm";

        public static final String TO_SECOND_N = "yyyyMMddHHmmss";

        public static final String TO_MILLISECOND_N = "yyyyMMddHHmmssSSSS";

        public static final String TO_SHORT_MILLISECOND_N = "yyMMddHHmmssSSSS";

        public static final String YEAR = "y";

        public static final String MONTH = "m";

        public static final String DAY = "d";

        public static final String TO_DATE_SLASH = "yyyy/M/d";

        public static final String HOUR_N = "H";

        public static final String HOUR = "HH";

        public static final String TO_DATE_SIMPLE = "yyyy年MM月dd日";
    }

    /**
     * 符号 .<br>
     *
     * @author andy.sher
     * @date 2018/8/7 13:29
     */
    public final class Symbol {
        /**
         * 逗号
         */
        public static final String COMMA = ",";

        /**
         * 减号
         */
        public static final String MINUS = "-";

        /**
         * 空格
         */
        public static final String SPACE = " ";

        /**
         * 点
         */
        public static final String DOT = ".";
        /**
         * 正斜杠
         */
        public static final String SLASH = "/";

        /**
         * 反斜杠
         */
        public static final String BACKSLASH = "\\";

        /**
         * 下划线
         */
        public static final String UNDERLINE = "_";

        /**
         * 左中括号
         */
        public static final String LEFT_SQUARE_BRACKET = "[";

        /**
         * 右中括号
         */
        public static final String RIGHT_SQUARE_BRACKET = "]";

        /**
         * 全角左中括号
         */
        public static final String DOUBLE_BYTE_LEFT_SQUARE_BRACKET = "［";

        /**
         * 全角右中括号
         */
        public static final String DOUBLE_BYTE_RIGHT_SQUARE_BRACKET = "］";

        /**
         * 星号
         */
        public static final String ASTERISK = "*";

        /**
         * 英文问号
         */
        public static final String QUESTION_EN = "?";

        /**
         * 顿号
         */
        public static final String DAWN = "、";

        /**
         * 全角空格
         */
        public static final String DOUBLE_BYTE_SPACE = " ";

        /**
         * 等号
         */
        public static final String EQUAL_MARK = "=";

        /**
         * 英文加号
         */
        public static final String PLUS = "+";

        /**
         * 左括号
         */
        public static final String LEFT_BRACKET = "(";

        /**
         * 右括号
         */
        public static final String RIGHT_BRACKET = ")";

        /**
         * 分号
         */
        public static final String SEMICOLON = ":";

        /**
         * 百分号
         */
        public static final String PERCENT_SIGN = "%";

        /**
         * 句号
         */
        public static final String PERCENT_STOP = "。";

        /**
         * 中文逗号
         */
        public static final String COMMA_CN = "，";

        /**
         * 与
         */
        public static final String AND = "&";

        /**
         * 或
         */
        public static final String OR = "|";

        /**
         * 非
         */
        public static final String NOT = "!";

    }

}
