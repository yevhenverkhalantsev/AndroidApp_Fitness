package com.example.fitness.utils

import android.content.Context
import com.example.fitness.R
import com.example.fitness.description.model.ExerciseWithDescription
import com.example.fitness.di.annotations.ApplicationContext
import com.example.fitness.screens.exercises.exercisePerforming.model.ExerciseInterval
import com.example.fitness.screens.exercises.exercisePerforming.model.PerformingExercise
import com.example.fitness.screens.exercises.someother.model.UserExerciseUI
import com.example.fitness.screens.exercises.someother.model.Exercise
import com.example.fitness.screens.exercises.workouts.model.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun getExercises(category: Category): List<Exercise> {
        return when (category) {
            Category.BEACH_READY -> getBeachReadyExercises()
            Category.LOSE_WEIGHT -> getLoseWeightExercises()
            Category.GET_FIT -> getGetFitExercises()
            Category.GET_STARTED -> getGetStartedExercises()
        }
    }

    private fun getExercisesNames(category: Category): List<String> {
        val resourceId =  when (category) {
            Category.BEACH_READY -> R.array.beach_ready_exercises
            Category.LOSE_WEIGHT -> R.array.loose_weight_exercises
            Category.GET_FIT -> R.array.get_fit_exercises
            Category.GET_STARTED -> R.array.get_started_exercises
        }
        return context.resources.getStringArray(resourceId).asList()
    }

    private fun getLoseWeightExercises(): List<Exercise> {
        val exercisesNames = context.resources.getStringArray(R.array.loose_weight_exercises)

        return exercisesNames.mapIndexed { index, exerciseName ->
            Exercise(getImageDrawableIdLoseWeight(index), exerciseName)
        }
    }

    private fun getBeachReadyExercises(): List<Exercise> {
        val exercisesNames = context.resources.getStringArray(R.array.beach_ready_exercises)
        val exercisesImagesIds = getDrawableIdsBeachReady()
        return exercisesNames.mapIndexed { index, exerciseName ->
            Exercise(exercisesImagesIds[index], exerciseName)
        }

    }
    private fun getGetFitExercises(): List<Exercise> {
        val exercisesNames = context.resources.getStringArray(R.array.get_fit_exercises)
        val exercisesImagesIds = getDrawableIdGetFit()
        return exercisesNames.mapIndexed { index, exerciseName ->
            Exercise(exercisesImagesIds[index], exerciseName)
        }
    }

    private fun getGetStartedExercises(): List<Exercise> {
        val exercisesNames = context.resources.getStringArray(R.array.get_started_exercises)
        val exercisesImagesIds = getDrawableIdsGetStarted()
        return exercisesNames.mapIndexed { index, exerciseName ->
            Exercise(exercisesImagesIds[index], exerciseName)
        }

    }

    fun getExercisesWithDescription(category: Category, id: Int): ExerciseWithDescription {
        return when (category) {
            Category.BEACH_READY -> getBeachReadyExerciseWithDescription(id)
            Category.LOSE_WEIGHT -> getLoseWeightExerciseWithDescription(id)
            Category.GET_FIT -> getGetFitExerciseWithDescription(id)
            Category.GET_STARTED -> getGetStartedExerciseWithDescription(id)
        }
    }

    private fun getBeachReadyExerciseWithDescription(id: Int): ExerciseWithDescription {
        val imageDrawable = getImageDrawableIdBeachReady(id)
        val exerciseName = context.resources.getStringArray(R.array.beach_ready_exercises)[id]
        val descriptions = context.resources.getStringArray(R.array.description_of_beach_ready_exercises)[id].split(".")
        return ExerciseWithDescription(Exercise(imageDrawable, exerciseName), descriptions)
    }

    private fun getImageDrawableIdBeachReady(id: Int): Int {
        val drawableIds = getDrawableIdsBeachReady()
        if (id in drawableIds.indices) {
            return drawableIds[id]
        }
        return R.color.white
    }

    private fun getGetStartedExerciseWithDescription(id: Int): ExerciseWithDescription {
        val imageDrawable = getImageDrawableIdGetStarted(id)
        val exerciseName = context.resources.getStringArray(R.array.get_started_exercises)[id]
        val descriptions = context.resources.getStringArray(R.array.description_of_get_started_exercises)[id].split(".")
        return ExerciseWithDescription(Exercise(imageDrawable, exerciseName), descriptions)
    }

    private fun getImageDrawableIdGetStarted(id: Int): Int {
        val drawableIds = getDrawableIdsGetStarted()
        if (id in drawableIds.indices) {
            return drawableIds[id]
        }
        return R.color.white
    }

    private fun getGetFitExerciseWithDescription(id: Int): ExerciseWithDescription {
        val imageDrawable = getImageDrawableIdGetFit(id)
        val exerciseName = context.resources.getStringArray(R.array.get_fit_exercises)[id]
        val descriptions = context.resources.getStringArray(R.array.description_of_get_fit_exercises)[id].split(".")
        return ExerciseWithDescription(Exercise(imageDrawable, exerciseName), descriptions)
    }

    private fun getLoseWeightExerciseWithDescription(id: Int): ExerciseWithDescription {
        val imageDrawable = getImageDrawableIdLoseWeight(id)
        val exerciseName = context.resources.getStringArray(R.array.loose_weight_exercises)[id]
        val descriptions = context.resources.getStringArray(R.array.description_of_loose_weight_exercises)[id].split(".")
        return ExerciseWithDescription(Exercise(imageDrawable, exerciseName), descriptions)
    }

    private fun getExerciseDescriptions(category: Category): List<String> {
        val resourceId =  when (category) {
            Category.BEACH_READY -> R.array.description_of_beach_ready_exercises
            Category.LOSE_WEIGHT -> R.array.description_of_loose_weight_exercises
            Category.GET_FIT -> R.array.description_of_get_fit_exercises
            Category.GET_STARTED -> R.array.description_of_get_started_exercises
        }
        return context.resources.getStringArray(resourceId).asList()
    }

    private fun getImageDrawableIdLoseWeight(id: Int): Int {
        val drawableIds = getDrawableIdsLoseWeight()
        if (id in drawableIds.indices) {
            return drawableIds[id]
        }
        return R.color.white
    }

    private fun getImageDrawableIdGetFit(id: Int): Int {
        val drawableIds = getDrawableIdGetFit()
        if (id in drawableIds.indices) {
            return drawableIds[id]
        }
        return R.color.white
    }

    fun getPerformingExercises(category: Category): List<PerformingExercise> {
        val rawExercises = getExercises(category)
        val exercisesIntervalsList = getExercisesIntervals(category)
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

    private fun getBeachReadyExercisesTimes(): List<ExerciseInterval> {
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
            ExerciseInterval(60, 15)
        )
    }

    private fun getGetStartedExercisesTimes(): List<ExerciseInterval> {
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
            ExerciseInterval(60, 15)
        )
    }

    private fun getFitExercisesTimes(): List<ExerciseInterval> {
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
            ExerciseInterval(60, 15)
        )
    }


    private fun getExercisesIntervals(category: Category): List<ExerciseInterval> {
        return when (category) {
            Category.BEACH_READY -> getBeachReadyExercisesTimes()
            Category.LOSE_WEIGHT -> getLoseWeightExercisesTimes()
            Category.GET_FIT -> getFitExercisesTimes()
            Category.GET_STARTED -> getGetStartedExercisesTimes()
        }
    }

    fun getDatabaseExercises(category: Category): List<UserExerciseUI> {
        val exercisesNames = getExercisesNames(category)
        val exercisesImagesId = getExercisesImagesId(category)
        val exercisesIntervalsList = getExercisesIntervals(category)
        val exercisesDescription = getExerciseDescriptions(category)
        return exercisesNames.mapIndexed { index, exercise ->
            UserExerciseUI(exercise, exercisesImagesId[index], exercisesDescription[index], exercisesIntervalsList[index])
        }
    }

    private fun getExercisesImagesId(category: Category): List<Int> {
        return when (category) {
            Category.BEACH_READY -> getDrawableIdsBeachReady()
            Category.LOSE_WEIGHT -> getDrawableIdsLoseWeight()
            Category.GET_FIT -> getDrawableIdGetFit()
            Category.GET_STARTED -> getDrawableIdsGetStarted()
        }
    }


    private fun getDrawableIdsGetStarted(): List<Int> {
        return listOf(
            R.drawable.gym_1,
            R.drawable.gym_2,
            R.drawable.gym_3,
            R.drawable.gym_4,
            R.drawable.gym_5,
            R.drawable.gym_6,
            R.drawable.gym_7,
            R.drawable.gym_8,
            R.drawable.gym_9,
            R.drawable.gym_10
        )
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

    private fun getDrawableIdsBeachReady(): List<Int> {
        return listOf(
            R.drawable.beach_ready_1,
            R.drawable.beach_ready_2,
            R.drawable.beach_ready_3,
            R.drawable.beach_ready_4,
            R.drawable.beach_ready_5,
            R.drawable.beach_ready_6,
            R.drawable.beach_ready_7,
            R.drawable.beach_ready_8,
            R.drawable.beach_ready_9,
            R.drawable.beach_ready_10
        )
    }
    
    private fun getDrawableIdGetFit(): List<Int> {
        return listOf(
            R.drawable.get_fit_1,
            R.drawable.get_fit_2,
            R.drawable.get_fit_3,
            R.drawable.get_fit_4,
            R.drawable.get_fit_5,
            R.drawable.get_fit_6,
            R.drawable.get_fit_7,
            R.drawable.get_fit_8,
            R.drawable.get_fit_9,
            R.drawable.get_fit_10
        )
    }
    

    fun getProgramIcon(selectedCategory: Category): Int {
        return when (selectedCategory) {
            Category.BEACH_READY -> R.drawable.beachready
            Category.LOSE_WEIGHT -> R.drawable.loseweight
            Category.GET_FIT -> R.drawable.getfit
            Category.GET_STARTED -> R.drawable.getstarted
        }
    }
}
