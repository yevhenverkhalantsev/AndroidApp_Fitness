package com.example.fitness.retrofit

import com.example.fitness.data.remotesource.model.AppProgramType
import com.example.fitness.data.remotesource.model.UserExercise
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramExercise
import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.screens.registration.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "004E5EEA-E02"
interface ApiService {



    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("users")
    suspend fun putUser(@Body user: User): User

    @Headers(
        "Authorization: $API_KEY"
    )
    @GET("users")
    suspend fun getUsers(): List<User>

    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("app_program_types")
    suspend fun putAppProgramType(@Body appProgramType: AppProgramType): AppProgramType

    @Headers(
        "Authorization: $API_KEY"
    )
    @GET("app_program_types")
    suspend fun getAllAppProgramTypes(): List<AppProgramType>

    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("user_programs")
    suspend fun putUserProgram(@Body userProgram: UserProgram): UserProgram

    @Headers(
        "Authorization: $API_KEY"
    )
    @GET("user_programs")
    suspend fun getUserProgram(
        @Query("user_id") user_id: Int): List<UserProgram>

    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("user_exercises")
    suspend fun putUserExercise(@Body userExercise: UserExercise): UserExercise

    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("user_program_exercises")
    suspend fun putUserProgramExercise(@Body userProgramExercise: UserProgramExercise): UserProgramExercise

    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("user_program_sessions")
    suspend fun putUserProgramSession(@Body userProgramSession: UserProgramSession): UserProgramSession

    @Headers(
        "Authorization: $API_KEY"
    )
    @GET("user_program_sessions")
    suspend fun getUserProgramSession(
        @Query("user_program_id") user_program_id: Int): List<UserProgramSession>

    @Headers(
        "Authorization: $API_KEY"
    )
    @DELETE("user_program_sessions/{id}")
    suspend fun deleteUserProgramSession(
        @Path("id") id: Int): Response<ResponseBody>

    @Headers(
        "Authorization: $API_KEY"
    )
    @DELETE("user_programs/{id}")
    suspend fun deleteUserProgram(
        @Path("id") id: Int): Response<ResponseBody>

    @Headers(
        "Authorization: $API_KEY"
    )
    @PUT("user_program_sessions/")
    suspend fun updateUserProgramSession(
        @Body userProgramSession: UserProgramSession): UserProgramSession

    companion object {
        const val BASE_URL: String = "https://wfa-media.com/exercise23/v3/api.php/"
    }

}