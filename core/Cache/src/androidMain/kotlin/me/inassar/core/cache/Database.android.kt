package me.inassar.core.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.dsl.module

fun getDataBaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext ?: throw IllegalStateException("Not yet initialized")
    val dbFile = appContext.getDatabasePath("database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

actual val platformCacheModule = module {
    single<RoomDatabase.Builder<AppDatabase>> { getDataBaseBuilder(get()) }
}