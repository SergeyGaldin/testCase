package com.example.testcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testcase.databinding.ActivityMainBinding
import com.example.testcase.fragments.ListRequestsFragment
import com.example.testcase.interfaces.ReplaceFragment

class MainActivity : AppCompatActivity(), ReplaceFragment {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)
        replace(ListRequestsFragment(), false)
    }

    override fun replace(fragment: Fragment, boolean: Boolean) {
        if (boolean) supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment)
            .addToBackStack("stack").commit()
        else supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

}