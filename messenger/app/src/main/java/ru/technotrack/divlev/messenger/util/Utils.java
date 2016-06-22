package ru.technotrack.divlev.messenger.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private Utils() {}

    public static String md5(String s) {
        String md5sum = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            md5sum = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5sum;
    }

}
