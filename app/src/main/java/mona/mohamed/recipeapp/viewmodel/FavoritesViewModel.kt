package mona.mohamed.recipeapp.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.data.FavoriteEntity
import mona.mohamed.recipeapp.repository.FavoritesRepository

class FavoritesViewModel(private val repository: FavoritesRepository) : ViewModel() {
    fun getFavorites(userId: String) = repository.getFavorites(userId)

    fun addFavorite(fav: FavoriteEntity) {
        viewModelScope.launch { repository.addFavorite(fav) }
    }

    fun removeFavorite(fav: FavoriteEntity) {
        viewModelScope.launch { repository.removeFavorite(fav) }
    }

    fun isFavorite(mealId: String, userId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            callback(repository.isFavorite(mealId, userId))
        }
    }
}
