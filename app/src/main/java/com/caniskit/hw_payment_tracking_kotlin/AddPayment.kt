package com.caniskit.hw_payment_tracking_kotlin

import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import com.caniskit.hw_payment_tracking_kotlin.databinding.ActivityAddPaymentBinding
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class AddPayment : AppCompatActivity() {

    lateinit var binding: ActivityAddPaymentBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id= intent.getIntExtra("pid",-1)
        val etamount = binding.etxPaymentAmount
        val etdate = binding.etxPickDate
        val current = Calendar.getInstance().time
        val format =  SimpleDateFormat("dd-MM-yyyy")

        etdate.setText(format.format(current))


    binding.pymSave.setOnClickListener{


        val pymOp = PaymentOperation(this)

        pymOp.AddPayment(Payment(null,id,etdate.text.toString(),etamount.text.toString()))
        setResult(RESULT_OK)
        finish()
    }

        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish() }
    }




}