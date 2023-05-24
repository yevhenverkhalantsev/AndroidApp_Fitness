package com.example.fitness.utils.Extensions.UiToDatabase

import com.example.fitness.data.remotesource.model.UserExercise
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.domain.model.UserProgramUI
import com.example.fitness.screens.exercises.exercisePerforming.model.ExerciseInterval
import com.example.fitness.screens.exercises.someother.model.UserExerciseUI

fun UserProgramUI.toUserProgram(userId: Int, appProgramTypeId: Int): UserProgram {
    return UserProgram(
        id = 0,
        user_id = userId,
        app_program_type_id = appProgramTypeId,
        name = name,
        use_timing = 0,
        icon = icon.toString()
    )

}

fun UserExerciseUI.toUserExercise(user_id : Int) : UserExercise {
    return UserExercise(
        id = 0,
        user_id = user_id,
        name = name,
        description = description,
        icon = imageId.toString(),
        photo_url = timeInteval.toStringTime()
    )
}
