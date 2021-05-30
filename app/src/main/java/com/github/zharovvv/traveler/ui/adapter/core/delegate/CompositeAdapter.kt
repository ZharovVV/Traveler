package com.github.zharovvv.traveler.ui.adapter.core.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.*

class CompositeAdapter<BaseItemType : DelegateBaseItemType>() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(delegates: List<AdapterDelegate<out BaseItemType>>) : this() {
        delegates.forEach {
            addAdapter(it)
        }
    }

    private val adapterDelegatesManager = AdapterDelegatesManager<BaseItemType>()
    private val mDiffer: AsyncListDiffer<BaseItemType> = AsyncListDiffer<BaseItemType>(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(
            object : DiffUtil.ItemCallback<BaseItemType>() {
                override fun areItemsTheSame(
                    oldItem: BaseItemType,
                    newItem: BaseItemType
                ): Boolean {
                    return adapterDelegatesManager.areItemsTheSame(oldItem, newItem)
                }

                override fun areContentsTheSame(
                    oldItem: BaseItemType,
                    newItem: BaseItemType
                ): Boolean {
                    return adapterDelegatesManager.areContentsTheSame(oldItem, newItem)
                }
            }).build()
    )

    @Suppress("MemberVisibilityCanBePrivate")
    fun addAdapter(adapterDelegate: AdapterDelegate<out BaseItemType>): CompositeAdapter<BaseItemType> {
        adapterDelegatesManager.addDelegate(adapterDelegate)
        return this
    }

    operator fun plus(adapterDelegate: AdapterDelegate<out BaseItemType>): CompositeAdapter<BaseItemType> {
        addAdapter(adapterDelegate)
        return this
    }

    override fun getItemViewType(position: Int): Int {
        return adapterDelegatesManager.getItemViewType(
            mDiffer.currentList[position]
        )
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegatesManager.onBindViewHolder(holder, mDiffer.currentList[position])
    }

    fun submitList(list: List<BaseItemType>) {
        mDiffer.submitList(list)
    }
}