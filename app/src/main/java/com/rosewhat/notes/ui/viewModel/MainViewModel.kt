package com.rosewhat.notes.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosewhat.notes.data.InfoListRepositoryImpl
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.usecases.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = InfoListRepositoryImpl(application)


    private val addInfoItemUseCase = AddInfoItemUseCase(infoListRepository = repository)
    private val deleteInfoItemUseCase = DeleteItemInfoUseCase(infoListRepository = repository)
    private val editInfoItemUseCase = EditInfoItemUseCase(infoListRepository = repository)
    private val getInfoItemInfoUseCase = GetInfoItemUseCase(infoListRepository = repository)
    private val getInfoListUseCase = GetInfoListUseCase(infoListRepository = repository)

    private val scope = CoroutineScope(Dispatchers.Main)

    val infoList = getInfoListUseCase.getInfoList()

    fun deleteInfo(infoItem: InfoItem) {
        scope.launch {
            deleteInfoItemUseCase.deleteInfoItem(infoItem = infoItem)
        }


    }

    fun changeInfo(infoItem: InfoItem) {
        scope.launch {
            val newItem = infoItem.copy(enabled = !infoItem.enabled)
            editInfoItemUseCase.editInfoItem(newItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}