package me.inassar.kmp_instagram_clone

import android.app.Application
import me.inassar.kmp_instagram_clone.di.initKoin
import org.koin.android.ext.koin.androidContext

/**
 * Android `Application` entry point responsible for bootstrapping dependency injection.
 */
class KmpApp : Application() {

    /**
     * Starts the shared Koin graph and ties it to the Android application context.
     */
    override fun onCreate() {
        super.onCreate()
        initKoin(appDeclaration = { androidContext(applicationContext) })
    }
}
