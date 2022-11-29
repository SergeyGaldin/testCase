package com.example.testcase.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.testcase.interfaces.ReplaceFragment

open class BaseFragment : Fragment() {
    protected var replaceFragment: ReplaceFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragment = context as ReplaceFragment
    }
}