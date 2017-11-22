package com.lakala.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptUtils {
    private static final Logger logger = Logger.getLogger(EncryptUtils.class);
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    public static String decodeBase64(String encoder) {
        byte[] decoderByte = Base64.decodeBase64(encoder.getBytes(UTF_8_CHARSET));
        String decoder = new String(decoderByte, UTF_8_CHARSET);
        return decoder;
    }

    public static String encodeMD5(String code) {
        MessageDigest md5 = null;
        String encoder = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] encoderByte = md5.digest(code.getBytes(UTF_8_CHARSET));
            encoder = binaryToHexString(encoderByte);
        } catch (NoSuchAlgorithmException e) {
            logger.error("EncryptUtils: encodeMD5 hit exception" + e);
        }
        return encoder;
    }

    public static String encodeMD5WithSalt(String code, int salt) {
        return new Md5Hash(code, String.valueOf(salt)).toString();
    }

    public static String binaryToHexString(byte[] md5Bytes) {

        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static int getSalt() {
        Random random = new Random();
        int result = random.nextInt();
        return result;
    }

}
