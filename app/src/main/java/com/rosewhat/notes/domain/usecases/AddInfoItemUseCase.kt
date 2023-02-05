package com.rosewhat.notes.domain.usecases

import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository

class AddInfoItemUseCase(private val infoListRepository: InfoListRepository) {

    fun addInfoItem(infoItem: InfoItem) {
        infoListRepository.addInfoItem(infoItem = infoItem)
    }
}