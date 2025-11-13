package me.inassar.core.cache

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDataBaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/database.db"
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

actual val platformCacheModule = module {
    single<RoomDatabase.Builder<AppDatabase>> { getDataBaseBuilder() }
}