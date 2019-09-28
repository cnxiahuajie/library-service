package work.codehub.library.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.regex.Pattern;

/**
 * 字符串转换格式工具类
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static Pattern PATTERN_SCRIPT = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern PATTERN_SCRIPT_ATTR = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern PATTERN_SCRIPT_ATTR2 = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern PATTERN_SCRIPT_END = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static Pattern PATTERN_ANY_TAG = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern PATTERN_EVAL = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern PATTERN_E­XPRESSION = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern PATTERN_JAVASCRIPT = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static Pattern PATTERN_VBSCRIPT = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static Pattern PATTERN_ONLOAD = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    /**
     * 驼峰转烤串
     *
     * @param str 驼峰字符串
     * @return 烤串字符串
     */
    public static String camelToKebab(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return str;
        } else {
            char[] array = str.toCharArray();
            StringBuffer newStr = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                if (Character.isUpperCase(array[i])) {
                    array[i] = Character.toLowerCase(array[i]);
                    if (i > 0) {
                        newStr.append('-');
                    }
                }
                newStr.append(array[i]);
            }
            return newStr.toString();
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param str 驼峰字符串
     * @return 下划线字符串
     */
    public static String camelToUnderline(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return str;
        } else {
            char[] array = str.toCharArray();
            StringBuffer newStr = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                if (Character.isUpperCase(array[i])) {
                    array[i] = Character.toLowerCase(array[i]);
                    if (i > 0) {
                        newStr.append('_');
                    }
                }
                newStr.append(array[i]);
            }
            return newStr.toString();
        }
    }

    /**
     * 清除全角空格 .
     *
     * @param text 原文本
     * @return java.lang.String 清除空格后的文本
     * @author andy.sher
     * @date 2018/12/14 17:05
     */
    public static String clearDoubleByteSpace(String text) {
        if (org.apache.commons.lang3.StringUtils.isBlank(text)) {
            return text;
        } else {
            return text.replaceAll(" ", "");
        }
    }

    /**
     * 去除HTML标签 .
     *
     * @param content 内容
     * @return java.lang.String 去除HTML标签后的内容
     * @author andy.sher
     * @date 2018/11/15 16:59
     */
    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?p>", StringUtils.EMPTY);
        // 去除样式
        content = content.replaceAll("<style .*?style>", StringUtils.EMPTY);
        // <br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", StringUtils.EMPTY);
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", StringUtils.EMPTY);
        // 去掉其它&;
        content = content.replaceAll("&\\w+?;", StringUtils.EMPTY);
        return content;
    }

    /**
     * 去除xss .
     *
     * @param content 内容
     * @return java.lang.String 去除HTML标签后的内容
     * @author andy.sher
     * @date 2019/9/18 17:12
     */
    public static String stripXSS(String content) {
        if (StringUtils.isNotBlank(content)) {
            content = content.replaceAll("", "");
            content = PATTERN_SCRIPT.matcher(content).replaceAll("");
            // Avoid anything in a src="..." type of e­xpression
            content = PATTERN_SCRIPT_ATTR.matcher(content).replaceAll("");
            content = PATTERN_SCRIPT_ATTR2.matcher(content).replaceAll("");
            // Remove any lonesome </script> tag
            content = PATTERN_SCRIPT_END.matcher(content).replaceAll("");
            // Remove any lonesome <script ...> tag
            content = PATTERN_ANY_TAG.matcher(content).replaceAll("");
            // Avoid eval(...) e­xpressions
            content = PATTERN_EVAL.matcher(content).replaceAll("");
            // Avoid e­xpression(...) e­xpressions
            content = PATTERN_E­XPRESSION.matcher(content).replaceAll("");
            // Avoid javascript:... e­xpressions
            content = PATTERN_JAVASCRIPT.matcher(content).replaceAll("");
            // Avoid vbscript:... e­xpressions
            content = PATTERN_VBSCRIPT.matcher(content).replaceAll("");
            // Avoid onload= e­xpressions
            content = PATTERN_ONLOAD.matcher(content).replaceAll("");
        }
        return content;
    }

    /**
     * 获取临时文件目录 .
     *
     * @param
     * @return java.lang.String 获取临时文件目录
     * @author andy.sher
     * @date 2019/9/19 11:47
     */
    public static String getTmpPath() {
        String tmpPath = System.getProperty("java.io.tmpdir");
        if (tmpPath.length() - 1 != tmpPath.lastIndexOf(File.separator)) {
            tmpPath += File.separator;
        }
        return tmpPath;
    }

}
