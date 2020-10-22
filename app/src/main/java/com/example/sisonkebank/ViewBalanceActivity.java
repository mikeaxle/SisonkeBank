package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class ViewBalanceActivity extends AppCompatActivity {
    private TextView holderFirstNameTextView;
    private TextView holderLastNameTextView;
    private TextView currentAccountBalanceTextView;
    private TextView savingsAccountBalanceTextView;
    private BankUser bankUser;
    private DBHelper mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_balance);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // get extras
        Bundle extras = getIntent().getExtras();

        // get database instance
        mydb = new DBHelper(this);

        // init ui elements
        holderFirstNameTextView = findViewById(R.id.textViewHolderFirstName);
        holderLastNameTextView = findViewById(R.id.textViewHolderSurname);
        currentAccountBalanceTextView = findViewById(R.id.textViewCurrentAccountBalance);
        savingsAccountBalanceTextView = findViewById(R.id.textViewSavingsAccountBalance2);

        // get user details
        bankUser = mydb.getUserDetails(extras.getInt("userId"));

        // append values
        holderFirstNameTextView.append(" " + bankUser.getFirstName());
        holderLastNameTextView.append(" " + bankUser.getLastName());
        currentAccountBalanceTextView.append(" R" + bankUser.getCurrentAccountBalance());
        savingsAccountBalanceTextView.append(" R" + bankUser.getSavingsAccountBalance());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}