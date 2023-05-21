package com.example.fitness.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.fitness.R
import com.example.fitness.description.model.ExerciseWithDescription
import com.example.fitness.screens.context.injection.annotations.ApplicationContext
import com.example.fitness.screens.exercises.exercisePerforming.model.ExerciseInterval
import com.example.fitness.screens.exercises.exercisePerforming.model.PerformingExercise
import com.example.fitness.screens.exercises.exercises.model.DatabaseExercise
import com.example.fitness.screens.exercises.exercises.model.Exercise
import com.example.fitness.screens.exercises.workouts.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun getExercises(category: Category): List<Exercise> {
        return when (category) {
            Category.BEACH_READY -> listOf() //@TODO
            Category.LOSE_WEIGHT -> getLoseWeightExercises()
            Category.GET_FIT -> listOf()
            Category.GET_STARTED -> listOf()
        }
    }

    private fun getExercisesNames(category: Category): List<String> {
        val resourceId =  when (category) {
            Category.BEACH_READY -> 0 //@TODO
            Category.LOSE_WEIGHT -> R.array.loose_weight_exercises
            Category.GET_FIT -> 0
            Category.GET_STARTED -> 0
        }
        return context.resources.getStringArray(resourceId).asList()
    }

    private fun getLoseWeightExercises(): List<Exercise> {
        val exercisesNames = context.resources.getStringArray(R.array.loose_weight_exercises)
        return exercisesNames.mapIndexed { index, exerciseName ->
            Exercise(getImageDrawableLoseWeight(index), exerciseName)
        }
    }

    fun getExercisesWithDescription(category: Category, id: Int): ExerciseWithDescription {
        return when (category) {
            Category.BEACH_READY -> ExerciseWithDescription(
                Exercise(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.burpees
                    )!!, ""
                ), listOf()
            ) //@TODO
            Category.LOSE_WEIGHT -> return getLoseWeightWithDescription(id)
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

    private fun getLoseWeightWithDescription(id: Int): ExerciseWithDescription {
        val imageDrawable = getImageDrawableLoseWeight(id)
        val exerciseName = context.resources.getStringArray(R.array.loose_weight_exercises)[id]
        val descriptions = context.resources.getStringArray(R.array.description_of_loose_weight_exercises)[id].split(".")
        return ExerciseWithDescription(Exercise(imageDrawable, exerciseName), descriptions)
    }

    private fun getExerciseDescriptions(category: Category): List<String> {
        val resourceId =  when (category) {
            Category.BEACH_READY -> 0 //@TODO
            Category.LOSE_WEIGHT -> R.array.description_of_loose_weight_exercises
            Category.GET_FIT -> 0
            Category.GET_STARTED -> 0
        }
        return context.resources.getStringArray(resourceId).asList()
    }

    private fun getImageDrawableLoseWeight(id: Int): Drawable {
        val drawableIds = getDrawableIdsLoseWeight()
        if (id in drawableIds.indices) {
            return AppCompatResources.getDrawable(context, drawableIds[id])!!
        }
        return AppCompatResources.getDrawable(context, R.color.white)!!
    }

    fun getPerformingExercises(category: Category): List<PerformingExercise> {
        return when (category) {
            Category.BEACH_READY -> listOf() //@TODO
            Category.LOSE_WEIGHT -> getLoseWeightPerformingExercises(category)
            Category.GET_FIT -> listOf()
            Category.GET_STARTED -> listOf()
        }
    }

    private fun getLoseWeightPerformingExercises(category: Category): List<PerformingExercise> {
        val rawExercises = getExercises(category)
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

    private fun getExercisesIntervals(category: Category): List<ExerciseInterval> {
        return when (category) {
            Category.BEACH_READY -> listOf()//@TODO
            Category.LOSE_WEIGHT -> getLoseWeightExercisesTimes()
            Category.GET_FIT -> listOf()
            Category.GET_STARTED -> listOf()
        }
    }

    fun getDatabaseExercises(category: Category): List<DatabaseExercise> {
        val exercisesNames = getExercisesNames(category)
        val exercisesImagesId = getExercisesImagesId(category)
        val exercisesIntervalsList = getExercisesIntervals(category)
        val exercisesDescription = getExerciseDescriptions(category)
        return exercisesNames.mapIndexed { index, exercise ->
            DatabaseExercise(exercise, exercisesImagesId[index], exercisesDescription[index], exercisesIntervalsList[index])
        }
    }

    private fun getExercisesImagesId(category: Category): List<Int> {
        return when (category) {
            Category.BEACH_READY -> listOf() //@TODO
            Category.LOSE_WEIGHT -> getDrawableIdsLoseWeight()
            Category.GET_FIT -> listOf()
            Category.GET_STARTED -> listOf()
        }
    }

    private fun getDrawableIdsLoseWeight(): List<Int> {
        return listOf(
            R.drawable.pushups,
            R.drawable.situps,
            R.drawable.lungs,
            R.drawable.squats,
            R.drawable.plank,
            R.drawable.utfall,
            R.drawable.jumpingjacks,
            R.drawable.burpees,
            R.drawable.mountainclimbers,
            R.drawable.jumpsquats
        )
    }
}
