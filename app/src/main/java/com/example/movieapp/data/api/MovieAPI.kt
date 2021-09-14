package com.example.movieapp.data.api

import com.example.movieapp.data.models.MovieDetail
import com.example.movieapp.data.models.MoviesResponse
import com.example.movieapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    companion object {
        const val DISCOVER_MOVIES = "/3/discover/movie"
        const val DETAIL_MOVIE = "3/movie/{id}"
    }

    @GET(DISCOVER_MOVIES)
    suspend fun getDiscoverMovies(
        @Query("api_key")
        apiKey : String = API_KEY,
        @Query("primary_release_date.lte")
        release_data : String = "2016-12-31",
        @Query("sort_by")
        sort_by : String = "release_date.desc",
        @Query("page")
        page : Int = 1,
    ):Response<MoviesResponse>


    @GET(DETAIL_MOVIE)
    suspend fun getDetailMovie(
        @Path("id")
        id: Int,
        @Query("api_key")
        apiKey : String = API_KEY,
    ): Response<MovieDetail>
}