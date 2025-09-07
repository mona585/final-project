package mona.mohamed.recipeapp.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.data.local.FavoriteMeal
import mona.mohamed.recipeapp.databinding.ItemFavoriteMealBinding

class FavoritesAdapter(
    private val onMealClick: (FavoriteMeal) -> Unit,
    private val onDeleteClick: (FavoriteMeal) -> Unit
) : ListAdapter<FavoriteMeal, FavoritesAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteMealBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteViewHolder(
        private val binding: ItemFavoriteMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: FavoriteMeal) {
            binding.apply {
                tvMealName.text = favorite.mealName
                tvCategory.text = favorite.category ?: "Unknown"

                Glide.with(itemView)
                    .load(favorite.mealThumb)
                    .placeholder(R.drawable.placeholder)
                    .into(ivMealImage)

                root.setOnClickListener { onMealClick(favorite) }
                btnDelete.setOnClickListener { onDeleteClick(favorite) }
            }
        }
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteMeal>() {
    override fun areItemsTheSame(oldItem: FavoriteMeal, newItem: FavoriteMeal): Boolean {
        return oldItem.mealId == newItem.mealId
    }

    override fun areContentsTheSame(oldItem: FavoriteMeal, newItem: FavoriteMeal): Boolean {
        return oldItem == newItem
    }
}