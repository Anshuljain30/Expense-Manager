package com.example.expensemanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.data.Expense
import com.example.expensemanager.data.ExpenseDatabase
import com.example.expensemanager.data.ExpenseType
import com.example.expensemanager.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository
    val allExpenses: Flow<List<Expense>>
    val totalIncome: Flow<Double?>
    val totalExpenses: Flow<Double?>

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpenses = repository.getAllExpenses()
        totalIncome = repository.getTotalByType(ExpenseType.INCOME)
        totalExpenses = repository.getTotalByType(ExpenseType.EXPENSE)
    }

    fun getExpensesByType(type: ExpenseType): Flow<List<Expense>> =
        repository.getExpensesByType(type)

    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>> =
        repository.getExpensesByDateRange(startDate, endDate)

    fun getCategoryTotals(type: ExpenseType): Flow<Map<String, Double>> =
        repository.getCategoryTotals(type)

    fun insertExpense(expense: Expense) = viewModelScope.launch {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }
} 