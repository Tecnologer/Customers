package com.firstapp.rey.firstapp;

/**
 * Created by Andrea on 29/2/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, nombre, factory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        //aquí creamos la tabla de customer (phone, name)
        db.execSQL("create table Customers(sCustomerPhone text primary key, sCustomerName text)");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists Customers");

        db.execSQL("create table Customers(sCustomerPhone text primary key, sCustomerName text)");

    }

}