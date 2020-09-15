package com.holodos.ellcoretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.holodos.ellcoretrofit.R
import com.holodos.ellcoretrofit.database.model.TicketData
import com.ramotion.foldingcell.FoldingCell

class TicketListAdapter: RecyclerView.Adapter<TicketListAdapter.ViewHolder>() {

    var listTicket: List<TicketData> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val foldingCell: FoldingCell = view.findViewById(R.id.folding_cell)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)
        val textViewSender: TextView = view.findViewById(R.id.textViewSender)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listTicket[position]

        holder.textViewName.text = currentItem.name
        holder.textViewDescription.text = currentItem.description
        holder.textViewSender.text = "Отправил пользователь: ${currentItem.sender.username}, id: ${currentItem.sender.userId}"

        holder.foldingCell.setOnClickListener {
            holder.foldingCell.toggle(false);
        }
    }

    override fun getItemCount(): Int = listTicket.size
}