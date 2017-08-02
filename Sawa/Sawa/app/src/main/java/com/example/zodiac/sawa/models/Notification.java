package com.example.zodiac.sawa.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Rabee on 4/5/2017.
 */

public class Notification {
    @SerializedName("sent_notifications")
    List<NotificationInfo> sent_notifications;

    @SerializedName("not_sent_notifications")
    List<NotificationInfo> not_sent_notifications;

    public List<NotificationInfo> getSent_notifications() {
        return sent_notifications;
    }

    public void setSent_notifications(List<NotificationInfo> sent_notifications) {
        this.sent_notifications = sent_notifications;
    }

    public List<NotificationInfo> getNot_sent_notifications() {
        return not_sent_notifications;
    }

    public void setNot_sent_notifications(List<NotificationInfo> not_sent_notifications) {
        this.not_sent_notifications = not_sent_notifications;
    }

    public class NotificationInfo {
        @SerializedName("id")
        String id;

        @SerializedName("friend1_id")
        String friend1_id;

        @SerializedName("friend2_id")
        String friend2_id;

        @SerializedName("type")
        String type;

        @SerializedName("read_flag")
        String read_flag;

        @SerializedName("sent_flag")
        String sent_flag;

        @SerializedName("timestamp")
        String timestamp;

        @SerializedName("user_image")
        String user_image;

        @SerializedName("first_name")
        String first_name;

        @SerializedName("last_name")
        String last_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFriend1_id() {
            return friend1_id;
        }

        public void setFriend1_id(String friend1_id) {
            this.friend1_id = friend1_id;
        }

        public String getFriend2_id() {
            return friend2_id;
        }

        public void setFriend2_id(String friend2_id) {
            this.friend2_id = friend2_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRead_flag() {
            return read_flag;
        }

        public void setRead_flag(String read_flag) {
            this.read_flag = read_flag;
        }

        public String getSent_flag() {
            return sent_flag;
        }

        public void setSent_flag(String sent_flag) {
            this.sent_flag = sent_flag;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }


    }

}
