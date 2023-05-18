package com.example.fitness.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.fitness.R
import com.example.fitness.description.model.ExerciseWithDescription
import com.example.fitness.screens.exercises.exercisePerforming.model.ExerciseInterval
import com.example.fitness.screens.exercises.exercisePerforming.model.PerformingExercise
import com.example.fitness.screens.exercises.exercises.model.Exercise
import com.example.fitness.screens.exercises.workouts.model.Category

class ExercisesManager {

    companion object {

        fun getExercises(context: Context, category: Category): List<Exercise> {
            return when (category) {
                Category.BEACH_READY -> listOf() //@TODO
                Category.LOSE_WEIGHT -> return getLoseWeightExercises(context)
                Category.GET_FIT -> listOf()
                Category.GET_STARTED -> listOf()
            }
        }

        private fun getLoseWeightExercises(context: Context): List<Exercise> {
            val exercisesNames = context.resources.getStringArray(R.array.loose_weight_exercises)
            return exercisesNames.mapIndexed { index, exerciseName ->
                Exercise(getImageDrawableLoseWeight(context, index), exerciseName)
            }
        }


        fun getExercisesWithDescription(
            context: Context,
            category: Category,
            id: Int
        ): ExerciseWithDescription {
            return when (category) {
                Category.BEACH_READY -> ExerciseWithDescription(
                    Exercise(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.burpees
                        )!!, ""
                    ), listOf()
                ) //@TODO
                Category.LOSE_WEIGHT -> return getLoseWeightWithDescription(context, id)
                Category.GET_FIT -> ExerciseWithDescription(
                    Exercise(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.burpees
                        )!!, ""
                    ), listOf()
                ) //@TODO
                Category.GET_STARTED -> ExerciseWithDescription(
                    Exercise(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.burpees
                        )!!, ""
                    ), listOf()
                ) //@TODO
            }
        }

        private fun getLoseWeightWithDescription(context: Context, id: Int): ExerciseWithDescription {
            val imageDrawable = getImageDrawableLoseWeight(context, id)
            val exerciseName = context.resources.getStringArray(R.array.loose_weight_exercises)[id]
            val descriptions = context.resources.getStringArray(R.array.description_of_loose_weight_exercises)[id].split(".")
            return ExerciseWithDescription(Exercise(imageDrawable, exerciseName), descriptions)
        }

        private fun getImageDrawableLoseWeight(context: Context, id: Int): Drawable {
            when (id) {
                0 -> return AppCompatResources.getDrawable(context, R.drawable.pushups)!!
                1 -> return AppCompatResources.getDrawable(context, R.drawable.situps)!!
                2 -> return AppCompatResources.getDrawable(context, R.drawable.lungs)!!
                3 -> return AppCompatResources.getDrawable(context, R.drawable.squats)!!
                4 -> return AppCompatResources.getDrawable(context, R.drawable.plank)!!
                5 -> return AppCompatResources.getDrawable(context, R.drawable.utfall)!!
                6 -> return AppCompatResources.getDrawable(context, R.drawable.jumpingjacks)!!
                7 -> return AppCompatResources.getDrawable(context, R.drawable.burpees)!!
                8 -> return AppCompatResources.getDrawable(context, R.drawable.mountainclimbers)!!
                9 -> return AppCompatResources.getDrawable(context, R.drawable.jumpsquats)!!
            }
            return AppCompatResources.getDrawable(context, R.color.white)!!
        }

        fun getPerformingExercises(context: Context, category: Category): List<PerformingExercise> {
            return when (category) {
                Category.BEACH_READY -> listOf() //@TODO
                Category.LOSE_WEIGHT -> return getLoseWeightPerformingExercises(context, category)
                Category.GET_FIT -> listOf()
                Category.GET_STARTED -> listOf()
            }
        }

        private fun getLoseWeightPerformingExercises(context: Context, category: Category): List<PerformingExercise> {
            val rawExercises = getExercises(context, category)
            val exercisesIntervalsList = getLoseWeightExercisesTimes()
            return rawExercises.mapIndexed { index, exercise ->
                PerformingExercise(exercise, exercisesIntervalsList[index])
            }
        }

        private fun getLoseWeightExercisesTimes(): List<ExerciseInterval> {
            return listOf(
                ExerciseInterval(30, 10),
                ExerciseInterval(45, 15),
                ExerciseInterval(30, 10),
                ExerciseInterval(30, 10),
                ExerciseInterval(30, 10),
                ExerciseInterval(30, 10),
                ExerciseInterval(30, 10),
                ExerciseInterval(30, 10),
                ExerciseInterval(30, 10),
                ExerciseInterval(60, 15),
            )
        }
    }
}