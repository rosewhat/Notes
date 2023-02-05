package com.rosewhat.notes.domain.usecases

import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository

class GetInfoItemUseCase(private val infoListRepository: InfoListRepository) {

    fun getInfoItem(infoItem: Int): InfoItem {
        return infoListRepository.getInfoItem(infoItem = infoItem)
    }
}