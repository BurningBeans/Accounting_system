package com.example.accountingsystem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class CreateIncomeActivity implements View.OnClickListener {

    private Context context;
    private Button datePickerButton;

    public CreateIncomeActivity(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View otherLayout = inflater.inflate(R.layout.create_income_alert, null);
        datePickerButton = otherLayout.findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == datePickerButton) {
            // Get the current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Create a DatePickerDialog and set the current date as the initial date
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Handle the selected date
                    // You can update your UI or perform any necessary operations
                    String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    // Do something with the selected date
                }
            }, year, month, dayOfMonth);

            // Show the date picker dialog
            datePickerDialog.show();
        }
    }
}
