package com.caniskit.hw_payment_tracking_kotlin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.caniskit.hw_payment_tracking_kotlin.databinding.ActivityPaymentDetailBinding

class PaymentDetail : AppCompatActivity() {
    lateinit var binding: ActivityPaymentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPaymentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnEdit = binding.btnEdit
        val btnCancel = binding.btnCancel1
        val pyOp = PaymentOperation(this)
        val id = intent.getIntExtra("pid",0)
        val pylist =pyOp.GetAllPaymentbyId(id)

        if(pylist!=null)
        {val lm= LinearLayoutManager(this)
            lm.orientation= LinearLayoutManager.VERTICAL
            binding.recyclerView2.layoutManager = lm
            binding.recyclerView2.adapter= PaymentAdapter(pylist){payment -> adapterOnClick(payment) }

            btnCancel.setOnClickListener { finish() }
            btnEdit.setOnClickListener { val intent = Intent(this, AddNewPaymentType()::class.java )
            intent.putExtra("id",id)
                startActivity(intent)
            }


    }
}

    private fun adapterOnClick(payment: Payment) {
        println("payment id"+payment.Amount+"basıldı")
        val pyOp = PaymentOperation(this)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Silme İşlemi")
        builder.setMessage("Ödeme öğesi silinsin mi?")
        builder.setPositiveButton("Sil",DialogInterface.OnClickListener{ dialog, id->
           pyOp.DeletePayment(payment.Id!!)
        })
        builder.setNegativeButton("İptal",DialogInterface.OnClickListener{
            dialog, which -> println("iptal")
        })

        builder.create()
    }

    }
