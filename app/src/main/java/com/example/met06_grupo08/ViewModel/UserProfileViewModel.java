package com.example.met06_grupo08.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.met06_grupo08.Model.User;
import com.example.met06_grupo08.Repository.UserRepository;


public class UserProfileViewModel extends AndroidViewModel {
    private MutableLiveData<User> user;
    private UserRepository userRepository;

    public UserProfileViewModel(Application application){
        super(application);
        userRepository = new UserRepository();
    }
    public void initUser(){
        if (this.user != null){
            return;
        }
        user = new MutableLiveData<>();
    }
    //funciona s'entra als dos cantons per a poder guardar el hardware
    public void addUser(String Name, String Email, String Password,String Hardware){
        user = userRepository.writeNewUserFirebaseAuth(Name,Email, Password,Hardware);
    }
    //ara el checkUser
    public void checkUserifExists(String Email, String Password){
        user = userRepository.checkUserExistsFirebase(Email,Password);
    }
    public void checkUserifExistsByID(String uID){
        user = userRepository.checkUserExistsByIDFirebase(uID);
    }

    public LiveData<User> getUser(){
        return user;
    }
}
