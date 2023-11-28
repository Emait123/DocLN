package com.example.docln.plugins

import android.content.Context

object Graph {
    lateinit var db:AppDatabase
        private set

    val repository by lazy {
        DBRepository(
            novelDao = db.novelDao(),
            accountDao = db.accountDao(),
            thiSinhDao = db.thisinhDao()
        )
    }

    fun provide(context: Context) {
        db = AppDatabase.getDatabase(context)
    }
}