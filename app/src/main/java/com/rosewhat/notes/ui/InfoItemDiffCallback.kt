package com.rosewhat.notes.ui

import androidx.recyclerview.widget.DiffUtil
import com.rosewhat.notes.domain.models.InfoItem

// сравнивает отдельные элементы
class InfoItemDiffCallback : DiffUtil.ItemCallback<InfoItem>() {
    override fun areItemsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: InfoItem, newItem: InfoItem): Boolean {
        return oldItem == newItem
    }
}