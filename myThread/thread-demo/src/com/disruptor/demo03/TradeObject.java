package com.disruptor.demo03;
/**
 * @author Zhengzy
 * @param 
 * @date 2019/3/20 10:06
 * @desc 描述
 * @return 
 * @exception 
 */
public class TradeObject {

    private String id;

    private String name;

    private String phone;

    private String unit;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TradeObject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                '}';
    }
}
