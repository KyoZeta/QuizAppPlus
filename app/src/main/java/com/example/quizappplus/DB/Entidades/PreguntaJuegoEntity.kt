package com.example.quizappplus.DB.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "PreguntaJuego",
    primaryKeys = arrayOf("idJuego","idPregunta"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = JuegoEntity::class,
            parentColumns = arrayOf("idJuego"),
            childColumns = arrayOf("idJuego"),
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = PreguntaEntity::class,
            parentColumns = arrayOf("idPregunta"),
            childColumns = arrayOf("idPregunta"),
            onDelete = ForeignKey.NO_ACTION)
    )
)
data class PreguntaJuegoEntity (
    @ColumnInfo(name = "idjuego") val idJuego:Int,
    @ColumnInfo(name = "idPregunta") val idPregunta:Int,
    @ColumnInfo(name = "contestada") val contestada:Boolean = false,
    @ColumnInfo(name = "correcta") val correcta:Boolean,
    @ColumnInfo(name = "optionsCheated") val optionsCheated:String,
    @ColumnInfo(name = "cheated") val cheated:Boolean
)