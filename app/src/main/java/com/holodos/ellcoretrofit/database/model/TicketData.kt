package com.holodos.ellcoretrofit.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TicketData(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @Embedded val sender: Sender
)