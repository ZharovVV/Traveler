package com.github.zharovvv.traveler.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.zharovvv.traveler.R
import com.github.zharovvv.traveler.utils.dpToPx
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class PaginationRecyclerView<Item> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    data class Page<Item>(val lastItem: Item, val limit: Int)
    private enum class PageLocation {
        TOP, BOTTOM
    }

    private enum class PaginationState {
        READY_TO_LOADING,
        LOADING,
        ALL_PAGES_LOADED
    }

    companion object {
        private const val DEFAULT_LIMIT = 10
    }

    var limit = DEFAULT_LIMIT
    private var paginationState: PaginationState = PaginationState.READY_TO_LOADING
    private val compositeDisposable = CompositeDisposable()
    private val scrollPageChannel: PublishSubject<Page<Item>> = PublishSubject.create()
    private val pageLocationChannel: PublishSubject<PageLocation> =
        PublishSubject.create<PageLocation>()
            .apply {
                compositeDisposable += this.distinctUntilChanged()
                    .subscribe { pageLocation ->
                        when (pageLocation) {
                            PageLocation.TOP -> {
                                progressBar.visibility = View.INVISIBLE
                            }
                            else -> {
                                progressBar.visibility = View.VISIBLE
                            }
                        }
                    }
            }

    private lateinit var progressBar: ProgressBar

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleItemPosition = getLastVisibleItemPosition()
                val currentAdapter =
                    adapter ?: throw IllegalArgumentException("Adapter is not defined!")
                val updatePosition = currentAdapter.itemCount - 1 - (limit / 2)
                val realLastItemPosition = currentAdapter.itemCount - 1
                @Suppress("UNCHECKED_CAST")
                if (lastVisibleItemPosition >= updatePosition) {
                    when (paginationState) {
                        PaginationState.READY_TO_LOADING -> {
                            currentAdapter as ListAdapter<Item, *>
                            scrollPageChannel.onNext(
                                Page(currentAdapter.currentList[realLastItemPosition], limit)
                            )
                            setPadding( //TODO некорректное поведение при пересоздании экрана
                                0,
                                8.dpToPx(),
                                0,
                                progressBar.height + 8.dpToPx() + 16.dpToPx()
                            )
                        }
                        PaginationState.LOADING -> {
                            setPadding(
                                0,
                                8.dpToPx(),
                                0,
                                progressBar.height + 8.dpToPx() + 16.dpToPx()
                            )
                        }
                        else -> {
                            //doNothing
                        }
                    }
                }

                if (paginationState == PaginationState.LOADING) {
                    if (lastVisibleItemPosition >= realLastItemPosition) {
                        pageLocationChannel.onNext(PageLocation.BOTTOM)
                    } else {
                        pageLocationChannel.onNext(PageLocation.TOP)
                    }
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initLoadingIndicator()
    }

    //TODO Рассмотреть возможность использования ItemDecorator/ItemAnimator для размещения логики показа индикотора загрузки.
    private fun initLoadingIndicator() {
        when (val parentContainer = parent) {
            is ConstraintLayout -> {
                progressBar = ProgressBar(context)
                progressBar.indeterminateTintList = ColorStateList.valueOf(
                    resources.getColor(R.color.blue, null)
                )
                val constraintLayoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                    )
                )
                constraintLayoutParams.bottomToBottom = 0
                constraintLayoutParams.startToStart = 0
                constraintLayoutParams.endToEnd = 0
                constraintLayoutParams.setMargins(0, 8.dpToPx(), 0, 16.dpToPx())
                progressBar.layoutParams = constraintLayoutParams
                progressBar.visibility = View.INVISIBLE
                parentContainer.addView(progressBar)
            }
            else -> throw IllegalArgumentException("RecyclerView parent is not ConstraintLayout! $parentContainer")
        }
    }

    fun addOnNewPageRequiredListener(onNewPageRequiredListener: (Page<Item>) -> Unit) {
        compositeDisposable += scrollPageChannel
            .distinctUntilChanged()
            .subscribe { page ->
                onNewPageRequiredListener(page)
            }
    }

    fun onStartPageLoading() {
        paginationState = PaginationState.LOADING
    }

    fun onNewPageLoaded() {
        if (paginationState == PaginationState.LOADING) {//TODO при пересоздании прогрессбар почему-то ещё не проинициализирован
            progressBar.visibility = View.INVISIBLE
        }
        paginationState = PaginationState.READY_TO_LOADING
    }

    fun onAllPagesLoaded() {
        if (paginationState == PaginationState.LOADING) {//TODO при пересоздании прогрессбар почему-то ещё не проинициализирован
            setPadding(0, 8.dpToPx(), 0, 8.dpToPx())
            progressBar.visibility = View.INVISIBLE
        }
        paginationState = PaginationState.ALL_PAGES_LOADED
        compositeDisposable.dispose()
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}