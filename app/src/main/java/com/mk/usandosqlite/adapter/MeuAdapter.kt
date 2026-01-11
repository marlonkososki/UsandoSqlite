package com.mk.usandosqlite.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.mk.usandosqlite.MainActivity
import com.mk.usandosqlite.R
import com.mk.usandosqlite.database.DatabaseHandler
import com.mk.usandosqlite.entity.Cadastro
import kotlin.jvm.java

class MeuAdapter(val context: Context, val cursor: Cursor) : BaseAdapter() {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(posicao: Int): Any? {
        cursor.moveToPosition(posicao)
        val cadastro = Cadastro(
            cursor.getInt(DatabaseHandler.COLUMN_ID.toInt()),
            cursor.getString(DatabaseHandler.COLUMN_NOME.toInt()),
            cursor.getString(DatabaseHandler.COLUMN_TELEFONE.toInt())
        )
        return cadastro
    }

    override fun getItemId(posicao: Int): Long {
        cursor.moveToPosition(posicao)
        return cursor.getInt(DatabaseHandler.COLUMN_ID.toInt()).toLong()
    }

    override fun getView(
        posicao: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.elemento_lista, null)

        val tvNomeElementoLista = v.findViewById<TextView>(R.id.tvNomeElementoLista)
        val tvTelefoneElementoLista = v.findViewById<TextView>(R.id.tvTelefoneElementoLista)
        val btEditartElementoLista = v.findViewById<ImageButton>(R.id.btEditarElementoLista)


        cursor.moveToPosition(posicao)

        tvNomeElementoLista.text = cursor.getString(DatabaseHandler.COLUMN_NOME.toInt())
        tvTelefoneElementoLista.text = cursor.getString(DatabaseHandler.COLUMN_TELEFONE.toInt())

        btEditartElementoLista.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            cursor.moveToPosition(posicao)

            intent.putExtra("cod", cursor.getInt(DatabaseHandler.COLUMN_ID.toInt()))
            intent.putExtra("nome", cursor.getString(DatabaseHandler.COLUMN_NOME.toInt()))
            intent.putExtra("telefone", cursor.getString(DatabaseHandler.COLUMN_TELEFONE.toInt()))

            context.startActivity(intent)
        }

        return v
    }

}