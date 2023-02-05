package com.rosewhat.notes.domain.usecases

import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository

class EditInfoItemUseCase(private val infoListRepository: InfoListRepository) {

    fun editInfoItem(infoItem: InfoItem) {
        infoListRepository.editInfoItem(infoItem = infoItem)
    }
}