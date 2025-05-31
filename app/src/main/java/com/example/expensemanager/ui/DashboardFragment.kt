package com.example.expensemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.expensemanager.data.ExpenseType
import com.example.expensemanager.databinding.FragmentDashboardBinding
import com.example.expensemanager.viewmodel.ExpenseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalIncome.collectLatest { income ->
                binding.totalIncomeTextView.text = String.format("₹%.2f", income ?: 0.0)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalExpenses.collectLatest { expenses ->
                binding.totalExpensesTextView.text = String.format("₹%.2f", expenses ?: 0.0)
            }
        }
    }

    private fun setupClickListeners() {
        binding.addExpenseButton.setOnClickListener {
            showAddTransactionDialog(ExpenseType.EXPENSE)
        }

        binding.addIncomeButton.setOnClickListener {
            showAddTransactionDialog(ExpenseType.INCOME)
        }
    }

    private fun showAddTransactionDialog(type: ExpenseType) {
        val dialog = AddTransactionDialogFragment.newInstance().apply {
            arguments = Bundle().apply {
                putSerializable("transaction_type", type)
            }
        }
        dialog.show(parentFragmentManager, "add_transaction")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 