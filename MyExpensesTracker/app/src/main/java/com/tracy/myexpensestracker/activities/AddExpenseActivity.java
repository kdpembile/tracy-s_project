package com.tracy.myexpensestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.tracy.myexpensestracker.R;

import java.util.Objects;

public class AddExpenseActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutPrice;
    private EditText editTextDate;
    private EditText editTextDescription;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        textInputLayoutTitle = findViewById(R.id.text_input_title);
        textInputLayoutPrice = findViewById(R.id.text_input_price);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextDescription = findViewById(R.id.edit_text_description);

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
    }


    public void save(View view) {
        String title = Objects.requireNonNull(textInputLayoutTitle.getEditText()).getText().toString();
        Double price = Double.valueOf(Objects.requireNonNull(textInputLayoutPrice.getEditText()).getText().toString());
        String date = Objects.requireNonNull(editTextDate.getText().toString());
        String description = Objects.requireNonNull(editTextDescription.getText().toString());

        Intent data = new Intent();
        data.putExtra("Title", title);
        data.putExtra("Price", price);
        data.putExtra("Date", date);
        data.putExtra("Description", description);
        data.putExtra("Category", category);

        setResult(RESULT_OK, data);
        finish();
    }

    public void back(View view) {
        finish();
    }
}