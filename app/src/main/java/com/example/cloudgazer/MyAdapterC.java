package com.example.cloudgazer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.cloudgazer.R.layout.row;
import static com.example.cloudgazer.R.layout.row_community;
import static com.example.cloudgazer.Welcome.DEFAULT;

public class MyAdapterC extends RecyclerView.Adapter<MyAdapterC.MyViewHolderC> {

    public ArrayList<String> list;
    Context context;

    public MyAdapterC(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MyAdapterC.MyViewHolderC onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(row_community,parent,false);
        MyAdapterC.MyViewHolderC viewHolder = new MyAdapterC.MyViewHolderC(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapterC.MyViewHolderC holder, int position) {
        String[] results = (list.get(position).toString()).split("~");
        holder.titleView.setText(results[0]);
        holder.dateView.setText(results[1]);
        holder.dayDesView.setText(results[2]);
        holder.userView.setText(results[3]);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderC extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleView;
        public TextView dateView;
        public TextView dayDesView;
        public TextView userView;
        public LinearLayout myLayout;

        Context context;

        public MyViewHolderC(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            titleView = (TextView)itemView.findViewById(R.id.titleRow);
            dateView = (TextView) itemView.findViewById(R.id.dateRow);
            dayDesView = (TextView) itemView.findViewById(R.id.dayDesRow);
            userView = (TextView) itemView.findViewById(R.id.userRow);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(context,
//                    "You have clicked " + ((TextView)view.findViewById(R.id.titleRow)).getText().toString(),
//                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), CommunityViewEntryActivity.class);
            intent.putExtra("title",((TextView)view.findViewById(R.id.titleRow)).getText().toString() );
            view.getContext().startActivity(intent);
        }
    }
}
