package com.holodos.ellcoretrofit.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.holodos.ellcoretrofit.database.model.TicketData
import com.holodos.ellcoretrofit.repository.TicketRepository


class TicketViewModal(application: Application) : AndroidViewModel(application) {

    private val ticketRepository = TicketRepository(application)

    var ticket: LiveData<List<TicketData>>

    init {
        ticket = ticketRepository.getData()
    }

    fun loadData() {
        ticketRepository.loadData(getApplication())
    }

    override fun onCleared() {
        super.onCleared()
        ticketRepository.closeDisposable()
    }
}