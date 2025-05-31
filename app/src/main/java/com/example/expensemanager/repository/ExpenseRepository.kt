package com.example.expensemanager.repository

import com.example.expensemanager.data.Expense
import com.example.expensemanager.data.ExpenseDao
import com.example.expensemanager.data.ExpenseType
import com.example.expensemanager.data.CategoryTotal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
    
    fun getExpensesByType(type: ExpenseType): Flow<List<Expense>> = 
        expenseDao.getExpensesByType(type)
    
    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>> =
        expenseDao.getExpensesByDateRange(startDate, endDate)
    
    fun getTotalByType(type: ExpenseType): Flow<Double?> = expenseDao.getTotalByType(type)
    
    fun getCategoryTotals(type: ExpenseType): Flow<Map<String, Double>> =
        expenseDao.getCategoryTotals(type).map { categoryTotals ->
            categoryTotals.associate { it.category to it.total }
        }
    
    suspend fun insertExpense(expense: Expense) = expenseDao.insertExpense(expense)
    
    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)
    
    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)
} 