package com.rosewhat.notes.domain.repository

import androidx.lifecycle.LiveData
import com.rosewhat.notes.domain.models.InfoItem

interface InfoListRepository {
    fun addInfoItem(infoItem: InfoItem)
    fun deleteInfoItem(infoItem: InfoItem)
    fun editInfoItem(infoItem: InfoItem)
    fun getInfoItem(infoItem: Int) : InfoItem
    fun getInfoList() : LiveData<List<InfoItem>>
}