package com.example.cloudgazer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * Created by helmine on 2017-02-09.
 */

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener{
    RecyclerView myRecycler;
    MyDatabase db;
    MyAdapter myAdapter;
    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myRecycler = (RecyclerView) findViewById(R.id.recycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        //populate all the data and put it inside the arraylist
        Cursor cursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.CLOUD_DES);

        //retrieve the arraylist from the database
        //populate all the data from the database and run the while loop

//        Intent intent = getIntent();
//        String intentQuery = intent.getStringExtra("query");
        //Log.i("TEST", "This is the value of what's passed: "+plantQuery);

//        if(intentQuery != null) {
//            ArrayList<String> mArrayList = new ArrayList<>();
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                //checking for if cloud des matches
//                String intentQueryMatch = cursor.getString(index2);
//                //Log.i("TEST", "This is the value of cursor: "+plantQueryMatch);
//                if (intentQuery.equals(intentQueryMatch)) {
//                    //Log.i("TEST", "It's a MATCH");
//                    String date = cursor.getString(index1);
//                    String cloudType = cursor.getString(index2);
//                    String s = date + "," + cloudType;
//                    mArrayList.add(s);
//                    cursor.moveToNext();
//                }
//
//                else if (intentQuery != intentQueryMatch) {
//                    cursor.moveToNext();
//                }
//            }
//            Log.i("TEST", "Size of ArrayList: "+mArrayList.size());
//            myAdapter = new MyAdapter(mArrayList);
//            myRecycler.setAdapter(myAdapter);
//        }

//        else if (intentQuery == null){
//        if (intentQuery == null){
            ArrayList<String> mArrayList = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(index1);
                String date = cursor.getString(index2);
                String cloudDes = cursor.getString(index3);
                String row = title + "," + date + "," + cloudDes;
                mArrayList.add(row);
                cursor.moveToNext();
            }
            myAdapter = new MyAdapter(mArrayList);
            myRecycler.setAdapter(myAdapter);
//        }
    }

    public void meditationActivity (View view)
    {
        Intent intent = new Intent(this, Meditation.class);
        startActivity(intent);
    }

    public void communityActivity (View view)
    {
//        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putString("communitySelect", "yes");
//        Toast.makeText(this, "Community selection has been selected", Toast.LENGTH_LONG).show();
//        editor.commit();

        Intent intent = new Intent(this, CommunityActivity.class);
        intent.putExtra("communitySelect", "yes");
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView title = (TextView) view.findViewById(R.id.titleRow);
        TextView date = (TextView) view.findViewById(R.id.dateRow);
        TextView cloudDes = (TextView) view.findViewById(R.id.singleCloudDesRow);
        Toast.makeText(this, "row " + (1+position) + ":  " + title.getText() +" "+date.getText() +" "+cloudDes.getText(), Toast.LENGTH_LONG).show();
    }
}
