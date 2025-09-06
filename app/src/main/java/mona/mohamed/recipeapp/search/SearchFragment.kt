package mona.mohamed.recipeapp.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.data.RetrofitInstance
import mona.mohamed.recipeapp.databinding.FragmentSearchBinding
import androidx.navigation.fragment.findNavController
import mona.mohamed.recipeapp.repository.MealsRepository

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAdapter
    private val mealsRepository by lazy { MealsRepository(RetrofitInstance.api) }

    private var allMeals: List<Meal> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFilterSpinner()
        fetchMealsFromApi()

        binding.searchEditText.addTextChangedListener { query ->
            filterMeals(query.toString(), binding.filterSpinner.selectedItem.toString())
        }
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter { meal ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToMealDetailFragment(meal.id)
            findNavController().navigate(action)
        }
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter = adapter
    }



    private fun setupFilterSpinner() {
        val filterOptions = listOf("Category", "Ingredient", "Country")
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            filterOptions
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinner.adapter = spinnerAdapter

        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterMeals(binding.searchEditText.text.toString(), filterOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun fetchMealsFromApi() {
        lifecycleScope.launch {
            try {
                val response = mealsRepository.searchMeals("") // Fetch all meals
                allMeals = response.meals?.map {
                    Meal(
                        id = it.idMeal,
                        name = it.strMeal,
                        ingredient = it.strInstructions ?: "",
                        category = it.strCategory ?: "",
                        country = it.strArea ?: ""
                    )
                } ?: emptyList()

                adapter.submitList(allMeals)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error loading meals: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun filterMeals(query: String, filterType: String) {
        val filtered = allMeals.filter { meal ->
            when (filterType) {
                "Category" -> meal.category.contains(query, ignoreCase = true)
                "Ingredient" -> meal.ingredient.contains(query, ignoreCase = true)
                "Country" -> meal.country.contains(query, ignoreCase = true)
                else -> meal.name.contains(query, ignoreCase = true)
            }
        }
        adapter.submitList(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Meal(
    val id: String,
    val name: String,
    val ingredient: String,
    val category: String,
    val country: String
)
