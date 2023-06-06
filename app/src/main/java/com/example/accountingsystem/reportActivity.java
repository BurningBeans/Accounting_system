package com.example.accountingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class reportActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button increaseMonthBtn, decreaseMonthBtn;
    private TextView tvCurrentDate;
    private ViewPager2Adapter adapter;
    //private String[] tabTitles = {"收入", "支出"};
    private TabLayout tablayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_date_selecter);

        increaseMonthBtn = findViewById(R.id.increaseMonthBtn);
        decreaseMonthBtn = findViewById(R.id.decreaseMonthBtn);
        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        myDate date = new myDate();
        tvCurrentDate.setText(date.toMonthString());
        increaseMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.increaseMonth();
                tvCurrentDate.setText(date.toMonthString());
            }
        });
        decreaseMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.decreaseMonth();
                tvCurrentDate.setText(date.toMonthString());
            }
        });
        adapter = new ViewPager2Adapter(getSupportFragmentManager());
        adapter.addFragment(new incomeact(),"收入");
        adapter.addFragment(new expenseact(),"支出");
        viewPager = findViewById(R.id.viewPagerMain);
        tablayout = findViewById(R.id.tablayout);


        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(2);
        tablayout.setupWithViewPager(viewPager);


    }



}