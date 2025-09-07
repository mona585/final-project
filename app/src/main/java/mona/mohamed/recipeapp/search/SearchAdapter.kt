package mona.mohamed.recipeapp.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.databinding.ItemSearchResultBinding

class SearchAdapter(
    private val onItemClick: (SearchMeal) -> Unit
) : ListAdapter<SearchMeal, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: SearchMeal) {
            binding.apply {
                mealName.text = meal.name
                mealCategory.text = meal.category
                mealCountry.text = meal.country

                Glide.with(itemView)
                    .load(meal.thumbnail)
                    .placeholder(R.drawable.placeholder)
                    .into(mealImage)

                root.setOnClickListener { onItemClick(meal) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchMeal>() {
        override fun areItemsTheSame(oldItem: SearchMeal, newItem: SearchMeal) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SearchMeal, newItem: SearchMeal) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}