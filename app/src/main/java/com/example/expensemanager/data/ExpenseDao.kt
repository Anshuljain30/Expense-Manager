package com.example.expensemanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE type = :type ORDER BY date DESC")
    fun getExpensesByType(type: ExpenseType): Flow<List<Expense>>
    
    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDateRange(startDate: Date, endDate: Date): Flow<List<Expense>>
    
    @Query("SELECT SUM(amount) FROM expenses WHERE type = :type")
    fun getTotalByType(type: ExpenseType): Flow<Double?>
    
    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE type = :type GROUP BY category")
    fun getCategoryTotals(type: ExpenseType): Flow<List<CategoryTotal>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)
    
    @Update
    suspend fun updateExpense(expense: Expense)
    
    @Delete
    suspend fun deleteExpense(expense: Expense)
} 