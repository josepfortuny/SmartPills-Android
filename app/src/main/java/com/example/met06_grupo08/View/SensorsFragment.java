package com.example.met06_grupo08.View;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.Model.Sensors;
import com.example.met06_grupo08.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Fragment for the Sensors control Panel
 */
public class SensorsFragment extends Fragment {
    private View v;
    private EditText et_precense,et_temperature,et_tap_control;
    private FloatingActionButton fbtn_tap_more, fbtn_tap_less;
    private ImageView iv_precense,iv_temperature;
    private LinearLayout layout_precense;
    private boolean connected;
    public Sensors sensorsClass;
    private final String HARDWARE_NUMBER = "Hardware_Grupo08";
    private static int MAX_TAP_POSITION = 10;
    private static int MIN_TAP_POSITION = 0;
    int tap_position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_sensors, container, false);
        lisenSensorsFirebase();
        initializeView();
        return v;
    }
    private void lisenSensorsFirebase() {
        ((MenuActivityNavigation)getActivity()).sensorsViewModel.initSensors();
        ((MenuActivityNavigation)getActivity()).sensorsViewModel.readSensorsUser(HARDWARE_NUMBER);
        ((MenuActivityNavigation)getActivity()).sensorsViewModel.getSensors().observe(((MenuActivityNavigation)getActivity()), new Observer<Sensors>() {
            @Override
            public void onChanged(Sensors sensors) {
                if (sensors != null){

                    sensorsClass = sensors;
                    if (getActivity() != null) {
                        initializeView();
                        actualizeUI();
                    }
                }else{
                    Log.i("SensorsFragment","null of on changed");
                }
            }
        });
    }
    private void writeSensorsFirebase(){
        ((MenuActivityNavigation)getActivity()).sensorsViewModel.initSensors();
        ((MenuActivityNavigation)getActivity()).sensorsViewModel.writeSensorsFirebase(HARDWARE_NUMBER,sensorsClass);
    }
    private void initializeView(){
        et_tap_control = (EditText) v.findViewById(R.id.et_tap_control);
        et_precense = (EditText) v.findViewById(R.id.et_precense);
        et_temperature = (EditText) v.findViewById(R.id.et_temperature);
        iv_temperature = (ImageView) v.findViewById(R.id.iv_temperature);
        iv_precense = (ImageView) v.findViewById(R.id.image_precense);
        layout_precense = (LinearLayout) v.findViewById(R.id.layout_precense);
        fbtn_tap_more = (FloatingActionButton) v.findViewById(R.id.btn_tap_more);
        fbtn_tap_less = (FloatingActionButton) v.findViewById(R.id.btn_tap_less);
        fbtn_tap_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ENVIAR A LA PLACA Y DB
                substractTapPosition();
            }
        });

        fbtn_tap_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addTapPosition();
            }
        });
    }
    private void actualizeUI(){
        //((MenuActivityNavigation)getActivity()).getContext();
        if (sensorsClass.getTapPosition() == MAX_TAP_POSITION){
            et_tap_control.setText(getString(R.string.et_tap_open));
        }else if (sensorsClass.getTapPosition() == MIN_TAP_POSITION) {
            et_tap_control.setText(getString(R.string.et_tap_closed));
        }else{
            et_tap_control.setText(String.valueOf(sensorsClass.getTapPosition()*10)+"% Abierto");
        }
        if (sensorsClass.getPrecense().equals("Hay caida")){
            fallDetected();

        }else if (sensorsClass.getPrecense().equals("Hay movimiento")){
            precenseDetected();
        }else if (sensorsClass.getPrecense().equals("No Hay movimiento")){
            noPrecenseDetected();
        }else{
            noDataDetected();
        }
        if (sensorsClass.getTemperature().equals("no data")){
            et_temperature.setText(sensorsClass.getTemperature());
            iv_temperature.setImageResource(R.drawable.ic_no_connection);


        }else{
            et_temperature.setText(sensorsClass.getTemperature()+"ºC");
            iv_temperature.setImageResource(R.drawable.ic_temperature);
        }

    }
    private void addTapPosition(){
        if (sensorsClass.getTapPosition() < MAX_TAP_POSITION ) {
            sensorsClass.setTapPosition((sensorsClass.getTapPosition()+1));
        }
        writeSensorsFirebase();
    }
    private void substractTapPosition(){
        if (sensorsClass.getTapPosition() > MIN_TAP_POSITION ) {
            sensorsClass.setTapPosition((sensorsClass.getTapPosition()-1));
        }
        writeSensorsFirebase();
    }
    private void noPrecenseDetected(){
        connected = true;
        et_precense.setText(R.string.et_no_movement);
        iv_precense.setImageResource(R.drawable.ic_no_precense);
        layout_precense.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_sensors));

    }
    private void precenseDetected(){
        connected = true;
        et_precense.setText(R.string.et_movement);
        iv_precense.setImageResource(R.drawable.ic_precense_detect);
        layout_precense.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_sensors));

    }
    private void fallDetected(){
        connected = true;
        et_precense.setText(R.string.et_fall_detect);
        iv_precense.setImageResource(R.drawable.ic_fall_detection);
        layout_precense.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_sensors_red));
    }
    private void noDataDetected(){
        connected =false;
        et_precense.setText("No Data");
        iv_precense.setImageResource(R.drawable.ic_no_connection);
        layout_precense.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_sensors));
    }
}




