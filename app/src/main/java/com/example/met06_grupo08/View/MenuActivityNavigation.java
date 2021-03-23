package com.example.met06_grupo08.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.met06_grupo08.Model.Day;
import com.example.met06_grupo08.Model.User;
import com.example.met06_grupo08.R;
import com.example.met06_grupo08.Service.Fcm;
import com.example.met06_grupo08.ViewModel.CalendarProfileViewModel;
import com.example.met06_grupo08.ViewModel.SensorsViewModel;
import com.example.met06_grupo08.ViewModel.UserProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * Control of the navigaton of the app
 */
public class MenuActivityNavigation extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    Toolbar toolbar;
    Resources res;
    NavHostFragment navHostFragment;
    NavController navcontroller;
    CalendarFragment calendarFragment;  // NOT USED
    UserProfileViewModel userViewModel;
    CalendarProfileViewModel calendarProfileViewModel;
    SensorsViewModel sensorsViewModel;
    User userapp;
    Fcm fcm;
    ArrayList<Day> calendar;
    String userID;
    public final String SHARED_PREF_NAME = "sharedPrefs";
    public final String SESSION_CONECTION = "connected";
    public final String SESSION_KEY = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navigation);
        loadSharedPreferences();
        initViewModel();
        getUserFirebase();
        getCalendarFirebase();
        res = getResources();
        setToolbar();
        setUpNavigation();
        navcontroller = Navigation.findNavController(this,R.id.fragment_menu);
        Intent intent = getIntent();
        String fromNotification = intent.getStringExtra("fromNotification");
        if (fromNotification != null){
            gotoSensorsFragment();
        }
    }


    public void initViewModel(){
        calendarProfileViewModel = new ViewModelProvider(this).get(CalendarProfileViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        sensorsViewModel = new ViewModelProvider(this).get(SensorsViewModel.class);
    }
    private void initFcm() {
        fcm = new Fcm();
        fcm.getToken(userapp.getId(),userapp.getHardware());
    }
    public void getCalendarFirebase(){
        calendarProfileViewModel.initCalendar();
        calendarProfileViewModel.readCalendarUser(userID);
        calendarProfileViewModel.getCalendar().observe(this, new Observer<ArrayList<Day>>() {
            @Override
            public void onChanged(ArrayList<Day> days) {
                if (days != null){

                    //Log.e("MenuActivityNavigation","Nonull of on changed" + days.size());
                    calendar = days;
                    if(toolbar.getTitle().toString()=="Calendario"){
                        navcontroller.navigate(R.id.action_calendar_calendar);
                    }
                }else{
                    //Log.e("MenuActivityNavigation","null of on changed");

                }
            }
        });
    }
    public void getUserFirebase() {
        userViewModel.initUser();
        userViewModel.checkUserifExistsByID(userID);
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {

                    userapp = user;
                    initFcm();
                    // una vez obtenido el usuario se ha de llamar al fcm para enviar el token

                } else {
                    //do nothing
                }
            }
        });
    }

    public void setToolbar() {
        toolbar = findViewById(R.id.toolbar_menu_nav);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_choose,menu);
        return true;
    }
    /**
     * Menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(toolbar.getTitle().toString()){
            case"Inicio":
                navcontroller.navigate(R.id.action_about_user);
                toolbar.setTitle("Usuario");
                break;
            case"Calendario":
                navcontroller.navigate(R.id.action_calendar_user);
                toolbar.setTitle("Usuario");
                break;
            case "Sensores":
                navcontroller.navigate(R.id.action_sensors_user);
                toolbar.setTitle("Usuario");
                break;
            case "Creacion Calendario":
                navcontroller.navigate(R.id.action_createCalendar_user);
                toolbar.setTitle("Usuario");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setUpNavigation(){
        bottomNav =findViewById(R.id.bottom_navigation);
        navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_menu);
        NavigationUI.setupWithNavController(bottomNav,
                navHostFragment.getNavController());
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.aboutFragment:
                        toolbar.setTitle("Inicio");
                        break;
                    case R.id.calendarFragment:
                        toolbar.setTitle("Calendario");
                        break;
                    default:
                        toolbar.setTitle("Sensores");
                        break;
                }
                // You need this line to handle the navigation
                return NavigationUI.onNavDestinationSelected(menuItem, navHostFragment.getNavController());
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        gotoSensorsFragment();
    }

    //from UserFragment
    public void closeSession() {
        finish();
        fcm.deleteToken(userID,userapp.getHardware());
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("FromMenuActivity","true");
        startActivity(intent);
        finish();
    }
    public void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        // El default UserID = "-1"
        userID = sharedPreferences.getString(SESSION_KEY, SESSION_KEY);
        writeSharedPreferences ("yes");
    }
    public void writeSharedPreferences(String conectado){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_CONECTION, conectado);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if (toolbar.getTitle().toString()=="Usuario"){
            toolbar.setTitle("Inicio");
            navcontroller.navigate(R.id.action_user_to_about);
        }else if(toolbar.getTitle().toString()=="Creacion Calendario"){
            toolbar.setTitle("Calendario");
            navcontroller.navigate(R.id.action_createCalendar_calendar);
        }else if(toolbar.getTitle().toString()=="Calendario") {
            toolbar.setTitle("Inicio");
            navcontroller.navigate(R.id.action_calendar_about);
        }else if(toolbar.getTitle().toString()=="Sensores") {
            toolbar.setTitle("Inicio");
            navcontroller.navigate(R.id.action_sensors_about);
        }else{
            writeSharedPreferences("no");
            finish();
        }
    }
    //from calendarFragment
    public void gotoCreateCalendar() {
        navcontroller.navigate(R.id.action_calendar_createCalendar);
        toolbar.setTitle("Creacion Calendario");
    }
    public void gotoSensorsFragment(){
        if (toolbar.getTitle().toString()=="Usuario") {
            navcontroller.navigate(R.id.action_user_sensors);
        }else if(toolbar.getTitle().toString()=="Calendario"){
            navcontroller.navigate(R.id.action_calendar_sensors);
        }else if(toolbar.getTitle().toString()=="Creacion Calendario") {
            navcontroller.navigate(R.id.action_createCalendar_sensors);
        }else if(toolbar.getTitle().toString()=="Sensores"){
            navcontroller.navigate(R.id.action_sensors_sensors);
        }
        toolbar.setTitle("Sensores");
    }
    //from createcalendarFragment
    public void gotoCalendar(ArrayList<Day> days) {
        calendar = days;
        navcontroller.navigate(R.id.action_createCalendar_calendar);
        toolbar.setTitle("Calendario");
    }
}

