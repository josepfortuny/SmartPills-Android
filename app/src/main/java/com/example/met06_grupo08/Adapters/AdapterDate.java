package com.example.met06_grupo08.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.R;


import java.util.ArrayList;

/**
 * RecyclerView (Adapter) for Dates in Calendar panel
 */
public class AdapterDate extends RecyclerView.Adapter<AdapterDate.MyViewHolder> {
    private ArrayList<Day> days;
    private static ItemClickListener mClickListener;
    private Context c;
    private ArrayList<View> view=new ArrayList();
    private int today;
    private MyViewHolder mv;
    // Constructor for adapter class
    // which takes a list of String type
    public AdapterDate(ArrayList<Day> days, int today,Context c){
        this.c = c;
        this.today = today;
        this.days = days;
    }
    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView day;
        RelativeLayout linearLayout;
        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyViewHolder(View v) {
            super(v);
            day = (TextView) v.findViewById(R.id.et_day);
            linearLayout = v.findViewById(R.id.layout_date);
            v.setOnClickListener(this);
            view.add(v);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null){
                mClickListener.onItemClick(v, getAdapterPosition());
                selectItemClicked(getAdapterPosition());
                //linearLayout.setBackgroundResource(R.color.backgroundAppColor);
            }
        }
        private void selectItemClicked (int position){
            int i = 0;
            for (View v:view){
                if (i == position){
                    if (position == today){
                        v.setBackgroundResource(R.color.btn_green_selected);
                    }else{
                        v.setBackgroundResource(R.color.btn_grey);
                    }
                }else{
                    if (i == today){
                        v.setBackgroundResource(R.color.btn_green);
                    }else{
                        v.setBackgroundResource(R.color.btn_sensors);
                    }
                }
                i++;
            }
        }
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_calendar, viewGroup, false);
        int height = viewGroup.getMeasuredHeight();
        int width = viewGroup.getMeasuredWidth()/7;
        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        return new MyViewHolder(view);

    }
    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(AdapterDate.MyViewHolder myViewHolder, int i) {
        this.mv = myViewHolder;
        myViewHolder.day.setText(days.get(i).getDay().substring(0,3)+".");
        if (i == today) {
            myViewHolder.linearLayout.setBackgroundResource(R.color.btn_green_selected);
        }
        if (i>4){
            myViewHolder.day.setTextColor(c.getResources().getColor(R.color.black));
        }
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return days.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}