package mona.mohamed.recipeapp.view.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.RecipeApplication
import mona.mohamed.recipeapp.databinding.FragmentAboutBinding
import mona.mohamed.recipeapp.model.AuthRepository
import mona.mohamed.recipeapp.model.AuthRepositoryImp // Import your implementation

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AboutViewModel

    // Initialize AuthRepository - you might want to inject this via Dagger/Hilt
    private val authRepository: AuthRepository by lazy {
        (requireActivity().application as RecipeApplication).authRepository
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel with Factory
        val factory = AboutViewModelFactory(requireContext(), authRepository)
        viewModel = ViewModelProvider(this, factory).get(AboutViewModel::class.java)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Click listeners
        binding.layoutAbout.setOnClickListener {
            showAboutDialog()
        }

        binding.layoutPrivacy.setOnClickListener {
            openPrivacyPolicy()
        }

        binding.layoutRate.setOnClickListener {
            rateApp()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun observeViewModel() {
        // Observe user name
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvUserName.text = name
        }

        // Observe user email
        viewModel.userEmail.observe(viewLifecycleOwner) { email ->
            binding.tvUserEmail.text = email
        }
    }

    private fun showAboutDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_about, null)

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun openPrivacyPolicy() {
        val url = "https://yourwebsite.com/privacy-policy"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun rateApp() {
        try {
            val uri = Uri.parse("market://details?id=${requireContext().packageName}")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e: Exception) {
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=${requireContext().packageName}")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        // Clear the login status - THIS IS THE KEY!
        authRepository.setLoggedIn(false)

        // Sign out from Firebase
        authRepository.signOut()

        // Clear local data
        viewModel.clearAllData()

        // Navigate to login fragment
        findNavController().navigate(
            R.id.action_aboutFragment_to_loginFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.nav_recipe_graph, true)
                .build()
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}