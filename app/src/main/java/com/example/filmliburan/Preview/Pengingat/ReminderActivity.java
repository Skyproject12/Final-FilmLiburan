package com.example.filmliburan.Preview.Pengingat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.filmliburan.Data.Model.NotificationItem;
import com.example.filmliburan.Data.Source.Local.NotificationPreference;
import com.example.filmliburan.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {

    Switch switchHarian;
    Switch switchUpdate;
    ReminderReceiver reminderReceiver;
    NotificationPreference sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        switchHarian= findViewById(R.id.switch_harian);
        switchUpdate= findViewById(R.id.switch_update);
        reminderReceiver= new ReminderReceiver();
        switchHarian.setOnClickListener(this);
        switchUpdate.setOnClickListener(this);
        sharedPreferences= new NotificationPreference(this);
        setSwitch();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_harian:
                if(switchHarian.isChecked()){
                    sharedPreferences.setKlik(NotificationPreference.KLIK_HARIAN, true);
                    reminderReceiver.setHarianAlarm(this,"7:00","Ayok buka aplikasi Film Liburan untuk melihat daftar film");
                    Toast.makeText(this, "Reminder harian aktif", Toast.LENGTH_SHORT).show();
                }
                else{
                    sharedPreferences.setKlik(NotificationPreference.KLIK_HARIAN, false);
                    reminderReceiver.cancelAlarmHarian(this);
                    Toast.makeText(this, "Reminder harian tidak aktif", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_update:
                if(switchUpdate.isChecked()){
                    sharedPreferences.setKlik(NotificationPreference.KLIK_UPDATE, true);
                    reminderReceiver.setUpdateAlarm(this, "8:00",ReminderReceiver.EXTRA_MESSAGE);
                    Toast.makeText(this, "Reminder update aktif", Toast.LENGTH_SHORT).show();
                }
                else{
                    sharedPreferences.setKlik(NotificationPreference.KLIK_UPDATE, false);
                    reminderReceiver.cancelAlarmUpdate(this);
                    Toast.makeText(this, "Reminder update tidak aktif", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void setSwitch(){
        if(sharedPreferences.getKlikHarian()){
            switchHarian.setChecked(true);
        }
        else{
            switchHarian.setChecked(false);
        }
        if(sharedPreferences.getKlikUpdate()){
            switchUpdate.setChecked(true);
        }
        else{
            switchUpdate.setChecked(false);
        }
    }
    public static void getNewRelease(final Context context){
        final NotificationPreference notificationPreference= new NotificationPreference(context);
        SimpleDateFormat date= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate= date.format(new Date());
        String KEY_NAME="412327d8b23a411e90711834b24fe08e";
        final AsyncHttpClient[] client= {new AsyncHttpClient()};
        String url= "https://api.themoviedb.org/3/discover/movie?api_key=" + KEY_NAME + "&primary_release_date.gte=" + currentDate + "&primary_release_date.lte="+ currentDate;

        client[0].get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result= new String(responseBody);
                    JSONObject responseObject= new JSONObject(result);
                        String title= responseObject.getJSONArray("results").getJSONObject(0).getString("title");
                        notificationPreference.setTitle(NotificationPreference.TITLE_NOTIFICATION, title);
                }
                catch (Exception e){
                    notificationPreference.setTitle(NotificationPreference.TITLE_NOTIFICATION, "error request update film");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String message= error.getMessage();
                Log.d("ReminderActivity", message);

            }
        });
    }
}
