package com.example.shoppingnotepad.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_items",
    indices = [Index(value = ["shoppingDate"])]
)
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val quantity: String = "",
    val note: String = "",
    /** Local date represented as ISO-8601 yyyy-MM-dd. */
    val shoppingDate: String,
    val isPurchased: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

val shoppingCategories = listOf(
    "Groceries",
    "Vegetables",
    "Fruits",
    "Dairy",
    "Prepared food",
    "Meat & fish",
    "Bakery",
    "Household",
    "Personal care",
    "Other"
)

fun categoryEmoji(category: String): String = when (category) {
    "Groceries" -> "🛒"
    "Vegetables" -> "🥦"
    "Fruits" -> "🍎"
    "Dairy" -> "🥛"
    "Prepared food" -> "🍱"
    "Meat & fish" -> "🐟"
    "Bakery" -> "🥖"
    "Household" -> "🧹"
    "Personal care" -> "🧴"
    else -> "📝"
}
