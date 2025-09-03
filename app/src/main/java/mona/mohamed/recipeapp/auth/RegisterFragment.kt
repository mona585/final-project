package mona.mohamed.recipeapp.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mona.mohamed.recipeapp.databinding.FragmentRegisterBinding
import mona.mohamed.recipeapp.R

class RegisterFragment : Fragment() {

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
                    binding.progressBar.visibility = View.VISIBLE

                    // ⚡ مكان الـ Firebase أو API request
                    binding.progressBar.postDelayed({
                        // Hide loading
                        binding.progressBar.visibility = View.GONE

                        // Save state locally (for now)
                        requireContext().getSharedPreferences("prefs", android.content.Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean("isLoggedIn", true)
                            .apply()

                        Toast.makeText(requireContext(), "Verification successful. Please login.", Toast.LENGTH_LONG).show()

                        // Navigate to login screen
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }, 2000) // simulate delay 2 sec
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
