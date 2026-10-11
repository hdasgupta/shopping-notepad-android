package com.example.shoppingnotepad.data

import kotlinx.coroutines.flow.Flow

class ShoppingRepository(private val dao: ShoppingDao) {
    fun observeItemsForDate(date: String): Flow<List<ShoppingItem>> =
        dao.observeItemsForDate(date)

    suspend fun add(item: ShoppingItem) {
        dao.insert(item)
    }

    suspend fun update(item: ShoppingItem) {
        dao.update(item)
    }

    suspend fun delete(item: ShoppingItem) {
        dao.delete(item)
    }

    suspend fun clearPurchased(date: String) {
        dao.clearPurchasedForDate(date)
    }
}
