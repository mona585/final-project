package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.data.models.Meal
import mona.mohamed.recipeapp.data.repository.FavoriteRepository
import mona.mohamed.recipeapp.data.repository.MealRepository

class MealDetailViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val mealRepository = MealRepository()

    private val _meal = MutableLiveData<Meal?>()
    val meal: LiveData<Meal?> = _meal

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun loadMealDetails(mealId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = mealRepository.getMealDetails(mealId)
                _meal.value = response.meals?.firstOrNull()
                checkIfFavorite(mealId)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun checkIfFavorite(mealId: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.isFavorite(mealId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _meal.value?.let { meal ->
                val newFavoriteStatus = favoriteRepository.toggleFavorite(
                    mealId = meal.idMeal,
                    mealName = meal.strMeal,
                    mealThumb = meal.strMealThumb ?: "",
                    category = meal.strCategory,
                    area = meal.strArea
                )
                _isFavorite.value = newFavoriteStatus
            }
        }
    }
}