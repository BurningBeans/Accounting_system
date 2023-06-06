package com.example.accountingsystem;

public class Expense{
    private int expenseId;
    private myDate date;
    private String category;
    private String item;
    private double amount;
    private String description;

    private int reportId;

    public Expense(int expenseId, myDate date, String category, String item, double amount, String description, int reportId) {
        this.expenseId = expenseId;
        this.date = date;
        this.category = category;
        this.item = item;
        this.amount = amount;
        this.description = description;
        this.reportId = reportId;
    }

    public Expense(){
        this.date = new myDate();
    }

    public void insertData(DBHelper db){
        int rowId = (int) db.insertExpenseData(date.toString() ,category, item, amount, description,reportId);
        if(rowId != -1){
            this.expenseId = rowId;
            System.out.println( toString()+ " insert success");
        }
        else{
            System.out.println( toString()+ " insert failed");
        }
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public myDate getDate() {
        return date;
    }

    public void setDate(myDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExpenseReportId() {
        return expenseId;
    }

    public void setExpenseReportId(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return  "Expense{ " +
                "Expense ID: " + expenseId +
                ", Date: " + date +
                ", Category: " + category +
                ", Item: " + item +
                ", Amount: " + amount +
                ", Description: " + description +
                ", Report ID: " + reportId +" }";
    }
}
