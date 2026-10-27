package com.smakolyky.di

import com.smakolyky.data.repository.FavoritesRepositoryImpl
import com.smakolyky.data.repository.RecipeRepositoryImpl
import com.smakolyky.data.repository.ShoppingRepositoryImpl
import com.smakolyky.domain.repository.FavoritesRepository
import com.smakolyky.domain.repository.RecipeRepository
import com.smakolyky.domain.repository.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    @Singleton
    abstract fun bindShoppingRepository(impl: ShoppingRepositoryImpl): ShoppingRepository
}
