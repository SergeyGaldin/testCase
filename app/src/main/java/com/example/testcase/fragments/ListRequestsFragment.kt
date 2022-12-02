package com.example.testcase.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.testcase.R
import com.example.testcase.adapters.RequestAdapter
import com.example.testcase.constants.Constants
import com.example.testcase.databinding.FragmentListRequestsBinding
import com.example.testcase.interfaces.ReplaceFragment
import com.example.testcase.models.Request
import org.json.JSONException

class ListRequestsFragment : Fragment() {
    private lateinit var binding: FragmentListRequestsBinding
    private lateinit var adapter: RequestAdapter
    private lateinit var requestModel: Request
    private var replaceFragment: ReplaceFragment? = null
    private val listRequest = ArrayList<Request>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListRequestsBinding.inflate(layoutInflater)
        init()
        initializeAppBar()
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
        else initializeAdapter(listRequest)
    }

    fun initializeAdapter(list: ArrayList<Request>) {
        val itemOnClick: (Int) -> Unit = { position ->
            setFragmentResult("key_list", bundleOf("list" to listRequest))
            setFragmentResult("key_position", bundleOf("position" to position))
            replaceFragment?.replace(ChangeDataRequestFragment(), true)
        }
        adapter = RequestAdapter(requireContext(), list, itemClickListener = itemOnClick)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataRequest() {
        binding.progressBar.visibility = View.VISIBLE
        val stringRequest = JsonArrayRequest(Constants.URL_GET_DATA_REQUEST, { response ->
            try {
                for (i in 0 until response.length()) {
                    val objectRequest = response.getJSONObject(i)
                    requestModel = Request(
                        objectRequest.getString("id_request"),
                        objectRequest.getString("name_request"),
                        objectRequest.getString("name_deposit"),
                        objectRequest.getString("name_service"),
                        objectRequest.getString("name_priority"),
                        objectRequest.getString("name_status"),
                        objectRequest.getString("date_creation"),
                        objectRequest.getString("date_begine"),
                        objectRequest.getString("date_end"),
                        objectRequest.getString("author"),
                        objectRequest.getString("user_name"),
                        objectRequest.getString("name_organization")
                    )
                    listRequest.add(requestModel)
                }
                initializeAdapter(listRequest)
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

    private fun initializeAppBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Заявки"
        replaceFragment = context as ReplaceFragment
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_bar_menu, menu)
                with(menu.findItem(R.id.search).actionView as SearchView) {
                    this.isIconifiedByDefault = false
                    this.queryHint = "Поиск"
                    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            val newList = ArrayList<Request>()
                            for (item in listRequest) {
                                if (item.getNameRequest.contains(newText.toString()) ||
                                    item.getPriorityRequest.contains(newText.toString()) ||
                                    item.getStatusRequest.contains(newText.toString()) ||
                                    item.getDateBeginRequest.contains(newText.toString()) ||
                                    item.getExecutorRequest.contains(newText.toString())
                                ) {
                                    newList.add(
                                        Request(
                                            item.getIdRequest,
                                            item.getNameRequest,
                                            item.getDepositRequest,
                                            item.getServiceRequest,
                                            item.getPriorityRequest,
                                            item.getStatusRequest,
                                            item.getDateCreateRequest,
                                            item.getDateBeginRequest,
                                            item.getDateEndRequest,
                                            item.getAuthorRequest,
                                            item.getExecutorRequest,
                                            item.getOrganizationRequest
                                        )
                                    )
                                }
                            }
                            initializeAdapter(newList)
                            return true
                        }
                    })
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.update -> {
                        listRequest.clear()
                        getDataRequest()
                    }
                }
                return true
            }
        })
    }

}