package com.apkbus.mobile.bean;

import java.util.List;

/**
 * Created by liyiheng on 16/9/29.
 */

public class GiftWrapper {
    private boolean error;
    private List<Gift> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gift> getResult() {
        return results;
    }

    public void setResult(List<Gift> result) {
        this.results = result;
    }

    public static class Gift {
        /**
         * _id : 57eb0495421aa95dd78e8d79
         * createdAt : 2016-09-28T07:45:25.283Z
         * desc : 9-28
         * publishedAt : 2016-09-28T11:35:12.91Z
         * source : chrome
         * type : 福利
         * url : http://ww3.sinaimg.cn/large/610dc034jw1f88ylsqjvqj20u011hn5i.jpg
         * used : true
         * who : 代码家
         */
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}

