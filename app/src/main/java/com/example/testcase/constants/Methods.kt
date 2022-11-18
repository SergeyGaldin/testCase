package com.example.testcase.constants

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Methods {
    fun callSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}