package com.tracy.myexpensestracker.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tracy.myexpensestracker.dao.ExpenseDao;
import com.tracy.myexpensestracker.databases.ExpenseDatabase;
import com.tracy.myexpensestracker.entities.Expense;

import java.util.List;

public class ExpenseRepository {

    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> expenses;

    public ExpenseRepository(Application application) {
        ExpenseDatabase expenseDatabase = ExpenseDatabase.getInstance(application);
        expenseDao = expenseDatabase.expenseDao();

        expenses = expenseDao.getAllExpenses();
    }

    public void insert(Expense expense) {
        new InsertExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void update(Expense expense) {
        new UpdateExpenseAsyncTask(expenseDao).execute(expense);
    }

    public void delete(Expense expense) {
        new DeleteExpenseAsyncTask(expenseDao).execute(expense);
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return expenses;
    }

    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private final ExpenseDao expenseDao;

        private InsertExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.insert(expenses[0]);
            return null;
        }
    }

    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private final ExpenseDao expenseDao;

        private UpdateExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.update(expenses[0]);
            return null;
        }
    }

    private static class DeleteExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private final ExpenseDao expenseDao;

        private DeleteExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.delete(expenses[0]);
            return null;
        }
    }
}
