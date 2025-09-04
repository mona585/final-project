package mona.mohamed.recipeapp.repository

import mona.mohamed.recipeapp.data.MealsApiService

class MealsRepository(private val api: MealsApiService) {

    suspend fun searchMeals(query: String) = api.searchMeals(query)

    suspend fun filterByCategory(category: String) = api.filterByCategory(category)

    suspend fun filterByCountry(country: String) = api.filterByCountry(country)

    suspend fun filterByIngredient(ingredient: String) = api.filterByIngredient(ingredient)
}
