package mona.mohamed.recipeapp.data.repository

import mona.mohamed.recipeapp.network.RetrofitInstance

class MealRepository {
    suspend fun getRandomMeal() = RetrofitInstance.api.getRandomMeal()

    suspend fun getMealsByCategory(category: String) =
        RetrofitInstance.api.getMealsByCategory(category)

    // In MealRepository.kt, add:
    suspend fun getMealDetails(mealId: String) =
        RetrofitInstance.api.getMealDetails(mealId)

    // In MealRepository
    suspend fun searchMealsByName(name: String) = RetrofitInstance.api.searchMealsByName(name)
}