package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao
{
    @Query("SELECT * FROM PEOPLES LIMIT :i1, :i2")
   List<Person> queryList(long i1, long i2);

    @Query("SELECT * FROM PEOPLES WHERE _id = :id")
    Person queryPerson(long id);

    @Insert
    void insertPerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Delete
    void deletePersons(List<Person> list);
}


