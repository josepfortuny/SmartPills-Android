package com.example.met06_grupo08.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.met06_grupo08.Adapters.AdapterCreateCalendar;
import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Create model for each day in Calendar Fragment
 */
public class CreateCalendarFragment extends Fragment {
    private View v;
    RecyclerView recyclerView;
    Context c;
    AdapterCreateCalendar adapterCreateCalendar;
    private FloatingActionButton fab_save_calendar;
    private ArrayList<Day> days = new ArrayList<Day>(){
        {
            add(new Day("Lunes"));
            add(new Day("Martes"));
            add(new Day("Miercoles"));
            add(new Day("Jueves"));
            add(new Day("Viernes"));
            add(new Day("Sabado"));
            add(new Day("Domingo"));
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_create_callendar, container, false);
        c = getActivity();
        recyclerView=v.findViewById(R.id.recycler_create_callendar);
        adapterCreateCalendar = new AdapterCreateCalendar(getActivity(),days);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterCreateCalendar);
        adapterCreateCalendar.notifyDataSetChanged();
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_save_calendar = v.findViewById(R.id.fab_save);
        fab_save_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = adapterCreateCalendar.getTakenPills();
                ((MenuActivityNavigation)getActivity()).gotoCalendar(days);
                ((MenuActivityNavigation)getActivity()).calendarProfileViewModel.initCalendar();
                ((MenuActivityNavigation)getActivity()).calendarProfileViewModel.addCalendarUser(((MenuActivityNavigation)getActivity()).userID,days);

            }
        });
    }
}
