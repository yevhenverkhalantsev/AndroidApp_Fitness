package com.example.fitness.retrofit

import com.example.fitness.data.remotesource.model.AppProgramType
import com.example.fitness.data.remotesource.model.UserExercise
import com.example.fitness.data.remotesource.model.UserProgram
import com.example.fitness.data.remotesource.model.UserProgramExercise
import com.example.fitness.data.remotesource.model.UserProgramSession
import com.example.fitness.registration.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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

    @GET("user_exercises")
    suspend fun getUserExercises(
        @Query("user_id") user_id: Int): List<UserExercise>

    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("user_program_exercises")
    suspend fun putUserProgramExercise(@Body userProgramExercise: UserProgramExercise): UserProgramExercise

    @GET("user_program_exercises")
    suspend fun getUserProgramExercises(
        @Query("user_program_id") user_program_id: Int): List<UserProgramExercise>


    @Headers(
        "Authorization: $API_KEY"
    )
    @POST("user_program_sessions")
    suspend fun putUserProgramSession(@Body userProgramSession: UserProgramSession): UserProgramSession

    @GET("user_program_sessions")
    suspend fun getUserProgramSession(
        @Query("user_program_id") user_program_id: Int): List<UserProgramSession>

//
//    @GET("albums/{id}")
//    suspend fun getAlbum(@Path("id") id: Int): ArtAlbum
//
//
//    @Headers (
//        "Authorization : $API_KEY"
//    )
//    @POST("app_program_type")
//    suspend fun putProgramType(@Body programType: ProgramType): ProgramType
//
//    @Headers (
//        "Authorization: $API_KEY"
//    )
//    @GET("app_program_type")
//    suspend fun getProgramType(): List<ProgramType>
//
//    @Headers (
//        "Authorization: $API_KEY"
//    )
//    @GET("user_programs")
//    suspend fun getUserProgram(): List<UserProgram>
//
//    @Headers (
//        "Authorization: $API_KEY"
//    )
//    @POST("user_programs")
//    suspend fun putUserProgram(@Body userProgram: UserProgram): UserProgram
//
//    @Headers(
//        "Authorization: $API_KEY"
//    )
//    @PUT("user_programs/{id}")
//    suspend fun updateUserProgram(@Body userProgram: UserProgram): UserProgram
//
//    @Headers(
//        "Authorization: $API_KEY"
//    )
//    @DELETE("user_programs/{id}")
//    suspend fun deleteUserProgram(@Body userProgram: UserProgram): UserProgram


    companion object {
        const val BASE_URL: String = "https://wfa-media.com/exercise23/v3/api.php/"
    }

}