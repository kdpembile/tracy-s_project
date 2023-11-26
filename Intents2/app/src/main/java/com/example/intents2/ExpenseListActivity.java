package com.example.intents2;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpenseListActivity extends AppCompatActivity {

    private MyDBHelper dbHelper;
    private ListView listView;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        dbHelper = new MyDBHelper(this);
        listView = findViewById(R.id.listview);

        displayExpenses();
    }

    private void displayExpenses() {
        Cursor cursor = dbHelper.getAllExpenses();

        String[] fromColumns = {"_id", MyDBHelper.COLUMN_DATE, MyDBHelper.COLUMN_TITLE,
                MyDBHelper.COLUMN_DESCRIPTION, MyDBHelper.COLUMN_AMOUNT, MyDBHelper.COLUMN_CATEGORY};

        int[] toViews = {R.id.dateTextView, R.id.titleTextView,
                R.id.descriptionTextView, R.id.amountTextView, R.id.categoryTextView};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.expense_list_item,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listView.setAdapter(adapter);
    }
}