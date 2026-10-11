package com.example.shoppingnotepad.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoppingnotepad.data.ShoppingDatabase
import com.example.shoppingnotepad.data.ShoppingItem
import com.example.shoppingnotepad.data.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {
    fun itemsForDate(date: String): Flow<List<ShoppingItem>> =
        repository.observeItemsForDate(date)

    fun addItem(item: ShoppingItem) {
        viewModelScope.launch { repository.add(item) }
    }

    fun updateItem(item: ShoppingItem) {
        viewModelScope.launch { repository.update(item) }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch { repository.delete(item) }
    }

    fun clearPurchased(date: String) {
        viewModelScope.launch { repository.clearPurchased(date) }
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (!modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                    val dao = ShoppingDatabase.getInstance(context).shoppingDao()
                    return ShoppingViewModel(ShoppingRepository(dao)) as T
                }
            }
    }
}
