package com.tracy.myexpensestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.tracy.myexpensestracker.R;

import java.util.Objects;

public class UpdateExpenseActivity extends AppCompatActivity {

    public static final int UPDATE_OK = 2;

    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutPrice;
    private EditText editTextDate;
    private EditText editTextDescription;

    private Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);

        textInputLayoutTitle = findViewById(R.id.update_expense_title);
        textInputLayoutPrice = findViewById(R.id.update_expense_price);
        editTextDate = findViewById(R.id.update_expense_date);
        editTextDescription = findViewById(R.id.update_expense_description);

        Intent intent = getIntent();
        id=intent.getIntExtra("Id", -1);
        Objects.requireNonNull(textInputLayoutTitle.getEditText()).setText(intent.getStringExtra("Title"));
        Objects.requireNonNull(textInputLayoutPrice.getEditText()).setText(String.valueOf(intent.getDoubleExtra("Price", 0.00)));
        Objects.requireNonNull(editTextDate).setText(intent.getStringExtra("Date"));
        Objects.requireNonNull(editTextDescription).setText(intent.getStringExtra("Description"));
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

        setResult(UPDATE_OK, data);
        finish();
    }
}