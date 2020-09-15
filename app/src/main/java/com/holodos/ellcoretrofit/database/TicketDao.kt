package com.holodos.ellcoretrofit.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.holodos.ellcoretrofit.database.model.TicketData

@Dao
interface TicketDao {

    @Query("SELECT * FROM TicketData")
    fun getAll(): LiveData<List<TicketData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tickets: List<TicketData>)

    @Delete
    fun deleteAll(vararg tickets: TicketData)
}