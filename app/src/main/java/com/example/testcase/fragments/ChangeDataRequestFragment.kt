package com.example.testcase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.testcase.constants.Methods
import com.example.testcase.databinding.FragmentChangeDataRequestBinding

class ChangeDataRequestFragment : Fragment() {
    private lateinit var binding: FragmentChangeDataRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeDataRequestBinding.inflate(inflater)
        init()
        return binding.root
    }
    private fun init() {
        setFragmentResultListener("request_key") { _, result ->
            Methods.callToast(requireContext(), result.getString("id").toString())
        }
    }
}