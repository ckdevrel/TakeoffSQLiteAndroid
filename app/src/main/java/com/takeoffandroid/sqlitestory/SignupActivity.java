package com.takeoffandroid.sqlitestory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private EditText edtUsername;
    private EditText edtMobile;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignup;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signup);
        findViews ();
    }

    private void findViews () {
        edtUsername = (EditText)findViewById( R.id.edt_username );
        edtMobile = (EditText)findViewById( R.id.edt_mobile );
        edtEmail = (EditText)findViewById( R.id.edt_email );
        edtPassword = (EditText)findViewById( R.id.edt_password );
        btnSignup = (Button)findViewById( R.id.btn_signup );

        btnSignup.setOnClickListener (this);


    }

    @Override
    public void onClick (View v) {

        String name = edtUsername.getText ().toString ();
        String mobile = edtMobile.getText ().toString ();
        String email = edtEmail.getText ().toString ();
        String password = edtPassword.getText ().toString ();


        switch (v.getId ()) {


            case R.id.btn_signup:

                if(mobile.length () > 0 && password.length () > 0) {
                    if(!DBAccess.init(SignupActivity.this).isMobileAlreadyExists (mobile)) {
                        if(!DBAccess.init(SignupActivity.this).isVerifyUser (mobile, password)) {

                            DBAccess.init(SignupActivity.this).insertUserMasterObject (new Authentication (name,mobile,email, password));
                            Toast.makeText (SignupActivity.this, "Registration Success", Toast.LENGTH_SHORT).show ();
                            finish ();

                        } else {
                            Toast.makeText (SignupActivity.this, "You are already registered with us.", Toast.LENGTH_SHORT).show ();

                        }
                    }else{
                        Toast.makeText (SignupActivity.this, "User already exists. Please register with some other mobile number", Toast.LENGTH_SHORT).show ();

                    }
                } else {
                    Toast.makeText (SignupActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show ();

                }
                break;


        }
    }
}
