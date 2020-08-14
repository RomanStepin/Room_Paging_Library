package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 2, entities = {Person.class})
public abstract class DatabasePerson extends RoomDatabase {
    public abstract PersonDao personDao();
}
