package com.mktech.onovassignment.data.api

import com.mktech.onovassignment.data.model.Character
import com.mktech.onovassignment.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    suspend fun getCharacters(): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): Character

}