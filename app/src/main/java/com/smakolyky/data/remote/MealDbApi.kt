package com.smakolyky.data.remote

import com.smakolyky.data.remote.dto.CategoriesResponse
import com.smakolyky.data.remote.dto.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDbApi {

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealsResponse

    @GET("lookup.php")
    suspend fun lookupMeal(@Query("i") id: String): MealsResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
}
