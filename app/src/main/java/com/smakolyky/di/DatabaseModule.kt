package com.smakolyky.di

import android.content.Context
import androidx.room.Room
import com.smakolyky.data.local.AppDatabase
import com.smakolyky.data.local.dao.FavoriteDao
import com.smakolyky.data.local.dao.RecipeDao
import com.smakolyky.data.local.dao.ShoppingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "smakolyky.db")
            .fallbackToDestructiveMigration() // прийнятно для навчального проєкту без продакшн-даних
            .build()

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    fun provideShoppingDao(db: AppDatabase): ShoppingDao = db.shoppingDao()
}
