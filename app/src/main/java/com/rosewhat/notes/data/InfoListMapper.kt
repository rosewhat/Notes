package com.rosewhat.notes.data

import com.rosewhat.notes.domain.models.InfoItem

class InfoListMapper {

    fun mapEntityToDbModel(infoItem: InfoItem) = InfoItemDbModel(
        id = infoItem.id,
        name = infoItem.name,
        count = infoItem.count,
        enabled = infoItem.enabled
    )


    fun mapDbModelToEntity(infoItemDbModel: InfoItemDbModel) = InfoItem(
        id = infoItemDbModel.id,
        name = infoItemDbModel.name,
        count = infoItemDbModel.count,
        enabled = infoItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<InfoItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }


}