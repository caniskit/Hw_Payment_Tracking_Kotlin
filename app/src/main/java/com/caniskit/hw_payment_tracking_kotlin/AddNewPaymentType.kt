package com.caniskit.hw_payment_tracking_kotlin

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caniskit.hw_payment_tracking_kotlin.databinding.ActivityAddNewPaymentTypeBinding

class AddNewPaymentType : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var  binding: ActivityAddNewPaymentTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewPaymentTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val etxTitle = binding.etxTitle
        val spPeriod = binding.spPeriod
        val etxPeriodDay = binding.etxPeriodDay
        val btnSubmit= binding.btnSubmit
        val btnDelete= binding.btnDelete
        val id = intent.getIntExtra("id",-1)
        ArrayAdapter.createFromResource(this,R.array.periods,android.R.layout.simple_spinner_item).also{
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spPeriod.adapter= adapter
        }
        if(id != -1){
            btnDelete.isClickable=true
            btnDelete.isVisible=true
            btnSubmit.text="Güncelle"

            spPeriod.onItemSelectedListener=this
                if(spPeriod.selectedItemId==0L){
                    etxPeriodDay.isClickable=false
                    etxPeriodDay.text=null

                }
            btnSubmit.setOnClickListener {
                if(etxTitle.text.isBlank()){
                    Toast.makeText(this,"Başlık Boş Bıraklamaz",Toast.LENGTH_SHORT)
                } else if(!rangeControl(spPeriod.selectedItem.toString(),etxPeriodDay.text.toString().toInt())){
                    Toast.makeText(this,"Periyot ve Günü tutarsız",Toast.LENGTH_SHORT)
                }else{
                    val pymOp = PaymentOperation(this)
                    if(spPeriod.id==0){

                        pymOp.UpdatePaymentType(PaymentType(id,etxTitle.text.toString(),null,null))
                        setResult(RESULT_OK)
                        finish()

                    }else{
                        pymOp.UpdatePaymentType(PaymentType(id,etxTitle.text.toString(),spPeriod.id,etxPeriodDay.text.toString().toInt()))
                        setResult(RESULT_OK)
                        finish()
                    }
                }}


        }else{


        spPeriod.onItemSelectedListener = this

        btnSubmit.setOnClickListener {
            if(etxTitle.text.isBlank()){
                Toast.makeText(this,"Başlık Boş Bıraklamaz",Toast.LENGTH_SHORT)
            } else if(!rangeControl(spPeriod.selectedItem.toString(),etxPeriodDay.text.toString().toIntOrNull())){
                Toast.makeText(this,"Periyot ve Günü tutarsız",Toast.LENGTH_SHORT)
            }else{
                val pymOp = PaymentOperation(this)
                if(spPeriod.id==0){

                 pymOp.AddPaymentType(PaymentType(null,etxTitle.text.toString(),null,null))
                    setResult(RESULT_OK)
                    finish()

                }else{
                    pymOp.AddPaymentType(PaymentType(null,etxTitle.text.toString(),spPeriod.selectedItemId.toInt(),etxPeriodDay.text.toString().toIntOrNull()))
                    setResult(RESULT_OK)
                    finish()
                }
            }}
        }





    }
    fun rangeControl(period :String?,day: Int?):Boolean{

        if(period=="AYLIK" && (day==null||day<1 ||day>31)){
            return false

        }else if(period=="HAFTALIK" && (day==null||day<1 || day>7) ){
            return false
        }
        return true
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val day = parent?.findViewById<EditText>(R.id.tvDay)
        if(day !=null){

            if(id==0L ){



                day!!.isClickable=false
                day!!.text=null

            }else{
                day!!.isClickable=true

            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        val day = parent?.findViewById<EditText>(R.id.tvDay)
        if(day !=null){
        day!!.isClickable= false
        day!!.text=null}
    }
}

