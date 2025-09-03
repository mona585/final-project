package mona.mohamed.recipeapp.view.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.databinding.FragmentLoginBinding
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.model.AuthRepositoryImp
import mona.mohamed.recipeapp.viewmodel.AuthViewModel
import mona.mohamed.recipeapp.viewmodel.AuthViewModelFactory

class LoginFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(AuthRepositoryImp(requireContext()))
    }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = "Email required"
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailEditText.error = "Invalid email"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Password required"
                }
                password.length < 6 -> {
                    binding.passwordEditText.error = "Password must be at least 6 characters"
                }
                else -> {
                    lifecycleScope.launch {
                        val result = viewModel.login(email, password)
                        if (result) {
                            val verified = viewModel.isVerified()
                            if (verified) {
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            } else {
                                findNavController().navigate(R.id.verificationFragment)
                            }
                        } else {
                            Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.goToRegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}