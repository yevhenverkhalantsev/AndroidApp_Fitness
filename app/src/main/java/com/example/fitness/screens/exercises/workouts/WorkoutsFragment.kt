package com.example.fitness.screens.exercises.workouts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.screens.context.application.MainApplication
import com.example.fitness.databinding.FragmentWorkoutsBinding
import com.example.fitness.screens.exercises.workouts.model.Category
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class WorkoutsFragment : DaggerFragment() {

    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ExercisesViewModel by activityViewModels( factoryProducer = { factory } )

    override fun onAttach(context: Context) {
        //(requireActivity().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWorkoutsBinding.bind(view)
        initListeners()
    }

    private fun initListeners() {

        binding.loseWeight.setOnClickListener {
            moveToNextFragment(Category.LOSE_WEIGHT)
        }
        binding.loseWeightIcon.setOnClickListener {
            moveToNextFragment(Category.LOSE_WEIGHT)
        }
        binding.beachReady.setOnClickListener {
            moveToNextFragment(Category.BEACH_READY)
        }
        binding.beachReadyIcon.setOnClickListener {
            moveToNextFragment(Category.BEACH_READY)
        }
        binding.getStarted.setOnClickListener {
            moveToNextFragment(Category.GET_STARTED)
        }
        binding.getStartedIcon.setOnClickListener {
            moveToNextFragment(Category.GET_STARTED)
        }
        binding.getFit.setOnClickListener {
            moveToNextFragment(Category.GET_FIT)
        }
        binding.getFitIcon.setOnClickListener {
            moveToNextFragment(Category.GET_FIT)
        }

    }

    private fun moveToNextFragment(category: Category) {
        viewModel.selectedCategory = category
        findNavController().navigate(R.id.action_workoutsFragment_to_exersicesFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}