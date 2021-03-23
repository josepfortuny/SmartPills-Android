package com.example.met06_grupo08.Service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.met06_grupo08.R;

import com.example.met06_grupo08.View.MenuActivityNavigation;
import com.example.met06_grupo08.View.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Random;

import static androidx.lifecycle.Lifecycle.State.STARTED;

public class Fcm extends FirebaseMessagingService {
    private String token;
    private DatabaseReference ref;
    private final String SHARED_PREF_NAME = "sharedPrefs"; //Use local storage
    public final String SESSION_CONECTION = "connected";
    //private Intent nf;

    public Fcm() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.e("remoteMesage","remoteMessage"+ remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            reciveMessage(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));

        }
    }
    private void reciveMessage(String title, String body){
        String channelid = "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelid);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(channelid,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }
        Bitmap iconLarge = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.appicon);
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(iconLarge)
                .setSmallIcon(R.drawable.appicon)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(clicknoti())
                .setContentInfo("nuevo");
        Random random  = new Random();
        //int id_notify = random.nextInt(8000);
        assert nm != null;
        //nm.notify(id_notify,builder.build());
        nm.notify(0,builder.build());
        // ahora si das click a la notificacion petara ya que ha de ir a algun lugar
    }
    public PendingIntent clicknoti(){

        //if (isUserLogged()) {
        Intent nf = new Intent(getApplicationContext(), MenuActivityNavigation.class);
        //Necesitamos el extra para saber que viene de la notificacion
        nf.putExtra("fromNotification", "true");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //el flag necesario para evitar abrir mil actividades
        return PendingIntent.getActivity(this,0,nf,PendingIntent.FLAG_UPDATE_CURRENT);
    }
    /*
     * Called if InstanceID token is    updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    public void getToken(String userID,String HardwareID){
        // solo se obtiene si esta en el menu activity por lo tanto
        // se inicializa este menuActivity

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Error token", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        // Log and toast
                        Log.d("token","token  :  " + token);
                        sendRegistrationToFirebase(userID,HardwareID);
                    }
                });
    }
    private void sendRegistrationToFirebase(String userID,String hardware) {
        ref = FirebaseDatabase.getInstance().getReference().child("Tokens");
        ref.child(hardware).child(userID).setValue(token);
    }
    public void deleteToken(String userID,String hardware){
        ref = FirebaseDatabase.getInstance().getReference().child("Tokens");
        ref.child(hardware).child(userID).removeValue();
    }
    private boolean isUserLogged(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Log.e("fsdfsa","shared" + sharedPreferences.getString(SESSION_CONECTION, SESSION_CONECTION) );
        // El default UserID = "-1"
        if(sharedPreferences.getString(SESSION_CONECTION, SESSION_CONECTION).equals( "yes")){
            return true;
        }
        return false;
    }
}