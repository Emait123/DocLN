package com.example.docln.plugins

import android.app.Application
import android.content.ClipData.Item
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.docln.Novel
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "topTruyen")
data class RoomNovel(
    @PrimaryKey(autoGenerate = true) val idTruyen: Int,
    @ColumnInfo(name = "ten_truyen") val tenTruyen: String,
    @ColumnInfo(name = "coverImg") val coverImg: String
)
@Entity(tableName = "account")
data class RoomAccount(
    @PrimaryKey(autoGenerate = true) val idAccount: Int,
    @ColumnInfo(name = "displayName") val displayName: String
)

@Dao
interface NovelDao {
    @Query("SELECT * FROM topTruyen")
    fun getAll(): Flow<List<RoomNovel>>

    @Query("DELETE FROM topTruyen")
    suspend fun deleteAllNovel()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNovel(idTruyen : Int, tenTruyen : String, coverImg : String)
}

@Database(entities = [RoomNovel::class, RoomAccount::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            val tempInstance = Instance
            if (tempInstance != null) { return tempInstance }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "novel_database").build()
                Instance = instance
                return instance
            }
        }
    }
}

class NovelRepository (application: Application) {
    private var novelDao : NovelDao

    init {
        val database = AppDatabase.getDatabase(application)
        novelDao = database.novelDao()
    }

    val getAllNovel : Flow<List<RoomNovel>> = novelDao.getAll()

    suspend fun replaceTopNovel(novel : Novel) {
        novelDao.deleteAllNovel()
        novelDao.insertNovel(novel.id_truyen, novel.ten_truyen, novel.coverImg)
    }
}