package com.smakolyky.data.remote

import com.smakolyky.data.remote.dto.AreasResponse
import com.smakolyky.data.remote.dto.CategoriesResponse
import com.smakolyky.data.remote.dto.MealSummariesResponse
import com.smakolyky.data.remote.dto.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDbApi {

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealsResponse

    @GET("lookup.php")
    suspend fun lookupMeal(@Query("i") id: String): MealsResponse

    @GET("random.php")
    suspend fun getRandomMeal(): MealsResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("list.php?a=list")
    suspend fun getAreas(): AreasResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealSummariesResponse

    @GET("filter.php")
    suspend fun filterByArea(@Query("a") area: String): MealSummariesResponse
}
