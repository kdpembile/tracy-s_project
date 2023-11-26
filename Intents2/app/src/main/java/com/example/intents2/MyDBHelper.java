package com.example.intents2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "expenses_table";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY = "category";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_AMOUNT + " TEXT,"
                + COLUMN_CATEGORY + " TEXT);";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addExpense(ExpenseData expenseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, expenseData.getDate());
        values.put(COLUMN_TITLE, expenseData.getTitle());
        values.put(COLUMN_DESCRIPTION, expenseData.getDescription());
        values.put(COLUMN_AMOUNT, expenseData.getAmount());
        values.put(COLUMN_CATEGORY, expenseData.getCategory());

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public Cursor getAllExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{COLUMN_ID + " as _id", COLUMN_DATE, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_AMOUNT, COLUMN_CATEGORY};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }


    public void updateExpense(long id, ExpenseData expenseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, expenseData.getDate());
        values.put(COLUMN_TITLE, expenseData.getTitle());
        values.put(COLUMN_DESCRIPTION, expenseData.getDescription());
        values.put(COLUMN_AMOUNT, expenseData.getAmount());
        values.put(COLUMN_CATEGORY, expenseData.getCategory());

        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteExpense(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
