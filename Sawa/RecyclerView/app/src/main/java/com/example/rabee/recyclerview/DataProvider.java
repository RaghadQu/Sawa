package com.example.rabee.recyclerview;

/**
 * Created by Rabee on 4/1/2017.
 */

public class DataProvider {
    int img_res;
    int img_res1;
    String post;
    public  DataProvider(int img_res,int img_res1,String post){
        this.img_res=img_res;
        this.img_res1=img_res1;
        this.post=post;
    }

    public void setImg_res(int img_res) {
        this.img_res = img_res;
    }

    public void setImg_res1(int img_res1) {
        this.img_res1 = img_res1;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getImg_res() {
        return img_res;
    }

    public int getImg_res1() {
        return img_res1;
    }

    public String getPost() {
        return post;
    }
}
