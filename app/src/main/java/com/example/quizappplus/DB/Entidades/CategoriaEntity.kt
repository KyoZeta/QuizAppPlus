package com.example.quizappplus.DB.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Categoria")

data class CategoriaEntity(
    @PrimaryKey @ColumnInfo(name = "idCategoria") val idCategoria : Int,
    @ColumnInfo(name = "nombre") var nombre : String
)