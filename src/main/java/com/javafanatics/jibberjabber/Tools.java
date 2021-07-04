package com.javafanatics.jibberjabber;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {
    // Create an MD5 hash for a static random gravatar
    public static String getMD5Hash(String email) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] data = messageDigest.digest(email.getBytes());
            StringBuffer buffer = new StringBuffer();

            for (int x = 0; x < data.length; x++) {
                buffer.append(Integer.toHexString(data[x] & 0xFF | 0x100).substring(1, 3));
            }

            return buffer.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            // Nope, Chuck Testa!
        }

        // Return nothing in case of errors
        return null;
    }
}
