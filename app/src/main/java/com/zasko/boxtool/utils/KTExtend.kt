package com.zasko.boxtool.utils

import android.content.res.Resources
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


inline fun safeInvoke(
    catch: (e: Exception) -> Unit = {}, finally: () -> Unit = {}, whenTry: () -> Unit
) {
    try {
        whenTry.invoke()
    } catch (e: Exception) {
        catch.invoke(e)
    } finally {
        finally.invoke()
    }
}


fun runOnIO(action: () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) { action.invoke() }
}

fun runOnMain(action: () -> Unit) {
    MainScope().launch { action.invoke() }
}

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()


fun <T : Any> Single<T>.swiThread(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}