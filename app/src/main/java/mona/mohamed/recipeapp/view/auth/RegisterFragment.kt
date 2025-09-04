package mona.mohamed.recipeapp.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import mona.mohamed.recipeapp.databinding.FragmentRegisterBinding
import mona.mohamed.recipeapp.R
import mona.mohamed.recipeapp.model.AuthRepositoryImp
import mona.mohamed.recipeapp.viewmodel.AuthViewModel
import mona.mohamed.recipeapp.viewmodel.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(AuthRepositoryImp(requireContext()))
    }
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString().trim()
            val lastName = binding.lastNameEditText.text.toString().trim()
            val email = binding.emailRegisterEditText.text.toString().trim()
            val password = binding.passwordRegisterEditText.text.toString().trim()

            when {
                firstName.isEmpty() -> {
                    binding.firstNameEditText.error = "First name required"
                }
                lastName.isEmpty() -> {
                    binding.lastNameEditText.error = "Last name required"
                }
                email.isEmpty() -> {
                    binding.emailRegisterEditText.error = "Email required"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.emailRegisterEditText.error = "Invalid email"
                }
                password.isEmpty() -> {
                    binding.passwordRegisterEditText.error = "Password required"
                }
                password.length < 6 -> {
                    binding.passwordRegisterEditText.error = "Password must be at least 6 characters"
                }
                else -> {
                    lifecycleScope.launch {
                        val fullName = "$firstName $lastName"
                        if (viewModel.register(email, password)) {
                            viewModel.setUserName(fullName)
                            if (viewModel.sendVerification()) {
                                findNavController().navigate(R.id.action_registerFragment_to_verificationFragment)
                            } else {
                                Toast.makeText(requireContext(), "Something wrong with the email you entered, please try again.", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}