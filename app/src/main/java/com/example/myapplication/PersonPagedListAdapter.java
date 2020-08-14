package com.example.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonPagedListAdapter extends PagedListAdapter<Person, PersonPagedListAdapter.PersonViewHolder> {
    ActionMode actionMode;
    ArrayList<Person> arrayListSelectedItems;
    Boolean selectMode;
    ViewGroup viewGroup;
    Activity mainActivity;

    protected PersonPagedListAdapter(@NonNull DiffUtil.ItemCallback<Person> diffCallback, Activity mainActivity) {
        super(diffCallback);
        arrayListSelectedItems = new ArrayList<>();
        selectMode = false;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, null);
        this.viewGroup = parent;
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonViewHolder holder, final int position) {
        holder.bind(Objects.requireNonNull(getItem(position)));

        if (arrayListSelectedItems.contains(getItem(position)))
            holder.itemView.setBackgroundColor(viewGroup.getResources().getColor(R.color.colorAccent));
        else
            holder.itemView.setBackgroundColor(viewGroup.getResources().getColor(android.R.color.background_light));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListSelectedItems.isEmpty()) {
                    holder.itemView.setBackgroundColor(viewGroup.getResources().getColor(R.color.colorAccent));
                    arrayListSelectedItems.add(holder.person);
                    actionMode = viewGroup.startActionMode(new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            mode.getMenuInflater().inflate(R.menu.action_mode, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            ((IDatabaseEdit)mainActivity).deletePersons(arrayListSelectedItems);
                            arrayListSelectedItems.remove(arrayListSelectedItems);
                            actionMode.finish();
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            arrayListSelectedItems.clear();
                            ((IRecyclerViewUpdate)mainActivity).recyclerViewUpdate();
                        }
                    });
                }
                else {
                    if (arrayListSelectedItems.contains(holder.person)) {
                        arrayListSelectedItems.remove(holder.person);
                        holder.itemView.setBackgroundColor(viewGroup.getResources().getColor(android.R.color.background_light));
                    }
                    else
                    {
                        arrayListSelectedItems.add(holder.person);
                        holder.itemView.setBackgroundColor(viewGroup.getResources().getColor(R.color.colorAccent));
                    }
                }

                if (arrayListSelectedItems.size() == 0) {
                    selectMode = false;
                    actionMode.finish();
                }
            }
        });
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView1;
        TextView textView2;
        Person person;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(android.R.id.text1);
            textView2 = itemView.findViewById(android.R.id.text2);

        }

        void bind (Person person)
        {
            textView1.setText(person.name);
            textView2.setText(person.surname);
            this.person = person;
        }
    }

    public interface IDatabaseEdit
    {
         void deletePersons(List<Person> list);
    }

    public interface IRecyclerViewUpdate
    {
         void recyclerViewUpdate();
    }
}

