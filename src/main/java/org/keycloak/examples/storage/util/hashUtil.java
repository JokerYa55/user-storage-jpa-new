/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.keycloak.examples.storage.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vasil
 */
public class hashUtil {

    /**
     * Функция для получения hash SHA-1 из текста в виде набора байтов
     */
    public static byte[] getSHA_1(String text) {
        byte[] res = null;
        try {
            MessageDigest md = MessageDigest.getInstance("sha");
            md.update(text.getBytes());
            res = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(hashUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    private static String convertToHexString(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}
