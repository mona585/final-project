package mona.mohamed.recipeapp.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.databinding.FragmentVerificationBinding
import mona.mohamed.recipeapp.model.AuthRepositoryImp
import mona.mohamed.recipeapp.viewmodel.AuthViewModel
import mona.mohamed.recipeapp.viewmodel.AuthViewModelFactory
import kotlin.getValue
import mona.mohamed.recipeapp.navigateToRecipeActivity
class VerificationFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(AuthRepositoryImp(requireContext()))
    }
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheckVerified.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.isVerified()) {
                    Toast.makeText(requireContext(), "Verification successful. Welcome!", Toast.LENGTH_LONG).show()
                    viewModel.setStatus(true)
                    navigateToRecipeActivity()
                } else {
                    Toast.makeText(requireContext(), "You're not verified yet!", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnResendEmail.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.sendVerification()) {
                    Toast.makeText(requireContext(), "Email has been sent.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}