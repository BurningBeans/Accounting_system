package com.example.accountingsystem;

public class Login extends Account {
    public boolean getLoginStatus(String userid){
        return loginStatus;
    }
    public boolean verifyStatus(String id, String passwd){
        // check DB id is right or not;
        return true;
    }

}
