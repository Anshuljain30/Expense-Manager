package com.example.expensemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensemanager.databinding.FragmentTransactionsBinding
import com.example.expensemanager.ui.adapter.TransactionAdapter
import com.example.expensemanager.viewmodel.ExpenseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionsFragment : Fragment() {
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeTransactions()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter { expense ->
            // Handle click on transaction item
        }
        
        binding.transactionsRecyclerView.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeTransactions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allExpenses.collectLatest { expenses ->
                transactionAdapter.submitList(expenses)
                binding.emptyStateLayout.visibility = 
                    if (expenses.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 