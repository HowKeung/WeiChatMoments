package com.keung.weichat.http.bean;

import java.util.List;

import androidx.annotation.Keep;

@Keep
public class TweetListBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Keep
    public static class DataBean {
        private String content;
        private List<Images> images;
        private Sender sender;
        private List<Comments> comments;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setImages(List<Images> images) {
            this.images = images;
        }

        public List<Images> getImages() {
            return images;
        }

        public void setSender(Sender sender) {
            this.sender = sender;
        }

        public Sender getSender() {
            return sender;
        }

        public void setComments(List<Comments> comments) {
            this.comments = comments;
        }

        public List<Comments> getComments() {
            return comments;
        }
    }


    @Keep
    public static class Images {

        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }

    @Keep
    public static class Sender {

        private String username;
        private String nick;
        private String avatar;

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getNick() {
            return nick;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }

    }

    @Keep
    public static class Comments {

        private String content;
        private Sender sender;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setSender(Sender sender) {
            this.sender = sender;
        }

        public Sender getSender() {
            return sender;
        }
    }
}
