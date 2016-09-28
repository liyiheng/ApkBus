package com.apkbus.mobile.bean;

import android.text.TextUtils;

/**
 * There are 2 kinds of beans now.
 * Two in one.
 * Created by liyiheng on 16/9/18.
 */
public class Bean {

    /**
     * Bean1
     * <p>
     * fulltitle : 对Zxing源码进行了优化
     * author : MrlLee
     * authorid : 705730
     * avatar : http://www.apkbus.com/uc_server/avatar.php?uid=705730&size=small
     * avatar_middle : http://www.apkbus.com/uc_server/avatar.php?uid=705730&size=middle
     * avatar_big : http://www.apkbus.com/uc_server/avatar.php?uid=705730&size=big
     * posts : null
     * threads : null
     * todayposts : null
     * typename : null
     * typeicon : null
     * lastpost : 1474193803
     * dateline : 1466738046
     * replies : 96
     * forumurl : forum.php?mod=forumdisplay&fid=417
     * forumname : Android精品源码
     * <p>
     * typeurl : forum.php?mod=forumdisplay&fid=417&filter=typeid&typeid=0
     * sortname : Android精品源码
     * sorturl : forum.php?mod=forumdisplay&fid=417&filter=sortid&sortid=12
     * views : 465
     * heats : 85
     * recommends : 0
     * hourviews : 465
     * todayviews : 465
     * weekviews : 465
     * monthviews : 465
     * url : http://www.apkbus.com/forum.php?mod=viewthread&tid=258732
     * pic : http://www.apkbus.com/static/image/common/nophoto.gif
     * <p>
     * Bean2
     * <p>
     * fulltitle : Android使用Mockito和Roboletric进行单元测试
     * dateline : 1474256673
     * uid : 705730
     * username : MrlLee
     * avatar : http://www.apkbus.com/uc_server/avatar.php?uid=705730&size=small
     * avatar_middle : http://www.apkbus.com/uc_server/avatar.php?uid=705730&size=middle
     * avatar_big : http://www.apkbus.com/uc_server/avatar.php?uid=705730&size=big
     * replynum : 0
     * viewnum : 5
     * click1 : 0
     * click2 : 0
     * click3 : 0
     * click4 : 0
     * click5 : 0
     * click6 : 0
     * click7 : 0
     * click8 : 0
     * url : http://www.apkbus.com/home.php?mod=space&uid=705730&do=blog&id=61768
     * pic : http://www.apkbus.com/static/image/common/nophoto.gif
     */
    private String author;
    private String authorid;
    private String fulltitle;
    private String uid;
    private String username;
    private String avatar_middle;

    private String url;


    public String getUid() {
        if (TextUtils.isEmpty(uid))
            return authorid;
        return uid;
    }


    public String getTitle() {
        return fulltitle;
    }


    public String getAuthorAvatar() {
        return avatar_middle;
    }

    public String getNickname() {
        if (TextUtils.isEmpty(username))
            return author;
        return username;
    }

    public String getURL() {
        return url;
    }
}
