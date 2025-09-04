package mona.mohamed.recipeapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApiService {

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByCountry(@Query("a") country: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): MealsResponse
}

data class MealsResponse(
    val meals: List<MealDto>?
)

data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?
)
