package mona.mohamed.recipeapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import mona.mohamed.recipeapp.databinding.FragmentHomeBinding
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.data.models.Meal
import mona.mohamed.recipeapp.model.AuthRepositoryImp
import mona.mohamed.recipeapp.viewmodel.AuthViewModel
import mona.mohamed.recipeapp.viewmodel.AuthViewModelFactory
// HomeFragment.kt
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setupRecyclerView()
        observeData()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        mealAdapter = MealAdapter { meal ->
            navigateToMealDetail(meal)
        }
        binding.rvMeals.adapter = mealAdapter
    }

    private fun observeData() {
        viewModel.suggestedMeal.observe(viewLifecycleOwner) { meal ->
            meal?.let { displaySuggestedMeal(it) }
        }

        viewModel.categoryMeals.observe(viewLifecycleOwner) { meals ->
            mealAdapter.submitList(meals)
        }
    }

    private fun displaySuggestedMeal(meal: Meal) {
        binding.apply {
            tvSuggestedMealName.text = meal.strMeal
            tvSuggestedMealDesc.text = meal.strInstructions?.take(100) + "..."

            Glide.with(requireContext())
                .load(meal.strMealThumb)
                .placeholder(R.drawable.placeholder)
                .into(ivSuggestedMeal)
        }
    }

    private fun setupClickListeners() {
        binding.cardSuggestedMeal.setOnClickListener {
            viewModel.suggestedMeal.value?.let { meal ->
                navigateToMealDetail(meal)
            }
        }
        binding.btnCookNow.setOnClickListener {
            viewModel.suggestedMeal.value?.let { meal ->
                navigateToMealDetail(meal)
            }
        }
    }

    private fun navigateToMealDetail(meal: Meal) {
        val action = HomeFragmentDirections.actionHomeFragmentToMealDetailFragment(meal.idMeal)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}