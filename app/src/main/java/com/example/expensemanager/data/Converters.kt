package com.example.expensemanager.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromExpenseType(value: ExpenseType): String {
        return value.name
    }

    @TypeConverter
    fun toExpenseType(value: String): ExpenseType {
        return ExpenseType.valueOf(value)
    }
} 