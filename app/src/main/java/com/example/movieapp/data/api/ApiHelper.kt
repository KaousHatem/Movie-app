package com.example.movieapp.data.api

import com.example.movieapp.data.models.MovieDetail
import com.example.movieapp.data.models.MoviesResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getMovies(page: Int, sortBy: String): Response<MoviesResponse>

    suspend fun getMovieDetail(id: Int): Response<MovieDetail>
}