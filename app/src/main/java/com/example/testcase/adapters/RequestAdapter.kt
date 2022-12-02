package com.example.testcase.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcase.R
import com.example.testcase.models.Request

class RequestAdapter(
    context: Context,
    listRequest: ArrayList<Request>,
    private val itemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<RequestAdapter.RequestAdapterVH>() {
    private var layoutInflater: LayoutInflater
    private var listRequest: List<Request>

    init {
        layoutInflater = LayoutInflater.from(context)
        this.listRequest = listRequest
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAdapterVH {
        return RequestAdapterVH(layoutInflater.inflate(R.layout.layout_request, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RequestAdapterVH, position: Int) {
        val request: Request = listRequest[position]
        holder.nameRequest.text = "№" + request.getIdRequest + " " + request.getNameRequest
        holder.priorityRequest.text = request.getPriorityRequest
        holder.statusRequest.text = request.getStatusRequest
        holder.dateRequest.text = request.getDateBeginRequest
        holder.executorRequest.text = request.getExecutorRequest + " / " + request.getOrganizationRequest
    }

    override fun getItemCount(): Int {
        return listRequest.size
    }

    inner class RequestAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameRequest: TextView
        var priorityRequest: TextView
        var statusRequest: TextView
        var dateRequest: TextView
        var executorRequest: TextView

        init {
            nameRequest = itemView.findViewById(R.id.nameRequest)
            priorityRequest = itemView.findViewById(R.id.priority)
            statusRequest = itemView.findViewById(R.id.status)
            dateRequest = itemView.findViewById(R.id.date)
            executorRequest = itemView.findViewById(R.id.executor)
            itemView.setOnClickListener { itemClickListener(adapterPosition) }
        }
    }
}