package com.example.met06_grupo08.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.R;

/**
 * RecycleView (Adapter) for pills in the calendar panel
 */
public class AdapterPillsCalendar extends RecyclerView.Adapter<AdapterPillsCalendar.MyViewHolder>{
    private Day day;
    private Context c;
    public AdapterPillsCalendar(Day day,Context c){
        this.c = c;
        this.day = day;
    }
    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView datePill;
        ImageView imvPills;
        LinearLayout llManager,llText,llPill,llDate;
        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyViewHolder(View v) {
            super(v);;
            imvPills = v.findViewById(R.id.imv_pill);
            datePill = v.findViewById(R.id.tv_text_date);
            llManager = v.findViewById(R.id.ll_manager_pills);
            llText = v.findViewById(R.id.ll_pill_text);
            llPill = v.findViewById(R.id.ll_pill);
            llDate = v.findViewById(R.id.ll_text_date);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            day.getPill(getAdapterPosition()).setPillSelection(true);
            //aixo sera fet desde fora quan la data ha cambiat
            //Toast.makeText(c,"AA", Toast.LENGTH_SHORT).show();
        }
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_item_view_pills, viewGroup, false);
        return new MyViewHolder(view);

    }
    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(AdapterPillsCalendar.MyViewHolder myViewHolder, int i) {
        myViewHolder.imvPills.setImageResource(day.getPill(i).getSrc());
        myViewHolder.datePill.setText(day.getPill(i).getDate());
        if (day.getPill(i).getPillTaken()) {
            myViewHolder.llManager.setBackgroundResource(R.drawable.btn_rounded_green);
            myViewHolder.llText.setBackgroundResource(R.drawable.btn_rounded_green);
            myViewHolder.llDate.setBackgroundResource(R.drawable.btn_rounded_green);
            myViewHolder.llPill.setBackgroundResource(R.drawable.btn_rounded_green);
        } else {
            myViewHolder.llManager.setBackgroundResource(R.drawable.btn_rounded_sensors);
            myViewHolder.llText.setBackgroundResource(R.drawable.btn_rounded_sensors);
            myViewHolder.llDate.setBackgroundResource(R.drawable.btn_rounded_sensors);
            myViewHolder.llPill.setBackgroundResource(R.drawable.btn_rounded_sensors);
        }
    }
    @Override
    public int getItemCount() {
        return day.getPills().size();
    }
    public void updateDay(Day day){
        this.day = day;
    }

}
