package com.example.intents2;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpenseData implements Parcelable {
    private String date;
    private String title;
    private String description;
    private String amount;
    private String category;



    public ExpenseData(String date, String title, String description, String amount, String category) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    protected ExpenseData(Parcel in) {
        date = in.readString();
        title = in.readString();
        description = in.readString();
        amount = in.readString();
        category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(amount);
        dest.writeString(category);
    }

    public static final Creator<ExpenseData> CREATOR = new Creator<ExpenseData>() {
        @Override
        public ExpenseData createFromParcel(Parcel in) {
            return new ExpenseData(in);
        }

        @Override
        public ExpenseData[] newArray(int size) {
            return new ExpenseData[size];
        }
    };


    // Getter methods
    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}
