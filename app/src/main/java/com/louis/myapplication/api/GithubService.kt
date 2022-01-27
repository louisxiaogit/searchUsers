package com.louis.myapplication.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int?
    ): Single<UserSearchResponse?>?
}