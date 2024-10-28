package com.example.cred_assignment.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cred_assignment.databinding.ItemAmountBinding
import com.example.cred_assignment.databinding.ItemBankBinding
import com.example.cred_assignment.databinding.ItemDurationBinding
import com.example.cred_assignment.Bank
import com.example.cred_assignment.EmiOption

class StackViewAdapter : ListAdapter<StackViewAdapter.StackItem, RecyclerView.ViewHolder>(StackDiffCallback()) {

    sealed class StackItem {
        data class AmountItem(
            val title: String,
            val subtitle: String,
            val maxAmount: Int,
            val minAmount: Int,
            val footer: String,
            val ctaText: String
        ) : StackItem()

        data class DurationItem(
            val title: String,
            val subtitle: String,
            val options: List<EmiOption>,
            val footer: String,
            val ctaText: String
        ) : StackItem()

        data class BankItem(
            val title: String,
            val subtitle: String,
            val banks: List<Bank>,
            val footer: String,
            val ctaText: String
        ) : StackItem()
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is StackItem.AmountItem -> VIEW_TYPE_AMOUNT
            is StackItem.DurationItem -> VIEW_TYPE_DURATION
            is StackItem.BankItem -> VIEW_TYPE_BANK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_AMOUNT -> {
                val binding = ItemAmountBinding.inflate(inflater, parent, false)
                AmountViewHolder(binding)
            }
            VIEW_TYPE_DURATION -> {
                val binding = ItemDurationBinding.inflate(inflater, parent, false)
                DurationViewHolder(binding)
            }
            VIEW_TYPE_BANK -> {
                val binding = ItemBankBinding.inflate(inflater, parent, false)
                BankViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is StackItem.AmountItem -> (holder as AmountViewHolder).bind(item)
            is StackItem.DurationItem -> (holder as DurationViewHolder).bind(item)
            is StackItem.BankItem -> (holder as BankViewHolder).bind(item)
        }
    }

    class AmountViewHolder(private val binding: ItemAmountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StackItem.AmountItem) {
            binding.titleText.text = item.title
            binding.subtitleText.text = item.subtitle
            binding.footerText.text = item.footer
            binding.ctaButton.text = item.ctaText

            // Set up Slider to handle amount selection within range
//            binding.amountSlider.valueFrom = 500.0f
//            binding.amountSlider.valueTo = 487891.0f
//            binding.amountSlider.value = 500.0f

            binding.amountSlider.addOnChangeListener { _, value, _ ->
                binding.amountText.text = value.toInt().toString()
            }
        }
    }

    class DurationViewHolder(private val binding: ItemDurationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StackItem.DurationItem) {
            binding.titleText.text = item.title
            binding.subtitleText.text = item.subtitle
            binding.footerText.text = item.footer
            binding.ctaButton.text = item.ctaText

            // Initialize RecyclerView for EMI options
            val emiOptionsAdapter = EmiOptionsAdapter(item.options)
            binding.emiOptionsRecyclerView.apply {
                adapter = emiOptionsAdapter
                layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    class BankViewHolder(private val binding: ItemBankBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StackItem.BankItem) {
            // Set main texts
            binding.bankName.text = item.title
            binding.accountNumber.text = item.subtitle
            binding.footerText.text = item.footer
            binding.ctaButton.text = item.ctaText

            // Initialize the adapter for bank items and set up RecyclerView
            val bankAdapter = BankAdapter(item.banks)
            binding.banksRecyclerView.apply {
                adapter = bankAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
                setHasFixedSize(true)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_AMOUNT = 0
        private const val VIEW_TYPE_DURATION = 1
        private const val VIEW_TYPE_BANK = 2
    }

    private class StackDiffCallback : DiffUtil.ItemCallback<StackItem>() {
        override fun areItemsTheSame(oldItem: StackItem, newItem: StackItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StackItem, newItem: StackItem): Boolean {
            return oldItem == newItem
        }
    }
}
