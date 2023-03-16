package com.example.gpsapp;

public class Addy {
        private String Add1;
        private long Time1;
        public Addy(String Add1)
        {
            this.Add1 = Add1;
            Time1 = 0;
        }
        public Addy(String Add1, long Time1)
        {
            this.Add1 = Add1;
            this.Time1 = Time1;
        }

    public String getAdd1() {
        return Add1;
    }

    public long getTime1() {
        return Time1;
    }

    public void setTime1(long time1) {
        Time1 = time1;
    }
}
