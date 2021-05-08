package com.github.zharovvv.traveler.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class PaginationRecyclerView<Item> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    data class Page<Item>(val lastItem: Item, val limit: Int)

    companion object {
        private const val DEFAULT_LIMIT = 10
    }

    var limit = DEFAULT_LIMIT

    private val compositeDisposable = CompositeDisposable()
    private val scrollPageChannel: PublishSubject<Page<Item>> = PublishSubject.create()

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleItemPosition = getLastVisibleItemPosition()
                val currentAdapter =
                    adapter ?: throw IllegalArgumentException("Adapter is not defined!")
                val updatePosition = currentAdapter.itemCount - 1 - (limit / 2)
                @Suppress("UNCHECKED_CAST")
                if (lastVisibleItemPosition >= updatePosition) {
                    val realLastItemPosition = currentAdapter.itemCount - 1
                    currentAdapter as ListAdapter<Item, *>
                    scrollPageChannel.onNext(
                        Page(
                            currentAdapter.currentList[realLastItemPosition],
                            limit
                        )
                    )
                }
            }
        })
    }

    private fun getLastVisibleItemPosition(): Int {
        val currentLayoutManager =
            layoutManager ?: throw IllegalArgumentException("Layout manager is not defined!")
        when (currentLayoutManager) {
            is LinearLayoutManager -> {
                return currentLayoutManager.findLastVisibleItemPosition()
            }
            else -> throw IllegalArgumentException(
                "Unsupported layout manager type: ${currentLayoutManager.javaClass}"
            )
        }
    }

    fun addOnNewPageRequiredListener(onNewPageRequiredListener: (Page<Item>) -> Unit) {
        compositeDisposable += scrollPageChannel
            .distinctUntilChanged()
            .subscribe { page ->
                onNewPageRequiredListener(page)
            }
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}