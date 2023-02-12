package com.rosewhat.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info_items")
data class InfoItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)