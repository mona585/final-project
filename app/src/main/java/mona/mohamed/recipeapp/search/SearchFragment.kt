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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mona.mohamed.recipeapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel

    private var searchJob: Job? = null
    private val searchScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val factory = SearchViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        setupRecyclerView()
        setupFilterSpinner()
        setupSearchInput()
        observeViewModel()
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
        val filterOptions = listOf("Name", "Category", "Country")
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
                val query = binding.searchEditText.text.toString()
                viewModel.filterMeals(query, filterOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener { editable ->
            val query = editable.toString()
            val filterType = binding.filterSpinner.selectedItem.toString()

            // Cancel previous search job
            searchJob?.cancel()

            if (query.isEmpty()) {
                viewModel.filterMeals("", filterType)
            } else {
                // Debounce search - wait 500ms before searching
                searchJob = searchScope.launch {
                    delay(500)
                    if (query.length >= 2) {
                        viewModel.searchMeals(query)
                    } else {
                        viewModel.filterMeals(query, filterType)
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.submitList(meals)

            // Show empty state if needed
            if (meals.isEmpty()) {
                binding.emptyStateText?.visibility = View.VISIBLE
                binding.searchRecyclerView.visibility = View.GONE
            } else {
                binding.emptyStateText?.visibility = View.GONE
                binding.searchRecyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
        _binding = null
    }
}