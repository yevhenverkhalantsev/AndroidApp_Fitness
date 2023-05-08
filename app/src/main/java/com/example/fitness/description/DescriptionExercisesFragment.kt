package com.example.fitness.description

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitness.R
import com.example.fitness.context.application.MainApplication
import com.example.fitness.utils.Utils
import com.example.fitness.workouts.model.Category
import com.example.fitness.workouts.viewmodel.ExercisesViewModel
import javax.inject.Inject

class DescriptionExercisesFragment : Fragment() {
    private lateinit var exerciseDescription: String
    private lateinit var exerciseName: String
    @Inject lateinit var viewModel: ExercisesViewModel

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as MainApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setExerciseInfo()
        showExerciseInfo()
    }

    private fun showExerciseInfo() {
        //@TODO recycler view in the bottom
    }

    private fun setExerciseInfo() {
        val id = requireArguments().getInt(Utils.exerciseKeyID)
        var descriptionResource = 0
        var exerciseNameResource = 0
        when (viewModel.selectedCategory) {
            Category.LOSE_WEIGHT -> {
                exerciseNameResource = R.array.loose_weight_exercises
                descriptionResource = R.array.description_of_loose_weight_exercises
            }
            Category.GET_FIT -> descriptionResource =  1 //R.array.description_of_get_fit_exercises
            //@TODO
            else -> {}
        }
        resources.getStringArray(exerciseNameResource)[id].let {
            exerciseName = it
        }
        resources.getStringArray(descriptionResource)[id].let {
            exerciseDescription = it
        }
    }

    private fun setLoseWeightExerciseInfo(id: Int) {
        TODO("Not yet implemented")
    }

}