package com.example.fitness.retrofit

import com.example.fitness.registration.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
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

    companion object {
        const val BASE_URL: String = "https://wfa-media.com/exercise23/v3/api.php/"
    }

}