package com.example.testcase

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.constants.Constants
import com.example.testcase.constants.Methods
import com.example.testcase.models.Executor
import com.example.testcase.models.User
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddRequestActivity : AppCompatActivity() {
    private lateinit var nameRequest: TextView
    private lateinit var deposit: TextView
    private lateinit var service: TextView
    private lateinit var executor: TextView
    private lateinit var priority: TextView
    private lateinit var dateBegine: TextView
    private lateinit var author: TextView
    private lateinit var dateCreate: TextView
    private lateinit var content: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)
        init()
        clicks()
        getDataDeposit()
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
        content = findViewById(R.id.content)
    }

    private fun clicks() {
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { onBackPressed() }
        findViewById<Button>(R.id.buttonSave).setOnClickListener { }
        findViewById<Button>(R.id.buttonSelectingDeposit).setOnClickListener { openAlertDialogDeposit() }
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

    private fun openAlertDialogDeposit() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        val arrayList = ArrayList<String>()
//        val arrayAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList)
//        recyclerView.adapter = arrayAdapter
        alertDialog.show()
    }

    private fun returnView(layout: Int): View {
        return layoutInflater.inflate(layout, null)
    }

    private fun returnAlertDialog(view: View): AlertDialog {
        return AlertDialog.Builder(this, R.style.Theme_MyAlertDialog)
            .setView(view).setCancelable(true).create()
    }

    private fun initializeAdapter(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    @SuppressLint("SimpleDateFormat")
    private fun setValueFields() {
        dateCreate.text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date())
        author.text = User.getUserName
    }

    private fun getDataDeposit() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_DEPOSIT, { response ->
            try {
                val arrayList = ArrayList<String>()
                for (i in 0 until response.length()) {
                    arrayList.add(response.getJSONObject(i).getString("name_deposit"))
                }
                getDataService()
            } catch (e: JSONException) {
                Log.d(TAG, "getDataDeposit: ${e.message}")
            }
        }) { error ->
            Log.d(TAG, "getDataDeposit: ${error.message}")
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun getDataService() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_SERVICE, { response ->
            try {
                val arrayList = ArrayList<String>()
                for (i in 0 until response.length()) {
                    arrayList.add(response.getJSONObject(i).getString("name_service"))
                }
                getDataPriority()
            } catch (e: JSONException) {
                Log.d(TAG, "getDataService: ${e.message}")
            }
        }) { error ->
            Log.d(TAG, "getDataService: ${error.message}")
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun getDataPriority() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_PRIORITY, { response ->
            try {
                val arrayList = ArrayList<String>()
                for (i in 0 until response.length()) {
                    arrayList.add(response.getJSONObject(i).getString("name_priority"))
                }
                getDataExecutor()
            } catch (e: JSONException) {
                Log.d(TAG, "getDataPriority: ${e.message}")
            }
        }) { error ->
            Log.d(TAG, "getDataPriority: ${error.message}")
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun getDataExecutor() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_EXECUTOR, { response ->
            try {
                val arrayList = ArrayList<Executor>()
                for (i in 0 until response.length()) {
                    val objectRequest = response.getJSONObject(i)
                    arrayList.add(
                        Executor(
                            objectRequest.getString("user_name"),
                            objectRequest.getString("name_organization")
                        )
                    )
                }
                content.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            } catch (e: JSONException) {
                Log.d(TAG, "getDataExecutor: ${e.message}")
            }
        }) { error ->
            Log.d(TAG, "getDataExecutor: ${error.message}")
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}