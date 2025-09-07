package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import mona.mohamed.recipeapp.data.local.FavoriteMeal
import mona.mohamed.recipeapp.data.repository.FavoriteRepository

class FavoritesViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    val favorites: LiveData<List<FavoriteMeal>> =
        favoriteRepository.getAllFavorites()?.asLiveData() ?:
        androidx.lifecycle.liveData { emit(emptyList()) }
}