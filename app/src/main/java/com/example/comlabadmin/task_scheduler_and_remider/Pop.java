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
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pop extends Activity{

    EditText date, time, title;
    Button btndate, btntime, setsched;
    private int mYear, mMonth, mDay, mHour, mMins;
    String sched;

    Calendar alert = Calendar.getInstance();

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

                    }
                },mYear,mMonth,mDay);
                DatePicker.show();
            }
        });

        btntime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMins = calendar.get(Calendar.MINUTE);

                TimePickerDialog TimePicker = new TimePickerDialog(Pop.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        time.setText(hour + ":" + minute);
                    }
                }, mHour, mMins, false);
                TimePicker.show();
            }
        });

        setsched.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                alert.setTimeInMillis(System.currentTimeMillis());

                scheduleNotification(getNotification(title.toString()),);
            }
        });
    }

    private void scheduleNotification(Notification notification, long ms) {

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        time.add(Calendar.HOUR_OF_DAY, );

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        return builder.build();
    }
}
