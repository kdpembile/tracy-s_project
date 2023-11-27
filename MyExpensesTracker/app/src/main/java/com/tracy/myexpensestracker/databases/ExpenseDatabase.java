package com.tracy.myexpensestracker.databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tracy.myexpensestracker.dao.ExpenseDao;
import com.tracy.myexpensestracker.entities.Expense;

@Database(entities = {Expense.class}, version = 2)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ExpenseDatabase.class, "expense_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ExpenseDao expenseDao;

        private PopulateDbAsyncTask(ExpenseDatabase expenseDatabase) {
            expenseDao = expenseDatabase.expenseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.insert(new Expense("Meralco", 2500.00, "11/27/2023", "Meralco bill for december paid."));
            expenseDao.insert(new Expense("Credit Card", 2500.00, "11/27/2023", "Credit card bill for december paid."));
            return null;
        }
    }
}
