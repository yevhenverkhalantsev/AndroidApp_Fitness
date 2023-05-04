package com.example.fitness.retrofit

import com.example.fitness.registration.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

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

    @Headers (
        "Authorization : $API_KEY"
    )
    @POST("app_program_type")
    suspend fun putProgramType(@Body programType: ProgramType): ProgramType

    @Headers (
        "Authorization: $API_KEY"
            )
    @GET("app_program_type")
    suspend fun getProgramType(): List<ProgramType>

    companion object {
        const val BASE_URL: String = "https://wfa-media.com/exercise23/v3/api.php/"
    }

}