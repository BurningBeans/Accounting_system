package com.example.accountingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginAct extends AppCompatActivity {
    Button login,forgetPasswd,createAcc;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        System.out.println("current layout is login");
        Intent intent = getIntent();
        //dbHelper = (DBHelper) intent.getSerializableExtra("dbHelper");
        dbHelper = new DBHelper(this);
        login = findViewById(R.id.register);
        forgetPasswd = findViewById(R.id.forgetpasswd);
        createAcc = findViewById(R.id.createAccount);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText) findViewById(R.id.edAccount)).getText().toString();
                String password = ((EditText) findViewById(R.id.edPassword)).getText().toString();
                Account account = new Account().getAccountByUsername(dbHelper,username,password);

                if(account != null){
                    /*Intent intent = new Intent(LoginAct.this, MainActivity.class);
                    intent.putExtra("Account", (Serializable) account);
                    startActivity(intent);*/
                    Intent resultIntent = new Intent();

                    // Put the value you want to send back as an extra in the intent
                    resultIntent.putExtra("RESULT_ACCOUNT", account);

                    // Set the result code and intent to be sent back
                    setResult(Activity.RESULT_OK, resultIntent);

                    // Call finish to close the activity and send back the result
                    finish();
                }else{
                    ((TextView) findViewById(R.id.errorMsg)).setText("no account, create first");

                }

            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAct.this, RegisterAct.class);
                startActivity(intent);
            }
        });


    }
}