package com.example.fitness.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.databinding.FragmentProfileBinding
import com.example.fitness.utils.UserManager
import com.example.fitness.utils.UserManager.Companion.logOut

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        binding.userId.text = getString(R.string.user_id_placeholder, UserManager.userId)

        binding.logOutImage.setOnClickListener {
            logOut()
            findNavController().navigate(R.id.action_profileFragment_to_registration)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}