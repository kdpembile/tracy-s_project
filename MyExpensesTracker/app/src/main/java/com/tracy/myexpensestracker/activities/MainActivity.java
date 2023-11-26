package com.tracy.myexpensestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracy.myexpensestracker.R;
import com.tracy.myexpensestracker.adapters.ExpenseRecyclerAdapter;
import com.tracy.myexpensestracker.entities.Expense;
import com.tracy.myexpensestracker.viewmodel.ExpenseViewModel;


public class MainActivity extends AppCompatActivity {

    private ExpenseViewModel expenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_add_expense);

        final ExpenseRecyclerAdapter expenseRecyclerAdapter = new ExpenseRecyclerAdapter();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(expenseRecyclerAdapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(this, expenseRecyclerAdapter::setExpenses);
    }

    public void addExpense(View view) {
        Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();

                    String title = data.getStringExtra("Title");
                    Double price = data.getDoubleExtra("Price", 0.00);
                    String date = data.getStringExtra("Date");
                    String description = data.getStringExtra("Description");

                    Expense expense = new Expense(title, price, date, description);
                    expenseViewModel.insert(expense);
                }
            });
}