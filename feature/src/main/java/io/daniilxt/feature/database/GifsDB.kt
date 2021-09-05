package io.daniilxt.feature.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.daniilxt.feature.database.dao.GifDao
import io.daniilxt.feature.database.models.GifDataBaseModel

@Database(entities = [GifDataBaseModel::class], version = 1, exportSchema = false)
abstract class GifsDB : RoomDatabase() {
    abstract fun gifsDao(): GifDao

    companion object {
        @Volatile
        private var INSTANCE: GifsDB? = null

        fun getDatabase(context: Context): GifsDB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GifsDB::class.java,
                    "gifs_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}