package com.csair.csairmind.hunter.common.util;

import java.security.MessageDigest;

/**
 * Created by ZC on 2016/5/12.
 */
public class Md5Code {
    public static void main(String[] args) {
        System.out.println(md5ByHex("sdas12"));
    }

    /**
     * MD5加密(32位大写)
     * @param src
     * @return
     */
    public static String md5ByHex(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = src.getBytes("GBK");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            String hs = "";
            String stmp = "";
            for (int i = 0; i < hash.length; i++) {
                stmp = Integer.toHexString(hash[i] & 0xFF);
                if (stmp.length() == 1)
                    hs = hs + "0" + stmp;
                else {
                    hs = hs + stmp;
                }
            }
            return hs;
        } catch (Exception e) {
            return "";
        }
    }
}
