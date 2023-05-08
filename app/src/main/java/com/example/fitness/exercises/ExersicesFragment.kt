package com.example.fitness.exercises

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.utils.Utils
import com.example.fitness.workouts.model.Category
import com.example.fitness.workouts.viewmodel.ExercisesViewModel
import com.squareup.moshi.internal.Util
import javax.inject.Inject

class ExersicesFragment : Fragment() {
    private lateinit var exercises: List<String>
    @Inject lateinit var viewModel: ExercisesViewModel

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exersices, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getExercises()
        showExercises()
        //TODO RecyclerView
        initRecyclerView()
    }

    private fun initRecyclerView() {

    }

    private fun showExerciseDescription(exerciseId: Int) {
        findNavController().navigate(R.id.action_exersicesFragment_to_descriptionExercisesFragment,
            Bundle().apply { putInt(Utils.exerciseKeyID, exerciseId) })
    }

    private fun showExercises() {
        //@TODO show in UI
    }

    private fun getExercises() {
        //@TODO
        exercises = when (viewModel.selectedCategory) {
            Category.BEACH_READY -> listOf("1", "2", "3")
            Category.LOSE_WEIGHT -> resources.getStringArray(R.array.loose_weight_exercises).toList()
            Category.GET_FIT -> listOf("7", "8", "9")
            Category.GET_STARTED -> listOf("10", "11", "12")
        }
        Log.i("test", "exercises: $exercises")

    }

}