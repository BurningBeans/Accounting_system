package com.example.accountingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Accounting_system6.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_ACCOUNT = "AccountInfo";
    private static final String TABLE_REPORT = "ReportInfo";
    private static final String TABLE_INCOME = "IncomeInfo";
    private static final String TABLE_EXPENSE = "ExpenseInfo";

    // Common column names
    private static final String COLUMN_ID = "id";

    // AccountInfo table columns
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String COLUMN_ACCOUNT_NAME = "account_name";

    private static final String COLUMN_ACCOUNT_PASSWORD = "account_password";


    // ReportInfo table columns
    private static final String COLUMN_REPORT_DATE = "report_date";

    // IncomeInfo table columns
    private static final String COLUMN_INCOME_DATE = "income_date";
    private static final String COLUMN_INCOME_CATEGORY = "income_category";
    private static final String COLUMN_INCOME_ITEM = "income_item";
    private static final String COLUMN_INCOME_AMOUNT = "income_amount";
    private static final String COLUMN_INCOME_DESCRIPTION = "income_description";
    private static final String COLUMN_INCOME_REPORT_ID = "income_report_id";

    // ExpenseInfo table columns
    private static final String COLUMN_EXPENSE_DATE = "expense_date";
    private static final String COLUMN_EXPENSE_CATEGORY = "expense_category";
    private static final String COLUMN_EXPENSE_ITEM = "expense_item";
    private static final String COLUMN_EXPENSE_AMOUNT = "expense_amount";
    private static final String COLUMN_EXPENSE_DESCRIPTION = "expense_description";
    private static final String COLUMN_EXPENSE_REPORT_ID = "expense_report_id";


    public DBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create AccountInfo table
        // Table: AccountInfo
        // | account_id (Primary Key) | account_name | ...
        String createAccountTableQuery = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ACCOUNT_NAME + " TEXT,"
                + COLUMN_ACCOUNT_PASSWORD + " TEXT)";
        db.execSQL(createAccountTableQuery);
        System.out.println("Account table created");

        // Create ReportInfo table
        String createReportTableQuery = "CREATE TABLE " + TABLE_REPORT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_REPORT_DATE + " TEXT,"
                + COLUMN_ACCOUNT_ID + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_ACCOUNT_ID + ") REFERENCES " + TABLE_ACCOUNT + "(" + COLUMN_ID + "))";
        db.execSQL(createReportTableQuery);

        // Create IncomeInfo table
        String createIncomeTableQuery = "CREATE TABLE " + TABLE_INCOME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_INCOME_DATE + " TEXT,"
                + COLUMN_INCOME_CATEGORY + " TEXT,"
                + COLUMN_INCOME_ITEM + " TEXT,"
                + COLUMN_INCOME_AMOUNT + " REAL,"
                + COLUMN_INCOME_DESCRIPTION + " TEXT,"
                + COLUMN_INCOME_REPORT_ID + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_INCOME_REPORT_ID + ") REFERENCES " + TABLE_REPORT + "(" + COLUMN_ID + "))";
        db.execSQL(createIncomeTableQuery);

        // Create ExpenseInfo table
        String createExpenseTableQuery = "CREATE TABLE " + TABLE_EXPENSE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EXPENSE_DATE + " TEXT,"
                + COLUMN_EXPENSE_CATEGORY + " TEXT,"
                + COLUMN_EXPENSE_ITEM + " TEXT,"
                + COLUMN_EXPENSE_AMOUNT + " REAL,"
                + COLUMN_EXPENSE_DESCRIPTION + " TEXT,"
                + COLUMN_EXPENSE_REPORT_ID + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_EXPENSE_REPORT_ID + ") REFERENCES " + TABLE_REPORT + "(" + COLUMN_ID + "))";
        db.execSQL(createExpenseTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
    }

    public long insertAccountData(String accountName, String accountPassword){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACCOUNT_NAME,accountName);
        contentValues.put(COLUMN_ACCOUNT_PASSWORD,accountPassword);
        long result=DB.insert(TABLE_ACCOUNT,null,contentValues);
        return result;
    }
    public long insertReportData(String reportDate, int accountId){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COLUMN_ID,reportId);
        contentValues.put(COLUMN_REPORT_DATE, reportDate);
        contentValues.put(COLUMN_ACCOUNT_ID, accountId);
        long result = DB.insert(TABLE_REPORT, null, contentValues);
        return result;
    }

    public long insertIncomeData(String date, String category, String item, double amount, String description, int reportId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INCOME_DATE, date);
        contentValues.put(COLUMN_INCOME_CATEGORY, category);
        contentValues.put(COLUMN_INCOME_ITEM, item);
        contentValues.put(COLUMN_INCOME_AMOUNT, amount);
        contentValues.put(COLUMN_INCOME_DESCRIPTION, description);
        contentValues.put(COLUMN_INCOME_REPORT_ID, reportId);

        long result = db.insert(TABLE_INCOME, null, contentValues);
        return result;
    }

    public long insertExpenseData(String date, String category, String item, double amount, String description, int reportId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSE_DATE, date);
        contentValues.put(COLUMN_EXPENSE_CATEGORY, category);
        contentValues.put(COLUMN_EXPENSE_ITEM, item);
        contentValues.put(COLUMN_EXPENSE_AMOUNT, amount);
        contentValues.put(COLUMN_EXPENSE_DESCRIPTION, description);
        contentValues.put(COLUMN_EXPENSE_REPORT_ID, reportId);

        return db.insert(TABLE_EXPENSE, null, contentValues);

    }

    /*public Boolean updateAccountData(int accountId, String accountName, String accountPassword){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COLUMN_ACCOUNT_NAME",accountName);
        contentValues.put("COLUMN_ACCOUNT_PASSWORD",accountPassword);

        String whereClause = "accountId=?";
        Cursor cursor = DB.rawQuery("Select * from TABLE_ACCOUNT where name = ?", new String[]{name});

        if (cursor.getCount()>0){
            long result=DB.update("TABLE_ACCOUNT",contentValues,"name=?",new String[]{name});
            if (result==-1){
                return false;
            }
            else {
                return true;
            }
        }else {
            return  false;
        }
    }*/

    public Boolean updateAccountData(int accountId, String accountName, String accountPassword){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACCOUNT_NAME, accountName);
        contentValues.put(COLUMN_ACCOUNT_PASSWORD, accountPassword);

        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = { String.valueOf(accountId) };

        int rowsAffected = DB.update(TABLE_ACCOUNT, contentValues, whereClause, whereArgs);

        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean updateReportData(int reportId, String reportDate, int accountId) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_REPORT_DATE, reportDate);
        contentValues.put(COLUMN_ACCOUNT_ID, accountId);

        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(reportId)};
        int rowsAffected = DB.update(TABLE_REPORT, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }

    public boolean updateIncomeData(int incomeId, String category, String item, double amount, String description) {
        SQLiteDatabase writableDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INCOME_CATEGORY, category);
        contentValues.put(COLUMN_INCOME_ITEM, item);
        contentValues.put(COLUMN_INCOME_AMOUNT, amount);
        contentValues.put(COLUMN_INCOME_DESCRIPTION, description);

        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(incomeId)};

        int rowsAffected = writableDB.update(TABLE_INCOME, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }


    public boolean updateExpenseData(int expenseId, String category, String item, double amount, String description) {
        SQLiteDatabase writableDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSE_CATEGORY, category);
        contentValues.put(COLUMN_EXPENSE_ITEM, item);
        contentValues.put(COLUMN_EXPENSE_AMOUNT, amount);
        contentValues.put(COLUMN_EXPENSE_DESCRIPTION, description);

        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(expenseId)};

        int rowsAffected = writableDB.update(TABLE_EXPENSE, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }



    /*protected Boolean deleteData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});

        if (cursor.getCount()>0){
            long result=DB.delete("Userdetails","name=?",new String[]{name});
            if (result==-1){
                return false;
            }
            else {
                return true;
            }
        }else {
            return  false;
        }


    }*/

    public boolean deleteAccountData(int accountId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "account_id=?";
        String[] whereArgs = {String.valueOf(accountId)};

        int rowsAffected = db.delete(TABLE_ACCOUNT, whereClause, whereArgs);

        return rowsAffected > 0;
    }
    public boolean deleteReportData(int reportId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "report_id=?";
        String[] whereArgs = {String.valueOf(reportId)};

        int rowsAffected = db.delete(TABLE_REPORT, whereClause, whereArgs);

        return rowsAffected > 0;
    }
    public boolean deleteIncomeData(int incomeId) {
        SQLiteDatabase writableDB = this.getWritableDatabase();

        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(incomeId)};

        int rowsAffected = writableDB.delete(TABLE_INCOME, whereClause, whereArgs);

        return rowsAffected > 0;
    }
    public boolean deleteExpenseData(int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "expense_id=?";
        String[] whereArgs = {String.valueOf(expenseId)};

        int rowsAffected = db.delete(TABLE_EXPENSE, whereClause, whereArgs);

        return rowsAffected > 0;
    }

    /*public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }*/

    public Account getAccountData(int accountId) {
        SQLiteDatabase DB = this.getReadableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(accountId) };

        Cursor cursor = DB.query(TABLE_ACCOUNT, null, selection, selectionArgs, null, null, null);

        Account account = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_NAME));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_PASSWORD));
            Report report = new Report();
            account = new Account(name, password);
        }

        cursor.close();
        return account;
    }

    public Account verifyAccountByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_ACCOUNT_NAME + " = ? ";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(TABLE_ACCOUNT, null, selection, selectionArgs, null, null, null);

        Account account = null;

        if (cursor.moveToFirst()) {
             username = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_NAME));
             //String password = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_PASSWORD));
             String password = "";
            account = new Account(username, password);
            cursor.close();
            return account;
        }else
        {
            cursor.close();
            return null;
        }
    }

    public Account verifyAccount(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_ACCOUNT_NAME + " = ? AND " + COLUMN_ACCOUNT_PASSWORD + " = ?";
        String[] selectionArgs = { username , password};

        Cursor cursor = db.query(TABLE_ACCOUNT, null, selection, selectionArgs, null, null, null);

        Account account = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            username = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_NAME));
            password = cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT_PASSWORD));
            account = new Account(username, password);
            account.setAccountId(id);
            cursor.close();
            return account;
        }else
        {
            cursor.close();
            return null;
        }
    }


    public Report getReportData(int reportId) {
        SQLiteDatabase DB = this.getReadableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(reportId) };

        Cursor cursor = DB.query(TABLE_REPORT, null, selection, selectionArgs, null, null, null);

        Report report = null;

        if (cursor.moveToFirst()) {
            String tmpReportDate = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DATE));
            int account_Id = cursor.getInt(cursor.getColumnIndex(COLUMN_ACCOUNT_ID));

            myDate reportDate = new myDate();
            reportDate = reportDate.parseDate(tmpReportDate);

            // 創建報告物件
            report = new Report(reportId, reportDate , account_Id);
        }

        cursor.close();
        return report;
    }

    public Report getReportsByAccountIdAndDate(int accountId, String date) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Report table
        String[] columns = {COLUMN_ID, COLUMN_REPORT_DATE, COLUMN_ACCOUNT_ID};

        // Define the selection criteria to filter reports based on the account ID and date
        String selection = COLUMN_ACCOUNT_ID + " = ? AND " + COLUMN_REPORT_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(accountId), date};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_REPORT, columns, selection, selectionArgs, null, null, null);

        Report report = null;
        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            do {
                // Extract the report data from the cursor for each row
                int reportId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String tmpReportDate = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DATE));

                // Create a new Report object with the extracted data
                myDate reportDate = new myDate();
                reportDate = reportDate.parseDate(tmpReportDate);
                report = new Report(reportId, reportDate, accountId);

                // Add the Report object to the list
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        // Return the Report objects
        return report;
    }


    public List<Report> getReportsByAccount(int accountId) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Report table
        String[] columns = {COLUMN_ID, COLUMN_REPORT_DATE, COLUMN_ACCOUNT_ID};

        // Define the selection criteria to filter reports based on the account_id
        String selection = COLUMN_ACCOUNT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(accountId)};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_REPORT, columns, selection, selectionArgs, null, null, null);

        List<Report> reportList = new ArrayList<>();

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            do {
                // Extract the report data from the cursor for each row
                int reportId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String tmpReportDate = cursor.getString(cursor.getColumnIndex(COLUMN_REPORT_DATE));

                // Create a new Report object with the extracted data
                myDate reportDate = new myDate();
                reportDate = reportDate.parseDate(tmpReportDate);
                Report report = new Report(reportId, reportDate, accountId);

                // Add the Report object to the list
                reportList.add(report);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();

        // Return the list of Report objects
        return reportList;
    }


    public Income getIncomeById(int incomeId) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Income table
        String[] columns = {COLUMN_ID, COLUMN_INCOME_DATE, COLUMN_INCOME_CATEGORY, COLUMN_INCOME_ITEM, COLUMN_INCOME_AMOUNT, COLUMN_INCOME_DESCRIPTION,COLUMN_INCOME_REPORT_ID};

        // Define the selection criteria to filter income based on the income_id
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(incomeId)};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_INCOME, columns, selection, selectionArgs, null, null, null);

        Income income = null;

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            // Extract the income data from the cursor
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DATE));
            String category = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_CATEGORY));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_ITEM));
            double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_INCOME_AMOUNT));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DESCRIPTION));
            int reportId = cursor.getInt(cursor.getColumnIndex(COLUMN_INCOME_REPORT_ID));
            myDate incomeDate=new myDate();
            incomeDate = incomeDate.parseDate(date);
            // Create a new Income object with the extracted data
            income = new Income(incomeId, incomeDate, category, item, amount, description,reportId);
        }

        // Close the cursor
        cursor.close();

        // Return the Income object
        return income;
    }


    public List<Income> getIncomeByReportId(int reportId) {
        List<Income> incomeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_INCOME_DATE, COLUMN_INCOME_CATEGORY, COLUMN_INCOME_ITEM, COLUMN_INCOME_AMOUNT, COLUMN_INCOME_DESCRIPTION};

        String selection = COLUMN_INCOME_REPORT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(reportId)};

        Cursor cursor = db.query(TABLE_INCOME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int incomeId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DATE));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_CATEGORY));
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_ITEM));
                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_INCOME_AMOUNT));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DESCRIPTION));
                myDate incomeDate=new myDate();
                incomeDate = incomeDate.parseDate(date);
                Income income = new Income(incomeId, incomeDate, category, item, amount, description,reportId);
                incomeList.add(income);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return incomeList;
    }

    public List<Income> getIncomeListByReportIdAndDate(int reportId, String date) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Income table
        String[] columns = {COLUMN_ID, COLUMN_INCOME_DATE, COLUMN_INCOME_CATEGORY, COLUMN_INCOME_ITEM, COLUMN_INCOME_AMOUNT, COLUMN_INCOME_DESCRIPTION};

        // Define the selection criteria to filter income based on the report ID and date
        String selection = COLUMN_INCOME_REPORT_ID + " = ? AND " + COLUMN_INCOME_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(reportId), date};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_INCOME, columns, selection, selectionArgs, null, null, null);

        List<Income> incomeList = new ArrayList<>();

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            do {
                // Extract the income data from the cursor for each row
                int incomeId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String tmpIncomeDate = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DATE));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_CATEGORY));
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_ITEM));
                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_INCOME_AMOUNT));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DESCRIPTION));

                // Create a new Income object with the extracted data
                myDate incomeDate = new myDate();
                incomeDate = incomeDate.parseDate(tmpIncomeDate);
                Income income = new Income(incomeId, incomeDate, category, item, amount, description,reportId);

                // Add the Income object to the list
                incomeList.add(income);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();

        // Return the list of Income objects
        return incomeList;
    }


    public Expense getExpenseById(int expenseId) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Expense table
        String[] columns = {COLUMN_ID, COLUMN_EXPENSE_DATE, COLUMN_EXPENSE_CATEGORY, COLUMN_EXPENSE_ITEM, COLUMN_EXPENSE_AMOUNT, COLUMN_EXPENSE_DESCRIPTION,COLUMN_EXPENSE_REPORT_ID};

        // Define the selection criteria to filter expenses based on the expense_id
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(expenseId)};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_EXPENSE, columns, selection, selectionArgs, null, null, null);

        Expense expense = null;

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            // Extract the expense data from the cursor
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DATE));
            String category = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_CATEGORY));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_ITEM));
            double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DESCRIPTION));
            int reportId = cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_REPORT_ID));
            myDate expenseDate=new myDate();
            expenseDate = expenseDate.parseDate(date);
            // Create a new Expense object with the extracted data
            expense = new Expense(expenseId, expenseDate, category, item, amount, description,reportId);
        }

        // Close the cursor
        cursor.close();

        // Return the Expense object
        return expense;
    }


    public List<Expense> getExpensesByReportId(int reportId) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Expense table
        String[] columns = {COLUMN_ID, COLUMN_EXPENSE_DATE, COLUMN_EXPENSE_CATEGORY, COLUMN_EXPENSE_ITEM, COLUMN_EXPENSE_AMOUNT, COLUMN_EXPENSE_DESCRIPTION};

        // Define the selection criteria to filter expenses based on the report_id
        String selection = COLUMN_EXPENSE_REPORT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(reportId)};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_EXPENSE, columns, selection, selectionArgs, null, null, null);

        // Create an empty list to store the expenses
        List<Expense> expenseList = new ArrayList<>();

        // Iterate over the cursor to extract each expense
        while (cursor.moveToNext()) {
            // Extract the expense data from the cursor
            int expenseId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DATE));
            String category = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_CATEGORY));
            String item = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_ITEM));
            double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DESCRIPTION));

            myDate expenseDate=new myDate();
            expenseDate = expenseDate.parseDate(date);
            // Create a new Expense object with the extracted data
            Expense expense = new Expense(expenseId, expenseDate, category, item, amount, description,reportId);

            // Add the expense to the list
            expenseList.add(expense);
        }

        // Close the cursor
        cursor.close();

        // Return the list of expenses
        return expenseList;
    }

    public List<Expense> getExpenseListByReportIdAndDate(int reportId, String date) {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        // Define the columns you want to retrieve from the Expense table
        String[] columns = {COLUMN_ID, COLUMN_EXPENSE_DATE, COLUMN_EXPENSE_CATEGORY, COLUMN_EXPENSE_ITEM, COLUMN_EXPENSE_AMOUNT, COLUMN_EXPENSE_DESCRIPTION};

        // Define the selection criteria to filter expenses based on the report ID and date
        String selection = COLUMN_EXPENSE_REPORT_ID + " = ? AND " + COLUMN_EXPENSE_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(reportId), date};

        // Perform the query and retrieve the cursor
        Cursor cursor = readableDB.query(TABLE_EXPENSE, columns, selection, selectionArgs, null, null, null);

        List<Expense> expenseList = new ArrayList<>();

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            do {
                // Extract the expense data from the cursor for each row
                int expenseId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String tmpExpenseDate = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DATE));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_CATEGORY));
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_ITEM));
                double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_EXPENSE_AMOUNT));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_DESCRIPTION));

                // Create a new Expense object with the extracted data
                myDate expenseDate = new myDate();
                expenseDate = expenseDate.parseDate(tmpExpenseDate);
                Expense expense = new Expense(expenseId, expenseDate, category, item, amount, description, reportId);

                // Add the Expense object to the list
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();

        // Return the list of Expense objects
        return expenseList;
    }













}
