package com.example.quizappplus.DB

import android.content.Context
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizappplus.DB.DAOs.*
import com.example.quizappplus.DB.Entidades.*

@Database(
    entities =[
        AplicacionEntity::class,
        CategoriaEntity::class,
        ConfiguracionEntity::class,
        JuegoEntity::class,
        OpcionEntity::class,
        PreguntaEntity::class,
        PreguntaJuegoEntity::class,
        PuntuacionEntity::class,
        UsuarioEntity::class
    ],
    version = 1
)

abstract class AppDatabase:RoomDatabase(){

    abstract fun getAplicacionDao(): AplicacionDao

    abstract fun getCategoriaDao(): CategoriaDao

    abstract fun getConfiguracionDao(): ConfiguracionDao

    abstract fun getJuegoDao(): JuegoDao

    abstract fun getOpcionDao(): OpcionDao

    abstract fun getPreguntaDao(): PreguntaDao

    abstract fun getPreguntaJuegoDao(): PreguntaJuegoDao

    abstract fun getPuntuacionDao(): PuntuacionDao

    abstract fun getUsuarioDao(): UsuarioDao


    //Objeto singleton
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase::class.java,
                    "QuizzAppPro.db"
                )
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            initializeData(db)
                        }
                    })
                    .build()
            }

            return INSTANCE as AppDatabase
        }

        fun initializeData(db: SupportSQLiteDatabase) {
            //db.beginTransaction();



            //db.setTransactionSuccessful();
            //db.endTransaction();
        }
    }
}