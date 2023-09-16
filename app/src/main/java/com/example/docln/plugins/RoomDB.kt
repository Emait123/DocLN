package com.example.docln.plugins

import android.content.ClipData.Item
import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "truyen")
data class RoomNovel(
    @PrimaryKey val id_truyen: Int,
    @ColumnInfo(name = "ten_truyen") val tenTruyen: String,
    @ColumnInfo(name = "coverImg") val coverImg: String
)

@Dao
interface NovelDao {
    @Query("SELECT * FROM truyen")
    fun getAll(): Flow<List<RoomNovel>>
}

@Database(entities = [RoomNovel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "novel_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

interface NovelRepository {
    fun getAllNovel(): Flow<List<RoomNovel>>
}

class OfflineNovelRepository (private val novelDao: NovelDao) : NovelRepository {
    override fun getAllNovel(): Flow<List<RoomNovel>> = novelDao.getAll()

}

//override val itemsRepository: NovelRepository by lazy {
//    OfflineNovelRepository(AppDatabase.getDatabase(context).novelDao())
//}