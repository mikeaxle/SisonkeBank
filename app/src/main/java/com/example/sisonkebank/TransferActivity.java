package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class TransferActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView currentAccountBalanceTextView;
    private TextView savingsAccountBalanceTextView;
    private TextView transferAmountEditText;
    private DBHelper mydb = null;
    private BankUser bankUser;
    private Spinner accountsSpinner;
    private ArrayList<String> accountoptions;
    private Button transferButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // get extras
        Bundle extras = getIntent().getExtras();

        // get database instance
        mydb = new DBHelper(this);

        // get user details
        bankUser = mydb.getUserDetails(extras.getInt("userId"));

        currentAccountBalanceTextView = findViewById(R.id.textViewCurrentAccountBalance);
        savingsAccountBalanceTextView = findViewById(R.id.textViewSavingsAccountBalance);
        transferAmountEditText =  findViewById(R.id.editTextTransferAmount);

        currentAccountBalanceTextView.append(" R" + bankUser.getCurrentAccountBalance());
        savingsAccountBalanceTextView.append(" R" + bankUser.getSavingsAccountBalance());

        accountsSpinner = findViewById(R.id.spinnerAccounts);
        accountsSpinner.setOnItemSelectedListener(this);

        accountoptions = new ArrayList<String>();
        accountoptions.add("Current to Savings");
        accountoptions.add("Savings to Current");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountoptions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountsSpinner.setAdapter(dataAdapter);

        transferButton = findViewById(R.id.buttonTransfer);
        transferButton.setOnClickListener(event -> {
            String item = accountsSpinner.getSelectedItem().toString();

            // validation
            CharSequence amount = transferAmountEditText.getText();

            if (TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            Double temp = Double.parseDouble(amount.toString());

            if(item.equals("Current to Savings")) {

                if (bankUser.getCurrentAccountBalance() >= temp) {
                    bankUser.setCurrentAccountBalance(bankUser.getCurrentAccountBalance() - temp);
                    bankUser.setSavingsAccountBalance(bankUser.getSavingsAccountBalance() + temp);

                    mydb.updateBankAccount(bankUser, "Current to Savings", Double.parseDouble(amount.toString()));
                    Toast.makeText(this, "Transfer completed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (bankUser.getSavingsAccountBalance() >= Double.parseDouble(amount.toString())) {
                    bankUser.setCurrentAccountBalance(bankUser.getCurrentAccountBalance() + temp);
                    bankUser.setSavingsAccountBalance(bankUser.getSavingsAccountBalance() - temp);

                    mydb.updateBankAccount(bankUser,"Savings to Current", Double.parseDouble(amount.toString()));
                    Toast.makeText(this, "Transfer completed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}