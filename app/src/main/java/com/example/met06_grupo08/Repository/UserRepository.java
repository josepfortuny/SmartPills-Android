package com.example.met06_grupo08.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


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

/**
 * Repository to manage the remote Firebase database
 * Write and Read sensor information
 */
public class UserRepository {
    // private connect to firebase
    static UserRepository instance;
    private MutableLiveData<User> userMutableLiveData;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("users");
    private String uid;


    public static UserRepository getInstance(){
        if (instance == null){
            instance = new UserRepository();
        }
        return instance;
    }
    public void UserRepository(){
    }

    /**
     * userMutableLiveData. Creates a new user in firebase from a form
     * @param Name
     * @param email
     * @param password
     * @return userMutableLiveData: user information to be storege in Firebase
     */
    public MutableLiveData<User> writeNewUserFirebaseAuth (final String Name , final String email, String password,String hardware) {
        userMutableLiveData = new MutableLiveData<>();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                    User user = new User(currentFirebaseUser.getUid(),Name,email,hardware);
                    userMutableLiveData.setValue(user);
                    writeNewUserFirebase(userMutableLiveData);

                } else {

                    // If sign in fails, display a message to the user.
                    userMutableLiveData.setValue(null);
                    //Log.i("User Repository", "createUserWithEmail:failure", task.getException());
                }
            }
        });
        return userMutableLiveData;
    }

    /**
     * Create new instance in Database
     * @param u: MutableLiveData<User>
     */
    public void writeNewUserFirebase (MutableLiveData<User> u) {
        String key = myRef.push().getKey();
        myRef.child(key).setValue(u.getValue());
    }
    /**
     * Check the is the user exists in firebase with the same email and password
     * @param email, password
     */
    public MutableLiveData<User> checkUserExistsFirebase(String email,String password){
        userMutableLiveData = new MutableLiveData<>();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d("User Repository", "signInWithEmail:success");
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    userMutableLiveData.setValue(new User(currentFirebaseUser.getUid(),null,null,null));
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w("User Repository", "signInWithEmail:failure", task.getException());
                    userMutableLiveData.setValue(null);
                }
            }
        });
        return userMutableLiveData;
    }
    public MutableLiveData<User> checkUserExistsByIDFirebase(final String uid){
        userMutableLiveData = new MutableLiveData<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               boolean userFound = false;
               for(DataSnapshot ds : dataSnapshot.getChildren()){
                   if (ds.getValue(User.class).getId().equals(uid)){
                       userMutableLiveData.setValue(new User(ds.getValue(User.class).getId(),ds.getValue(User.class).getName(),ds.getValue(User.class).getEmail(),ds.getValue(User.class).getHardware()));
                       userFound =true;
                   }
               }
               if (!userFound){
                   userMutableLiveData.setValue(null);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               //Log.i("CheckUserByUD" , "Fail");

           }
       });
        return userMutableLiveData;
    }
}