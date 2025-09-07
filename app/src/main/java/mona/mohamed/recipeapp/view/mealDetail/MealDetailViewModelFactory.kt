package mona.mohamed.recipeapp.view.mealDetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mona.mohamed.recipeapp.repository.FavoriteRepository


class MealDetailViewModelFactory(
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealDetailViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}