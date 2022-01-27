package com.louis.myapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.louis.myapplication.api.UserSearchResponse
import com.louis.myapplication.data.User
import com.louis.myapplication.data.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val disposables = CompositeDisposable()
    internal var users = MutableLiveData<List<User>>()
    internal var usersLoadMore = MutableLiveData<List<User>>()
    internal var loading = MutableLiveData<Boolean>()
    internal var error = MutableLiveData<String>()
    private val PER_PAGE = 10

    private var usersRepository = UsersRepository()
    private var page: Int = 2
    private var lastQuery = ""

    fun loading() {
        loading.postValue(true)
    }

    fun searchUsers(query: String) {
        if (query == lastQuery) return

        disposables.add(
            usersRepository.searchUsers(query, 1, PER_PAGE)!!.subscribeOn(Schedulers.io())!!
                .observeOn(AndroidSchedulers.mainThread())!!
                .subscribeWith(object : DisposableSingleObserver<UserSearchResponse>() {
                    override fun onSuccess(t: UserSearchResponse) {
                        users.postValue(t.usersList)
                        loading.postValue(false)
                    }

                    override fun onError(e: Throwable) {
                        error.postValue(e.message)
                        loading.postValue(false)
                    }
                })
        )
    }

    fun loadMore(query: String) {
        disposables.add(
            usersRepository.searchUsers(query, page, PER_PAGE)!!.subscribeOn(Schedulers.io())!!
                .observeOn(AndroidSchedulers.mainThread())!!
                .subscribeWith(object : DisposableSingleObserver<UserSearchResponse>() {
                    override fun onSuccess(t: UserSearchResponse) {
                        usersLoadMore.postValue(t.usersList)
                        page++
                        loading.postValue(false)
                    }

                    override fun onError(e: Throwable) {
                        error.postValue(e.message)
                        loading.postValue(false)
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear();
    }

}