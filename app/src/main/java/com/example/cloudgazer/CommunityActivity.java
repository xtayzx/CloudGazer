package com.example.cloudgazer;

import android.app.Activity;
import android.content.Intent;
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

public class CommunityActivity extends Activity implements AdapterView.OnItemClickListener{
    RecyclerView myRecycler;
    MyDatabase db;
    MyAdapterC myAdapter;
    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        myRecycler = (RecyclerView) findViewById(R.id.recycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        //populate all the data and put it inside the arraylist
        Cursor cursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.DAY_DES);
        int index4 = cursor.getColumnIndex(Constants.USER);
        int communitySelect = cursor.getColumnIndex(Constants.COMMUNITY);

        //retrieve the arraylist from the database
        //populate all the data from the database and run the while loop

        Intent intent = getIntent();
        String intentQuery = intent.getStringExtra("communitySelect");
        Log.i("TEST", "This is the value of what's passed: "+intentQuery);

            ArrayList<String> mArrayList = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String intentQueryMatch = cursor.getString(communitySelect);
//                is the entry shared in the community forum
                if (intentQuery.equals(intentQueryMatch)) {
                    String title = cursor.getString(index1);
                    String date = cursor.getString(index2);
                    String dayDes = cursor.getString(index3);
                    String user = cursor.getString(index4);
                    String s = title + "," + date + "," + dayDes+ "," + user;
                    mArrayList.add(s);
                    cursor.moveToNext();
                }

                else if (intentQuery != intentQueryMatch) {
                    cursor.moveToNext();
                }
            }
            Log.i("TEST", "Size of ArrayList: "+mArrayList.size());
            myAdapter = new MyAdapterC(mArrayList);
            myRecycler.setAdapter(myAdapter);
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
        TextView user = (TextView) view.findViewById(R.id.userRow);
        Toast.makeText(this, "row " + (1+position) + ":  " + title.getText() + " " + date.getText() +" "+dayDes.getText() +" "+user.getText(), Toast.LENGTH_LONG).show();
    }
}

