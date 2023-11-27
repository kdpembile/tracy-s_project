package com.tracy.myexpensestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                expenseViewModel.delete(expenseRecyclerAdapter.getExpenseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Expense deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        expenseRecyclerAdapter.setOnItemClickListener(expense -> {
            Intent intent = new Intent(MainActivity.this, UpdateExpenseActivity.class);
            intent.putExtra("Id", expense.getId());
            intent.putExtra("Title", expense.getTitle());
            intent.putExtra("Price", expense.getPrice());
            intent.putExtra("Date", expense.getDate());
            intent.putExtra("Description", expense.getDescription());
            intent.putExtra("Category", expense.getCategory());

            someActivityResultLauncher.launch(intent);
        });
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
                    String category = data.getStringExtra("Category");

                    Expense expense = new Expense(title, price, date, description, category);
                    expenseViewModel.insert(expense);
                } else if (result.getResultCode() == UpdateExpenseActivity.UPDATE_OK && result.getData() != null) {
                    Intent data = result.getData();

                    int id = data.getIntExtra("Id", -1);

                    if (id == -1) {
                        Toast.makeText(this, "Expense can't be updated", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String title = data.getStringExtra("Title");
                    Double price = data.getDoubleExtra("Price", 0.00);
                    String date = data.getStringExtra("Date");
                    String description = data.getStringExtra("Description");
                    String category = data.getStringExtra("Category");

                    Expense expense = new Expense(title, price, date, description, category);
                    expense.setId(id);

                    expenseViewModel.update(expense);

                    Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Expense not saved", Toast.LENGTH_SHORT).show();
                }
            });
}