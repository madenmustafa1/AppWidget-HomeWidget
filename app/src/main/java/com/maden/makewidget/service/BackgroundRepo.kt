package com.maden.makewidget.service

import com.maden.makewidget.model.LoginResult
import com.maden.makewidget.model.count_model.CountModel
import com.maden.makewidget.service.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*

class BackgroundRepo {
    private var  disposable: CompositeDisposable = CompositeDisposable()
    private var  retrofit: Repository = Repository()

    //init { login() }

    suspend fun login(): String {
        val userName: String = "admin"
        val password: String = "DK.Password.123*"
        var token = ""

        val waitFor = CoroutineScope(Dispatchers.IO).async {
            val disposable = disposable.add(
                retrofit
                    .connectToken(userName, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<LoginResult>() {
                        override fun onSuccess(t: LoginResult) {
                            try {
                                token = t.getaccess_token()!!
                                //getCount(auth = token)
                                //result = true


                            } catch (e: Exception) {
                                // -> Handle Exception
                            }
                        }
                        override fun onError(e: Throwable) {
                            // -> Handle Exception
                        }
                    })
            )
            delay(1500)
            return@async token
        }
        waitFor.await()
        return token
    }


    suspend fun getCount(auth: String): CountModel? {
        var countModel: CountModel? = null
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            disposable.add(
                retrofit
                    .getCount(auth = "Bearer $auth", "2022-04-16")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<CountModel>() {
                        override fun onSuccess(t: CountModel) {
                            try {
                                countModel = t
                            } catch (e: Exception) {
                                // -> Handle Exception
                            }
                        }
                        override fun onError(e: Throwable) {
                            // -> Handle Exception
                        }
                    })
            )

            delay(1500)
            return@async countModel
        }
        waitFor.await()

        return countModel
    }
}