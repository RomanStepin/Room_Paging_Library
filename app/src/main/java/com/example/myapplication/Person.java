package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "PEOPLES")
public class Person
{
    String name;
    String surname;
    @PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "_id")
    int id;


    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}

