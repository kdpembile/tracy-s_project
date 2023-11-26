package com.example.intents2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        // Intent to get data
        Intent intent = getIntent();
        ExpenseData expenseData = intent.getParcelableExtra("ExpenseData");

        // TextView
        TextView mResultTv = findViewById(R.id.resultTv);

        if (expenseData != null) {
            // Use getters to retrieve data and display it
            String text = "Date: " + expenseData.getDate() + "\nTitle: " + expenseData.getTitle()
                    + "\nDescription: " + expenseData.getDescription() + "\nAmount: " + expenseData.getAmount()
                    + "\nCategory: " + expenseData.getCategory();

            mResultTv.setText(text);
        }

        // Button
        Button mViewBtn = findViewById(R.id.viewbtn);

        mViewBtn.setOnClickListener(view -> {
            // Start the ExpenseListActivity to view expenses
            Intent viewIntent = new Intent(ExpenseActivity.this, ExpenseListActivity.class);
            startActivity(viewIntent);
        });

        // Set OnClickListener outside of the above listener
        mViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the behavior when the button is clicked (navigate back to the previous activity)
                onBackPressed();
            }
        });
    }
}
