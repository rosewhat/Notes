package com.rosewhat.notes.domain.usecases

import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository

class DeleteItemInfoUseCase(private val infoListRepository: InfoListRepository) {

    suspend fun deleteInfoItem(infoItem: InfoItem) {
        infoListRepository.deleteInfoItem(infoItem = infoItem)
    }
}