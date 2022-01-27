package com.louis.myapplication.data

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("login")
    lateinit var name: String

    @SerializedName("avatar_url")
    lateinit var avatarUrl: String
}