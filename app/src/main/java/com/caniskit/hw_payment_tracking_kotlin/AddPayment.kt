package com.caniskit.hw_payment_tracking_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.caniskit.hw_payment_tracking_kotlin.databinding.ActivityAddPaymentBinding

class AddPayment : AppCompatActivity() {

    lateinit var binding: ActivityAddPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id= intent.getIntExtra("id",-1)
        val etamount = binding.etxPaymentAmount
        val etdate = binding.etxPickDate



    binding.pymSave.setOnClickListener{


        val pymOp = PaymentOperation(this)

        pymOp.AddPayment(Payment(null,id,etdate.text.toString(),etamount.text.toString()))
    }

    }




}