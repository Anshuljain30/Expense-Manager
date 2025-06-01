package com.example.expensemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensemanager.R
import com.example.expensemanager.data.Expense
import com.example.expensemanager.data.ExpenseType
import com.example.expensemanager.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter(
    private val onItemClick: (Expense) -> Unit
) : ListAdapter<Expense, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position >= 0) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(expense: Expense) {
            binding.apply {
                categoryTextView.text = expense.category
                
                // Show description only if it's not blank
                if (expense.description.isNotBlank()) {
                    descriptionTextView.text = expense.description
                    descriptionTextView.visibility = View.VISIBLE
                } else {
                    descriptionTextView.visibility = View.GONE
                }
                
                dateTextView.text = dateFormat.format(expense.date)
                
                // Format amount with proper sign and color
                val amountText = when (expense.type) {
                    ExpenseType.INCOME -> String.format(Locale.getDefault(), "₹%.2f", expense.amount)
                    ExpenseType.EXPENSE -> String.format(Locale.getDefault(), "₹%.2f", expense.amount)
                }
                amountTextView.text = amountText
                
                // Set color based on transaction type
                amountTextView.setTextColor(
                    itemView.context.getColor(
                        if (expense.type == ExpenseType.INCOME)
                            R.color.income_green
                        else
                            R.color.expense_red
                    )
                )
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
} 