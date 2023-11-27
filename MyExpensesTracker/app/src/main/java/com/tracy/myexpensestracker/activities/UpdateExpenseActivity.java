package com.tracy.myexpensestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.tracy.myexpensestracker.R;

import java.util.Arrays;
import java.util.Objects;

public class UpdateExpenseActivity extends AppCompatActivity {

    public static final int UPDATE_OK = 2;

    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutPrice;
    private EditText editTextDate;
    private EditText editTextDescription;

    private Integer id;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);

        textInputLayoutTitle = findViewById(R.id.update_expense_title);
        textInputLayoutPrice = findViewById(R.id.update_expense_price);
        editTextDate = findViewById(R.id.update_expense_date);
        editTextDescription = findViewById(R.id.update_expense_description);

        // Spinner
        Spinner spinner = findViewById(R.id.category);
        String[] categories = {"Category", "Utilities", "Food", "Savings", "Housing", "Transportation"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        Intent intent = getIntent();
        id=intent.getIntExtra("Id", -1);
        Objects.requireNonNull(textInputLayoutTitle.getEditText()).setText(intent.getStringExtra("Title"));
        Objects.requireNonNull(textInputLayoutPrice.getEditText()).setText(String.valueOf(intent.getDoubleExtra("Price", 0.00)));
        Objects.requireNonNull(editTextDate).setText(intent.getStringExtra("Date"));
        Objects.requireNonNull(editTextDescription).setText(intent.getStringExtra("Description"));
        Objects.requireNonNull(spinner).setSelection(Arrays.asList(categories).indexOf(intent.getStringExtra("Category")));
    }

    public void back(View view) {
        finish();
    }

    public void update(View view) {
        String title = Objects.requireNonNull(textInputLayoutTitle.getEditText()).getText().toString();
        Double price = Double.valueOf(Objects.requireNonNull(textInputLayoutPrice.getEditText()).getText().toString());
        String date = Objects.requireNonNull(editTextDate.getText().toString());
        String description = Objects.requireNonNull(editTextDescription.getText().toString());

        Intent data = new Intent();
        data.putExtra("Id", id);
        data.putExtra("Title", title);
        data.putExtra("Price", price);
        data.putExtra("Date", date);
        data.putExtra("Description", description);
        data.putExtra("Category", category);

        setResult(UPDATE_OK, data);
        finish();
    }
}