package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGalleryBinding

open class GalleryFragment : Fragment() {
    lateinit var fragmentGalleryBinding: FragmentGalleryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentGalleryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false)
        return fragmentGalleryBinding.root

    }
}