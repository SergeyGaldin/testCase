package com.example.testcase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcase.R
import com.example.testcase.models.Executor

class ExecutorAdapter(
    context: Context,
    listExecutor: ArrayList<Executor>,
    private val itemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<ExecutorAdapter.ExecutorAdapterVH>() {
    private var layoutInflater: LayoutInflater
    private var listExecutor: List<Executor>

    init {
        layoutInflater = LayoutInflater.from(context)
        this.listExecutor = listExecutor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecutorAdapterVH {
        return ExecutorAdapterVH(layoutInflater.inflate(R.layout.layout_executor, parent, false))
    }

    override fun onBindViewHolder(holder: ExecutorAdapterVH, position: Int) {
        val executor: Executor = listExecutor[position]
        holder.nameExecutor.text = executor.getNameExecutor
        holder.organizationExecutor.text = executor.getOrganizationExecutor
    }

    override fun getItemCount(): Int {
        return listExecutor.size
    }

    inner class ExecutorAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameExecutor: TextView
        var organizationExecutor: TextView

        init {
            nameExecutor = itemView.findViewById(R.id.name_executor)
            organizationExecutor = itemView.findViewById(R.id.executor_organization)
            itemView.setOnClickListener { itemClickListener(adapterPosition) }
        }
    }
}