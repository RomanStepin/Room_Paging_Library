package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PersonDataSource extends PositionalDataSource<Person>
{
    DatabasePerson databasePerson;
    Executor executor;

    PersonDataSource(DatabasePerson databasePerson)
    {
        this.databasePerson = databasePerson;
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback<Person> callback) {
        Log.d("LOG", "  " + params.requestedStartPosition + "   " + params.requestedLoadSize);
                List<Person> list = databasePerson.personDao().queryList(params.requestedStartPosition, params.requestedStartPosition + params.requestedLoadSize);
                callback.onResult(list,0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback<Person> callback) {
        Log.d("LOG", "  " + params.startPosition + "   " +  params.loadSize);

                List<Person> list = databasePerson.personDao().queryList(params.startPosition, params.startPosition + params.loadSize);
                callback.onResult(list);
    }


}

