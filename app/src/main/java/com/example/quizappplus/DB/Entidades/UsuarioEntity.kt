package com.example.quizappplus.DB.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = ConfiguracionEntity::class,
            parentColumns = arrayOf("idConfiguracion"),
            childColumns = arrayOf("idConfiguracion"),
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = AplicacionEntity::class,
            parentColumns = arrayOf("idAplicacion"),
            childColumns = arrayOf("idAplicacion"),
            onDelete = ForeignKey.NO_ACTION)
    )
)
data class UsuarioEntity (
    @PrimaryKey @ColumnInfo(name = "idUsuario") val idUsuario:Int,
    @ColumnInfo(name = "idConfiguracion") val idConcifuracion:Int,
    @ColumnInfo(name = "idAplicacion") val idAplicacion:Int,
    @ColumnInfo(name = "imagenAvatar") var imagenAvatar:Int,
    @ColumnInfo(name = "contraseña") var contraseña:String
)