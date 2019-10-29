package work.codehub.library.util;

import lombok.extern.slf4j.Slf4j;
import work.codehub.library.constants.CommonConstants;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类 .<br>
 *
 * @author andy.sher
 * @date 2018/7/16 14:03
 */
@Slf4j
public final class AesUtils {

    private AesUtils() {
    }

    private static final String AES = "AES";

    private static final String PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    private static final Integer BASE64_LENGTH = 4;

    private static final String DATA_UTF_8 = CommonConstants.Character.UTF_8;

    /**
     * 加密模式 .
     *
     * @author andy.sher
     * @date 2018/9/21 11:32
     */
    public enum Mode {
        /**
         * API接口
         */
        API,
        /**
         * 数据库
         */
        DB
    }

    /**
     * aes解密 .<br>
     *
     * @param encrypt 加密的字符串
     * @return java.lang.String 解密字符串
     * @author andy.sher
     * @date 2018/7/16 15:15
     */
    public static String decrypt(String encrypt, String key, String iv) {
        if (StringUtils.isNotBlank(encrypt) && Base64Utils.isBase64(encrypt) && !NumberUtils.isInteger(encrypt)
                && (encrypt.length() % BASE64_LENGTH == 0) && encrypt.length() >= 24) {
            return doDecrypt(encrypt, key, iv);
        }
        return encrypt;
    }

    /**
     * aes加密 .<br>
     *
     * @param content 普通字符串
     * @return java.lang.String 加密后的字符串
     * @author andy.sher
     * @date 2018/7/16 15:16
     */
    public static String encrypt(String content, String key, String iv) {
        final boolean base64 = Base64Utils.isBase64(content);
        if (StringUtils.isNotBlank(content) && (!base64 || (base64 && content.length() <= 24))) {
            return doEncrypt(content, key, iv);
        }
        return content;
    }

    /**
     * 执行加密 .
     *
     * @param content  加密前的字符串
     * @param keyValue 秘钥
     * @param ivValue  偏移量
     * @return java.lang.String 加密后的字符串
     */
    public static String doDecrypt(String content, String keyValue, String ivValue) {
        try {
            // 先用base64解密
            byte[] data = Base64Utils.DECODER.decode(content);
            // 执行加解密
            byte[] original = doFinal(data, keyValue, ivValue, Cipher.DECRYPT_MODE);
            return new String(original, DATA_UTF_8);
        } catch (Exception e) {
            log.error(AesUtils.class.getName(), e);
        }
        return content;
    }

    /**
     * 执行解密 .
     *
     * @param content  加密前的字符串
     * @param keyValue 秘钥
     * @param ivValue  偏移量
     * @return java.lang.String 解密后的值
     */
    public static String doEncrypt(String content, String keyValue, String ivValue) {
        try {
            byte[] data = content.getBytes(DATA_UTF_8);
            // 处理加解密
            byte[] encrypted = doFinal(data, keyValue, ivValue, Cipher.ENCRYPT_MODE);
            // 此处使用Base64做转码
            return Base64Utils.ENCODER.encodeToString(encrypted).replaceAll("\r\n", StringUtils.EMPTY);
        } catch (Exception e) {
            log.error(AesUtils.class.getName(), e);
        }
        return content;
    }

    /**
     * 创建密码书
     *
     * @param keyValue
     * @param ivValue
     * @param opmode
     * @return
     * @author oscar.yu
     * @date 2019/9/9 13:53
     */
    public static Cipher getCipher(String keyValue, String ivValue, int opmode) {
        Cipher cipher = null;
        try {
            byte[] key = keyValue.getBytes(CommonConstants.Character.ASCII);
            byte[] iv = ivValue.getBytes(DATA_UTF_8);
            // 创建密码书
            SecretKey secretKey = new SecretKeySpec(key, AES);
            IvParameterSpec secureRandom = new IvParameterSpec(iv);
            cipher = Cipher.getInstance(PKCS5_PADDING);
            cipher.init(opmode, secretKey, secureRandom);
        } catch (Exception err) {
            log.error(AesUtils.class.getName(), err);
        }
        return cipher;
    }

    /**
     * 处理加解密
     *
     * @param data
     * @param keyValue
     * @param ivValue
     * @param opmode
     * @return
     */
    private static byte[] doFinal(byte[] data, String keyValue, String ivValue, int opmode) {
        byte[] result = null;
        try {
            // 创建密码书
            Cipher cipher = getCipher(keyValue, ivValue, opmode);
            result = cipher.doFinal(data);
        } catch (Exception err) {
            log.error(AesUtils.class.getName(), err);
        }
        return result;
    }

}