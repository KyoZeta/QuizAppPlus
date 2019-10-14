package com.example.quizappplus.DB.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Aplicacion",
    foreignKeys = arrayOf(ForeignKey(entity = UsuarioEntity::class,
    parentColumns = arrayOf("idUsuario"),
    childColumns = arrayOf("idUsuarioActivo")))
)
data class AplicacionEntity(
    @PrimaryKey @ColumnInfo(name = "idUsuario") val idUsuario : Int,
    @ColumnInfo(name = "idUsuarioActivo") var idUsuarioActivo : Int?
)