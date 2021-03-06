package com.rodgy.care_it.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialActivity extends AppCompatActivity {
    Button customer_button,technician_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        ActionBar actionBar = getSupportActionBar();


        customer_button=findViewById(R.id.customer_button);
        technician_button=findViewById(R.id.technician);

        customer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(InitialActivity.this, LoginActivity.class));
            }
        });

        technician_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(InitialActivity.this, TechnicianLoginActivity.class));
            }
        });

    }
}