package com.example.cred_assignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cred_assignment.Bank
import com.example.cred_assignment.R

class BankAdapter(private val bankList: List<Bank>) : RecyclerView.Adapter<BankAdapter.BankViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        return BankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val bank = bankList[position]
        holder.bind(bank)
    }

    override fun getItemCount(): Int = bankList.size

    class BankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bankName: TextView = itemView.findViewById(R.id.bankName)
        private val accountNumber: TextView = itemView.findViewById(R.id.accountNumber)
        private  val button:Button=itemView.findViewById(R.id.ctaButton)
        fun bind(bank: Bank) {
            bankName.text = bank.name
            accountNumber.text = "Account: ${bank.accountNumber}"
            button.text="Proceed"
        }
    }
}
