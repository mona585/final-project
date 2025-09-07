package mona.mohamed.recipeapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllFavorites(userId: String): Flow<List<FavoriteMeal>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_meals WHERE mealId = :mealId AND userId = :userId)")
    suspend fun isFavorite(mealId: String, userId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteMeal)

    @Query("DELETE FROM favorite_meals WHERE mealId = :mealId AND userId = :userId")
    suspend fun deleteFavorite(mealId: String, userId: String)

    @Query("DELETE FROM favorite_meals WHERE userId = :userId")
    suspend fun deleteAllFavorites(userId: String)
}