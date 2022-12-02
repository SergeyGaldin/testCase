package com.example.testcase.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
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
import com.example.testcase.databinding.FragmentChangeDataRequestBinding
import com.example.testcase.models.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

class ChangeDataRequestFragment : Fragment() {
    private lateinit var binding: FragmentChangeDataRequestBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var request: Request
    private lateinit var listRequest: ArrayList<Request>
    private val listExecutor = ArrayList<Executor>()
    private val listDeposit = ArrayList<Deposit>()
    private val listService = ArrayList<Service>()
    private val listPriority = ArrayList<Priority>()
    private var idRequest: String? = null
    private var idDeposit: Int? = null
    private var idService: Int? = null
    private var idExecutor: Int? = null
    private var idPriority: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeDataRequestBinding.inflate(inflater)
        init()
        clicks()
        getDataDeposit()
        initializeAppBar()
        return binding.root
    }

    private fun init() {
        setFragmentResultListener("key_list") { _, resultList ->
            listRequest = resultList.getParcelableArrayList<Request>("list") as ArrayList<Request>
            setFragmentResultListener("key_position") { _, resultPosition ->
                val position = resultPosition.getInt("position")
                idRequest = listRequest[position].getIdRequest
                binding.nameRequest.text = listRequest[position].getNameRequest
                binding.deposit.text = listRequest[position].getDepositRequest
                binding.service.text = listRequest[position].getServiceRequest
                binding.executor.text = listRequest[position].getExecutorRequest
                binding.priority.text = listRequest[position].getPriorityRequest
                binding.status.text = listRequest[position].getStatusRequest
                binding.dateBegine.text = listRequest[position].getDateBeginRequest
                binding.dateEnd.text = listRequest[position].getDateEndRequest
                binding.author.text = listRequest[position].getAuthorRequest
                binding.dateCreate.text = listRequest[position].getDateCreateRequest
                idDeposit = listRequest[position].getDeposit.toInt()
                idService = listRequest[position].getService.toInt()
                idExecutor = listRequest[position].getExecutor.toInt()
                idPriority = listRequest[position].getPriority.toInt()
            }
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

    private fun clicks() {
        binding.buttonSave.setOnClickListener { updateRequest() }
        binding.buttonCancel.setOnClickListener { activity?.onBackPressed() }
        binding.buttonDelete.setOnClickListener { deleteRequest() }
        binding.buttonSelectingDeposit.setOnClickListener { openAlertDialogDeposit() }
        binding.nameRequest.setOnClickListener { openAlertDialogNameRequest() }
        binding.service.setOnClickListener { openAlertDialogService() }
        binding.executor.setOnClickListener { openAlertDialogExecutor() }
        binding.priority.setOnClickListener { openAlertDialogPriority() }
        binding.dateBegine.setOnClickListener { openDataPicker() }
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

    @SuppressLint("SimpleDateFormat")
    private fun openDataPicker() {
        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите плановую дату")
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setTheme(R.style.MaterialCalendar)
            .build()
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.dateBegine.text = SimpleDateFormat("dd.MM.yyyy").format(Date(it))
        }
        materialDatePicker.show(requireActivity().supportFragmentManager, "MaterialDatePicker")
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

    private fun updateRequest() {
        if (checkSetData()) {
            val stringRequest = object :
                StringRequest(Method.POST, Constants.URL_UPDATE_REQUEST, Response.Listener {
                    binding.progressBar.visibility = View.GONE
                    binding.buttonSave.isClickable = true
                    try {
                        val jsonObject = JSONObject(it)
                        if (!jsonObject.getBoolean("error")) {
                            activity?.onBackPressed()
                        } else {
                            Methods.callSnackbar(requireView(), jsonObject.getString("message"))
                        }
                    } catch (e: JSONException) {
                        Methods.callSnackbar(requireView(), e.message.toString())
                    }
                }, Response.ErrorListener {
                    binding.progressBar.visibility = View.GONE
                    binding.buttonDelete.isClickable = true
                    if (it?.message != null) Methods.callSnackbar(requireView(), it.message!!)
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["id_request"] = idRequest!!
                    params["name_request"] = request.getNameRequest
                    params["deposit"] = request.getDeposit
                    params["service"] = request.getService
                    params["executor"] = request.getExecutor
                    params["priority"] = request.getPriority
                    params["date_begine"] = request.getDateBeginRequest
                    return params
                }
            }
            Volley.newRequestQueue(context).add(stringRequest)
        }
    }

    private fun deleteRequest() {
        val stringRequest = object :
            StringRequest(Method.POST, Constants.URL_DELETE_REQUEST, Response.Listener {
                binding.progressBar.visibility = View.GONE
                binding.buttonDelete.isClickable = true
                try {
                    val jsonObject = JSONObject(it)
                    if (!jsonObject.getBoolean("error")) {
                        activity?.onBackPressed()
                    } else {
                        Methods.callSnackbar(requireView(), jsonObject.getString("message"))
                    }
                } catch (e: JSONException) {
                    Methods.callSnackbar(requireView(), e.message.toString())
                }
            }, Response.ErrorListener {
                binding.progressBar.visibility = View.GONE
                binding.buttonDelete.isClickable = true
                if (it?.message != null) Methods.callSnackbar(requireView(), it.message!!)
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["id_request"] = idRequest!!
                return params
            }
        }
        Volley.newRequestQueue(context).add(stringRequest)
    }

    private fun checkSetData(): Boolean {
        request = Request(
            binding.nameRequest.text.toString(),
            idDeposit.toString(),
            idService.toString(),
            idExecutor.toString(),
            idPriority.toString(),
            binding.dateBegine.text.toString(),
        )
        if (request.getNameRequest.isEmpty()) {
            Methods.callToast(requireContext(), "Заполните название заявки")
            return false
        }
        return true
    }

}