package com.example.movieapp.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetail(
   val id: Int,
   val genres: List<Genre>,
   @SerializedName("original_language")
   val language: String,
   val title: String,
   val overview: String?,
   val poster_path: String?,
   @SerializedName("runtime")
   val duration: Int,
)

data class Genre(
   val id: Int,
   val name: String
)

data class Language(
   val iso_639_1: String,
   val name: String,
   val english_name: String
)