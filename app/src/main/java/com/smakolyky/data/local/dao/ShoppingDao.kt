package com.smakolyky.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smakolyky.data.local.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ShoppingItemEntity>)

    @Query("UPDATE shopping_items SET isChecked = :checked WHERE id = :id")
    suspend fun setChecked(id: Long, checked: Boolean)

    @Query("SELECT * FROM shopping_items WHERE userId = :userId ORDER BY recipeTitle, addedAt")
    fun observeAll(userId: String = "local"): Flow<List<ShoppingItemEntity>>

    @Query("DELETE FROM shopping_items WHERE userId = :userId AND isChecked = 1")
    suspend fun deleteChecked(userId: String = "local")
}
