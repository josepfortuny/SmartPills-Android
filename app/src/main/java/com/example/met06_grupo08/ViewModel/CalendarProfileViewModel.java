package com.example.met06_grupo08.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.Repository.CalendarRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarProfileViewModel extends AndroidViewModel {
    MutableLiveData<ArrayList<Day>> days;
    private CalendarRepository calendarRepository;
    public CalendarProfileViewModel(Application application){
        super(application);
        calendarRepository = new CalendarRepository();
    }
    public void initCalendar(){
        if (days != null){
            return;
        }
        days = new MutableLiveData<>();
    }
    public void addCalendarUser(String userID,ArrayList<Day> calendar){
        calendarRepository.writeCalendarFirebase(userID,calendar);
    }
    public void readCalendarUser(String userID){
        days = calendarRepository.readCalendarFirebase(userID);
    }
    public LiveData<ArrayList<Day>> getCalendar (){
        return days;
    }
}
