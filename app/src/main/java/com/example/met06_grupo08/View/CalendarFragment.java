package com.example.met06_grupo08.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.met06_grupo08.Adapters.AdapterDate;
import com.example.met06_grupo08.Adapters.AdapterPillsCalendar;
import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment implements AdapterDate.ItemClickListener {
    private View v;
    RecyclerView recyclerViewDate,recyclerViewPills;
    Calendar calendar;
    Context c;
    private TextView tvDate,tvPlannedDay;
    private FloatingActionButton fab_create_calendar;
    private int current_day;
    private Day day;
    AdapterDate adapterDate;
    AdapterPillsCalendar adapterPillsCalendar;
    private ArrayList<Day> days;

    @Override
    /**
     * Get data from ActivityNavigation getdaPills()
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_calendar, container, false);
        getTodayPosition();
        getdayPills(); // Get data from ActivityNavigation
        initRecyclerViewCalendar();
        initRecyclerViewPills();
        c = getActivity();

        return v;
    }
    /*@Override
    public void onResume() {
        days = ((MenuActivityNavigation)getActivity()).calendar;
        adapterPillsCalendar.notifyDataSetChanged();
        super.onResume();
    }*/


    @Override
    public void onStart() {
        days = ((MenuActivityNavigation)getActivity()).calendar;
        adapterPillsCalendar.notifyDataSetChanged();
        super.onStart();
        Log.e("aaa","aaa");
    }

    private void getdayPills(){
        //Get infor from Activity
        days = ((MenuActivityNavigation)getActivity()).calendar;
    }


    private void getTodayPosition(){
        calendar = Calendar.getInstance();
        current_day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (current_day) {

            case Calendar.MONDAY:
                current_day =0;
                break;
            case Calendar.TUESDAY:
                current_day =1;
                break;
            case Calendar.WEDNESDAY:
                current_day =2;
                break;
            case Calendar.THURSDAY:
                current_day =3;
                break;
            case Calendar.FRIDAY:
                current_day =4;
                break;
            case Calendar.SATURDAY:
                current_day =5;
                break;
            default:
                current_day =6;
                break;
        }
    }

    private void initRecyclerViewCalendar(){
        // Inflate the layout for this fragment
        recyclerViewDate = (RecyclerView) v.findViewById(R.id.recycler_view_date);
        adapterDate = new AdapterDate(days,current_day,getActivity());
        recyclerViewDate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewDate.setAdapter(adapterDate);
        adapterDate.setClickListener(this);
        adapterDate.notifyDataSetChanged();
    }
    private void initRecyclerViewPills(){
        // Inflate the layout for this fragment
        day = days.get(current_day);
        tvDate = v.findViewById(R.id.tv_day_nonrecicle);
        tvPlannedDay =v.findViewById(R.id.tv_planned_day);
        tvDate.setText(days.get(current_day).getDay());
        setvisiblePlannedDay();
        recyclerViewPills = (RecyclerView) v.findViewById(R.id.recycler_view_pills);
        adapterPillsCalendar = new AdapterPillsCalendar(day,getActivity());
        recyclerViewPills.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewPills.setAdapter(adapterPillsCalendar);
        adapterPillsCalendar.notifyDataSetChanged();
    }
    private void setvisiblePlannedDay(){
        if (days.get(current_day).somePlanned()){
            tvPlannedDay.setVisibility(View.GONE);

        }else{
            tvPlannedDay.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_create_calendar = v.findViewById(R.id.fab_create);
        fab_create_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivityNavigation)getActivity()).gotoCreateCalendar();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        current_day = position;
        tvDate.setText(days.get(position).getDay());
        day = days.get(current_day);
        adapterPillsCalendar.updateDay(day);
        adapterPillsCalendar.notifyDataSetChanged();
        setvisiblePlannedDay();
    }
}
