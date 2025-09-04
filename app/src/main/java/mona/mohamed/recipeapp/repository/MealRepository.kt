package mona.mohamed.recipeapp.repository

import mona.mohamed.recipeapp.network.RetrofitInstance

class MealRepository {
    suspend fun getRandomMeal() = RetrofitInstance.api.getRandomMeal()

    suspend fun getMealsByCategory(category: String) =
        RetrofitInstance.api.getMealsByCategory(category)

    // In MealRepository.kt, add:
    suspend fun getMealDetails(mealId: String) =
        RetrofitInstance.api.getMealDetails(mealId)
}