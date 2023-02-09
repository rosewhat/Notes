package com.rosewhat.notes.ui

import androidx.recyclerview.widget.DiffUtil
import com.rosewhat.notes.domain.models.InfoItem

// сравнивает списки
class InfoListDiffCallback(
    private val oldList: List<InfoItem>,
    private val newList: List<InfoItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    // объект тот или нет
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    // поле объекта
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}