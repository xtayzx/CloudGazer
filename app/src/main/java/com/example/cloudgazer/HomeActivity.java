package com.example.cloudgazer;

import static com.example.cloudgazer.Welcome.DEFAULT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myRecycler = (RecyclerView) findViewById(R.id.recycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        searchInput = (EditText)findViewById(R.id.searchEntry);

        //populate all the data and put it inside the arraylist
        Cursor cursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.DAY_DES);
        //int index4 = cursor.getColumnIndex(Constants.USER);
        int personalUser = cursor.getColumnIndex(Constants.USER);

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

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedUsername = sharedPrefs.getString("username", DEFAULT);

//        else if (intentQuery == null){
//        if (intentQuery == null){
            ArrayList<String> mArrayList = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String intentQueryMatch = cursor.getString(personalUser);

                //only show the user inputted entries
                if (intentQueryMatch.equals(savedUsername)) {
                    String title = cursor.getString(index1);
                    String date = cursor.getString(index2);
                    String dayDes = cursor.getString(index3);
                    //String user = cursor.getString(index4);
                    String row = title + "," + date + "," + dayDes;
                    mArrayList.add(row);
                    cursor.moveToNext();
                }

                else if (intentQueryMatch != savedUsername) {
                    cursor.moveToNext();
                }
            }
            myAdapter = new MyAdapter(mArrayList);
            myRecycler.setAdapter(myAdapter);
    //}


    }

    public void meditationActivity (View view)
    {
        Intent intent = new Intent(this, Meditation.class);
        startActivity(intent);
    }

    public void reflectionActivity (View view)
    {
        Intent intent = new Intent(this, ReflectionActivity.class);
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
        intent.putExtra("communitySelect", "Yes");
        startActivity(intent);
    }

    public void searchActivity (View view)
    {
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("searchQuery", searchInput.getText().toString());
        Toast.makeText(this, "Search query has been inputted", Toast.LENGTH_LONG).show();
        editor.commit();

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
        TextView title = (TextView) view.findViewById(R.id.titleRow);
        TextView date = (TextView) view.findViewById(R.id.dateRow);
        TextView dayDes = (TextView) view.findViewById(R.id.dayDesRow);
        //TextView user = (TextView) view.findViewById(R.id.userRow);
        Toast.makeText(this, "row " + (1+position) + ":  " + title.getText() +" "+date.getText() +" "+dayDes.getText(), Toast.LENGTH_LONG).show();
    }
}
