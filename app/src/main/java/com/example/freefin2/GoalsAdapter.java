package com.example.freefin2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.freefin2.Database.entities.Goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalViewHolder> {
    private List<Goals> goalsList = new ArrayList<>();

    public void setGoalsList(List<Goals> goalsList) {
        this.goalsList = goalsList;
        notifyDataSetChanged();
    }

    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_set_goal, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {
        Goals goal = goalsList.get(position);
        holder.goalTitle.setText(goal.getTitle());
        holder.goalAmount.setText(String.format(Locale.getDefault(), "$%.2f", goal.getAmount()));
        holder.goalDate.setText(goal.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goalTitle, goalAmount, goalDate;

        public GoalViewHolder(View itemView) {
            super(itemView);
            goalTitle = itemView.findViewById(R.id.editTextGoalTitle);
            goalAmount = itemView.findViewById(R.id.editTextGoalAmount);
            goalDate = itemView.findViewById(R.id.editTextGoalDeadline);
        }
    }
}
