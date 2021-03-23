package com.example.met06_grupo08.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.R;


import java.util.ArrayList;

/**
 * RecycleView (Adapter) Calendar
 */
public class AdapterCreateCalendar extends RecyclerView.Adapter<AdapterCreateCalendar.MyViewHolder> {
    Context c;
    private ArrayList<Day> days ;
    private static int BLUEPILL =0;
    private static int REDPILL = 1;

    // Constructor for adapter class
    // which takes a list of String type
    public AdapterCreateCalendar(Context c, ArrayList<Day> days){
        this.c = c;
        this.days = days;
        for(int i = 0; i <days.size(); i++){
            days.get(i).addPill("BluePill","",false,false);
            days.get(i).addPill("RedPill","",false,false);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView tv_day;
        LinearLayout ll_bluepill,ll_redpill;
        ImageView imv_bluewpill,imv_redpill;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyViewHolder(View v) {
            super(v);
            imv_bluewpill = v.findViewById(R.id.imv_bluepill);
            imv_redpill = v.findViewById(R.id.imv_redpill);
            ll_bluepill = v.findViewById(R.id.ll_bluepill);
            ll_redpill = v.findViewById(R.id.ll_redpill);
            tv_day=v.findViewById(R.id.tv_id_day);
            imv_bluewpill.setOnClickListener(this);
            imv_redpill.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imv_bluepill:
                    if (!days.get(getAdapterPosition()).getPill(0).getPillSelection()){
                        ll_bluepill.setBackgroundResource(R.color.btn_green);
                        days.get(getAdapterPosition()).getPill(0).setPillSelection(true);

                    }else{
                        ll_bluepill.setBackgroundResource(R.color.btn_sensors);
                        days.get(getAdapterPosition()).getPill(0).setPillSelection(false);
                    }
                    break;
                case R.id.imv_redpill:
                    if (!days.get(getAdapterPosition()).getPill(1).getPillSelection()){
                        ll_redpill.setBackgroundResource(R.drawable.btn_rounded_right_create_callendar);
                        days.get(getAdapterPosition()).getPill(1).setPillSelection(true);

                    }else{
                        ll_redpill.setBackgroundResource(R.drawable.btn_rounded_right_create_color);
                        days.get(getAdapterPosition()).getPill(1).setPillSelection(false);
                    }
                    break;
            }
        }
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public AdapterCreateCalendar.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_item_create_callendar, viewGroup, false);
        return new MyViewHolder(view);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(AdapterCreateCalendar.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_day.setText(days.get(i).getDay());
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return days.size();
    }
    public ArrayList<Day> getTakenPills(){
        for(int i = 0; i < days.size(); i++){
            //Log.e("AdapterCreateCallendar", "Posicion"+ i + "value" + days.get(i).getPill(0).getPillSelection());
            //Log.e("AdapterCreateCallendar", "Posicion"+ i + "value" + days.get(i).getPill(1).getPillSelection());
            if (!days.get(i).getPillByName("BluePill").getPillSelection()){
                days.get(i).removePill("BluePill");
            }
            if (!days.get(i).getPillByName("RedPill").getPillSelection()){
                days.get(i).removePill("RedPill");
            }
        }
        return days;
    }
}

