package com.caniskit.hw_payment_tracking_kotlin

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.caniskit.hw_payment_tracking_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pyOp=PaymentOperation(this)
        val btnNew = binding.addNewType

        btnNew.setOnClickListener {

            val intent = Intent(this,AddNewPaymentType()::class.java)
            resultLauncher.launch(intent)
        }

        //val pty = PaymentType(null,"Araba Taksidi",Period.AYLIK,20)
        //pyOp.AddPaymentType(pty)
        val ptList = pyOp.GetAllPaymentTypes()
        if(ptList!=null)
        {val lm= LinearLayoutManager(this)
        lm.orientation= LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.adapter= PaymentTypeAdapter(ptList){paymentType -> adapterOnClick(paymentType) }
    }}

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK){

            println("updated")
            binding.recyclerView.adapter?.notifyDataSetChanged()

        }
    }

    private fun adapterOnClick(paymentType: PaymentType) {
        val intent=Intent(this,PaymentDetail()::class.java )
        intent.putExtra("pid",paymentType.Id)
        resultLauncher.launch(intent)
    }
}