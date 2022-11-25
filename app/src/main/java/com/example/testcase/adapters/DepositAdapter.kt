package com.example.testcase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testcase.R
import com.example.testcase.models.Deposit

class DepositAdapter(
    context: Context,
    listDeposit: ArrayList<Deposit>,
    private val itemClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<DepositAdapter.DepositAdapterVH>() {
    private var layoutInflater: LayoutInflater
    private var listDeposit: List<Deposit>

    init {
        layoutInflater = LayoutInflater.from(context)
        this.listDeposit = listDeposit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepositAdapterVH {
        return DepositAdapterVH(layoutInflater.inflate(R.layout.layout_deposit, parent, false))
    }

    override fun onBindViewHolder(holder: DepositAdapterVH, position: Int) {
        val deposit: Deposit = listDeposit[position]
        holder.deposit.text = deposit.getDeposit
    }

    override fun getItemCount(): Int {
        return listDeposit.size
    }

    inner class DepositAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var deposit: TextView

        init {
            deposit = itemView.findViewById(R.id.name_deposit)
            itemView.setOnClickListener { itemClickListener(adapterPosition) }
        }
    }
}