package com.example.fitness.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

//    @GET("photos")
//    suspend fun getPhotos(): List<ArtPhoto>
//
//    @GET("albums/{id}")
//    suspend fun getAlbum(@Path("id") id: Int): ArtAlbum
//
//    @GET("users/{id}")
//    suspend fun getArtist(@Path("id") id: Int): ArtArtist

    companion object {
        const val BASE_URL: String = "https://wfa-media.com/exercise23/v3/"
    }

}