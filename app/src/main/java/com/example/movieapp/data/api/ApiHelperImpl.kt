package com.example.movieapp.data.api

import com.example.movieapp.data.models.MovieDetail
import com.example.movieapp.data.models.MoviesResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val movieAPI: MovieAPI
) : ApiHelper {
    override suspend fun getMovies(page: Int, sortBy: String): Response<MoviesResponse> = movieAPI.getDiscoverMovies(page = page,sort_by=sortBy)

    override suspend fun getMovieDetail(id: Int): Response<MovieDetail> = movieAPI.getDetailMovie(id)
}