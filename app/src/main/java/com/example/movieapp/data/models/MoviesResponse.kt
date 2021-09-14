package com.example.movieapp.data.models

data class MoviesResponse (
    val page: Int?,
    val results: MutableList<Movie>,
    val totalResult: Int,
    val totalPage: Int
    )