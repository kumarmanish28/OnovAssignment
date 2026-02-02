package com.mktech.onovassignment.data.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("species")
    val species: String = "",
    @SerializedName("status")
    val status: String = "",
)