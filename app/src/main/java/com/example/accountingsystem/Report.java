package com.example.accountingsystem;

import java.util.ArrayList;
import java.util.List;

public class Report  {
    private int reportId;
    private myDate date;
    private List<Income> incomeList = new ArrayList<>();
    private List<Expense> expenseList = new ArrayList<>();
    private int[] incomeCategoryAmount;
    private int[] expenseCategoryAmount;
    private int accountId;

    public void addIncomeList( Income income){
        incomeList.add(income);
    }
    public int getIncomeFromList(Income income){
        for (int i = 0; i < incomeList.size(); i++) {
            Income tmpIncome  = incomeList.get(i);
            if(tmpIncome.equals(income)) {
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }
    public List<Income> getIncomeList(){return  incomeList;}

    public void removeIncomeList( Income income){
        incomeList.remove(getIncomeFromList(income));
    }

    public int[] calculateIncome(){
        return incomeCategoryAmount;
    }

    public void addExpenseList( Expense expense){
        expenseList.add(expense);
    }
    public int getExpenseFromList(Expense expense){
        for (int i = 0; i < expenseList.size(); i++) {
            Expense tmpExpense  = expenseList.get(i);
            if(tmpExpense.equals(expense)) {
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }

    public void removeExpenseList( Expense expense){
        expenseList.remove(getExpenseFromList(expense));
    }

    public int[] calculateExpense(){
        return expenseCategoryAmount;
    }


    public Report(int reportId, myDate date, int accountId) {
        this.accountId = accountId;
        this.reportId = reportId;
        this.date = date;
    }

    public Report() {
        this.date = new myDate();
    }

    public void insertData(DBHelper db){
        int rowId = (int) db.insertReportData(date.toMonthString() ,accountId);
        if(rowId != -1){
            this.reportId = rowId;
            System.out.println( toString()+ " insert success");
        }
        else{
            System.out.println( toString()+ " insert failed");
        }
    }

    public void readData(DBHelper db, int reportId){
        Report existReport = db.getReportData(reportId);
        this.accountId = existReport.accountId;
        this.date = existReport.date;
    }

    public Income createAndInsertIncome(DBHelper db,myDate date,String category, String item, double amount, String description){
        Income income = new Income();
        income.setCategory(category);
        income.setItem(item);
        income.setAmount(amount);
        income.setDescription(description);
        income.setIncomeReportId(reportId);
        if(date!=null){income.setDate(date);}
        income.insertData(db);
        return income;
    }

    public List<Income> getIncomeListByReportIdAndDate(DBHelper db, String date){

        return db.getIncomeListByReportIdAndDate(reportId, date);
    }

    public void  setIncomeListByReportIdAndDate(DBHelper db, String date){
        this.incomeList = getIncomeListByReportIdAndDate(db,date);
    }


    public List<Income> getIncomeByReportId(DBHelper db, int reportId){
        return db.getIncomeByReportId(reportId);
    }

    public void setIncomeByReportId(DBHelper db, int reportId){
        this.incomeList = getIncomeByReportId(db,reportId);
    }

    public Expense createAndInsertExpense(DBHelper db,String category, String item, double amount, String description){
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setItem(item);
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setExpenseReportId(reportId);
        expense.insertData(db);
        return expense;
    }


    public List<Expense> getExpenseListByReportIdAndDate(DBHelper db, String date){

        return db.getExpenseListByReportIdAndDate(reportId, date);
    }

    public void  setExpenseListByReportIdAndDate(DBHelper db, String date){
        this.expenseList = getExpenseListByReportIdAndDate(db,date);
    }


    public List<Expense> getExpenseByReportId(DBHelper db, int reportId){
        return db.getExpensesByReportId(reportId);
    }

    public void setExpenseByReportId(DBHelper db, int reportId){
        this.expenseList = getExpenseByReportId(db,reportId);
    }


    // Setters
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public void setDate(myDate date) {
        this.date = date;
    }


    // Getters
    public int getAccountId() {
        return accountId;
    }

    public int getReportId() {
        return reportId;
    }

    public myDate getDate() {
        return date;
    }


    // toString() method
    @Override
    public String toString() {
        String incomeStr="";
        for (Income income : incomeList) {
            incomeStr= incomeStr + '\n'+ income.toString();
        }
        return "Report{" +
                "reportId=" + reportId +
                ", date=" + date.toMonthString() +
                ", accountId=" + accountId +
                ", incomeList=" + incomeStr + '}';
    }


}
