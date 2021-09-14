package com.example.movieapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.models.MovieDetail
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private val _movie = MutableLiveData<Resource<MovieDetail>>()
    val movie: LiveData<Resource<MovieDetail>>
        get() = _movie

    private var movieDetailResponse: MovieDetail? = null
    var movieId: Int? = null


    fun getMovieDetail() = viewModelScope.launch {
        if (movieId == null) Log.d("detail_view_model","it is null")
        safeMovieDetailCall(movieId!!)
    }


    private fun handleMovieResponse(response: Response<MovieDetail>): Resource<MovieDetail> {

        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                movieDetailResponse = resultResponse
                return Resource.Success(movieDetailResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun  safeMovieDetailCall(id: Int){
        _movie.postValue(Resource.Loading())
        try {
            val response = moviesRepository.getMovieDetail(id)
            _movie.postValue(handleMovieResponse(response))
        } catch (t: Throwable) {
            when(t){
                is IOException -> _movie.postValue(Resource.Error("Newtork Failure"))
                else -> _movie.postValue(Resource.Error("Conversion Failure"))
            }
        }
    }

}










