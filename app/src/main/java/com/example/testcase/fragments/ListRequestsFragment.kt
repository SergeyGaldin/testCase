package com.example.testcase.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.adapters.RequestAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.databinding.FragmentListRequestsBinding
import com.example.testcase.models.Request
import org.json.JSONException

class ListRequestsFragment : BaseFragment() {
    private lateinit var binding: FragmentListRequestsBinding
    private lateinit var adapter: RequestAdapter
    private lateinit var requestModel: Request
    private val listRequest = ArrayList<Request>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListRequestsBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        with(binding.recyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        binding.buttonAdd.setOnClickListener {
            replaceFragment?.replace(AddRequestFragment(), true)
        }
        if (listRequest.isEmpty()) getDataRequest()
        else initializeAdapter()
    }

    private fun initializeAdapter() {
        val itemOnClick: (Int) -> Unit = { position ->
            setFragmentResult("request_key", bundleOf("id" to listRequest[position].getNameRequest))
            replaceFragment?.replace(ChangeDataRequestFragment(), true)
        }
        adapter = RequestAdapter(requireContext(), listRequest, itemClickListener = itemOnClick)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataRequest() {
        binding.progressBar.visibility = View.VISIBLE
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_REQUEST, { response ->
            try {
                for (i in 0 until response.length()) {
                    val objectRequest = response.getJSONObject(i)
                    requestModel = Request(
                        objectRequest.getString("name_request"),
                        objectRequest.getString("name_priority"),
                        objectRequest.getString("name_status"),
                        objectRequest.getString("date_begine"),
                        objectRequest.getString("author")
                    )
                    listRequest.add(requestModel)
                }
                initializeAdapter()
                binding.progressBar.visibility = View.GONE
            } catch (e: JSONException) {
                Log.d(ContentValues.TAG, "getDataRequest: ${e.message}")
                binding.progressBar.visibility = View.GONE
            }
        }) { error ->
            Log.d(ContentValues.TAG, "getDataRequest: ${error.message}")
            binding.progressBar.visibility = View.GONE
        }
        Volley.newRequestQueue(context).add(stringRequest)
    }

}