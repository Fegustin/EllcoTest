package com.holodos.ellcoretrofit.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.holodos.ellcoretrofit.R
import com.holodos.ellcoretrofit.adapter.TicketListAdapter
import com.holodos.ellcoretrofit.viewmodal.TicketViewModal
import kotlinx.android.synthetic.main.fragment_ticket_list.*


class TicketListFragment : Fragment() {

    private val viewModel: TicketViewModal by viewModels()

    private var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ticket_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ticketAdapter = TicketListAdapter()

        viewModel.ticket.observe(viewLifecycleOwner, {
            ticketAdapter.listTicket = it

            if (ticketAdapter.listTicket.isNotEmpty()) {
                recyclerViewTicket.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                isLoad = true
            } else {
                recyclerViewTicket.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                isLoad = false

                resendDataTimer(view)
            }
        })

        recyclerViewTicket.apply {
            adapter = ticketAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }

    private fun resendDataTimer(view: View) {
        object : CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                progressBar.visibility = View.GONE
                if (!isLoad) {
                    Snackbar.make(view, resources.getString(R.string.repeat), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Отправить") {
                            viewModel.loadData()
                            progressBar.visibility = View.VISIBLE
                            resendDataTimer(view)

                        }
                        .show()
                }
            }
        }.start()
    }

}