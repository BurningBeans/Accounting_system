package com.example.accountingsystem;

import java.util.Calendar;

public class myDate {
    private int year;
    private int month;
    private int day;


    public static myDate parseDate(String dateString) {
        String[] parts = dateString.split("/");
         if (parts.length == 2) {
             int year = Integer.parseInt(parts[0]);
             int month = Integer.parseInt(parts[1]);
             int day = 1;
             return new myDate(year, month, day);
         }
        else if(parts.length == 3){
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return new myDate(year, month, day);
        }
        else{
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy/mm/dd");
        }
    }

    public void increaseYear() {
        year++;
    }

    public void decreaseYear() {
        year--;
    }

    public void increaseMonth() {
        month++;
        if (month > 12) {
            month = 1;
            increaseYear();
        }
    }

    public void decreaseMonth() {
        month--;
        if (month < 1) {
            month = 12;
            decreaseYear();
        }
    }

    public void increaseDay() {
        day++;
        int maxDays = getDaysInMonth();
        if (day > maxDays) {
            day = 1;
            increaseMonth();
        }
    }

    public void decreaseDay() {
        day--;
        if (day < 1) {
            decreaseMonth();
            day = getDaysInMonth();
        }
    }

    public int getDaysInMonth() {
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear()) {
            return 29;
        } else {
            return daysInMonth[month - 1];
        }
    }

    public boolean isLeapYear() {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        }
        return false;
    }

    public myDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
    public String toMonthString() {
        return year + "/" + month;
    }
    public myDate(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1; // Note: Month starts from 0 (January is 0)
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //System.out.println("Current date: " + year + "-" + month + "-" + day);
    }
}
