package com.github.zharovvv.traveler.ui.adapter.core.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegate<ItemType : DelegateBaseItemType> {

    val delegateViewType: DelegateViewType

    val diffUtilItemCallback: DiffUtil.ItemCallback<ItemType>

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ItemType)
}