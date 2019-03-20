package com.disruptor.demo04;
/**
 * @author Zhengzy
 * @param
 * @date 2019/3/20 10:06
 * @desc 描述
 * @return
 * @exception
 */
public class Order {
    //ID
    private String id;
    private String name;
    //金额
    private double price;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
