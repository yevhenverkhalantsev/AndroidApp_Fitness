package com.example.fitness.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.databinding.FragmentLoginBinding
import com.example.fitness.domain.model.RemoteResource
import com.example.fitness.login.viewmodel.LoginViewModel
import com.example.fitness.registration.model.User
import com.example.fitness.screens.context.activity.MainActivity
import com.example.fitness.utils.UserManager
import com.example.fitness.utils.Utils
import dagger.android.support.DaggerFragment
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels(factoryProducer = { factory })
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginUserIfOnline()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        binding.loginButton.setOnClickListener {

            //TODO CHECK IS PHONE NUMBRE CORRECT! if yes = login) {
            if (isPhoneNumberCorrect()) {
                login()
            } else {
                binding.phoneNumber.error = getString(R.string.error_phone_number_is_incorrect)
            }
        }

        setObservers()
    }
    private fun setObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is RemoteResource.Loading -> {
                    if (it.inProcess) {
                        hideMainUI(View.GONE)
                        binding.animationView.visibility = View.VISIBLE
                    } else {
                        binding.animationView.visibility = View.GONE
                    }
                }

                is RemoteResource.Error -> {
                    handleException(it.throwable)
                }

                is RemoteResource.Success<*> -> {
                    if (it.element is User) {
                        saveCurrentLoginnedUser(it.element)
                        UserManager.userId = it.element.id
                    }

                    moveToNextFragment()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_unknown),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun hideMainUI(visibility: Int) {
        binding.loginButton.visibility = visibility
        binding.logo .visibility = visibility
        binding.phoneNumber.visibility = visibility
    }

    private fun saveCurrentLoginnedUser(user: User) {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        sharedPref.edit()
            .putInt(Utils.userIdKey, user.id)
            .apply()
    }
    private fun moveToNextFragment() {
        (requireActivity() as MainActivity).showBottomNavigationView()
        findNavController().navigate(R.id.action_loginFragment_to_planFragment)
    }

    private fun isPhoneNumberCorrect(): Boolean {
        val text = binding.phoneNumber.text.toString()
        if (text.length < 8 || text.length > 12) {
            binding.phoneNumber.error = getString(R.string.error_phone_number_is_incorrect)
            return false
        }
        binding.phoneNumber.error = null
        return true
    }

    private fun login() {
        viewModel.loginUser(binding.phoneNumber.text.toString())
    }

    private fun loginUserIfOnline() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val userId = sharedPref.getInt(Utils.userIdKey, -1)
        if (userId == -1) {
            (requireActivity() as MainActivity).hideBottomNavigationView()
            return
        }
        UserManager.userId = userId
        moveToNextFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleException(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                Toast.makeText(requireContext(), getString(R.string.error_phone_number_is_already_registered), Toast.LENGTH_LONG).show()
            }
            is IOException -> {
                Toast.makeText(requireContext(), getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(requireContext(), getString(R.string.error_unknown_try_again_later), Toast.LENGTH_LONG).show()
            }
        }
    }
}
