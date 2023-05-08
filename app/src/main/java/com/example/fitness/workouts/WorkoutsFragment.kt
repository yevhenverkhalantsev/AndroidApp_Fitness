package com.example.fitness.workouts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.databinding.FragmentRegistrationBinding
import com.example.fitness.databinding.FragmentWorkoutsBinding
import com.example.fitness.workouts.model.Category
import com.example.fitness.workouts.viewmodel.ExercisesViewModel
import javax.inject.Inject

class WorkoutsFragment : Fragment() {

    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var viewModel: ExercisesViewModel

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as MainApplication).appComponent.inject(this)
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


        viewModel = ExercisesViewModel()
        initListeners()
    }

    private fun initListeners() {
        binding.beachReady.setOnClickListener {
            moveToNextFragment(Category.BEACH_READY)
        }
        binding.beachReadyIcon.setOnClickListener {
            moveToNextFragment(Category.BEACH_READY)
        }

        binding.loseWeight.setOnClickListener {
            moveToNextFragment(Category.LOSE_WEIGHT)
        }
        binding.loseWeightIcon.setOnClickListener {
            moveToNextFragment(Category.LOSE_WEIGHT)
        }

        //@TODO
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