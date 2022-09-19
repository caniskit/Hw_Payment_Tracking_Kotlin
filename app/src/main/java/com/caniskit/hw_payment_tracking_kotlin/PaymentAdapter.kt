package com.caniskit.hw_payment_tracking_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class PaymentViewHolder(itemView: View, val onClick: (Payment) -> Unit) : RecyclerView.ViewHolder(itemView){

    var tvDate: TextView
    var tvAmount: TextView

    private  var currentPayment: Payment?=null;

    init{
        itemView.setOnClickListener{
            currentPayment?.let{
                onClick(it)
            }
        }
        tvDate= itemView.findViewById(R.id.tvDate)
        tvAmount=itemView.findViewById(R.id.tvAmount)

    }

    fun bindData(payment: Payment){
        currentPayment= payment
        tvDate.text= payment.Date
        tvAmount.text= payment.Amount

    }

    object PaymentDiffCallback : DiffUtil.ItemCallback<Payment>(){
        override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.Id == newItem.Id
        }

    }



}


class PaymentAdapter(var data: ArrayList<Payment>, private val onClick:(Payment)->Unit) : ListAdapter<Payment, PaymentViewHolder>(PaymentViewHolder.PaymentDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rci_payment_detail,parent,false)
        return PaymentViewHolder(v,onClick)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bindData(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

}