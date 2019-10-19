package com.example.filmliburan.Preview.Pengingat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.filmliburan.Data.Source.Local.NotificationPreference;
import com.example.filmliburan.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE= "EXTRA_MESSAGE";

    public ReminderReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationPreference notificationPreference= new NotificationPreference(context);
        // get message from main use broadcash
        String message=intent.getStringExtra(EXTRA_MESSAGE);
        String title="Film Liburan";
        if(message.equalsIgnoreCase("EXTRA_MESSAGE")) {
            ReminderActivity.getNewRelease(context);
            title="Film Liburan";
            message= notificationPreference.getTitle();
        }
        tampilHarianNotification(context,title, message);
    }

    // melakukan pengecekan apakah format dan waktu sudah benar
    public boolean dateInvalid(String date, String format){
        try {
            DateFormat df= new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        }
        catch (ParseException e){
            return true;
        }
    }

    private void tampilHarianNotification(Context context, String title, String message){
        String CHANNEL_ID="Channel1";
        String CHANNEL_NAME="hariannotification channel";
        int requestCode= message.equalsIgnoreCase("EXTRA_MESSAGE")?102 :101;
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setSound(alarmSound);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            builder.setChannelId(CHANNEL_ID);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification= builder.build();
        if(notificationManager!=null){
            notificationManager.notify(requestCode, notification);
        }
    }
    public void setHarianAlarm(Context context, String time, String message){
        String TIME_FORMAT= "HH:mm";
        if(dateInvalid(time, TIME_FORMAT))return;
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        String timeArray[]= time.split(":");

        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context,101, intent,0 );
        if(alarmManager!=null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);
        }
    }
    public void setUpdateAlarm(Context context, String time, String message){
        String  TIME_FORMAT="HH:mm";
        if(dateInvalid(time, TIME_FORMAT))return;
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        String timeArray[]= time.split(":");
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context,102, intent,0);
        if(alarmManager!=null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY ,pendingIntent);
        }
    }
    public void cancelAlarmHarian(Context context){
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 101,intent,0);
        pendingIntent.cancel();
        if(alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "alarm harian dibatalkan", Toast.LENGTH_SHORT).show();
    }
    public void cancelAlarmUpdate(Context context){
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 102, intent,0);
        pendingIntent.cancel();
        if(alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "alarm update dibatalkan", Toast.LENGTH_SHORT).show();
    }
}
