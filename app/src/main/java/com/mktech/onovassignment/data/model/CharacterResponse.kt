package com.mktech.onovassignment.data.model


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("results")
    val results: List<Character> = listOf()
)

