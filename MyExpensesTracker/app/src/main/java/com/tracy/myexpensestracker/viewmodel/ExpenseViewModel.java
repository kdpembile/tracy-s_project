package com.tracy.myexpensestracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tracy.myexpensestracker.entities.Expense;
import com.tracy.myexpensestracker.repositories.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository expenseRepository;
    private LiveData<List<Expense>> expenses;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        expenses = expenseRepository.getAllExpenses();
    }

    public void insert(Expense expense) {
        expenseRepository.insert(expense);
    }

    public void update(Expense expense) {
        expenseRepository.update(expense);
    }

    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return expenseRepository.getAllExpenses();
    }
}
