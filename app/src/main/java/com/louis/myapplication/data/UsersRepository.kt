package com.louis.myapplication.data

import com.louis.myapplication.api.AppClientManager
import com.louis.myapplication.api.GithubService
import com.louis.myapplication.api.UserSearchResponse
import io.reactivex.Single

class UsersRepository {
    fun searchUsers(query: String?, page: Int?, perPage: Int?): Single<UserSearchResponse?>? {
        return AppClientManager.client.create(GithubService::class.java)
            .searchUsers(query, page, perPage)
    }
}