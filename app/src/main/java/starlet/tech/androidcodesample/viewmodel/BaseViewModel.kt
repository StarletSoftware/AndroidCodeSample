package starlet.tech.androidcodesample.viewmodel

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import starlet.tech.androidcodesample.api.RequestType
import starlet.tech.androidcodesample.listener.ProgressListener
import starlet.tech.androidcodesample.utils.Utils

abstract class BaseViewModel {

    protected fun makeSilentRequest(request: Observable<*>, requestType: RequestType) {
        makeRequest(request, requestType, null)
    }

    @SuppressLint("CheckResult")
    protected fun makeRequest(request: Observable<*>, requestType: RequestType, progressListener: ProgressListener?) {

        progressListener?.onShowProgress()

        request.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Any>() {
                override fun onNext(o: Any) {
                    val response = o as Response<Any>
                    if (response.isSuccessful) {
                        whenNotNull(response.body(), {
                            onRequestSuccess(it, requestType)
                        }, {
                            onRequestFailed(Throwable(response.message()), requestType)
                        })

                    } else {
                        onRequestFailed(Throwable(response.message()), requestType)
                    }

                    progressListener?.onHideProgress()
                }

                override fun onError(e: Throwable) {
                    onRequestFailed(e, requestType)
                    progressListener?.onHideProgress()
                }

                override fun onComplete() {
                    progressListener?.onHideProgress()
                }
            })
    }

    protected open fun onRequestSuccess(response: Any, requestType: RequestType) {
        Utils.log("onRequestSuccess [$requestType] - $response")
    }

    protected open fun onRequestFailed(e: Throwable, requestType: RequestType) {
        Utils.log("onRequestFailed [$requestType] - $e")
    }

    inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? {
        return input?.let(callback)
    }

    inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R, callback2: () -> R): R? {
        return input?.let(callback) ?: run(callback2)
    }

}