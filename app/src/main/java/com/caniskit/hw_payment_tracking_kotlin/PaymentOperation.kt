package com.caniskit.hw_payment_tracking_kotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class PaymentOperation(context: Context) {
    var paymentDatabase : SQLiteDatabase? = null
    var dbOpenHelper: DatabaseOpenHelper

    init {
        dbOpenHelper= DatabaseOpenHelper(context, "paymentDB",null,1)
    }

    fun open(){
        paymentDatabase= dbOpenHelper.writableDatabase
    }

    fun close(){
        if(paymentDatabase != null && paymentDatabase!!.isOpen){
            paymentDatabase!!.close()
        }
    }

    fun AddPaymentType(paymentType: PaymentType){
        val cv = ContentValues()
        cv.put("Title",paymentType.Title)
        cv.put("Period",if(paymentType.Period!=null)paymentType.Period!! else null)
        cv.put("Day",if(paymentType.Day!=null)paymentType.Day else null)
        open()
        val rq = paymentDatabase!!.insert("paymentType",null,cv)
        close()
    }

    fun AddPayment(payment: Payment ){

        val cv = ContentValues()
        cv.put("Date",payment.Date)
        cv.put("Amount",payment.Amount)
        cv.put("PTypeId",payment.PaymentTypeId)
        open()
        val rq = paymentDatabase!!.insert("payment",null,cv)
        close()

    }
    fun UpdatePaymentType(paymentType: PaymentType ){
        val cv = ContentValues()
        cv.put("Title", paymentType.Title)
        open()
        val rq= paymentDatabase!!.update("todo",cv,"Id=?", arrayOf(paymentType.Id.toString()))
        close()
    }


    fun DeletePaymentType(id: Int){
        open()
        paymentDatabase!!.delete("payment","PTypeId=?", arrayOf(id.toString()))
        paymentDatabase!!.delete("paymentType","id = ?",arrayOf(id.toString()))
        close()
    }

    fun DeletePayment(id : Int){
        open()
        paymentDatabase!!.delete("payment","Id=?", arrayOf(id.toString()))
        close()
    }

    @SuppressLint("Range")
    fun GetAllPaymentTypes (): ArrayList<PaymentType>{
        val PaymentTypes = ArrayList<PaymentType>()
        var paymentType : PaymentType
        open()
        val crs = GetAllPaymentType() as Cursor
        try {
            if (crs.moveToFirst()) {
            //if(it)
            do {
                val period = crs.getInt(crs.getColumnIndex("Period"))
                val day = crs.getInt(crs.getColumnIndex("Day"))
                paymentType = PaymentType( crs.getInt(crs.getColumnIndex("Id")), crs.getString(crs.getColumnIndex("Title")),
                if(period!=null)period!!else null,if(day != null)day else null)
                PaymentTypes.add(paymentType)
            }
            while(crs.moveToNext())

        }}catch (e: Exception){
            Log.d("error","Error while trying to get PaymentTypes from database")
        }finally {
            close()
        }




        return PaymentTypes
    }

    private fun GetAllPaymentType(): Cursor {
        var query = "Select * from paymentType"

        return paymentDatabase!!.rawQuery(query,null)
    }






    @SuppressLint("Range")
    fun GetAllPaymentbyId(id : Int): ArrayList<Payment>{

        val Payments = ArrayList<Payment>()
        var payment : Payment
        open()
        val crs = GetByTdPayments(id) as Cursor
        crs.let{
            it.moveToFirst()
            do {
                payment = Payment()
                payment.Id= it.getInt(it.getColumnIndex("Id"))
                payment.Amount= it.getString(it.getColumnIndex("Amount"))
                payment.Date= it.getString(it.getColumnIndex("Date"))
                Payments.add(payment)
            }
            while(it.moveToNext())

        }
        close()



        return Payments

    }

    private fun GetByTdPayments(id:Int): Cursor {
        var query = "Select * from payment Where PTypeId = ?"

        return paymentDatabase!!.rawQuery(query, arrayOf(id.toString()))
    }
}