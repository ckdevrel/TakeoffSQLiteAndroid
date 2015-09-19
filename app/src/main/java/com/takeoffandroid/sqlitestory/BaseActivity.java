package com.takeoffandroid.sqlitestory;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity<H extends SQLiteOpenHelper> extends AppCompatActivity  {
	private volatile H helper;
    private int mTitleRes;

    @Override
	protected void onCreate(Bundle arg0) {
		if(helper == null){
			helper = getHelpInternal(this);
		}
		super.onCreate(arg0);
	}

    public H getHelper() {
		if (helper == null) {
            boolean created = false;
            boolean destroyed = false;
            if (!created) {
				throw new IllegalStateException("A call has not been made to onCreate() yet so the helper is null");
			} else if (destroyed) {
				throw new IllegalStateException(
						"A call to onDestroy has already been made and the helper cannot be used after that point");
			} else {
				throw new IllegalStateException("Helper is null for some unknown reason");
			}
		} else {
			return helper;
		}
	}
	
	protected H getHelpInternal(Context context) {
		@SuppressWarnings({ "unchecked"})
		H newHelper = (H) new DBAccess (context);
		return newHelper;
	}

}
