package com.holodos.ellcoretrofit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.holodos.ellcoretrofit.database.model.TicketData

@Database(entities = [TicketData::class], version = 1, exportSchema = false)
abstract class TicketDatabase: RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        private var INSTANCE: TicketDatabase? = null

        fun getInstance(context: Context): TicketDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    TicketDatabase::class.java,
                    "ticketDB")
                    .build()
            }

            return INSTANCE as TicketDatabase
        }
    }
}