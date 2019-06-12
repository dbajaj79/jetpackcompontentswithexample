package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPermissionBinding

open class PermissionFragment : Fragment() {

    lateinit var fragmentPermissionBinding: FragmentPermissionBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentPermissionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_permission, container, false)
        return fragmentPermissionBinding.root

    }

}