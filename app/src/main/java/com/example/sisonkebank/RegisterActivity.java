package com.example.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    // declare ui components
    private Button registerButton;
    private EditText firstName;
    private EditText lastName;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText phoneEditText;
    private TextView loginTextView;
    private RadioGroup genderRadioGroup;
    private String gender = "";
    private DBHelper mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init ui components
        registerButton = findViewById(R.id.buttonRegister);
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmailReg);
        passwordEditText = findViewById(R.id.editTextPasswordReg);
        phoneEditText = findViewById(R.id.editTextPhone);
        genderRadioGroup = findViewById(R.id.radioGroupGender);
        loginTextView = findViewById(R.id.textViewLogin);



        // set click listeners
        registerButton.setOnClickListener(event -> {
            // get edit text components
            String firstName = this.firstName.getText().toString();
            String lastName = this.lastName.getText().toString();
            String email = this.emailEditText.getText().toString();
            String password = this.passwordEditText.getText().toString();
            String mobile = this.phoneEditText.getText().toString();
            double savingsAccountBalance = 5200;
            double currentAccountBalance = 3900;

            // call validation
           if(!validate(firstName, lastName, email, password, mobile, gender)) return;

           BankUser user = new BankUser(firstName, lastName, email, password, mobile, gender, currentAccountBalance, savingsAccountBalance);

           // register user
            registerUser(user);
        });

        loginTextView.setOnClickListener(event -> {
            // go to main activity
            startActivity(new Intent(this, LoginActivity.class));
        });

        genderRadioGroup.setOnCheckedChangeListener((radioGroup, id) -> {
            RadioButton selected = findViewById(id);
            gender = selected.getText().toString();
        });
    }
    // registration
    private void registerUser(BankUser bankUser) {
        // get database instance
        mydb = new DBHelper(this);

        // check if email is registered already
        boolean isRegistered = mydb.checkIfRegistered(bankUser.getEmail());

        if (isRegistered) {
            Toast.makeText(this, String.format("Email: %s is already registered", bankUser.getEmail()), Toast.LENGTH_SHORT).show();
        } else {
            // TODO: hash password and save

            // save user to database
            if (mydb.addUser(bankUser)) {
                // show toast
                Toast.makeText(this, String.format("New account for email: %s registered successfully", bankUser.getEmail()), Toast.LENGTH_SHORT).show();

                // go to main activity
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                // show toast
                Toast.makeText(this, String.format("Something went wrong, account not created for email: %s", bankUser.getEmail()), Toast.LENGTH_SHORT).show();


            }

        }




        // if success
        // go to login activity
//        startActivity(new Intent(this, LoginActivity.class));
    }

    private boolean validate(String firstName, String lastName, String email, String password, String mobile, String gender) {
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 5) {
                Toast.makeText(this, "Password must be more than 5 characters", Toast.LENGTH_SHORT).show();
                return false;
        }

        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
