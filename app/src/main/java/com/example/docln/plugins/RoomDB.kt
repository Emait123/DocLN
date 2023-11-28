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
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
//Entity: Khai báo cấu trúc mảng
@Entity(tableName = "truyen")
data class RoomNovel(
    @ColumnInfo(name = "id_truyen") @PrimaryKey(autoGenerate = true) val id_truyen: Int,
    @ColumnInfo(name = "ten_truyen") val ten_truyen: String,
    val tomtat: String,
    val coverImg: String,
    val tacgia: String,
    val minhhoa: String,
    val tag: String,
    val trangthai: String,
    val view: Int,
    val tenkhac: String
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

@Entity(tableName = "thisinh")
data class ThiSinh(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val tenTS: String,
    val ketQua: Int
)


//Các Interface chứa các function sẽ thực hiện với bảng đó
@Dao
interface NovelDao {
    @Query("SELECT * FROM truyen")
    fun getAll(): Flow<List<RoomNovel>>

    @Query("SELECT * FROM truyen WHERE id_truyen = :id")
    fun getNovel(id: Int): Flow<RoomNovel>

    @Query("DELETE FROM truyen")
    suspend fun deleteAllNovel()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNovel(novel: RoomNovel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNovel(novel: RoomNovel)
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

@Dao
interface ThiSinhDao {
    @Query("SELECT * FROM thisinh")
    fun getAll(): Flow<List<ThiSinh>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThiSinh(thiSinh: ThiSinh)
}

@Database(entities = [RoomNovel::class, RoomAccount::class, ThiSinh::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao
    abstract fun accountDao(): AccountDao
    abstract fun thisinhDao(): ThiSinhDao

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
    private val accountDao: AccountDao,
    private val thiSinhDao: ThiSinhDao
) {
    val account = accountDao.getAccount()
    val novels = novelDao.getAll()
    val thiSinhs = thiSinhDao.getAll()

    suspend fun insertOrUpdateThiSinh(thiSinh: ThiSinh) {
        thiSinhDao.insertThiSinh(thiSinh)
    }

    suspend fun logOut(account: RoomAccount){
        accountDao.deleteAccount(account)
    }

    suspend fun logIn(account: RoomAccount){
        accountDao.insertAccount(account)
    }

    suspend fun insertOrUpdateNovel(novel: RoomNovel) {
        novelDao.insertNovel(novel)
    }

    suspend fun getNovel(id: Int) {
        novelDao.getNovel(id)
    }
//    init {
//        val database = AppDatabase.getDatabase(application)
//        novelDao = database.novelDao()
//    }
}