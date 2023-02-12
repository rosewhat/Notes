package com.rosewhat.notes.domain.repository

import androidx.lifecycle.LiveData
import com.rosewhat.notes.domain.models.InfoItem

interface InfoListRepository {
    suspend fun addInfoItem(infoItem: InfoItem)
    suspend fun deleteInfoItem(infoItem: InfoItem)
    suspend fun editInfoItem(infoItem: InfoItem)
    suspend fun getInfoItem(infoItem: Int): InfoItem
    fun getInfoList() : LiveData<List<InfoItem>>
}