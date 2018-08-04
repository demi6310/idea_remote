package com.itheima.pojo;

/**
 * @Author： ZY
 * @Description:
 * @Date：Created 10:51 2018/8/3
 */
public class Item {
    private String pid;
    private String picture;
    private String name;
    private float price;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "pid='" + pid + '\'' +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
