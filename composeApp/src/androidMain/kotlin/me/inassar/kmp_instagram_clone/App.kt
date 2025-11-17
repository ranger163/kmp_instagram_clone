package me.inassar.kmp_instagram_clone

import android.app.Application
import me.inassar.kmp_instagram_clone.di.initKoin
import org.koin.android.ext.koin.androidContext

class KmpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(appDeclaration = { androidContext(applicationContext) })
    }
}