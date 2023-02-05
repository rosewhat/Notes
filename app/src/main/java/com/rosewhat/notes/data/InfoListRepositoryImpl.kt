package com.rosewhat.notes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository
import java.lang.RuntimeException

object InfoListRepositoryImpl : InfoListRepository {
    private val infoList = mutableListOf<InfoItem>()
    private val infoListLD = MutableLiveData<List<InfoItem>>()


    private var autoIncrementId = 0

    override fun addInfoItem(infoItem: InfoItem) {
        if (infoItem.id == InfoItem.UNDEFINED_ID) {
            infoItem.id = autoIncrementId++
        }
        infoList.add(infoItem)
        updateList()
    }

    override fun deleteInfoItem(itemInfo: InfoItem) {
        infoList.remove(itemInfo)
    }

    override fun editInfoItem(infoItem: InfoItem) {
        val oldElement = getInfoItem(infoItem.id)
        infoList.remove(oldElement)
        addInfoItem(infoItem)
    }

    override fun getInfoItem(infoItem: Int): InfoItem {
        return infoList.find {
            it.id == infoItem
        } ?: throw RuntimeException("No item $infoItem")
    }

    override fun getInfoList(): LiveData<List<InfoItem>> {
        return infoListLD
    }
    private fun updateList() {
        infoListLD.value = infoList.toList()
    }
}