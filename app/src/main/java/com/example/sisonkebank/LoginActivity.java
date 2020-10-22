package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    // declare ui components
    private Button loginButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView registerTextView;
    private DBHelper mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init ui components
        this.loginButton = findViewById(R.id.buttonLogin);
        this.emailEditText = findViewById(R.id.editTextEmailReg);
        this.passwordEditText = findViewById(R.id.editTextPasswordReg);
        this.registerTextView = findViewById(R.id.textViewRegister);


        // add login button listener
        this.loginButton.setOnClickListener(event -> {
            // get edit text components
            String email = this.emailEditText.getText().toString();
            String password = this.passwordEditText.getText().toString();

            // call validation
            if (!this.validate(email, password)) return;

            // authenticate user
            this.authenticateUser(email, password);
        });

        // add register textview button listener
        this.registerTextView.setOnClickListener(event -> {
            // go to main activity
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }

    // TODO: implement authentication
    private void authenticateUser(String email, String password) {
        // get database instance
        mydb = new DBHelper(this);

        // check if email is not registered
        boolean isRegistered = mydb.checkIfRegistered(email);
        if (!isRegistered) {
            Toast.makeText(this, String.format("No account matching email %s", email), Toast.LENGTH_SHORT).show();
            return;
        }

        // if authenticated
        // go to main activity
        int userId = mydb.authenticateUser(email, password);
        if (userId != -1) {
            Toast.makeText(this, String.format("%s successfully logged in!", email), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class).putExtra("userId", userId));
            finish();
        } else {
            Toast.makeText(this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate(String email, String password) {
        // check if empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }


        // check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        // check if password is 5 characters
        if (password.length() < 5) {
            Toast.makeText(this, "Password must be more than 5 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}