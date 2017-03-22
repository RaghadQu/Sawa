package com.example.zodiac.sawa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Authentication {


        @SerializedName("state")
        @Expose
        private int state;

        public int getState() {
            return state;
        }

        public void setStatus(int state) {
            this.state = state;
        }

    }

