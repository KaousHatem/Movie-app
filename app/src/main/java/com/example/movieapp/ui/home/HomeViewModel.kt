package com.example.movieapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.models.MoviesResponse
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Constants.Companion.ALPHABET
import com.example.movieapp.utils.Constants.Companion.ASCENDING
import com.example.movieapp.utils.Constants.Companion.DATE_RELEASE
import com.example.movieapp.utils.Constants.Companion.DESCENDING
import com.example.movieapp.utils.Constants.Companion.RATING
import com.example.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel(){

    private val _movies = MutableLiveData<Resource<MoviesResponse>>()
    val movies: LiveData<Resource<MoviesResponse>>
        get() = _movies

    var moviesPage = 1
    var moviesResponse: MoviesResponse? = null


    private val _sortWay = MutableLiveData<String>(ASCENDING)
    val sortWay: LiveData<String>
        get() = _sortWay

    private val _sortType = MutableLiveData<String>(DATE_RELEASE)
    val sortType: LiveData<String>
        get() = _sortType



    init {



        getMovies()
    }

    fun refresh(){
        moviesPage = 1
        moviesResponse = null
    }

    fun getMovies() = viewModelScope.launch {
        safeDiscoverMoviesCall()

        /*moviesRepository.getDiscoverMovies().collect { values ->
            Log.d("view_model",values.data.toString())
            _movies.value = values
        }*/
    }




    private fun handleMoviesResponse(response: Response<MoviesResponse>): Resource<MoviesResponse> {

        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                moviesPage++
                if (moviesResponse == null){
                    moviesResponse = resultResponse
                } else {
                    val oldMovies = moviesResponse?.results
                    val newMovies = resultResponse.results
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(moviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

   private suspend fun safeDiscoverMoviesCall() {
        _movies.postValue(Resource.Loading())
        try{
            val response = moviesRepository.getDiscoverMovies(moviesPage, sortBy=getSortSetting())
            _movies.postValue(handleMoviesResponse(response))
        } catch (t: Throwable) {
            when(t){
                is IOException -> _movies.postValue(Resource.Error("Newtork Failure"))
                else -> _movies.postValue(Resource.Error("Conversion Failure"))
            }
        }
    }

    private fun getSortSetting(): String{
        return _sortType.value+"."+_sortWay.value
    }

    fun setSortUp(){
        _sortWay.value = ASCENDING
    }
    fun setSortDown(){
        Log.d("home_view_model","sort down")
        _sortWay.value = DESCENDING
    }
    fun setSortByAlphabet(){
        _sortType.value = ALPHABET
    }
    fun setSortByRate(){
        _sortType.value = RATING
    }
    fun setSortByReleaseDate(){
        _sortType.value = DATE_RELEASE
    }

}