package com.zasko.boxtool.utils

import android.content.res.Resources
import android.view.View
import androidx.core.view.ViewCompat
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*


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

inline fun workOnUI(scope: CoroutineScope = GlobalScope, crossinline handler: () -> Unit): Job {
    return scope.launch(Dispatchers.Main) { handler.invoke() }
}

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()


fun <T : Any> Single<T>.swiThread(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

inline fun View.onClick(crossinline handler: (v: View) -> Unit) {
    setOnClickListener { view ->
        if (ClickUtils.isFastDoubleClick(view)) {
            return@setOnClickListener
        }

        workOnUI { handler.invoke(view) }.attach(view)
    }
}

fun Job.attach(view: View) = ViewLifecycleJob(view, this)


class ViewLifecycleJob(
    private val view: View, private val wrapped: Job
) : Job by wrapped, View.OnAttachStateChangeListener {
    init {
        if (ViewCompat.isAttachedToWindow(view)) {
            view.addOnAttachStateChangeListener(this)
        } else {
            cancel()
        }
        invokeOnCompletion {
            view.removeOnAttachStateChangeListener(this)
        }
    }

    override fun onViewDetachedFromWindow(v: View) {
        cancel()
        view.removeOnAttachStateChangeListener(this)
    }

    override fun onViewAttachedToWindow(v: View) = Unit
}