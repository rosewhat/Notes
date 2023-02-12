package com.rosewhat.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InfoListDao {

    @Query("SELECT * FROM info_items")
     fun getInfoList(): LiveData<List<InfoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInfoItem(infoItemDbModel: InfoItemDbModel)

    @Query("DELETE  FROM info_items WHERE id=:infoItemId")
    suspend fun deleteInfoItem(infoItemId: Int)


    @Query("SELECT * FROM info_items WHERE id=:infoItemId LIMIT 1")
    suspend fun getInfoItem(infoItemId: Int): InfoItemDbModel

}