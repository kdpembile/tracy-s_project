package com.example.intents2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private MyDBHelper dbHelper;
    private EditText mdate, mtitle, mdescription, mamount;
    private Spinner mcategory;
    private Button msavebtn, mviewbtn;
    private ExpenseData expenseData;  // Move the declaration here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBHelper(this);

        mdate = findViewById(R.id.date);
        mtitle = findViewById(R.id.title);
        mdescription = findViewById(R.id.description);
        mamount = findViewById(R.id.amount);
        mcategory = findViewById(R.id.category);

        // EditTexts
        EditText mdate = findViewById(R.id.date);
        EditText mtitle = findViewById(R.id.title);
        EditText mdescription = findViewById(R.id.description);
        EditText mamount = findViewById(R.id.amount);

        msavebtn = findViewById(R.id.savebtn);
        mviewbtn = findViewById(R.id.viewbtn);

        // Spinner
        Spinner mcategory = findViewById(R.id.category);
        String[] categories = {"Category", "Utilities", "Food", "Savings", "Housing", "Transportation"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        mcategory.setAdapter(adapter);

        // Button
        Button msavebtn = findViewById(R.id.savebtn);

        msavebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String date = mdate.getText().toString();
                String title = mtitle.getText().toString();
                String description = mdescription.getText().toString();
                String amount = mamount.getText().toString();
                String category = mcategory.getSelectedItem().toString();

                // Create an ExpenseData object
                expenseData = new ExpenseData(date, title, description, amount, category);

                // Put the ExpenseData object in the Intent
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("ExpenseData", expenseData); // Use putExtra for Parcelable objects

                startActivity(intent);

                // Add the expense to the database
                long newRowId = dbHelper.addExpense(expenseData);

                if (newRowId != -1) {
                    Toast.makeText(MainActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to add expense", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mviewbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start the ExpenseListActivity to view expenses
                Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
                startActivity(intent);

            }
        });
    }
}


