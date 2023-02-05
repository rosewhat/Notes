package com.rosewhat.notes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosewhat.notes.data.InfoListRepositoryImpl
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.usecases.*

class MainViewModel : ViewModel() {

    private val repository = InfoListRepositoryImpl


    private val addInfoItemUseCase = AddInfoItemUseCase(infoListRepository = repository)
    private val deleteInfoItemUseCase = DeleteItemInfoUseCase(infoListRepository = repository)
    private val editInfoItemUseCase = EditInfoItemUseCase(infoListRepository = repository)
    private val getInfoItemInfoUseCase = GetInfoItemUseCase(infoListRepository = repository)
    private val getInfoListUseCase = GetInfoListUseCase(infoListRepository = repository)

    val infoList = getInfoListUseCase.getInfoList()

    fun deleteInfo(infoItem: InfoItem) {
        deleteInfoItemUseCase.deleteInfoItem(infoItem = infoItem)

    }

    fun changeInfo(infoItem: InfoItem) {
        val newItem = infoItem.copy(enabled = !infoItem.enabled)
        editInfoItemUseCase.editInfoItem(newItem)
    }
}