package com.rosewhat.notes.domain.usecases

import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository

class EditInfoItemUseCase(private val infoListRepository: InfoListRepository) {

    suspend fun editInfoItem(infoItem: InfoItem) {
        infoListRepository.editInfoItem(infoItem = infoItem)
    }
}