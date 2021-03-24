package com.iot.barcode.models;

import android.os.Parcel;
import android.os.Parcelable;

public class IotProduct implements Parcelable {

    private int id;
    private String name;
    private String code;
    private float price;
    private String cat;
    private String brand;
    private String color;
    private String desc;

    public IotProduct() {}

    protected IotProduct(Parcel in) {
        id = in.readInt();
        name = in.readString();
        code = in.readString();
        price = in.readFloat();
        cat = in.readString();
        brand = in.readString();
        color = in.readString();
        desc = in.readString();
    }

    public static final Creator<IotProduct> CREATOR = new Creator<IotProduct>() {
        @Override
        public IotProduct createFromParcel(Parcel in) {
            return new IotProduct(in);
        }

        @Override
        public IotProduct[] newArray(int size) {
            return new IotProduct[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeFloat(price);
        dest.writeString(cat);
        dest.writeString(brand);
        dest.writeString(color);
        dest.writeString(desc);
    }

    @Override
    public String toString() {
        return getName();
    }
}
