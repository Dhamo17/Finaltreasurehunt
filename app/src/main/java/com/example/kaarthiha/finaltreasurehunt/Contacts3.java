package com.example.kaarthiha.finaltreasurehunt;

/**
 * Created by Dhamo on 30-03-2017.
 */
public class Contacts3 {

    public String name,mobile;
    public Contacts3(String name,String mobile)
    {
        this.setName(name);
        this.setMobile(mobile);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
