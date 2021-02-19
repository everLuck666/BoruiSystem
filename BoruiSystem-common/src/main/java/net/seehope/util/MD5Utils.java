package net.seehope.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    private static String getMd5(String plainText, boolean md5Format, String charset) {
        String md5Str = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(plainText.trim().getBytes(charset));
            byte[] hashBytes = messageDigest.digest();
            int i;
            StringBuffer tempStr = new StringBuffer(32);
            for (int offset = 0; offset < hashBytes.length; offset++) {
                i = hashBytes[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    tempStr.append("0");
                }
                tempStr.append(Integer.toHexString(i));
            }
            if (md5Format) {
                md5Str = tempStr.toString().toUpperCase();
            } else {
                md5Str = tempStr.toString().substring(8, 24).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

}