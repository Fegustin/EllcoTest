package com.holodos.ellcoretrofit.viewmodal

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.holodos.ellcoretrofit.api.ApiEllcoFactory
import com.holodos.ellcoretrofit.database.TicketDatabase
import com.holodos.ellcoretrofit.database.model.Sender
import com.holodos.ellcoretrofit.database.model.TicketData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject


class TicketViewModal(application: Application) : AndroidViewModel(application) {

    private var db: TicketDatabase = TicketDatabase.getInstance(application)
    private var compositeDisposable: CompositeDisposable? = null

    private val baseUrl = "https://lk.ellco.ru:8000/"
    private val token = "38fa0880d113c79d8e0196481d4f4562576b5348de1ab9619696d3449de5"

    var ticket: LiveData<List<TicketData>>

    init {
        ticket = db.ticketDao().getAll()
    }

    private fun insertTicket(tickets: List<TicketData>) {
        val disposable = Completable.fromAction { db.ticketDao().insertAll(tickets) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("testApi", "Insert completed")
            }, {
                Log.i("testApi", "Insert error")
            })

        compositeDisposable?.add(disposable)
    }

    private fun deleteAllTicket() {
        val disposable = Completable.fromAction { db.ticketDao().deleteAll() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
            .subscribe()

        compositeDisposable?.add(disposable)
    }

    fun loadData() {
        val disposable = ApiEllcoFactory.create(baseUrl)
            .getTicket(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                deleteAllTicket()
                val result = JSONObject(it.string())
                val array = result.getJSONArray("bug_trackers")
                val listTicket = ArrayList<TicketData>()

                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    val ticket = TicketData(
                        item.getInt("id"),
                        item.getString("name"),
                        item.getString("description"),
                        Sender(
                            item.getJSONObject("sender").getInt("id"),
                            item.getJSONObject("sender").getString("username")
                        )
                    )
                    listTicket.add(ticket)
                }

                insertTicket(listTicket)
            }, {
                Log.i("testApi", "Api Error: " + it.localizedMessage)
                Toast.makeText(getApplication(), "Ошибка получения данных", Toast.LENGTH_SHORT).show()
            })

        compositeDisposable?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}