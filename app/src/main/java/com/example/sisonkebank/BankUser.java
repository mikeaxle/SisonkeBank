package com.example.sisonkebank;

public class BankUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;
    private String gender;
    private double currentAccountBalance;
    private double savingsAccountBalance;

    public BankUser() {
    }

    public BankUser(int id, String firstName, String lastName, String email, String password, String mobile, String gender, double currentAccountBalance, double savingsAccountBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.gender = gender;
        this.currentAccountBalance = currentAccountBalance;
        this.savingsAccountBalance = savingsAccountBalance;
    }

    public BankUser(String firstName, String lastName, String email, String password, String mobile, String gender, double currentAccountBalance, double savingsAccountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.gender = gender;
        this.currentAccountBalance = currentAccountBalance;
        this.savingsAccountBalance = savingsAccountBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public double getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    public double getSavingsAccountBalance() {
        return savingsAccountBalance;
    }

    public void setCurrentAccountBalance(double currentAccountBalance) {
        this.currentAccountBalance = currentAccountBalance;
    }

    public void setSavingsAccountBalance(double savingsAccountBalance) {
        this.savingsAccountBalance = savingsAccountBalance;
    }
}
