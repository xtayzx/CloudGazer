package com.example.cloudgazer;

import android.content.Context;
import android.content.Intent;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<String> list;
    Context context;

    public MyAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        String[] results = (list.get(position).toString()).split(",");
        holder.titleView.setText(results[0]);
        holder.dateView.setText(results[1]);
        holder.dayDesView.setText(results[2]);
//        holder.locationTextView.setText(results[2]);
//        holder.latinTextView.setText(results[3]);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleView;
        public TextView dateView;
        public TextView dayDesView;
//        public TextView locationTextView;
//        public TextView latinTextView;
        public LinearLayout myLayout;

        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            titleView = (TextView)itemView.findViewById(R.id.titleRow);
            dateView = (TextView) itemView.findViewById(R.id.dateRow);
            dayDesView = (TextView) itemView.findViewById(R.id.dayDesRow);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context,
                    "You have clicked " + ((TextView)view.findViewById(R.id.titleRow)).getText().toString(),
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), ViewEntryActivity.class);
            intent.putExtra("title",((TextView)view.findViewById(R.id.titleRow)).getText().toString() );
            view.getContext().startActivity(intent);
        }
    }
}