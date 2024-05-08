package com.example.freefin2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.freefin2.Database.entities.Goals;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_list, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {
        Goals goal = goalsList.get(position);
        holder.goalTitle.setText(goal.getTitle());
        holder.goalAmount.setText(String.format(Locale.getDefault(), "$%.2f", goal.getAmount()));
        holder.goalDate.setText(goal.getDate().toString());

        holder.deleteButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                goalsList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView goalTitle, goalAmount, goalDate;
        Button deleteButton;

        public GoalViewHolder(View itemView) {
            super(itemView);
            goalTitle = itemView.findViewById(R.id.textViewGoalTitle);
            goalAmount = itemView.findViewById(R.id.textViewGoalAmount);
            goalDate = itemView.findViewById(R.id.textViewGoalDate);
            deleteButton = itemView.findViewById(R.id.buttonDeleteGoal);
        }
    }
}
