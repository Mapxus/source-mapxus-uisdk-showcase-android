package com.mapxus.uisdkshowcase

import android.app.Application
import com.mapxus.map.auth.CognitoContext

class ShowcaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CognitoContext.registerWithApiKey(
            this,
            BuildConfig.appid,
            BuildConfig.secret
        )
    }
}