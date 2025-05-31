package com.example.expensemanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val category: String,
    val description: String,
    val date: Date,
    val type: ExpenseType // INCOME or EXPENSE
)

enum class ExpenseType {
    INCOME,
    EXPENSE
}

enum class ExpenseCategory {
    FOOD,
    TRANSPORTATION,
    HOUSING,
    UTILITIES,
    ENTERTAINMENT,
    SHOPPING,
    HEALTHCARE,
    EDUCATION,
    SALARY,
    INVESTMENTS,
    OTHER
} 