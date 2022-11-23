package com.example.testcase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.testcase.constants.Methods

class AddRequestActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)
        findViewById<Button>(R.id.buttonCancel).setOnClickListener(this)
    }

    override fun onClick(view: View?){
        when(view?.id){
            R.id.buttonAdd -> {

            }
            R.id.buttonCancel -> {
                onBackPressed()
            }
        }
    }
}