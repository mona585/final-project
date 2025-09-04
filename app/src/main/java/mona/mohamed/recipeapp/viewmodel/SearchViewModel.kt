package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.repository.MealsRepository

class SearchViewModel(private val repository: MealsRepository) : ViewModel() {

    private val _meals = MutableLiveData<List<mona.mohamed.recipeapp.search.Meal>>()
    val meals: LiveData<List<mona.mohamed.recipeapp.search.Meal>> get() = _meals

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMeals(query)
                _meals.value = response.meals?.map {
                    mona.mohamed.recipeapp.search.Meal(
                        id = it.idMeal,
                        name = it.strMeal,
                        ingredient = it.strInstructions ?: "",
                        category = it.strCategory ?: "",
                        country = it.strArea ?: ""
                    )
                } ?: emptyList()
            } catch (e: Exception) {
                _meals.value = emptyList()
            }
        }
    }
}
