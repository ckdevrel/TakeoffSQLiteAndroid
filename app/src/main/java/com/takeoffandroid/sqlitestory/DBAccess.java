package com.takeoffandroid.sqlitestory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;


public class DBAccess extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME="database";

    //UserMaster Table
    public static final String TABLE_USERMASTER = "usermastertable";
    public static final String KEY_ID = "id";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public static final String CREATE_TABLE_USERMASTER =
            "CREATE TABLE "+ TABLE_USERMASTER +" ("+
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_USERNAME + " TEXT, "+
                    KEY_MOBILE + " TEXT, "+
                    KEY_EMAIL + " TEXT, "+
                    KEY_PASSWORD + " TEXT)";


    public static final String[] COLUMNS_USERMASTER = {
            KEY_ID,
            KEY_USERNAME,
            KEY_MOBILE,
            KEY_EMAIL,
            KEY_PASSWORD};

    public DBAccess (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {

        db.execSQL (CREATE_TABLE_USERMASTER);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {


    }


    public void insertUserMasterObject (Authentication authentication){

        SQLiteDatabase db = getWritableDatabase ();
        String INSERT = "insert into " + TABLE_USERMASTER + " ("+KEY_USERNAME + ","+ KEY_MOBILE+","+ KEY_EMAIL+","+ KEY_PASSWORD+")" + " values (?,?,?,?)";
        SQLiteStatement insertStatement = db.compileStatement (INSERT);
        insertStatement.bindString (1,authentication.getName ());
        insertStatement.bindString (2,authentication.getMobile ());
        insertStatement.bindString (3,authentication.getEmail ());
        insertStatement.bindString (4,authentication.getPassword ());

        insertStatement.executeInsert ();
        insertStatement.clearBindings ();
        db.close ();
        close();
    }

    public Authentication getAuthenticationDetails (String COLUMN_NAME, String COLUMN_VALUE) {

        SQLiteDatabase db = this.getReadableDatabase ();

        Cursor cursor = db.query (TABLE_USERMASTER, // a. table
                COLUMNS_USERMASTER, // b. column names
                COLUMN_NAME+ " = ?", // c. selections
                new String[]{String.valueOf (COLUMN_VALUE)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if(cursor != null )
            cursor.moveToFirst ();

        if(cursor.getCount ()>0) {
            Authentication authentication = new Authentication ();
            authentication.setName (cursor.getString (1));
            authentication.setMobile (cursor.getString (2));
            authentication.setEmail (cursor.getString (3));
            authentication.setPassword (cursor.getString (4));
            return authentication;
        }else
            return null;
    }


    public void insertUserMasterList (ArrayList<Authentication> authList){

        SQLiteDatabase db = getWritableDatabase ();
        String INSERT = "insert into " + TABLE_USERMASTER + " ("+KEY_USERNAME + ","+ KEY_MOBILE+","+ KEY_EMAIL+","+ KEY_PASSWORD+")" + " values (?,?,?,?)";
        SQLiteStatement insertStatement = db.compileStatement (INSERT);
        for(Authentication authentication: authList) {
            insertStatement.bindString (1, authentication.getName ());
            insertStatement.bindString (2, authentication.getMobile ());
            insertStatement.bindString (3, authentication.getEmail ());
            insertStatement.bindString (4, authentication.getPassword ());

            insertStatement.executeInsert ();
            insertStatement.clearBindings ();
        }

        db.close ();
        close();
    }


    public ArrayList<Authentication> getUserMasterList(){

        ArrayList<Authentication> allDetails = new ArrayList<Authentication> ();
        SQLiteDatabase db = getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERMASTER, null, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst ()) {
            if(cursor.getCount () > 0) {
                do{
                    Authentication authentication = new Authentication ();
                    authentication.setName (cursor.getString (0));
                    authentication.setMobile (cursor.getString (1));
                    authentication.setEmail (cursor.getString (2));
                    authentication.setPassword (cursor.getString (3));
                    allDetails.add (authentication);
                }while (cursor.moveToNext ());

            }
        }
        cursor.close ();
        db.close ();
        close ();
        return allDetails;
    }

    public void updatePassword(String mobile, String oldPassword, String newPassword){

        SQLiteDatabase db = getWritableDatabase ();
        ContentValues values = new ContentValues ();
        values.put (KEY_PASSWORD,newPassword);
        db.update (TABLE_USERMASTER, values, KEY_MOBILE + "=?" + " AND " + KEY_PASSWORD + "=?", new String[]{ mobile, oldPassword });
        db.close ();
        close ();
    }




    //Get password based on particular username
    public String getPasswordFromMobile(String mobile){

        String password = "";
        SQLiteDatabase db = getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERMASTER, new String[]{ KEY_PASSWORD }, KEY_MOBILE + "='" + mobile + "'", null, null, null, null);
        if(cursor != null && cursor.moveToFirst ()){
            if(cursor.getCount ()>0) {
                int columnIndex = cursor.getColumnIndex (KEY_PASSWORD);
                password = cursor.getString (columnIndex);
            }
        }

        cursor.close ();
        db.close ();
        close ();
        return password;
    }

    public boolean isVerifyUser (String mobile, String password){

        SQLiteDatabase db = getReadableDatabase ();
        String Query = "Select * from " + TABLE_USERMASTER + " where " + KEY_MOBILE + " = " + "'"+mobile+"'" + " AND "+KEY_PASSWORD + " = "+"'"+password+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean isMobileAlreadyExists(String mobile){

        SQLiteDatabase db = getReadableDatabase ();
        String Query = "Select * from " + TABLE_USERMASTER + " where " + KEY_MOBILE + " = " + "'"+mobile+"'";
        Cursor cursor = db.rawQuery (Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close ();
        return true;
    }
}
