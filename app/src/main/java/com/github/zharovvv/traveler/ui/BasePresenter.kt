package com.github.zharovvv.traveler.ui

import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    init {
        Log.i("presenter_lifecycle", "presenter#init $this")  //TODO delete me
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        Log.i("presenter_lifecycle", "presenter#onDestroy")  //TODO delete me
    }

    protected fun Disposable.keep(): Disposable {
        compositeDisposable += this
        return this
    }
}