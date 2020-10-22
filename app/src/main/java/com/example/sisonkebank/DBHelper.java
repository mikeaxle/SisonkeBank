package com.example.sisonkebank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sisonke";
    public static final String TAG = "DBHelper";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table users " +
                        "(id integer primary key, firstName text, lastName text, email text, password text,mobile text, gender text, currentAccountBalance number, savingsAccountBalance number)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean addUser(BankUser bankUser) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("firstName", bankUser.getFirstName());
            contentValues.put("lastName", bankUser.getLastName());
            contentValues.put("email", bankUser.getEmail());
            contentValues.put("password", bankUser.getPassword());
            contentValues.put("mobile", bankUser.getMobile());
            contentValues.put("gender", bankUser.getGender());
            contentValues.put("currentAccountBalance", bankUser.getCurrentAccountBalance());
            contentValues.put("savingsAccountBalance", bankUser.getSavingsAccountBalance());
            db.insert("users", null, contentValues);
            return true;
        } catch (Exception error) {
            Log.e(TAG, Objects.requireNonNull(error.getMessage()));
            error.printStackTrace();
            return false;
        }


    }

    public boolean checkIfRegistered(String email) {
        boolean flag = false;

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("select * from users where email='" + email + "'", null)) {
            if (cursor.moveToFirst()) flag = true;
        } catch (Exception error) {
            Log.e(TAG, Objects.requireNonNull(error.getMessage()));
            error.printStackTrace();
        }

        return flag;
    }

    public int authenticateUser(String email, String password) {
        int userId = -1;
        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("select * from users where email='" + email + "' AND password='" + password + "'", null)) {
            if (cursor.moveToFirst()) userId = cursor.getInt(0);
        } catch (Exception error) {
            Log.e(TAG, Objects.requireNonNull(error.getMessage()));
            error.printStackTrace();
        }
        return userId;
    }

    public BankUser getUserDetails(int id) {
        BankUser user = new BankUser();
        try (SQLiteDatabase db = this.getWritableDatabase(); Cursor cursor = db.rawQuery("select * from users where id=" + id + "", null)) {
            cursor.moveToFirst();
            user.setId(id);
            user.setFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
            user.setLastName(cursor.getString(cursor.getColumnIndex("lastName")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
            user.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            user.setCurrentAccountBalance(cursor.getDouble(cursor.getColumnIndex("currentAccountBalance")));
            user.setSavingsAccountBalance(cursor.getDouble(cursor.getColumnIndex("savingsAccountBalance")));

            if (!cursor.isClosed()) cursor.close();
        } catch (Exception error) {
            Log.e(TAG, Objects.requireNonNull(error.getMessage()));
            error.printStackTrace();
        }
        return user;
    }

    public boolean updateBankAccount(BankUser bankUser, String account, double amount) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("currentAccountBalance", bankUser.getCurrentAccountBalance());
        contentValues.put("savingsAccountBalance", bankUser.getSavingsAccountBalance());

        db.update("users", contentValues, "id = ?", new String[]{ Integer.toString(bankUser.getId())});

        return true;
    }
}
