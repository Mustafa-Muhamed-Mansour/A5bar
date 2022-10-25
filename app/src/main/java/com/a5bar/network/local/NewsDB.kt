package com.a5bar.network.local

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*
import com.a5bar.common.Constant
import com.a5bar.model.NewsModel
import com.a5bar.response.SourceConverter


@Database(entities = [NewsModel::class], version = 1)

@TypeConverters(SourceConverter::class)

abstract class NewsDB : RoomDatabase() {

    abstract fun getNewDao(): NewsDao

    companion object {

        @Volatile
        private var instance: NewsDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDB::class.java,
                Constant.DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
    }
}