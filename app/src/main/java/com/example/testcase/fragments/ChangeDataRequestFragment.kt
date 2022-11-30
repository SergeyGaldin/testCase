package com.example.testcase.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import com.example.testcase.R
import com.example.testcase.constants.Methods
import com.example.testcase.databinding.FragmentChangeDataRequestBinding

class ChangeDataRequestFragment : Fragment() {
    private lateinit var binding: FragmentChangeDataRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeDataRequestBinding.inflate(inflater)
        init()
        initializeAppBar()
        return binding.root
    }

    private fun init() {
        setFragmentResultListener("request_key") { _, result ->
            Methods.callToast(requireContext(), result.getString("id").toString())
        }
    }

    private fun initializeAppBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Изменение заявки"
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }
}