package com.example.testcase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcase.R
import com.example.testcase.models.Priority

class PriorityAdapter(
    context: Context,
    listPriority: ArrayList<Priority>,
    private val itemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<PriorityAdapter.PriorityAdapterVH>() {
    private var layoutInflater: LayoutInflater
    private var listPriority: List<Priority>

    init {
        layoutInflater = LayoutInflater.from(context)
        this.listPriority = listPriority
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriorityAdapterVH {
        return PriorityAdapterVH(layoutInflater.inflate(R.layout.layout_priority, parent, false))
    }

    override fun onBindViewHolder(holder: PriorityAdapterVH, position: Int) {
        val priority: Priority = listPriority[position]
        holder.priority.text = priority.getPriority
    }

    override fun getItemCount(): Int {
        return listPriority.size
    }

    inner class PriorityAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var priority: TextView

        init {
            priority = itemView.findViewById(R.id.name_priority)
            itemView.setOnClickListener { itemClickListener(adapterPosition) }
        }
    }
}