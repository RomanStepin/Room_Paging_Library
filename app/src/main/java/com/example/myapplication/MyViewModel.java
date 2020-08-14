package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.icu.util.LocaleData;
import android.util.TimeUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyViewModel extends AndroidViewModel
{
    DatabasePerson databasePerson;

    public MyViewModel(@NonNull Application application) {
        super(application);
        databasePerson = Room.databaseBuilder(application.getBaseContext(), DatabasePerson.class, "PERSON_BASE").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }
}
