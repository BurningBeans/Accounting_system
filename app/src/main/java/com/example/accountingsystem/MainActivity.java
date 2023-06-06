package com.example.accountingsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private PieChart pieChart;
    private Button increaseMonthBtn, decreaseMonthBtn;
    private TextView tvCurrentDate;
    private static final int REQUEST_CODE_OTHER_ACTIVITY = 1;

    DBHelper dbHelper;

    private Button datePickerButton, incomeConfirmBtn, expensebtn,incomebtn, selectBtn;
    private LayoutInflater inflater,exinflater ;
    private View dialogView,exdialogView;
    private AlertDialog dialog, dialog1,dialog2;
    private myDate date=new myDate();
    private TextView edDate;
    private LinearLayout mainLinearLayout;
    private View[] addedViews;
    private Account account;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialLayout();
        setButtonClickListener();
        initPieChart();
        showPieChart(new HashMap<>());
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true);

        if(account == null){ startLoginActivity();}
        else{
            date = new myDate();
            //date = date.parseDate("2023/6/4");


            //
            //Button btnPositive = dialogView.findViewById(R.id.incomeConfirmBtn);
            //Button csDatePickerButton = findViewById(R.id.datePickerButton);
            //Button btnNegative = dialogView.findViewById(R.id.btn_negative);
            //
            //dialog.show();

            //account = new Account("test1", "pass");
            //account.insertData(dbHelper);

            //Report report1 = account.createAndInsertReport(dbHelper, new myDate());
            //Report report1 = account.getReportsByAccountIdAndDate(dbHelper,account.getAccountId(), date.toMonthString());
            //report1.createAndInsertIncome(dbHelper,date,"food","breakfast",45,"sandwich");
            createList();

            //report1.createAndInsertIncome(dbHelper, "food","launch",90,"vegan house");
        /*
        Report report2 = account.getReportsByAccountIdAndDate(dbHelper, account.getAccountId(), "2023/6");
        report2.setIncomeByReportId(dbHelper, report2.getReportId());
        System.out.println(report2.toString());*/



        /*LinearLayout linearLayout = findViewById(R.id.linear); // Assuming you have a LinearLayout with id "linearLayout" in your layout
        LayoutInflater itemTemplateInflater = getLayoutInflater();
        View itemTemplateView = itemTemplateInflater.inflate(R.layout.item_templete_1,null);
        LinearLayout itemTemplateLinearLayout = itemTemplateView.findViewById(R.id.itemTemplateLinaer);
        int numberOfTextViews = 50; // The number of TextView instances you want to create

        for (int i = 0; i < numberOfTextViews; i++) {
            TextView textView = new TextView(this);
            textView.setText("TextView " + (i + 1)); // Set the text for the TextView

            // Add any additional configurations for the TextView (e.g., text color, size, etc.)

            linearLayout.addView(itemTemplateLinearLayout); // Add the TextView to the LinearLayout
        }*/

        }



    }
    private void startLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginAct.class);
        someActivityResultLauncher.launch(intent);
    }
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // Handle the result returned from OtherActivity
                    account = (Account) data.getSerializableExtra("RESULT_ACCOUNT");
                    createList();
                    Toast.makeText(this, "Received result: " + account.toString(), Toast.LENGTH_SHORT).show();
                }
            }
    );

    private void createList(){
        LayoutInflater itemTemplateInflater = getLayoutInflater();
        Report tmpReport = new Report();
        tmpReport=account.getReportsByAccountIdAndDate(dbHelper,account.getAccountId(),date.toMonthString());
        System.out.println(tmpReport.toString());
        tmpReport.setIncomeListByReportIdAndDate(dbHelper,date.toString());
        List<Income> tmpIncomeList = new ArrayList<>();
        tmpIncomeList = tmpReport.getIncomeList();
        if(tmpIncomeList.isEmpty()){
            addedViews = new View[1];
            View itemTemplateView = itemTemplateInflater.inflate(R.layout.item_template_1, null);
            //LinearLayout itemLinearLayout = itemTemplateView.findViewById(R.id.itemTemplateLinaer);
            // Find the TextViews in the inflated layout
            TextView topLeftTextView = itemTemplateView.findViewById(R.id.top_left);
            TextView bottomLeftTextView = itemTemplateView.findViewById(R.id.bottom_left);
            TextView topRightTextView = itemTemplateView.findViewById(R.id.top_right);
            TextView bottomRightTextView = itemTemplateView.findViewById(R.id.bottom_right);

            // Set the desired text for each TextView
            System.out.println("no data in "+date.toString());
            topLeftTextView.setText("no data");
            bottomLeftTextView.setText("no data");
            bottomRightTextView.setText("no data");
            addedViews[0] = itemTemplateView;
            mainLinearLayout.addView(itemTemplateView); // Add the itemTemplateView to the linearLayout

        }
        else {
            addedViews = new View[tmpIncomeList.size()];
        }
        for (int i = tmpIncomeList.size()-1; i >-1 ; i--) {
            View itemTemplateView = itemTemplateInflater.inflate(R.layout.item_template_1, null);
            //LinearLayout itemLinearLayout = itemTemplateView.findViewById(R.id.itemTemplateLinaer);
            Income tmpIncome = tmpIncomeList.get(i);
            // Find the TextViews in the inflated layout
            TextView topLeftTextView = itemTemplateView.findViewById(R.id.top_left);
            TextView bottomLeftTextView = itemTemplateView.findViewById(R.id.bottom_left);
            //TextView topRightTextView = itemTemplateView.findViewById(R.id.top_right);
            TextView bottomRightTextView = itemTemplateView.findViewById(R.id.bottom_right);

            // Set the desired text for each TextView
            System.out.println(tmpIncome.toString());
            topLeftTextView.setText(tmpIncome.getDate().toString());
            bottomLeftTextView.setText(tmpIncome.getCategory()+" - "+ tmpIncome.getItem());
            //topRightTextView.setText("Right Text " + i);
            bottomRightTextView.setText(Double.toString(tmpIncome.getAmount()));

            //itemLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.pink));
            mainLinearLayout.addView(itemTemplateView); // Add the itemTemplateView to the linearLayout
            addedViews[i] = itemTemplateView;
        }

    }
    private void releaseList(){
        if(addedViews == null){
            return;
        }
        for (int i = 0; i < addedViews.length; i++) {
            View viewToRemove = addedViews[i];
            mainLinearLayout.removeView(viewToRemove);
        }
        addedViews = null;
    }
    private Map<String, Double> getIncomeListToMap(){
        Map<String,Double> tmpMap = new HashMap<>();
        Report tmpReport = account.getReportsByAccountIdAndDate(dbHelper,account.getAccountId(),date.toMonthString());
        List<Income> tmpIncomeList = tmpReport.getIncomeByReportId(dbHelper, tmpReport.getReportId());
        for(int i =0; i< tmpIncomeList.size(); i++){
            Income tmpIncome = tmpIncomeList.get(i);
            String category = tmpIncome.getCategory();
            double amount = tmpIncome.getAmount();
            if(tmpMap.containsKey(category)){
                amount = tmpMap.get(category) + amount;
            }
            tmpMap.put(category, amount);
        }
        return tmpMap;
    }



    private void showPieChart(Map<String, Double> typeAmountMap1){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";
        //typeAmountMap = getIncomeListToMap();
        //initializing data
        Map<String, Double> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Toys", 200.0);
        typeAmountMap.put("Snacks", 230.0);
        typeAmountMap.put("Clothes", 100.0);
        typeAmountMap.put("Stationary", 500.0);
        typeAmountMap.put("Phone", 50.0);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    private void setButtonClickListener() {

        increaseMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.increaseDay();
                tvCurrentDate.setText(date.toString());
                releaseList();
                createList();
            }
        });
        decreaseMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.decreaseDay();
                tvCurrentDate.setText(date.toString());
                releaseList();
                createList();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) dialogView.findViewById(R.id.tvDate)).setText(date.toString());
                dialog1.show();
            }
        });
        incomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                dialog2.show();
            }
        });
        expensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                dialog.show();
            }
        });
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                // Create a DatePickerDialog and set the current date as the initial date
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date
                        // You can update your UI or perform any necessary operations
                        String selectedDate = year + "/" + (month+1) + "/" + dayOfMonth;
                        // Do something with the selected date
                        edDate.setText(selectedDate);
                    }
                }, date.getYear(), date.getMonth(), date.getDay());
                //}, year, month, dayOfMonth);
                // Show the date picker dialog
                datePickerDialog.show();
            }
        });

        incomeConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpDate =((TextView) dialogView.findViewById(R.id.tvDate)).getText().toString();
                myDate date = new myDate().parseDate (tmpDate);
                String category = ((EditText) dialogView.findViewById(R.id.edCategory)).getText().toString();
                String item = ((EditText) dialogView.findViewById(R.id.edItem)).getText().toString();
                String tmpAmount = ((EditText) dialogView.findViewById(R.id.edAmount)).getText().toString();
                String description = ((EditText) dialogView.findViewById(R.id.edDescription)).getText().toString();
                getData(view,account,tmpDate,date,category,item,tmpAmount,description);
                dialog.dismiss();
                releaseList();
                createList();
                ((TextView) dialogView.findViewById(R.id.tvDate)).setText("");
                ((EditText) dialogView.findViewById(R.id.edCategory)).setText("");
                ((EditText) dialogView.findViewById(R.id.edItem)).setText("");
                ((EditText) dialogView.findViewById(R.id.edAmount)).setText("");
                ((EditText) dialogView.findViewById(R.id.edDescription)).setText("");

            }
        });

    }
    public void startLoginPage(){
        Intent intent = new Intent(MainActivity.this, LoginAct.class);
        //intent.putExtra("dbHelper", (Serializable) dbHelper);
        startActivity(intent);
        //finish();
    }

    //view,date,category,item,tmpAmount,description
    private void initialLayout(){
        dbHelper = new DBHelper(MainActivity.this);
        mainLinearLayout = findViewById(R.id.linear); // Assuming you have a LinearLayout with id "linear" in your layout
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.create_income_alert, null);
        datePickerButton = dialogView.findViewById(R.id.datePickerButton);
        incomeConfirmBtn = dialogView.findViewById(R.id.incomeConfirmBtn);
        exinflater = getLayoutInflater();
        exdialogView = exinflater.inflate(R.layout.select, null);
        incomebtn = exdialogView.findViewById(R.id.exincome);
        expensebtn = exdialogView.findViewById(R.id.exexpense);
        selectBtn = findViewById(R.id.button);
        increaseMonthBtn = findViewById(R.id.increaseMonthBtn);
        decreaseMonthBtn = findViewById(R.id.decreaseMonthBtn);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        tvCurrentDate.setText(date.toString());

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("選擇愈新增項目")
                .setView(exdialogView);
        dialog1 = builder1.create();

        edDate = dialogView.findViewById(R.id.tvDate);
        edDate.setText(new myDate().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增收入")
                .setView(dialogView);
        dialog = builder.create();
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("新增支出")
                .setView(dialogView);
        dialog2 = builder2.create();

        pieChart = findViewById(R.id.pieChart_view);
        pieChart.setHoleColor(Color.parseColor("#000000"));
        pieChart.setOnChartValueSelectedListener(new PieChartOnChartValueSelectedListener());


    }
    private void getData(View view,Account account,String tmpDate,myDate date, String category, String item,String tmpAmount, String description)
    {
        double amount =0;
        if (tmpAmount.matches("-?\\d+(\\.\\d+)?")) {
            amount = Double.parseDouble(tmpAmount);
        }

        System.out.println("Date: " + date.toString());
        System.out.println("category: " + category);
        System.out.println("item: " + item);
        System.out.println("amount: " + amount);
        System.out.println("description: " + description);
        if( !tmpDate.isEmpty() && !category.isEmpty() && !item.isEmpty() && amount!=0 && !description.isEmpty()){
            Report tmpReport = new Report();
            tmpReport = account.getReportsByAccountIdAndDate(dbHelper ,account.getAccountId(), date.toMonthString() ) ;
            if(tmpReport != null){
                System.out.println(tmpReport.toString());
            }else{
                tmpReport = account.createAndInsertReport(dbHelper, date);
            }
            tmpReport.createAndInsertIncome(dbHelper,date, category, item, amount, description);


        }
    }
    private void initPieChart(){
        //using percentage as values instead of amount
        pieChart.setUsePercentValues(true);

        //remove the description label on the lower left corner, default true if not set
        pieChart.getDescription().setEnabled(false);

        //enabling the user to rotate the chart, default true
        pieChart.setRotationEnabled(true);
        //Set this to true to draw the entry labels into the pie slices.

        //adding friction when rotating the pie chart
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        //setting the first entry start from right hand side, default starting from top
        pieChart.setRotationAngle(0);

        //highlight the entry when it is tapped, default true if not set
        pieChart.setHighlightPerTapEnabled(true);
        //adding animation so the entries pop up from 0 degree
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        //setting the color of the hole in the middle, default white
        pieChart.setHoleColor(Color.parseColor("#ffffff"));

    }
    private class PieChartOnChartValueSelectedListener implements OnChartValueSelectedListener {

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            // Trigger activities when entry is clicked
        }

        @Override
        public void onNothingSelected() {

        }
    }
}