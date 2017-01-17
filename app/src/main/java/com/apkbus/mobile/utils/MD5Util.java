package com.apkbus.mobile.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 *
 * @author Sylthas
 */
public class MD5Util {
    private MD5Util() {
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * @return 32byte MD5 Value
     */
    public static String getMD5(String inStr) {
        byte[] inStrBytes = inStr.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inStrBytes);
            byte[] mdByte = md.digest();
            char[] str = new char[mdByte.length * 2];
            int k = 0;
            for (byte temp : mdByte) {
                str[k++] = hexDigits[temp >>> 4 & 0xf];
                str[k++] = hexDigits[temp & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException ignore) {
        }
        return null;
    }

    /**
     * @deprecated use {@link #md5(File)}
     */
    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception ignore) {
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            }
        }
        return value;
    }

    /**
     * 对一个文件求他的md5值
     *
     * @param f 要求md5值的文件
     * @return md5串
     */
    public static String md5(File f) {
        FileInputStream fis = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(f);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        } catch (NoSuchAlgorithmException | IOException ignore) {
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException ignore) {
            }
        }
        return null;
    }
}
