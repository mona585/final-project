package mona.mohamed.recipeapp.search

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.data.repository.MealRepository

class SearchViewModel : ViewModel() {

    private val mealRepository = MealRepository()

    private val _meals = MutableLiveData<List<SearchMeal>>()
    val meals: LiveData<List<SearchMeal>> = _meals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Store all meals for filtering
    private var allMeals: List<SearchMeal> = emptyList()

    init {
        // Load initial meals
        searchMeals("chicken")
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = mealRepository.searchMealsByName(query.ifEmpty { "chicken" })
                allMeals = response.meals?.map { meal ->
                    SearchMeal(
                        id = meal.idMeal,
                        name = meal.strMeal,
                        thumbnail = meal.strMealThumb ?: "",
                        category = meal.strCategory ?: "",
                        country = meal.strArea ?: ""
                    )
                } ?: emptyList()

                _meals.value = allMeals
            } catch (e: Exception) {
                _error.value = e.message
                _meals.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterMeals(query: String, filterType: String) {
        if (query.isEmpty()) {
            _meals.value = allMeals
            return
        }

        _meals.value = allMeals.filter { meal ->
            when (filterType) {
                "Name" -> meal.name.contains(query, ignoreCase = true)
                "Category" -> meal.category.contains(query, ignoreCase = true)
                "Country" -> meal.country.contains(query, ignoreCase = true)
                else -> meal.name.contains(query, ignoreCase = true)
            }
        }
    }
}