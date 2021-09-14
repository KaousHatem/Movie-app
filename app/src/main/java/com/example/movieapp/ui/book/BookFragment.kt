package com.example.movieapp.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.movieapp.base.BaseFragment
import com.example.movieapp.databinding.FragmentBookBinding
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.utils.Constants.Companion.BOOK_URL

class BookFragment : BaseFragment<FragmentBookBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            wvBook.apply {
                settings.loadsImagesAutomatically = true
                settings.useWideViewPort = true
                settings.javaScriptEnabled = true
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                webViewClient = WebViewClient()
            }
            wvBook.loadUrl(BOOK_URL)
        }

    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBookBinding.inflate(inflater, container, false)
}