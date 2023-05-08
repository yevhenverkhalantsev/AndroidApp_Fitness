package com.example.fitness.retrofit

import com.example.fitness.registration.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

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