package com.example.movieapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.movieapp.R
import com.example.movieapp.base.BaseFragment
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.data.models.MovieDetail
import com.example.movieapp.utils.Constants.Companion.BASE_URL_IMAGE
import com.example.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class DetailMovieFragment : BaseFragment<FragmentDetailBinding>() {


    val args: DetailMovieFragmentArgs by navArgs()

    val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var glideInstance: RequestManager



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clProgressBar.isVisible = true
        viewModel.movieId = args.movieId

        viewModel.getMovieDetail()

        subscribeObserver()

        binding.btnBook.setOnClickListener {
            findNavController().navigate(R.id.action_detailMovieFragment_to_bookFragment)
        }
    }



    private fun subscribeObserver(){
        viewModel.movie.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    binding.clProgressBar.isVisible = false
                    response.data?.apply {
                        setupUI(this)
                    }
                }
                is Resource.Loading -> {
                    binding.clProgressBar.isVisible = true
                }
                is Resource.Error -> {
                    Log.e("home_fragment","loading error")
                }
            }

        })

    }


    private fun setupUI(movie: MovieDetail) {
        with(binding){
            tvTitleMovie.text = movie.title
            tvOverview.text = movie.overview
            tvLangageMovie.text = if (movie.language=="xx") "N/A" else Locale(movie.language).getDisplayLanguage()
            tvDuration.text = (movie.duration / 60).toString() + "h "+(movie.duration % 60).toString()+"m"

            //Glide.with(requireContext()).load(BASE_URL_IMAGE+movie.poster_path).into(ivPosterMovie)
            glideInstance.load(BASE_URL_IMAGE+movie.poster_path).into(ivPosterMovie)
            Log.d("detail_movie_fragment","image = "+BASE_URL_IMAGE+movie.poster_path)
            val container = linearLayoutCategory
            container.removeAllViews()
            if (movie.genres.isNotEmpty()){
                movie.genres.forEach { genre ->
                    addGenreToLayout(genre.name)
                }
            } else {
                addGenreToLayout("Category not known")
            }


        }
    }

    private fun addGenreToLayout(genre: String){
        val view = layoutInflater.inflate(R.layout.item_category,null) as TextView
        view.text = genre
        binding.linearLayoutCategory!!.addView(view)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater,container,false)
}