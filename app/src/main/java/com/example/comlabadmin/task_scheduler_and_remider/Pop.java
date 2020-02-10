package com.example.comlabadmin.task_scheduler_and_remider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
/**
 * Created by COMLABADMIN on 10/02/2020.
 */
public class Pop extends Activity{

    EditText date, time;
    Button btndate, btntime;
    private int mYear, mMonth, mDay, mHour, mMins;

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
                    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
                        time.setText(hour + ":" + minute);

                    }
                }, mHour, mMins, false);
                TimePicker.show();
            }
        });
    }
}
