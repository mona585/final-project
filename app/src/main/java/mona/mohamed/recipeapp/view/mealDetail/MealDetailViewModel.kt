package mona.mohamed.recipeapp.view.mealDetail



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.data.models.Meal
import mona.mohamed.recipeapp.repository.MealRepository

class MealDetailViewModel : ViewModel() {
    private val repository = MealRepository()

    private val _meal = MutableLiveData<Meal?>()
    val meal: LiveData<Meal?> = _meal

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadMealDetails(mealId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getMealDetails(mealId)
                _meal.value = response.meals?.firstOrNull()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}