package com.example.accountingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterAct extends AppCompatActivity {
    Button register, backToLogin;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        System.out.println("current layout is register");
        Intent intent = getIntent();
        //dbHelper = (DBHelper) intent.getSerializableExtra("dbHelper");
        dbHelper = new DBHelper(this);
        register = findViewById(R.id.register);
        backToLogin = findViewById(R.id.createAccount);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.edAccount)).getText().toString();
                String password = ((EditText) findViewById(R.id.edPassword)).getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    if (new Account().isAccountAvailable(dbHelper, username)) {
                        Account account = new Account(username, password);
                        account.insertData(dbHelper);
                        ((TextView) findViewById(R.id.errorMsg)).setText("account create success, press back to login");
                    }
                    ((TextView) findViewById(R.id.errorMsg)).setText("username exist");

                } else {
                    ((TextView) findViewById(R.id.errorMsg)).setText("username or password is empty");
                }
            }

        });
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}