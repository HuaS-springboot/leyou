package com.vsj.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUtils {
    /**
     * 微信支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3).
     *
     * @param params   参数信息
     * @param signType 签名类型，如果为空，则默认为MD5
     * @param signKey  签名Key
     * @return 签名字符串
     */
    public static String createSign(Map<String, String> params, String signType, String signKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (value != null)
                toSign.append(key).append("=").append(value).append("&");
        }
        toSign.append("key=").append(signKey);
        if ("256".equals(signType)) {
            return createHmacSha256Sign(toSign.toString(), signKey);
        } else {
            return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
        }
    }

    private static String createHmacSha256Sign(String message, String key) {
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256.init(secretKeySpec);
            byte[] bytes = sha256.doFinal(message.getBytes());
            return Hex.encodeHexString(bytes).toUpperCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则

    /**
     * 去除字符串中的中文
     *
     * @param str
     * @return
     */
    public static String wipeChinese(String str) {
        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(str);
        String string = mat.replaceAll("");
        return string;
    }

    /**
     * 对字符串进行冒泡排序
     *
     * @param str
     * @return
     */
    public static String strSort(String str) {
        char[] arrayCh = str.toCharArray();
        Arrays.sort(arrayCh);
        return String.copyValueOf(arrayCh);
    }
}
