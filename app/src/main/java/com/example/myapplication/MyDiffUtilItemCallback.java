package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class MyDiffUtilItemCallback extends DiffUtil.ItemCallback<Person> {

    @Override
    public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
        return (oldItem.id == newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
        return (oldItem.name.equals(newItem.name) && oldItem.surname.equals(newItem.surname));
    }
}
