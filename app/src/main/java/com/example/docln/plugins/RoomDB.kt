package com.example.docln.plugins

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "topTruyen")
data class RoomNovel(
    @PrimaryKey(autoGenerate = true) val idTruyen: Int,
    @ColumnInfo(name = "ten_truyen") val tenTruyen: String,
    @ColumnInfo(name = "coverImg") val coverImg: String,
)
@Entity(tableName = "account")
data class RoomAccount(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "accountID") val accountID: Int,
    @ColumnInfo(name = "displayName") val displayName: String
){
    @Ignore
    constructor(accountID: Int, displayName: String) : this (null, accountID, displayName)
}

@Dao
interface NovelDao {
    @Query("SELECT * FROM topTruyen")
    fun getAll(): Flow<List<RoomNovel>>

    @Query("DELETE FROM topTruyen")
    suspend fun deleteAllNovel()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNovel(novel: RoomNovel)
}

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAccount(): Flow<List<RoomAccount>>

    @Delete
    suspend fun deleteAccount(account: RoomAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: RoomAccount)
}

@Database(entities = [RoomNovel::class, RoomAccount::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao
    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            val tempInstance = Instance
            if (tempInstance != null) { return tempInstance }

            synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, "novel_database").fallbackToDestructiveMigration().build()
                Instance = instance
                return instance
            }
        }
    }
}

class DBRepository (
    private val novelDao: NovelDao,
    private val accountDao: AccountDao
) {
    val account = accountDao.getAccount()

    suspend fun logOut(account: RoomAccount){
        accountDao.deleteAccount(account)
    }

    suspend fun logIn(account: RoomAccount){
        accountDao.insertAccount(account)
    }

//    init {
//        val database = AppDatabase.getDatabase(application)
//        novelDao = database.novelDao()
//    }

//    val getAllNovel : Flow<List<RoomNovel>> = novelDao.getAll()
//
//    suspend fun replaceTopNovel(novel : Novel) {
//        novelDao.deleteAllNovel()
//        novelDao.insertNovel(novel.id_truyen, novel.ten_truyen, novel.coverImg)
//    }
}