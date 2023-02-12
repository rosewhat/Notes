package com.rosewhat.notes.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.domain.repository.InfoListRepository
import java.lang.RuntimeException

class InfoListRepositoryImpl(application: Application) : InfoListRepository {

    private val infoListDao = AppDatabase.getInstance(application).infoListDao()
    private val mapper = InfoListMapper()


    override fun addInfoItem(infoItem: InfoItem) {
        infoListDao.addInfoItem(mapper.mapEntityToDbModel(infoItem))
    }

    override fun deleteInfoItem(itemInfo: InfoItem) {
        infoListDao.deleteInfoItem(itemInfo.id)
    }

    override fun editInfoItem(infoItem: InfoItem) {
        // заменяет объект replace
        infoListDao.addInfoItem(mapper.mapEntityToDbModel(infoItem))
    }

    override fun getInfoItem(infoItem: Int): InfoItem {
        val dbModel = infoListDao.getInfoItem(infoItem)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getInfoList(): LiveData<List<InfoItem>> = Transformations.map(
        infoListDao.getInfoList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }

}