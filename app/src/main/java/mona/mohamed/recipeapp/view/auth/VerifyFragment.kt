package mona.mohamed.recipeapp.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mona.mohamed.recipeapp.R

class VerifyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resendButton = view.findViewById<Button>(R.id.resendButton)
        val doneButton = view.findViewById<Button>(R.id.doneButton)

        resendButton.setOnClickListener {
            Toast.makeText(requireContext(), "Verification link resent", Toast.LENGTH_SHORT).show()
        }

        doneButton.setOnClickListener {
            findNavController().navigate(R.id.action_verifyFragment_to_loginFragment)
        }
    }
}
