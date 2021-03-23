package com.example.met06_grupo08.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.met06_grupo08.Model.Sensors;
import com.example.met06_grupo08.Repository.SensorsRepository;

public class SensorsViewModel extends AndroidViewModel {
    private MutableLiveData<Sensors> sensors;
    private SensorsRepository sensorsRepository;

    public SensorsViewModel(@NonNull Application application) {
        super(application);
        sensorsRepository = new SensorsRepository();
    }
    public void initSensors(){
        if (this.sensors != null){
            return;
        }
        sensors = new MutableLiveData<>();
    }
    public void writeSensorsFirebase(String userID, Sensors sensors){
        sensorsRepository.writeSensorsDataFirebase(userID,sensors);
    }
    public void checkSensorsExistsFirebase(String hardwareID){
        sensorsRepository.sensorsExistsFirebase(hardwareID);
    }

    public void readSensorsUser (String HardwareID ){
        sensors = sensorsRepository.readSensorsDataFirebase(HardwareID);

    }
    public LiveData<Sensors> getSensors(){
        return sensors;
    }
}
