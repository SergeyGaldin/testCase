package com.example.testcase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcase.R
import com.example.testcase.models.Service

class ServiceAdapter(
    context: Context,
    listService: ArrayList<Service>,
    private val itemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<ServiceAdapter.ServiceAdapterVH>() {
    private var layoutInflater: LayoutInflater
    private var listService: List<Service>

    init {
        layoutInflater = LayoutInflater.from(context)
        this.listService = listService
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceAdapterVH {
        return ServiceAdapterVH(layoutInflater.inflate(R.layout.layout_service, parent, false))
    }

    override fun onBindViewHolder(holder: ServiceAdapterVH, position: Int) {
        val service: Service = listService[position]
        holder.service.text = service.getService
    }

    override fun getItemCount(): Int {
        return listService.size
    }

    inner class ServiceAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var service: TextView

        init {
            service = itemView.findViewById(R.id.name_service)
            itemView.setOnClickListener { itemClickListener(adapterPosition) }
        }
    }
}