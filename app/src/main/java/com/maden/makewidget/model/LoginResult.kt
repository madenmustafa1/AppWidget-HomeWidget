package com.maden.makewidget.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class LoginResult {
    @SerializedName("access_token")
    @Expose
    private var access_token: String? = null

    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null

    @SerializedName("refresh_token")
    @Expose
    private var refresh_token: String? = null
    fun getaccess_token(): String? {
        return access_token
    }

    fun setaccess_token(access_token: String?) {
        this.access_token = access_token
    }

    fun getrefresh_token(): String? {
        return refresh_token
    }

    fun setrefresh_token(refresh_token: String?) {
        this.refresh_token = refresh_token
    }
}
