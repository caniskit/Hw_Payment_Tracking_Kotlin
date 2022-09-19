package com.caniskit.hw_payment_tracking_kotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable

class DatabaseOpenHelper(context : Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,name,factory,version){

    override fun onCreate(db: SQLiteDatabase?) {
    var query = "CREATE TABLE 'paymentType'(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Title TEXT,Period TEXT, Day INTEGER)"

    db!!.execSQL(query)

        query = "CREATE TABLE 'payment'(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,PTypeId INTEGER,Date TEXT,Amount TEXT, FOREIGN KEY(pTypeId) REFERENCES 'paymentType'(Id))"

        db!!.execSQL(query)
}

override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    if(oldVersion == 1){

    }else if(oldVersion ==2){

    }

}
}