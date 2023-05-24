package com.example.fitness.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fitness.databinding.FragmentLoginBinding
import com.example.fitness.login.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by activityViewModels(factoryProducer = { factory } )
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        // Set up click listener for the login button
        binding.loginButton.setOnClickListener {
            login()
        }

        // Observe the login result
        viewModel.loginResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Login successful, perform necessary actions
            } else {
                // Login failed, show error message
            }
        }
    }

    private fun login() {
        // Perform login logic without password

        // Simulating login success
        viewModel.loginSuccess()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
