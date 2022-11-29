package com.example.testcase.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.R
import com.example.testcase.adapters.DepositAdapter
import com.example.testcase.adapters.ExecutorAdapter
import com.example.testcase.adapters.PriorityAdapter
import com.example.testcase.adapters.ServiceAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.constants.Methods
import com.example.testcase.databinding.FragmentAddRequestBinding
import com.example.testcase.models.*
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

class AddRequestFragment : Fragment() {
    private lateinit var binding: FragmentAddRequestBinding
    private lateinit var setRequest: SetRequest
    private lateinit var recyclerView: RecyclerView
    private val listExecutor = ArrayList<Executor>()
    private val listDeposit = ArrayList<Deposit>()
    private val listService = ArrayList<Service>()
    private val listPriority = ArrayList<Priority>()
    private var idDeposit = -1
    private var idService = -1
    private var idExecutor = -1
    private var idPriority = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRequestBinding.inflate(inflater)
        setValueFields()
        clicks()
        getDataDeposit()
        return binding.root
    }

    private fun clicks() {
        binding.buttonSave.setOnClickListener { setDataRequest(it, binding.buttonSave) }
        binding.buttonCancel.setOnClickListener { activity?.onBackPressed() }
        binding.buttonSelectingDeposit.setOnClickListener { openAlertDialogDeposit() }
        binding.nameRequest.setOnClickListener { openAlertDialogNameRequest() }
        binding.service.setOnClickListener { openAlertDialogService() }
        binding.executor.setOnClickListener { openAlertDialogExecutor() }
        binding.priority.setOnClickListener { openAlertDialogPriority() }
        binding.dateBegine.setOnClickListener { openAlertDialogDateBeginRequest() }
    }

    private fun returnView(layout: Int): View {
        return layoutInflater.inflate(layout, null)
    }

    private fun returnAlertDialog(view: View): AlertDialog {
        return AlertDialog.Builder(requireContext(), R.style.Theme_MyAlertDialog)
            .setView(view).setCancelable(true).create()
    }

    private fun initializeRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
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
                binding.nameRequest.text = stringNameRequest
                alertDialog.dismiss()
            } else {
                Methods.callToast(requireContext(), "Заполните поле название заявки")
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
                binding.dateBegine.text = stringDateBeginRequest
                alertDialog.dismiss()
            } else {
                Methods.callToast(requireContext(), "Заполните поле плановая дата")
            }
        }
        alertDialog.show()
    }

    private fun openAlertDialogExecutor() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            binding.executor.text = listExecutor[position].getNameExecutor
            idExecutor = listExecutor[position].getIdExecutor.toInt()
            alertDialog.dismiss()
        }
        recyclerView.adapter =
            ExecutorAdapter(requireContext(), listExecutor, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    private fun openAlertDialogDeposit() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            binding.deposit.text = listDeposit[position].getDeposit
            idDeposit = position + 1
            alertDialog.dismiss()
        }
        Log.d(ContentValues.TAG, "getDataPriority: ${listPriority.size}")
        recyclerView.adapter =
            DepositAdapter(requireContext(), listDeposit, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    private fun openAlertDialogService() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            binding.service.text = listService[position].getService
            idService = position + 1
            alertDialog.dismiss()
        }
        recyclerView.adapter =
            ServiceAdapter(requireContext(), listService, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    private fun openAlertDialogPriority() {
        val viewAlert = returnView(R.layout.alert_dialog_recycler_view)
        val alertDialog = returnAlertDialog(viewAlert)
        initializeRecyclerView(viewAlert)
        val itemOnClick: (Int) -> Unit = { position ->
            binding.priority.text = listPriority[position].getPriority
            idPriority = position + 1
            alertDialog.dismiss()
        }
        recyclerView.adapter =
            PriorityAdapter(requireContext(), listPriority, itemClickListener = itemOnClick)
        alertDialog.show()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setValueFields() {
        binding.dateCreate.text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date())
        binding.author.text = User.getUserName + " / " + User.getOrganization
    }

    private fun getDataDeposit() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_DEPOSIT, { response ->
            try {
                for (i in 0 until response.length()) {
                    listDeposit.add(Deposit(response.getJSONObject(i).getString("name_deposit")))
                }
                getDataService()
            } catch (e: JSONException) {
                Log.d(ContentValues.TAG, "getDataDeposit: ${e.message}")
            }
        }) { error ->
            Log.d(ContentValues.TAG, "getDataDeposit: ${error.message}")
        }
        Volley.newRequestQueue(context).add(stringRequest)
    }

    private fun getDataService() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_SERVICE, { response ->
            try {
                for (i in 0 until response.length()) {
                    listService.add(Service(response.getJSONObject(i).getString("name_service")))
                }
                getDataPriority()
            } catch (e: JSONException) {
                Log.d(ContentValues.TAG, "getDataService: ${e.message}")
            }
        }) { error ->
            Log.d(ContentValues.TAG, "getDataService: ${error.message}")
        }
        Volley.newRequestQueue(context).add(stringRequest)
    }

    private fun getDataPriority() {
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_PRIORITY, { response ->
            try {
                for (i in 0 until response.length()) {
                    listPriority.add(Priority(response.getJSONObject(i).getString("name_priority")))
                }
                getDataExecutor()
            } catch (e: JSONException) {
                Log.d(ContentValues.TAG, "getDataPriority: ${e.message}")
            }
        }) { error ->
            Log.d(ContentValues.TAG, "getDataPriority: ${error.message}")
        }
        Volley.newRequestQueue(context).add(stringRequest)
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
                binding.content.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            } catch (e: JSONException) {
                Log.d(ContentValues.TAG, "getDataExecutor: ${e.message}")
            }
        }) { error ->
            Log.d(ContentValues.TAG, "getDataExecutor: ${error.message}")
        }
        Volley.newRequestQueue(context).add(stringRequest)
    }

    private fun setDataRequest(view: View, button: Button) {
        if (checkSetData()) {
            val stringRequest = object :
                StringRequest(Method.POST, Constants.URL_SET_DATA_REQUEST, Response.Listener {
                    binding.progressBar.visibility = View.GONE
                    button.isClickable = true
                    try {
                        val jsonObject = JSONObject(it)
                        if (!jsonObject.getBoolean("error")) {
                            activity?.onBackPressed()
                        } else {
                            Methods.callSnackbar(view, jsonObject.getString("message"))
                        }
                    } catch (e: JSONException) {
                        Methods.callSnackbar(view, e.message.toString())
                    }
                }, Response.ErrorListener {
                    binding.progressBar.visibility = View.GONE
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
            Volley.newRequestQueue(context).add(stringRequest)
        }
    }

    private fun checkSetData(): Boolean {
        setRequest = SetRequest(
            binding.nameRequest.text.toString(),
            idDeposit,
            idService,
            idExecutor,
            idPriority,
            binding.dateCreate.text.toString(),
            binding.dateBegine.text.toString(),
            binding.author.text.toString()
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
            Methods.callToast(requireContext(), "Заполните все поля")
            return false
        }
        return true
    }

}