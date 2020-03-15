package com.maoye.demo5;

import android.os.Parcel;
import android.os.Parcelable;

public class PersionBean implements Parcelable {
    private int age;
    private String name;
    public PersionBean(int age , String name) {
        this.age = age;
        this.name = name;
    }

    private PersionBean(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
    }
    public static final Creator<PersionBean> CREATOR = new Creator<PersionBean>() {
        @Override
        public PersionBean createFromParcel(Parcel in) {
            return new PersionBean(in);
        }

        @Override
        public PersionBean[] newArray(int size) {
            return new PersionBean[size];
        }
    };
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * int 时必须写，intout时还需要写readFromParcel
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
              dest.writeInt(age);
              dest.writeString(name);
    }

    /**
     * 为out时必须写，intout时，需要些此方法和writeToParcel
     * @param parcel
     */
    public void readFromParcel(Parcel parcel) {
        this.age = parcel.readInt();
        this.name = parcel.readString();
    }

}
