package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.data.models.Meal
import mona.mohamed.recipeapp.data.repository.MealRepository

class HomeViewModel : ViewModel() {
    private val repository = MealRepository()

    private val _suggestedMeal = MutableLiveData<Meal?>()
    val suggestedMeal: LiveData<Meal?> = _suggestedMeal

    private val _categoryMeals = MutableLiveData<List<Meal>>()
    val categoryMeals: LiveData<List<Meal>> = _categoryMeals

    init {
        loadSuggestedMeal()
        loadCategoryMeals()
    }

    private fun loadSuggestedMeal() {
        viewModelScope.launch {
            try {
                val response = repository.getRandomMeal()
                _suggestedMeal.value = response.meals?.firstOrNull()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun loadCategoryMeals() {
        viewModelScope.launch {
            try {
                // You can change the category as needed
                val response = repository.getMealsByCategory("Beef")
                _categoryMeals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}