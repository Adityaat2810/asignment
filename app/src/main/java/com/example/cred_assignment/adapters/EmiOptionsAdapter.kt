package com.example.cred_assignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cred_assignment.EmiOption
import com.example.cred_assignment.R
import com.example.cred_assignment.databinding.ItemEmiOptionBinding
class EmiOptionsAdapter(private val emiOptions: List<EmiOption>) :
    RecyclerView.Adapter<EmiOptionsAdapter.EmiOptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmiOptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_emi_option, parent, false)
        return EmiOptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmiOptionViewHolder, position: Int) {
        holder.bind(emiOptions[position])
    }

    override fun getItemCount(): Int = emiOptions.size

    inner class EmiOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val emiTitle: TextView = itemView.findViewById(R.id.emiTitle)
        private val emiDescription: TextView = itemView.findViewById(R.id.emiDescription)
        private val emiAmount: TextView = itemView.findViewById(R.id.emiAmount)
        private val recommendedTag: TextView = itemView.findViewById(R.id.recommendedTag)

        fun bind(emiOption: EmiOption) {
            emiTitle.text = emiOption.title
            emiDescription.text = emiOption.subtitle
            emiAmount.text = emiOption.emi

            // Set the "recommended" tag visibility based on isRecommended flag
            if (emiOption.isRecommended) {
                recommendedTag.visibility = View.VISIBLE
            } else {
                recommendedTag.visibility = View.GONE
            }
        }
    }
}