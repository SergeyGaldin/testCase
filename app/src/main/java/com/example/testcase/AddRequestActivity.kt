package com.example.testcase

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.adapters.DepositAdapter
import com.example.testcase.adapters.ExecutorAdapter
import com.example.testcase.adapters.PriorityAdapter
import com.example.testcase.adapters.ServiceAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.constants.Methods
import com.example.testcase.models.*
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

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
    private lateinit var buttonSave: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var setRequest: SetRequest
    private val listExecutor = ArrayList<Executor>()
    private val listDeposit = ArrayList<Deposit>()
    private val listService = ArrayList<Service>()
    private val listPriority = ArrayList<Priority>()
    private var idDeposit = -1
    private var idService = -1
    private var idExecutor = -1
    private var idPriority = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_request)
        init()
        setValueFields()
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
        buttonSave = findViewById(R.id.buttonSave)
        progressBar = findViewById(R.id.progressBar)
        content = findViewById(R.id.content)
    }

    private fun clicks() {
        buttonSave.setOnClickListener { setDataRequest(it, buttonSave) }
        findViewById<Button>(R.id.buttonCancel).setOnClickListener { onBackPressed() }
        findViewById<Button>(R.id.buttonSelectingDeposit).setOnClickListener { openAlertDialogDeposit() }
        nameRequest.setOnClickListener { openAlertDialogNameRequest() }
        service.setOnClickListener { openAlertDialogService() }
        executor.setOnClickListener { openAlertDialogExecutor() }
        priority.setOnClickListener { openAlertDialogPriority() }
        dateBegine.setOnClickListener { openAlertDialogDateBeginRequest() }
    }

    private fun returnView(layout: Int): View {
        return layoutInflater.inflate(layout, null)
    }

    private fun returnAlertDialog(view: View): AlertDialog {
        return AlertDialog.Builder(this, R.style.Theme_MyAlertDialog)
            .setView(view).setCancelable(true).create()
    }

    private fun initializeRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
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

    private fun openAlertDialogDateBeginRequest() {
        val viewAlert = returnView(R.layout.alert_dialog_date_begin)
        val alertDialog = returnAlertDialog(viewAlert)
        viewAlert.findViewById<Button>(R.id.buttonCancel)
            .setOnClickListener { alertDialog.dismiss() }
        viewAlert.findViewById<Button>(R.id.buttonOk).setOnClickListener {
            val stringDateBeginRequest =
                viewAlert.findViewById<TextInputEditText>(R.id.textDateBeginRequest).text.toString()
            if (stringDateBeginRequest.isNotEmpty()) {
                dateBegine.text = stringDateBeginRequest
                alertDialog.dismiss()
            } else {
                Methods.callToast(application, "Заполните поле плановая дата")
            }
        }
        alertDialog.show()
    }

    private fun openAlertDialogExecutor() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            executor.text = listExecutor[position].getNameExecutor
            idExecutor = listExecutor[position].getIdExecutor.toInt()
            alertDialog.dismiss()
        }
        recyclerView.adapter = ExecutorAdapter(this, listExecutor, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    private fun openAlertDialogDeposit() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            deposit.text = listDeposit[position].getDeposit
            idDeposit = position + 1
            alertDialog.dismiss()
        }
        Log.d(TAG, "getDataPriority: ${listPriority.size}")
        recyclerView.adapter = DepositAdapter(this, listDeposit, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    private fun openAlertDialogService() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            service.text = listService[position].getService
            idService = position + 1
            alertDialog.dismiss()
        }
        recyclerView.adapter = ServiceAdapter(this, listService, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    private fun openAlertDialogPriority() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            priority.text = listPriority[position].getPriority
            idPriority = position + 1
            alertDialog.dismiss()
        }
        recyclerView.adapter = PriorityAdapter(this, listPriority, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setValueFields() {
        dateCreate.text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date())
        author.text = User.getUserName + " / " + User.getOrganization
    }

    private fun getDataDeposit() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_DEPOSIT, { response ->
            try {
                for (i in 0 until response.length()) {
                    listDeposit.add(Deposit(response.getJSONObject(i).getString("name_deposit")))
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
                for (i in 0 until response.length()) {
                    listService.add(Service(response.getJSONObject(i).getString("name_service")))
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
                for (i in 0 until response.length()) {
                    listPriority.add(Priority(response.getJSONObject(i).getString("name_priority")))
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
                for (i in 0 until response.length()) {
                    val objectRequest = response.getJSONObject(i)
                    listExecutor.add(
                        Executor(
                            objectRequest.getString("id"),
                            objectRequest.getString("user_name"),
                            objectRequest.getString("name_role")
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

    private fun setDataRequest(view: View, button: Button) {
        if (checkSetData()) {
            val stringRequest = object :
                StringRequest(Method.POST, Constants.URL_SET_DATA_REQUEST, Response.Listener {
                    progressBar.visibility = View.GONE
                    button.isClickable = true
                    try {
                        val jsonObject = JSONObject(it)
                        if (!jsonObject.getBoolean("error")) {
                            onBackPressed()
                            finish()
                        } else {
                            Methods.callSnackbar(view, jsonObject.getString("message"))
                        }
                    } catch (e: JSONException) {
                        Methods.callSnackbar(view, e.message.toString())
                    }
                }, Response.ErrorListener {
                    progressBar.visibility = View.GONE
                    button.isClickable = true
                    if (it?.message != null) Methods.callSnackbar(view, it.message!!)
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["name_request"] = setRequest.getNameRequest
                    params["deposit"] = setRequest.getDepositRequest.toString()
                    params["service"] = setRequest.getServiceRequest.toString()
                    params["executor"] = setRequest.getExecutorRequest.toString()
                    params["priority"] = setRequest.getPriorityRequest.toString()
                    params["date_creation"] = setRequest.getDateCreationRequest
                    params["date_begine"] = setRequest.getDateBegineRequest
                    params["author"] = setRequest.getAuthorRequest
                    return params
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        }
    }

    private fun checkSetData(): Boolean {
        setRequest = SetRequest(
            nameRequest.text.toString(),
            idDeposit,
            idService,
            idExecutor,
            idPriority,
            dateCreate.text.toString(),
            dateBegine.text.toString(),
            author.text.toString()
        )
        if (
            setRequest.getNameRequest == "Добавить название заявки" ||
            setRequest.getDepositRequest == -1 ||
            setRequest.getServiceRequest == -1 ||
            setRequest.getExecutorRequest == -1 ||
            setRequest.getPriorityRequest == -1 ||
            setRequest.getDateCreationRequest == "Не выбрано" ||
            setRequest.getDateBegineRequest == "Не выбрано" ||
            setRequest.getAuthorRequest == "Не выбрано"
        ) {
            Methods.callToast(this, "Заполните все поля")
            return false
        }
        return true
    }
}