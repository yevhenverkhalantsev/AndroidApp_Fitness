package com.example.fitness.registration

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.context.activity.MainActivity
import com.example.fitness.context.application.MainApplication
import com.example.fitness.databinding.FragmentRegistrationBinding
import com.example.fitness.registration.model.Resource
import com.example.fitness.registration.model.User
import com.example.fitness.registration.viewmodel.OnExceptionHandler
import com.example.fitness.registration.viewmodel.RegistrationViewModel
import com.example.fitness.utils.Utils
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegistrationFragment:  Fragment(), OnExceptionHandler {

    @Inject lateinit var viewModel: RegistrationViewModel

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginUserIfOnline()
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegistrationBinding.bind(view)

        initListeners()
        setObservers()
    }

    private fun loginUserIfOnline() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val userId = sharedPref.getInt(Utils.userIdKey, 0)
        if (userId == 0) {
            (requireActivity() as MainActivity).hideBottomNavigationView()
            return
        }
        moveToNextFragment()
    }

    private fun registrateUser() {
        viewModel.registrateUser(User(
            phone = binding.phoneNumber.text.toString()
        ))
    }

    private fun initListeners() {
        binding.phoneNumber.doOnTextChanged { _, _, _, _ ->
            isPhoneNumberCorrect()
        }
        binding.phoneNumber.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                isPhoneNumberCorrect()
            }
        }


        binding.registerButton.setOnClickListener {
            if (isPhoneNumberCorrect()) registrateUser()
        }
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

    private fun setObservers() {
        viewModel.process.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    if (it.inProcess) {
                        hideMainUI(View.GONE)
                        binding.animationView.visibility = View.VISIBLE
                    }
                    else {
                        binding.animationView.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    handleException(it.throwable)
                }
                is Resource.Success<*> -> {
                    if (it.element is User) {
                        saveCurrentLoginnedUser(it.element)
                    }
                    //TODO Show Success animation and went to second fragment
                    moveToNextFragment()
                }
            }
        }
    }

    private fun moveToNextFragment() {
        (requireActivity() as MainActivity).showBottomNavigationView()
        findNavController().navigate(R.id.action_registration_to_planFragment)
    }

    private fun saveCurrentLoginnedUser(user: User) {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        sharedPref.edit()
            .putInt(Utils.userIdKey, user.id)
            .apply()
    }

    private fun hideMainUI(visibility: Int) {
        binding.registerButton.visibility = visibility
        binding.logo .visibility = visibility
        binding.phoneNumber.visibility = visibility
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun handleException(throwable: Throwable) {
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