package com.example.freefin2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freefin2.Database.entities.FreeFinUser;

import java.util.List;

public abstract class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private final List<FreeFinUser> users;
    private Context context;


    public UsersAdapter(List<FreeFinUser> users, Context context) {
        this.users = users;
        this.context = context;
    }

    public void updateUsers(List<FreeFinUser> newUsers) {
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.userNameTextView);
        }

    }
}
