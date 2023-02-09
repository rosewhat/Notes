package com.rosewhat.notes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rosewhat.notes.R
import com.rosewhat.notes.domain.models.InfoItem
import com.rosewhat.notes.ui.InfoItemDiffCallback
import com.rosewhat.notes.ui.InfoItemViewHolder


class InfoListAdapter :
    ListAdapter<InfoItem, InfoItemViewHolder>(InfoItemDiffCallback()) {


    var onInfoListLongClickListener: ((InfoItem) -> Unit)? = null
    var onInfoItemClickListener: ((InfoItem) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoItemViewHolder {

        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_list_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_list_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return InfoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoItemViewHolder , position: Int) {
        val infoItem = getItem(position)
        holder.tvName.text = infoItem.name
        holder.tvCount.text = infoItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onInfoListLongClickListener?.invoke(infoItem)
            true
        }
        holder.itemView.setOnClickListener {
            onInfoItemClickListener?.invoke(infoItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }




    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 10
        const val MAX_POOL_SIZE = 15
    }
}