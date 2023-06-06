package com.example.accountingsystem;

public class Forget_Pass extends Account {

    public String[] getQuest(){
        return quest;
    }
    public boolean verifyAnswer(String[] answers){
        return true;
    }
    public void changePasswd(String newPasswd){
        super.password = password;
    }
}
