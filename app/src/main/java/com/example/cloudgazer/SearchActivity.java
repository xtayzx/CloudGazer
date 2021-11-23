package com.example.cloudgazer;

import static com.example.cloudgazer.Welcome.DEFAULT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends Activity implements AdapterView.OnItemClickListener{
    RecyclerView myRecycler;
    MyDatabase db;
    MyAdapter myAdapter;
    MyHelper helper;
    TextView dateInputted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myRecycler = (RecyclerView) findViewById(R.id.recycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        dateInputted = (TextView)findViewById(R.id.dateQuery);

        Cursor cursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.DAY_DES);
        int dateSelect = cursor.getColumnIndex(Constants.DATE);
        int usernameSelect = cursor.getColumnIndex(Constants.USER);

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String searchQuery = sharedPrefs.getString("searchQuery", DEFAULT);
        String usernameQuery= sharedPrefs.getString("username", DEFAULT);

        ArrayList<String> mArrayList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            //does the date match and does the user match (is it just a personal entry)
            String dateQueryMatch = cursor.getString(dateSelect);
            String usernameQueryMatch = cursor.getString(usernameSelect);

            if (searchQuery.equals(dateQueryMatch) && usernameQuery.equals(usernameQueryMatch)) {
                String title = cursor.getString(index1);
                String date = cursor.getString(index2);
                String dayDes = cursor.getString(index3);
                String s = title + "," + date + "," + dayDes;
                mArrayList.add(s);
                cursor.moveToNext();
            }

            //else move to the next row
            else if (searchQuery != dateQueryMatch) {
                cursor.moveToNext();
            }
        }
        Log.i("TEST", "Size of ArrayList: "+mArrayList.size());
        myAdapter = new MyAdapter(mArrayList);
        myRecycler.setAdapter(myAdapter);
        //set the search input
        dateInputted.setText(searchQuery);
    }

    public void backToHome (View view)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView title = (TextView) view.findViewById(R.id.titleRow);
        TextView date = (TextView) view.findViewById(R.id.dateRow);
        TextView dayDes = (TextView) view.findViewById(R.id.dayDesRow);
        Toast.makeText(this, "row " + (1+position) + ":  " + title.getText() + " " + date.getText() +" "+dayDes.getText(), Toast.LENGTH_LONG).show();
    }
}

