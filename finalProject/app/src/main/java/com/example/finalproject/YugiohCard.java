package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class YugiohCard implements Parcelable {
    String name;
    public YugiohCard(){

    }
    public YugiohCard(String name){
        this.name = name;
    }
    private YugiohCard(Parcel in){
        name = in.readString();
    }
    public int describeContents() {
        return 0;
    }
    @Override

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }

    public static final Parcelable.Creator<YugiohCard> CREATOR = new Parcelable.Creator<YugiohCard>() {
        public YugiohCard createFromParcel(Parcel in) {
            return new YugiohCard(in);
        }

        public YugiohCard[] newArray(int size) {
            return new YugiohCard[size];
        }
    };

    public String getName(){
        return name;
    }

}