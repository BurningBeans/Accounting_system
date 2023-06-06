package com.example.accountingsystem;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    protected int accountId;
    protected String password;
    protected String userName;
    protected String[] quest;
    protected boolean loginStatus;

    private List<Report> reportList = new ArrayList<>();

    private Budget budget;


    public void insertData(DBHelper db){
        int rowId = (int)db.insertAccountData(userName, password);
        if( rowId != -1 ){
            accountId = rowId;
            System.out.println(toString()+" insert success");
        }
        else{
            System.out.println(toString()+" insert failed");
        }
    }

    public Account getAccountByUsername(DBHelper db, String userName, String password){
        Account existAccount = db.verifyAccount(userName,password);
        if (existAccount != null){
            this.accountId = existAccount.accountId;
            this.userName = existAccount.userName;
            this.password = existAccount.password;
            System.out.println("getAccountByUsername : "+ existAccount.toString());
            return existAccount;
        }else {
            System.out.println("Account not exist create one");
            return null;
        }
    }

    public boolean isAccountAvailable(DBHelper db, String userName){
        Account existAccount = db.verifyAccountByUsername(userName);
        if (existAccount == null){
            System.out.println("Account username: "+ userName+" available");
            return true;
        }else {
            System.out.println("Account username: "+ userName+" exist");
            return false;
        }
    }
    public void readData(DBHelper db, int accountId) {
        Account existAccount = db.getAccountData(accountId);
        this.accountId = accountId;
        this.userName = existAccount.userName;
        this.password = existAccount.password;
        System.out.println(toString()+" read success");
        //this.report = existAccount.report;
    }


    public void createAccount(Registration registration){
        this.accountId = registration.getUserid();
        this.userName = registration.getUserName();
        this.password = registration.getPasswd();
        this.quest = registration.getQuest();
    }

    public Report createAndInsertReport(DBHelper db, myDate date){
        Report newReport = new Report();
        newReport.setAccountId(accountId);
        if (date != null){
            newReport.setDate(date);
        }
        newReport.insertData(db);
        return newReport;

    }

    public Report getReportsByAccountIdAndDate(DBHelper db, int accountId, String date){
        Report existReport = db.getReportsByAccountIdAndDate(accountId, date);
        if(!(existReport==null)){
            System.out.println("Account: "
                    +" accountId: "+ accountId
                    +" reportId: "+ existReport.getReportId()
                    +" date: "+ date
                    +" read success");
            //existReport.setExpenseByReportId(db,existReport.getReportId());
            //existReport.setIncomeByReportId(db, existReport.getReportId());
            return existReport;
        }
        else{
            return existReport = createAndInsertReport(db,new myDate().parseDate(date));
        }

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId( int newId) {
        this.accountId = newId;
    }


    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(){}

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName=" + userName +
                ", password=" + password+'}';
    }


}
