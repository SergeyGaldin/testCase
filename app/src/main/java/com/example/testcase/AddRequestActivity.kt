package com.example.testcase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.testcase.constants.Methods
import com.example.testcase.models.User
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddRequestActivity : AppCompatActivity() {
    private lateinit var nameRequest: TextView
    private lateinit var deposit: TextView
    private lateinit var service: TextView
    private lateinit var executor: TextView
    private lateinit var priority: TextView
    private lateinit var dateBegine: TextView
    private lateinit var author: TextView
    private lateinit var dateCreate: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)
        init()
        clicks()
    }

    private fun init() {
        nameRequest = findViewById(R.id.nameRequest)
        deposit = findViewById(R.id.deposit)
        service = findViewById(R.id.service)
        executor = findViewById(R.id.executor)
        priority = findViewById(R.id.priority)
        dateBegine = findViewById(R.id.date_begine)
        author = findViewById(R.id.author)
        dateCreate = findViewById(R.id.date_create)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun clicks() {
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { onBackPressed() }
        findViewById<Button>(R.id.buttonSave).setOnClickListener { }
        findViewById<Button>(R.id.buttonSelectingDeposit).setOnClickListener { }
        nameRequest.setOnClickListener { openAlertDialogNameRequest() }
    }

    private fun openAlertDialogNameRequest() {
        val viewAlert = returnView(R.layout.alert_dialog_name_request)
        val alertDialog = returnAlertDialog(viewAlert)
        viewAlert.findViewById<Button>(R.id.buttonCancel)
            .setOnClickListener { alertDialog.dismiss() }
        viewAlert.findViewById<Button>(R.id.buttonOk).setOnClickListener {
            val stringNameRequest =
                viewAlert.findViewById<TextInputEditText>(R.id.textNameRequest).text.toString()
            if (stringNameRequest.isNotEmpty()) {
                nameRequest.text = stringNameRequest
                alertDialog.dismiss()
            } else {
                Methods.callToast(application, "Заполните поле название заявки")
            }
        }
        alertDialog.show()
    }

    private fun returnView(layout: Int): View {
        return layoutInflater.inflate(layout, null)
    }

    private fun returnAlertDialog(view: View): AlertDialog {
        return AlertDialog.Builder(this, R.style.Theme_MyAlertDialog)
            .setView(view).setCancelable(true).create()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setValueFields() {
        dateCreate.text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date())
        author.text = User.getUserName
    }
}