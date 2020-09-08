package com.rodgy.care_it.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.rodgy.care_it.app.MainActivity.userEmail;

public class CustomerHistoryActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtEmail;
    private Button btnLogout;
    static String userEmail;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_history);

        //back press toolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.your_history);
        }
        txtEmail = (TextView) findViewById(R.id.mail);
        //btnLogout = (Button) findViewById(R.id.btnLogout);
        // session manager
        session = new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        userEmail = intent.getStringExtra(LoginActivity.Email);
        txtEmail.setText(userEmail);

        // Logout button click event
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                logoutUser();
//            }
//        });

        floatingActionButton=findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerHistoryActivity.this, CustomerUploadActivity.class);
                startActivity(intent);
            }
        });
    }
    //back press toolbar
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);
        // Launching the login activity
        Intent intent = new Intent(CustomerHistoryActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        userEmail = intent.getStringExtra(LoginActivity.Email);
        txtEmail.setText(userEmail);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Intent intent = getIntent();
        userEmail = intent.getStringExtra(LoginActivity.Email);
        txtEmail.setText(userEmail);

    }
}