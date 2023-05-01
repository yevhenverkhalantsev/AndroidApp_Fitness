package com.example.fitness.registration

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.registration.model.User
import com.example.fitness.registration.viewmodel.RegistrationViewModel
import javax.inject.Inject

class RegistrationFragment:  Fragment() {

    @Inject lateinit var viewModel: RegistrationViewModel

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //@TODO DELETE
        viewModel.registrateUser(User(
            phone = "+002",
            name = "nami",
            email = "ASDASD",
            birth_year = 1999
        ))
        viewModel.getUsers()

    }

}