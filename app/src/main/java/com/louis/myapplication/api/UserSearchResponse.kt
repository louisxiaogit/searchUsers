package com.louis.myapplication.api

import com.google.gson.annotations.SerializedName
import com.louis.myapplication.data.User

data class UserSearchResponse(
    @SerializedName("items") val usersList: List<User>

)