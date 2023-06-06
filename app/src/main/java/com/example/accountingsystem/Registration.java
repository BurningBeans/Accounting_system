package com.example.accountingsystem;

public class Registration {

    protected int userid;
    protected String passwd;
    protected String userName;
    protected String[] quest;
    Registration(int userid, String passwd, String userName, String[] quest) {
        this.userid = userid;
        this.passwd = passwd;
        this.userName = userName;
        this.quest = quest;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getUserid() {
        return this.userid;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public String[] getQuest() {
        return this.quest;
    }

    public void insertUserData(String userid, String passwd, String userName, String[] quest){
        //DB insert...
    }
}
