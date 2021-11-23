package com.example.cloudgazer;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    Constants.TABLE_NAME + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.TITLE + " TEXT, " +
                    Constants.DATE + " TEXT, " +
                    Constants.TIME + " TEXT, " +
                    Constants.LOCATION + " TEXT, " +
                    Constants.RATE_DAY + " TEXT, " +
                    Constants.WEATHER+ " TEXT, " +
                    Constants.DAY_DES+ " TEXT, " +
                    Constants.COMMUNITY+ " TEXT, " +
                    Constants.USER + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i("DATABASE", "No database created, make a database");
            db.execSQL(CREATE_TABLE);
            communitySampleData(db);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Log.i("DATABASE", "Catch called");
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            Log.i("DATABASE", "Database created, clear and make new one");
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }

    protected void communitySampleData(SQLiteDatabase db) {
        //insert sample data for the community activity
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(Constants.TITLE, "The Best Day");
        contentValues1.put(Constants.DATE, "2021/11/01");
        contentValues1.put(Constants.TIME, "12:01");
        contentValues1.put(Constants.LOCATION, "Home");
        contentValues1.put(Constants.RATE_DAY, "6");
        contentValues1.put(Constants.WEATHER, "Cumulonimbus");
        contentValues1.put(Constants.DAY_DES, "Today was the best day!");
        contentValues1.put(Constants.COMMUNITY, "Yes");
        contentValues1.put(Constants.USER, "Amy");
        db.insert(Constants.TABLE_NAME, null, contentValues1);
        Log.i("INPUT DATA", "Data 1 has been inputted in the database");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(Constants.TITLE, "The Okay Day");
        contentValues2.put(Constants.DATE, "2021/11/02");
        contentValues2.put(Constants.TIME, "12:02");
        contentValues2.put(Constants.LOCATION, "The Park");
        contentValues2.put(Constants.RATE_DAY, "7");
        contentValues2.put(Constants.WEATHER, "Cumulus");
        contentValues2.put(Constants.DAY_DES, "Today was an okay day.");
        contentValues2.put(Constants.COMMUNITY, "Yes");
        contentValues2.put(Constants.USER, "Bob");
        db.insert(Constants.TABLE_NAME, null, contentValues2);
        Log.i("INPUT DATA", "Data Set 2 has been inputted in the database");

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(Constants.TITLE, "The Worst Day");
        contentValues3.put(Constants.DATE, "2021/11/03");
        contentValues3.put(Constants.TIME, "12:03");
        contentValues3.put(Constants.LOCATION, "Bedroom");
        contentValues3.put(Constants.RATE_DAY, "2");
        contentValues3.put(Constants.WEATHER, "Cirrus");
        contentValues3.put(Constants.DAY_DES, "Today was the worst day ever. I really hope it becomes sunny again soon.");
        contentValues3.put(Constants.COMMUNITY, "Yes");
        contentValues3.put(Constants.USER, "Charlie");
        db.insert(Constants.TABLE_NAME, null, contentValues3);
        Log.i("INPUT DATA", "Data Set 3 has been inputted in the database");
    }
}
