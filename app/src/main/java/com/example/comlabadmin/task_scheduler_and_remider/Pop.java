package com.example.comlabadmin.task_scheduler_and_remider;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.comlabadmin.task_scheduler_and_remider.AlarmReceiver.NOTIFICATION;
import static com.example.comlabadmin.task_scheduler_and_remider.AlarmReceiver.NOTIFICATION_ID;

public class Pop extends Activity{

    EditText date, time, title;
    Button btndate, btntime, setsched;
    private int mYear, mMonth, mDay, mHour, mMins;

    Calendar alert = Calendar.getInstance();

    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        btndate = (Button) findViewById(R.id.btnDate);
        date = (EditText) findViewById(R.id.etDate);
        btntime = (Button) findViewById(R.id.btnTime);
        time = (EditText) findViewById(R.id.etTime);
        setsched = (Button) findViewById(R.id.setSched);
        title = (EditText) findViewById(R.id.etTitle);

        btndate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DatePicker = new DatePickerDialog(Pop.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + month + "-" + year);

                        Toast.makeText(Pop.this, "This is calendar time: " + String.valueOf(alert.getTime()), Toast.LENGTH_LONG).show();
                        alert.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Toast.makeText(Pop.this, "This is dayOfMonth time: " + String.valueOf(dayOfMonth), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Pop.this, "This is calendar time: " + String.valueOf(alert.getTime()), Toast.LENGTH_LONG).show();
                        alert.set(Calendar.MONTH, month);
                        Toast.makeText(Pop.this, "This is month time: " + String.valueOf(month), Toast.LENGTH_SHORT).show();
                        alert.set(Calendar.YEAR, year);
                        Toast.makeText(Pop.this, "This is year time: " + String.valueOf(year), Toast.LENGTH_SHORT).show();
                    }
                },mYear,mMonth,mDay);
                DatePicker.show();
            }
        });

        btntime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMins = calendar.get(Calendar.MINUTE);

                TimePickerDialog TimePicker = new TimePickerDialog(Pop.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        time.setText(hour + ":" + minute);

                        alert.set(Calendar.HOUR_OF_DAY, hour);
                        Toast.makeText(Pop.this, "This is hour time: " + String.valueOf(hour), Toast.LENGTH_SHORT).show();
                        alert.set(Calendar.MINUTE, minute);
                        Toast.makeText(Pop.this, "This is minute time: " + String.valueOf(minute), Toast.LENGTH_SHORT).show();
                    }
                }, mHour, mMins, false);
                TimePicker.show();
            }
        });

        setsched.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                long alertTime = alert.getTimeInMillis() - calendar.getTimeInMillis();

                Toast.makeText(Pop.this, "This is calendar time: " + String.valueOf(calendar.getTimeInMillis()), Toast.LENGTH_LONG).show();
                Toast.makeText(Pop.this, "This is alert time: " + String.valueOf(alertTime),Toast.LENGTH_LONG).show();

                scheduleNotification(getNotification(title.toString()), alertTime);


            }
        });
    }

    private void scheduleNotification(Notification notification, long alertTime) {
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra(NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + alertTime;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        return builder.build();
    }
}
