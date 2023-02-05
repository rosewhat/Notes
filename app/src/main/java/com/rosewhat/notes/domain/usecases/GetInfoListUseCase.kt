package com.rosewhat.notes.domain.usecases

import androidx.lifecycle.LiveData
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository

class GetInfoListUseCase(private val infoListRepository: InfoListRepository) {

    fun getInfoList(): LiveData<List<InfoItem>> {
        return infoListRepository.getInfoList()
    }
}