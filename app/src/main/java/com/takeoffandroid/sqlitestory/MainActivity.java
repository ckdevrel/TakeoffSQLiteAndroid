package com.takeoffandroid.sqlitestory;

import android.os.Bundle;
import android.widget.TextView;


/**
 * Created by chandrasekar.kuppusa on 15-09-2015.
 */
public class MainActivity extends BaseActivity {


    private TextView txtName;
    private String mMobile;
    private Authentication mAuthentication;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        findViews ();

        mMobile = getIntent ().getStringExtra ("mobile");
        mAuthentication = DBAccess.init(MainActivity.this).getAuthenticationDetails (DBAccess.init(MainActivity.this).KEY_MOBILE,mMobile);

        txtName.setText ("Hello "+mAuthentication.getName ()+"!");
    }

    private void findViews() {
        txtName = (TextView)findViewById( R.id.txt_name );
    }
}
