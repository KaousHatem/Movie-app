package com.example.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapp.R
import com.example.movieapp.ui.adapters.MovieAdapter
import com.example.movieapp.base.BaseFragment
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.movieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    @Inject
    lateinit var movieAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@HomeFragment.scrollListener)
        }

        movieAdapter.setItemClickListener { movie ->
            val directions = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(movie.id)
            findNavController().navigate(directions)
        }

        binding.srlMovies.setOnRefreshListener(swipeRefreshListener)


        viewModel.movies.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    if (binding.srlMovies != null && binding.srlMovies.isRefreshing){
                        binding.srlMovies.isRefreshing = false
                    }
                    response.data?.let {
                        movieAdapter.differ.submitList(it.results.toList())

                    }
                }
                is Resource.Loading -> {
                    Log.e("home_fragment","loading error")
                }
            }
        })
    }


    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val swipeRefreshListener = object : SwipeRefreshLayout.OnRefreshListener{
        override fun onRefresh() {
            if (binding.srlMovies != null){
                binding.srlMovies.isRefreshing = true
            }
            viewModel.refresh()
            viewModel.getMovies()


        }

    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getMovies()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_bar, menu)

    }

        override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.actionSort -> {
            findNavController().navigate(R.id.action_homeFragment_to_sortDialogFragment)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.movies.removeObservers(viewLifecycleOwner)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater,container, false)
}