package com.mk.usandosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.mk.usandosqlite.entity.Cadastro

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "dbfile.sqlite"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cadastro"
        const val COLUMN_ID = "0"
        const val COLUMN_NOME = "0"
        const val COLUMN_TELEFONE = "0"

    }


    override fun onCreate(banco: SQLiteDatabase?) {
        banco?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT)")
    }

    override fun onUpgrade(
        banco: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        banco?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(banco)
    }


    fun inserir(cadastro: Cadastro){

        val registro = ContentValues()

        registro.put("nome", cadastro.nome)
        registro.put("telefone", cadastro.telefone)

        writableDatabase.insert(TABLE_NAME, null, registro)

    }
    fun alterar(){

    }
    fun excluir(){

    }
    fun pesquisar(){

    }
    fun listar(){

    }


}

