package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int userId;
    private BankUser bankUser;
    private DBHelper mydb = null;
    private TextView welcomeTextView;
    private Button viewBalanceButton;
    private Button transferBetweenAccountsButton;
    private Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // get database instance
        mydb = new DBHelper(this);

        // get extras
        Bundle extras = getIntent().getExtras();

        // init db
        bankUser = mydb.getUserDetails(extras.getInt("userId"));

        welcomeTextView = findViewById(R.id.textViewWelcome);
        viewBalanceButton = findViewById(R.id.buttonViewBalance);
        transferBetweenAccountsButton = findViewById(R.id.buttonAccountTransfer);
        logoutButton = findViewById(R.id.buttonLogout);

        // set welcome text
        String firstName = bankUser.getFirstName();
        welcomeTextView.append(" " + firstName);

        viewBalanceButton.setOnClickListener(event -> {
            // go to view balance activity
            startActivity(new Intent(this, ViewBalanceActivity.class).putExtra("userId", extras.getInt("userId")));
        });

        transferBetweenAccountsButton.setOnClickListener(event -> {
            // go to transfer activity
            startActivity(new Intent(this, TransferActivity.class).putExtra("userId", extras.getInt("userId")));
        });

        logoutButton.setOnClickListener(event -> {
            Toast.makeText(this, "User has successfully logged out", Toast.LENGTH_SHORT).show();

            // go to login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

//        Toast.makeText(this, "here is the UID: " + userId, Toast.LENGTH_LONG).show();
    }
}
