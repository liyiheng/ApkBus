package com.apkbus.mobile.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liyiheng on 16/9/23.
 */
public class RegexUtil {
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        //Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
