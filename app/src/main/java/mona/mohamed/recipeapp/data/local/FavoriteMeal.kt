package mona.mohamed.recipeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMeal(
    @PrimaryKey
    val mealId: String,
    val userId: String,
    val mealName: String,
    val mealThumb: String,
    val category: String? = null,
    val area: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)