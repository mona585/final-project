package mona.mohamed.recipeapp.view.home

import android.R.drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.data.models.Meal
import mona.mohamed.recipeapp.databinding.ItemMealBinding

class MealAdapter(
    private val onMealClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var meals = listOf<Meal>()

    fun submitList(newMeals: List<Meal>) {
        meals = newMeals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount() = meals.size

    inner class MealViewHolder(
        private val binding: ItemMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.apply {
                tvMealName.text = meal.strMeal

                // Load image using Glide
                Glide.with(itemView)
                    .load(meal.strMealThumb)
                    .placeholder(R.drawable.placeholder)
                    .into(ivMealImage)

                root.setOnClickListener {
                    onMealClick(meal)
                }
            }
        }
    }
}