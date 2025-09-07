package mona.mohamed.recipeapp.network

import mona.mohamed.recipeapp.data.models.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") mealId: String
    ): MealResponse

    @GET("search.php")
    suspend fun searchMealsByName(@Query("s") name: String): MealResponse
}