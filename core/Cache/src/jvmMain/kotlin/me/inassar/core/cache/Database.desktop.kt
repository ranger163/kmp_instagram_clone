package me.inassar.core.cache

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.dsl.module
import java.io.File

fun getDataBaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "database.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath
    )
}

actual val platformCacheModule = module {
    single <RoomDatabase.Builder<AppDatabase>>{ getDataBaseBuilder() }
}