package com.example.met06_grupo08.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.Model.Sensors;
import com.example.met06_grupo08.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Repository to manage the remote Firebase database
 * Write and Read sensor information
 */
public class SensorsRepository {
    static SensorsRepository instance;
    private MutableLiveData<Sensors> sensorsMutableLiveData;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Sensors");
    public static SensorsRepository getInstance(){
        if (instance == null){
            instance = new SensorsRepository();
        }
        return instance;
    }
    public void SensorsRepository(){

    }
    public void writeSensorsDataFirebase(String HardwareID, Sensors sensors) {
        myRef.child(HardwareID).setValue(sensors);
    }
    /**
     * Storage information from firebase first time
     * @param UserID
     * @return
     */
    public MutableLiveData<Sensors> readSensorsDataFirebaseFirstTime(String UserID){
        sensorsMutableLiveData = new MutableLiveData<>();
        myRef.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.i("Ha entrat","Unaltre" + dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return sensorsMutableLiveData;
    }

    /**
     * Storage information from each sensor updated periodically from firebase
     * @param hardwareID
     * @return sensorsMutableLiveData: sensor values, temperature, presence, position of the servomotor
     */
    public MutableLiveData<Sensors> readSensorsDataFirebase(String hardwareID) {
        sensorsMutableLiveData = new MutableLiveData<>();
        myRef.child(hardwareID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sensorsMutableLiveData.setValue(new Sensors(dataSnapshot.getValue(Sensors.class).getTemperature(),dataSnapshot.getValue(Sensors.class).getPrecense(),dataSnapshot.getValue(Sensors.class).getTapPosition()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return sensorsMutableLiveData;
    }
    /**
     * This function check if it exist in firebase
     * @param harwareID
     * @return sensorsMutableLiveData: sensor values, temperature, presence, tap position
     */
    public void sensorsExistsFirebase(String harwareID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(harwareID)) {
                    writeSensorsDataFirebase(harwareID,new Sensors("no data", "no data",0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
