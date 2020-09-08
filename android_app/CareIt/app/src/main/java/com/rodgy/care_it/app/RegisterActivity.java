package com.rodgy.care_it.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.firstname) EditText inputFirstName;
    @BindView(R.id.lastname) EditText inputLastName;
    @BindView(R.id.address) EditText inputAddress;
    @BindView(R.id.city) EditText inputCity;
    @BindView(R.id.country) EditText inputCountry;
    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.mobile) EditText inputMobile;
    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.input_reEnterPassword) EditText reEnterPasswordText;
    @BindView(R.id.btnRegister) Button btnRegister;
    @BindView(R.id.btnLinkToLoginScreen)TextView btnLinkToLogin;

    private ProgressDialog PD;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        // Progress dialog
        PD = new ProgressDialog(this);
        PD.setCancelable(false);


        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String firstname = inputFirstName.getText().toString().trim();
                String lastname = inputLastName.getText().toString().trim();
                String address = inputAddress.getText().toString().trim();
                String city = inputCity.getText().toString().trim();
                String country = inputCountry.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String mobile = inputMobile.getText().toString().trim();
                String password = inputPassword.getText().toString();
                String passwordR = reEnterPasswordText.getText().toString();

                if (!firstname.isEmpty() && !lastname.isEmpty() && !address.isEmpty() && !city.isEmpty() && !country.isEmpty()
                        && !mobile.isEmpty()
                        && !password.isEmpty() && !passwordR.isEmpty()) {
                    if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        inputEmail.setError("enter a valid email address");
                    } else {
                        if (password.equals(passwordR)) {
                            registerUser(firstname, lastname, address, city, country, email, mobile, password);
                        } else {
                            reEnterPasswordText.setError("no much, please verify your password!");
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, firstname, lastname, address, city, country
     * email, mobile, password) to register url
     * */
    private void registerUser(final String firstname, final String lastname, final String address, final String city,
                              final String country, final String email, final String mobile,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        PD.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                WebServiceLinks.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "User successfully registered. Login Now", Toast.LENGTH_LONG).show();
                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {


      /*     @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=utf-8" );
                    return params;
                }*/


            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("address", address);
                params.put("city", city);
                params.put("country", country);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!PD.isShowing())
            PD.show();
    }

    private void hideDialog() {
        if (PD.isShowing())
            PD.dismiss();
    }
}
