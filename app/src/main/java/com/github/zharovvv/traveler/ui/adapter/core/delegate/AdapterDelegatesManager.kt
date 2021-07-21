package com.github.zharovvv.traveler.ui.adapter.core.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class AdapterDelegatesManager<BaseItemType : DelegateBaseItemType> {

    private var delegatesCounter: Int = 0
    private val delegatesMap: MutableMap<Int, AdapterDelegate<out BaseItemType>> = mutableMapOf()
    private val viewTypeMap: MutableMap<DelegateViewType, Int> = mutableMapOf()

    fun addDelegate(adapterDelegate: AdapterDelegate<out BaseItemType>): AdapterDelegatesManager<BaseItemType> {
        val viewType: Int = delegatesCounter
        delegatesMap[viewType] = adapterDelegate
        viewTypeMap[adapterDelegate.delegateViewType] = viewType
        ++delegatesCounter
        return this
    }

    fun <Item : BaseItemType> getItemViewType(item: Item): Int {
        return viewTypeMap[item.delegateViewType] ?: throw NoSuchElementException()
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesMap[viewType]?.onCreateViewHolder(parent)
            ?: throw NoSuchElementException()
    }

    fun <Item : BaseItemType> onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
        val delegate: AdapterDelegate<out BaseItemType> =
            delegatesMap[getItemViewType(item)] ?: throw NoSuchElementException()
        delegate as AdapterDelegate<Item>
        delegate.onBindViewHolder(holder, item)
    }

    //some magic (*crutch)
    fun <OldItem : BaseItemType, NewItem : BaseItemType> areItemsTheSame(
        oldItem: OldItem,
        newItem: NewItem
    ): Boolean {
        if (oldItem.delegateViewType == newItem.delegateViewType) {
            val delegate: AdapterDelegate<out BaseItemType> =
                delegatesMap[getItemViewType(oldItem)] ?: throw NoSuchElementException()
            delegate as AdapterDelegate<OldItem>
            newItem as OldItem
            return delegate.diffUtilItemCallback.areItemsTheSame(oldItem, newItem)
        }
        return false
    }

    fun <Item : BaseItemType> areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        val delegate: AdapterDelegate<out BaseItemType> =
            delegatesMap[getItemViewType(oldItem)] ?: throw NoSuchElementException()
        delegate as AdapterDelegate<Item>
        return delegate.diffUtilItemCallback.areContentsTheSame(oldItem, newItem)
    }
}