package com.example.met06_grupo08.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.Model.Pill;
import com.example.met06_grupo08.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Repository to manage the remote Firebase Database
 * Write and Read Calendar information
 */
public class CalendarRepository {
    static CalendarRepository instance;
    private MutableLiveData<ArrayList<Day>> calendarMutableLiveData;
    ArrayList<Day> dayArrayList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Calendar");
    public static CalendarRepository getInstance(){
        if (instance == null){
            instance = new CalendarRepository();
        }
        return instance;
    }
    public void CalendarRepository(){

    }
    public void writeCalendarFirebase (String UserID , ArrayList<Day> calendar) {
        myRef.child(UserID).setValue(calendar);

    }

    /**
     * MutableLiveData for storage the information about pills set for each day
     * @param userID
     * @return calendarMutableLiveData: Information about pills set on a days
     */
    public MutableLiveData<ArrayList<Day>>readCalendarFirebase (final String userID){
        dayArrayList = new ArrayList<>();
        calendarMutableLiveData = new MutableLiveData<>();
        DatabaseReference reference = database.getReference("Calendar").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if (dayArrayList.size()> 6){
                        dayArrayList = new ArrayList<>();
                    }
                    String dayString=ds.child("day").getValue(String.class);
                    //Log.i("CheckUserByUD" , "cuanEntra obtenirelValue" + dayString);
                    Day day = new Day (dayString);
                    if ( ds.child("pills").getValue() != null) {
                        day.addPill(ds.child("pills").child("0").getValue(Pill.class).getName(),ds.child("pills").child("0").getValue(Pill.class).getDate(),ds.child("pills").child("0").getValue(Pill.class).getPillSelection(),ds.child("pills").child("0").getValue(Pill.class).getPillTaken());
                        if (ds.child("pills").getChildrenCount()==2){
                            day.addPill(ds.child("pills").child("1").getValue(Pill.class).getName(),ds.child("pills").child("1").getValue(Pill.class).getDate(),ds.child("pills").child("1").getValue(Pill.class).getPillSelection(),ds.child("pills").child("1").getValue(Pill.class).getPillTaken());
                        }
                    }
                    dayArrayList.add(day);
                }
                calendarMutableLiveData.setValue(dayArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("read Sensors:" , "Fail");

            }
        });
        return calendarMutableLiveData;
    }
}
