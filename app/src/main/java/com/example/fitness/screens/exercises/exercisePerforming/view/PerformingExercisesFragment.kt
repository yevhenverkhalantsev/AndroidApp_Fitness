package com.example.fitness.screens.exercises.exercisePerforming.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.screens.exercises.exercisePerforming.model.PerformingExercise
import com.example.fitness.screens.exercises.workouts.viewmodel.ExercisesViewModel
import com.example.fitness.utils.ExercisesManager
import javax.inject.Inject

class PerformingExercisesFragment : Fragment() {
    @Inject lateinit var viewModel: ExercisesViewModel
    private val performingExercises: List<PerformingExercise>
        by lazy { ExercisesManager.getPerformingExercises(requireContext(), viewModel.selectedCategory) }

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("PerformingExercisesFragment", "performingExercises: $performingExercises")

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_performing_exercises, container, false)
    }

}