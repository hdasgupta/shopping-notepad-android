package com.example.shoppingnotepad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query(
        "SELECT * FROM shopping_items " +
            "WHERE shoppingDate = :date " +
            "ORDER BY isPurchased ASC, createdAt DESC"
    )
    fun observeItemsForDate(date: String): Flow<List<ShoppingItem>>

    @Insert
    suspend fun insert(item: ShoppingItem): Long

    @Update
    suspend fun update(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("DELETE FROM shopping_items WHERE shoppingDate = :date AND isPurchased = 1")
    suspend fun clearPurchasedForDate(date: String)
}
