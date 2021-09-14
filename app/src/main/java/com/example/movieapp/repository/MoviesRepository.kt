package com.example.movieapp.repository

import com.example.movieapp.data.api.ApiHelper
import com.example.movieapp.data.models.MovieDetail
import com.example.movieapp.data.models.MoviesResponse
import retrofit2.Response
import javax.inject.Inject


class MoviesRepository @Inject constructor (
    private val apiHelper: ApiHelper
){
    /*suspend fun getDiscoverMovies(): Flow<Resource<MoviesResponse>> {
        return flow<Resource<MoviesResponse>> {
            emit( safeApiCall { service.getDiscoverMovies() })
        }.flowOn(Dispatchers.IO)
    }*/
    suspend fun getDiscoverMovies(page:Int, sortBy: String): Response<MoviesResponse> = apiHelper.getMovies(page, sortBy)

    suspend fun getMovieDetail(id: Int): Response<MovieDetail> = apiHelper.getMovieDetail(id)

    //suspend fun upsert(movie: Movie) = db.getMovieDao().upsert(movie)

    //suspend fun deleteMovie(movie: Movie) = db.getMovieDao().deleteMovie(movie)
}