package com.zasko.boxtool.base.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

interface BindLife {
    val compositeDisposable: CompositeDisposable

    fun <T : Any> Observable<T>.bindLife() {
        compositeDisposable.add(this.subscribe({}, {}, {}))
    }


    fun <T : Any> Single<T>.bindLife() {
        compositeDisposable.add(this.subscribe({}, { }))
    }

    fun Disposable.bindLife() {
        compositeDisposable.add(this)
    }
}

fun BindLife(): BindLife = object : BindLife {
    override val compositeDisposable: CompositeDisposable
        get() = CompositeDisposable()

}