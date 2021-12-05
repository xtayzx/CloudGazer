package com.example.cloudgazer;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

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
                    Constants.USER+ " TEXT, " +
                    Constants.PHOTO+ " TEXT);" ;

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
            Toast.makeText(context, "Welcome to Cloud Gazer!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
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
        contentValues1.put(Constants.TITLE, "rainbow sunsets");
        contentValues1.put(Constants.DATE, "2021/11/01");
        contentValues1.put(Constants.TIME, "16:06");
        contentValues1.put(Constants.LOCATION, "Home");
        contentValues1.put(Constants.RATE_DAY, "8");
        contentValues1.put(Constants.WEATHER, "Altocumulus");
        contentValues1.put(Constants.DAY_DES, "Today was super great! I saw the most gorgeous sunset from my porch. What a" +
                " view!");
        contentValues1.put(Constants.COMMUNITY, "Yes");
        contentValues1.put(Constants.USER, "Amy");
        contentValues1.put(Constants.PHOTO, "cloudGazer1");
        db.insert(Constants.TABLE_NAME, null, contentValues1);
        Log.i("INPUT DATA", "Data 1 has been inputted in the database");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(Constants.TITLE, "SUMMER SUNRISES!!");
        contentValues2.put(Constants.DATE, "2021/07/05");
        contentValues2.put(Constants.TIME, "06:45");
        contentValues2.put(Constants.LOCATION, "The Park");
        contentValues2.put(Constants.RATE_DAY, "9");
        contentValues2.put(Constants.WEATHER, "Other");
        contentValues2.put(Constants.DAY_DES, "Sometimes all you need is an amazing sunset to start off the day. Look at these" +
                " views! It may be 6:00AM, but just look at what you are missing!");
        contentValues2.put(Constants.COMMUNITY, "Yes");
        contentValues2.put(Constants.USER, "Bianca");
        contentValues2.put(Constants.PHOTO, "cloudGazer2");
        db.insert(Constants.TABLE_NAME, null, contentValues2);
        Log.i("INPUT DATA", "Data Set 2 has been inputted in the database");

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(Constants.TITLE, "On the train downtown");
        contentValues3.put(Constants.DATE, "2021/09/12");
        contentValues3.put(Constants.TIME, "06:37");
        contentValues3.put(Constants.LOCATION, "The Train Station");
        contentValues3.put(Constants.RATE_DAY, "5");
        contentValues3.put(Constants.WEATHER, "Cumulus");
        contentValues3.put(Constants.DAY_DES, "My day was okay today, but I just really wanted to share this photo that I" +
                        " took as I was waiting for the train to head off downtown. That's what made it great.");
        contentValues3.put(Constants.COMMUNITY, "Yes");
        contentValues3.put(Constants.USER, "Charlie");
        contentValues3.put(Constants.PHOTO, "cloudGazer3");
        db.insert(Constants.TABLE_NAME, null, contentValues3);
        Log.i("INPUT DATA", "Data Set 3 has been inputted in the database");
    }
}
