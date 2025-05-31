package com.example.expensemanager.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.expensemanager.data.ExpenseType
import com.example.expensemanager.databinding.FragmentStatisticsBinding
import com.example.expensemanager.viewmodel.ExpenseViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharts()
        observeData()
    }

    private fun setupCharts() {
        binding.expensePieChart.apply {
            description.isEnabled = false
            setUsePercentValues(true)
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.BLACK)
            legend.textSize = 12f
            legend.isWordWrapEnabled = true
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCategoryTotals(ExpenseType.EXPENSE).collectLatest { categoryTotals ->
                updatePieChart(categoryTotals)
            }
        }
    }

    private fun updatePieChart(categoryTotals: Map<String, Double>) {
        val entries = categoryTotals.map { (category, amount) ->
            PieEntry(amount.toFloat(), category)
        }

        val dataSet = PieDataSet(entries, "Expense Categories").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 12f
            valueTextColor = Color.BLACK
        }

        binding.expensePieChart.data = PieData(dataSet)
        binding.expensePieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 