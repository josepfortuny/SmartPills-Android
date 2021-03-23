package com.example.met06_grupo08.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.Model.Sensors;
import com.example.met06_grupo08.Model.User;
import com.example.met06_grupo08.R;
import com.example.met06_grupo08.Service.Fcm;
import com.example.met06_grupo08.ViewModel.CalendarProfileViewModel;
import com.example.met06_grupo08.ViewModel.SensorsViewModel;
import com.example.met06_grupo08.ViewModel.UserProfileViewModel;

import java.util.ArrayList;

/**
 * Activiy to login
 */
public class LoginActivity extends AppCompatActivity {

    private String userID;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private final String SHARED_PREF_NAME = "sharedPrefs"; //Use local storage
    private final String SESSION_KEY = "-1";
    //private final String HARDWARE_NUMBER = "Hardware_Grupo08";
    private String hardware_number;
    UserProfileViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUserViewModel();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSessionConfiguration();
    }
    private void initUserViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
    }
    private void checkSessionConfiguration(){
        //check if user is logged in
        //if user is logged in --> move to SplashActivity
        userViewModel.initUser();
        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = getIntent();
        String fromMenuActivity = intent.getStringExtra("FromMenuActivity");
        if (fromMenuActivity == null){
            loadSharedPreferences();
            userViewModel.checkUserifExistsByID(userID);
            userViewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null){
                        Log.i("LoginActivity", "onchanged checksesionConfiguration" +user.getId()+ user.getName() + user.getEmail() );
                        gotoSplash(user.getId());
                    }else{
                        setContentView(R.layout.activity_login);
                        removeSessionSharedPreferences();
                    }
                }
            });

        }else{
            setContentView(R.layout.activity_login);
            removeSessionSharedPreferences();
        }
    }
    public void syncSensorsUser(){
        SensorsViewModel sensors = new ViewModelProvider(this).get(SensorsViewModel.class);
        sensors.initSensors();
        sensors.checkSensorsExistsFirebase(hardware_number);

    }
    public void addCalendarTotheUser(String userID){
        ArrayList<Day> days = new ArrayList<Day>(){
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
        CalendarProfileViewModel calendarProfileViewModel = new ViewModelProvider(this).get(CalendarProfileViewModel.class);

        calendarProfileViewModel.addCalendarUser(userID,days);
    }

    /**
     * Set new user  in the remote Database
     * @param name
     * @param email
     * @param password
     * @param nav
     */

    public void addUserIfDontExistsFirebase(String name, String email, String password,String hardware, final NavController nav) {
        userViewModel.initUser();
        this.hardware_number = hardware;
        userViewModel.addUser(name,email,password,hardware);
        // Get the User from Database
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    //Log.i("LoginActivity", "perfEnteredonChanged" + user.getName() + user.getEmail() );
                    nav.navigate(R.id.action_menuLoginFragment);
                    syncSensorsUser();
                    addCalendarTotheUser(user.getId());

                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect Email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Check if user exist on remote Database
     * @param email
     * @param password
     */
    public void checkUserFirebase(String email, String password) {
        // Implemented on Library
        userViewModel.initUser();
        userViewModel.checkUserifExists(email,password);
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    //Log.i("LoginActivity", "perfEnteredonChanged" + user.getId() + user.getEmail() );
                    gotoSplash(user.getId());
                    saveSessionUserSharedPreferences(user.getId());

                }else{
                    Toast.makeText(getApplicationContext(), "This email is not subscribed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void gotoSplash(String userID) {
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);
        finish();
    }
    public void saveSessionUserSharedPreferences(String id) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_KEY, id);
        editor.apply();
    }
    public void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        // El default UserID = "-1"
        userID = sharedPreferences.getString(SESSION_KEY, SESSION_KEY);
    }

    public void removeSessionSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_KEY, "-1");
        editor.apply();
    }
}
