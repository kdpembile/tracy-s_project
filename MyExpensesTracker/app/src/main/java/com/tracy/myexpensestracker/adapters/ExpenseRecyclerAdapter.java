package com.tracy.myexpensestracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracy.myexpensestracker.R;
import com.tracy.myexpensestracker.entities.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.MyViewHolder> {

    private List<Expense> expenses = new ArrayList<>();
    private OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ExpenseItem);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(expenses.get(position));
                }
            });
        }
    }

    @NonNull
    @Override
    public ExpenseRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.add_expense_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseRecyclerAdapter.MyViewHolder holder, int position) {
        String title = expenses.get(position).getTitle();
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public Expense getExpenseAt(int position) {
        return expenses.get(position);
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
