package com.takeoffandroid.sqlitestory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText edtMobile;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtForgotPassword;
    private TextView txtChangePassword;
    private TextView txtSignup;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        findViews ();
        //        printKeyHash(this);
    }

    private void findViews () {
        edtMobile = (EditText) findViewById (R.id.edt_mobile);
        edtPassword = (EditText) findViewById (R.id.edt_password);
        btnLogin = (Button) findViewById (R.id.btn_login);
        txtForgotPassword = (TextView)findViewById (R.id.txt_forgot_pass);
        txtChangePassword = (TextView)findViewById (R.id.txt_change_pass);
        txtSignup = (TextView)findViewById (R.id.txt_signup);

        txtSignup.setOnClickListener (this);
        btnLogin.setOnClickListener (this);
        txtForgotPassword.setOnClickListener (this);
        txtChangePassword.setOnClickListener (this);


    }

    @Override
    public void onClick (View v) {

        String mobile = edtMobile.getText ().toString ();
        String password = edtPassword.getText ().toString ();

        switch (v.getId ()) {

            case R.id.btn_login:

                if(mobile.length () > 0 && password.length () > 0) {
                    if(DBAccess.init(this).isMobileAlreadyExists (mobile)) {
                        if(DBAccess.init(this).isVerifyUser (mobile, password)) {
                            Toast.makeText (LoginActivity.this, "You are Successfully logged in", Toast.LENGTH_SHORT).show ();
                            Intent mainIntent = new Intent (LoginActivity.this,MainActivity.class);
                            mainIntent.putExtra ("mobile",mobile);
                            startActivity (mainIntent);

                        } else {
                            Toast.makeText (LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show ();

                        }
                    }else {
                        Toast.makeText (LoginActivity.this, "You are not registered with us, please Signup", Toast.LENGTH_SHORT).show ();
                        Intent signupIntent = new Intent (LoginActivity.this,SignupActivity.class);
                        startActivity (signupIntent);
                    }
                } else {
                    Toast.makeText (LoginActivity.this, "The Username and Password fields should not left blank", Toast.LENGTH_SHORT).show ();

                }
                break;


            case R.id.txt_forgot_pass:

                   forgotPasswordDialog();

                break;

            case R.id.txt_change_pass:

                changePasswordDialog ();

                break;

            case R.id.txt_signup:

                Intent signupIntent = new Intent (LoginActivity.this,SignupActivity.class);
                startActivity (signupIntent);

                break;

        }
    }

    public void forgotPasswordDialog() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding (20,20,20,20);
        ll.setLayoutParams(params);


        // add edit text
        final EditText et = new EditText(this);
        et.setLayoutParams (params);
        et.setHint("Mobile");
        ll.addView (et);


        final AlertDialog.Builder builder = new AlertDialog.Builder (this, R.style.AppCompatAlertDialogStyle);
        builder.setView (ll);
        builder.setTitle ("Forgot Password");
        builder.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                if(DBAccess.init(LoginActivity.this).isMobileAlreadyExists (et.getText ().toString ())) {
                    Toast.makeText (LoginActivity.this, "Your password is " + DBAccess.init(LoginActivity.this).getPasswordFromMobile (et.getText ().toString ()), Toast.LENGTH_LONG).show ();
                }else{
                    Toast.makeText (LoginActivity.this, "User not found. Please register", Toast.LENGTH_LONG).show ();

                }
            }
        });
        builder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                dialog.dismiss ();
            }
        });
        builder.show ();
    }

    public void changePasswordDialog( ) {

        int size = 3;
        String[] edtHint = new String[]{"Enter your mobile number","Enter your old password","Enter your new password"};

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation (LinearLayout.VERTICAL);
        ll.setPadding (20,20,20,20);
        ll.setLayoutParams(params);


        // add edit text

        for(int i=0; i<size; i++) {
            EditText et = new EditText (this);
            et.setLayoutParams (params);
            et.setHint (edtHint[i]);
            ll.addView (et);
        }


        final AlertDialog.Builder builder = new AlertDialog.Builder (this, R.style.AppCompatAlertDialogStyle);
        builder.setView (ll);
        builder.setTitle ("Change Password");
        builder.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                if(DBAccess.init(LoginActivity.this).isVerifyUser (((EditText) ll.getChildAt (0)).getText ().toString (), ((EditText) ll.getChildAt (1)).getText ().toString ())) {

                    DBAccess.init(LoginActivity.this).updatePassword (((EditText) ll.getChildAt (0)).getText ().toString (), ((EditText) ll.getChildAt (1)).getText ().toString (), ((EditText) ll.getChildAt (2)).getText ().toString ());
                    Toast.makeText (LoginActivity.this, "Successfully your password updated", Toast.LENGTH_LONG).show ();

                }else{

                    Toast.makeText (LoginActivity.this, "User not found. Please register", Toast.LENGTH_LONG).show ();
                }
            }
        });
        builder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                dialog.dismiss ();
            }
        });
        builder.show ();
    }
}
