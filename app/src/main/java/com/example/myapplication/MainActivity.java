package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.renderscript.ScriptGroup;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LifecycleOwner, View.OnClickListener, PersonPagedListAdapter.IDatabaseEdit, PersonPagedListAdapter.IRecyclerViewUpdate {

    Button button;
    MyViewModel myViewModel;
    TextView textView;
    RecyclerView recyclerView;
    PersonPagedListAdapter personPagedListAdapter;
    PersonDataSource personDataSource;
    DatabasePerson databasePerson;
    PagedList.Config config;
    PagedList<Person> pagedList;
    MyExecutor myExecutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        binding.setMyViewModel(myViewModel);
        myExecutor = new MyExecutor();
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textView);

        databasePerson = myViewModel.databasePerson;
        personDataSource = new PersonDataSource(databasePerson);
        personPagedListAdapter = new PersonPagedListAdapter(new MyDiffUtilItemCallback(), this);
        config = new PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(20).setInitialLoadSizeHint(30).build();
        pagedList = new PagedList.Builder<>(personDataSource, config).setFetchExecutor(Executors.newFixedThreadPool(1)).setNotifyExecutor(myExecutor).build();
        personPagedListAdapter.submitList(pagedList);
        recyclerView.setAdapter(personPagedListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View v) {
        databasePerson.personDao().insertPerson(new PersonGenerator().getPerson());
        pagedList = new PagedList.Builder<>(personDataSource, config).setFetchExecutor(Executors.newFixedThreadPool(1)).setNotifyExecutor(myExecutor).build();
        personPagedListAdapter.submitList(pagedList);
    }

    @Override
    public void deletePersons(List<Person> list) {
        databasePerson.personDao().deletePersons(list);
        pagedList = new PagedList.Builder<>(personDataSource, config).setFetchExecutor(Executors.newFixedThreadPool(1)).setNotifyExecutor(myExecutor).build();
        personPagedListAdapter.submitList(pagedList);
    }

    @Override
    public void recyclerViewUpdate() {
        personPagedListAdapter.notifyDataSetChanged();
    }


    static class MyExecutor implements Executor {
        Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }
}




