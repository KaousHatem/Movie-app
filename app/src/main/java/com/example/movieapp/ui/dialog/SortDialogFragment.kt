package com.example.movieapp.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.movieapp.base.BaseFragment
import com.example.movieapp.databinding.BottomSheetLayoutBinding
import com.example.movieapp.ui.home.HomeFragment
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.utils.Constants.Companion.ALPHABET
import com.example.movieapp.utils.Constants.Companion.ASCENDING
import com.example.movieapp.utils.Constants.Companion.DATE_RELEASE
import com.example.movieapp.utils.Constants.Companion.DESCENDING
import com.example.movieapp.utils.Constants.Companion.RATING
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortDialogFragment: BottomSheetDialogFragment() {

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!


    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Sort_Dialog_Fragment", "apply sorting setting")
        _binding = BottomSheetLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        initGroupRadio()

        binding.btnApplySort.setOnClickListener {
            Log.d("Sort_Dialog_Fragment", "apply sorting setting")
            when(binding.rgSortWay.checkedRadioButtonId){
                binding.rBtnUp.id -> {
                    viewModel.setSortUp()}
                binding.rBtnDown.id -> viewModel.setSortDown()
            }
            when(binding.rGoupSortType.checkedRadioButtonId){
                binding.rBtnAlphabet.id -> viewModel.setSortByAlphabet()
                binding.rBtnRate.id -> viewModel.setSortByRate()
                binding.rBtnReleaseDate.id -> viewModel.setSortByReleaseDate()
            }
            Log.d("Sort_Dialog_Fragment", viewModel.sortType.value.toString())
            Log.d("Sort_Dialog_Fragment", viewModel.sortWay.value.toString())
            viewModel.refresh()
            viewModel.getMovies()
            this.dismiss()
        }

    }

    private fun initGroupRadio() {
        with(binding) {
            when(viewModel.sortWay.value){
                ASCENDING -> rBtnUp.isChecked = true
                DESCENDING -> rBtnDown.isChecked = true
            }
            when(viewModel.sortType.value){
                RATING -> rBtnRate.isChecked = true
                ALPHABET -> rBtnAlphabet.isChecked = true
                DATE_RELEASE -> rBtnReleaseDate.isChecked = true
            }
        }
    }




}