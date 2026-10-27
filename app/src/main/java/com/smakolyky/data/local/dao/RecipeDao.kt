package com.smakolyky.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smakolyky.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): RecipeEntity?

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%'")
    fun searchByTitle(query: String): Flow<List<RecipeEntity>>
}
