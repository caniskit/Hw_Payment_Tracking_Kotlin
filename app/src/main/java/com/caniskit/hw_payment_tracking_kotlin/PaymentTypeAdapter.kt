package com.caniskit.hw_payment_tracking_kotlin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PaymentTypeViewHolder(itemView: View, val onClick: (PaymentType) -> Unit) : RecyclerView.ViewHolder(itemView){

    var tvTitle: TextView
    var tvPeroid: TextView
    var tvDay : TextView
    var btnAdd :Button
    private  var currentPaymentType: PaymentType?=null;

    init{
        itemView.setOnClickListener{
            currentPaymentType?.let{
                onClick(it)
            }
        }
        tvTitle= itemView.findViewById(R.id.tvTitle)
        tvPeroid=itemView.findViewById(R.id.tvPeriod)
        tvDay=itemView.findViewById(R.id.tvDay)
        btnAdd= itemView.findViewById(R.id.btnAddPayment)
    }

    fun bindData(paymentType: PaymentType){
        currentPaymentType= paymentType
        tvTitle.text= paymentType.Title
        tvPeroid.text= //Period.values()[paymentType.Period!!].toString()
                if(paymentType.Period!=null) Period.values()[paymentType.Period!!].toString() else null
        tvDay.text= if(paymentType.Day!=null)paymentType.Day.toString() else null

        btnAdd.setOnClickListener {
            var intent = Intent( itemView.context, AddPayment()::class.java )
            intent.putExtra("pid",paymentType.Id)
            itemView.context.startActivity(intent)

        }


    }

    object PaymentTypeDiffCallback : DiffUtil.ItemCallback<PaymentType>(){
        override fun areItemsTheSame(oldItem: PaymentType, newItem: PaymentType): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: PaymentType, newItem: PaymentType): Boolean {
            return oldItem.Id == newItem.Id
        }

    }



}


class PaymentTypeAdapter(var data: ArrayList<PaymentType>, private val onClick:(PaymentType)->Unit) : ListAdapter<PaymentType, PaymentTypeViewHolder>(PaymentTypeViewHolder.PaymentTypeDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentTypeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rci_payment_type,parent,false)
        return PaymentTypeViewHolder(v,onClick)
    }

    override fun onBindViewHolder(holder: PaymentTypeViewHolder, position: Int) {
        holder.bindData(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }


}