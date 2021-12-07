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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
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

        Cursor cursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.DAY_DES);
        int personalUser = cursor.getColumnIndex(Constants.USER);

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedUsername = sharedPrefs.getString("username", DEFAULT);

            ArrayList<String> mArrayList = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String intentQueryMatch = cursor.getString(personalUser);

                //only show the user inputted entries that have the same username
                if (intentQueryMatch.equals(savedUsername)) {
                    String title = cursor.getString(index1);
                    String date = cursor.getString(index2);
                    String dayDes = cursor.getString(index3);
                    String row = title + "~" + date + "~" + dayDes;
                    mArrayList.add(row);
                    cursor.moveToNext();
                }

                //else move to the next row
                else if (intentQueryMatch != savedUsername) {
                    cursor.moveToNext();
                }
            }
        //set the recyclerview
            myAdapter = new MyAdapter(mArrayList);
            myRecycler.setAdapter(myAdapter);

            //set the title bar
        getSupportActionBar().setTitle("Welcome back " +savedUsername+"!");
    }

    public void meditationActivity (View view)
    {
        Intent intent = new Intent(this, Meditation.class);
        startActivity(intent);
    }

    public void communityActivity (View view)
    {
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
        Toast.makeText(this, "row " + (1+position) + ":  " + title.getText() +" "+date.getText() +" "+dayDes.getText(), Toast.LENGTH_LONG).show();
    }
}
