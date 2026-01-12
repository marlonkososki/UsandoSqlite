package com.mk.usandosqlite

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mk.usandosqlite.adapter.MeuAdapter
import com.mk.usandosqlite.database.DatabaseHandler
import com.mk.usandosqlite.databinding.ActivityListarBinding


class ListarActivity : AppCompatActivity() {


    private lateinit var binding: ActivityListarBinding

    private lateinit var banco: DatabaseHandler

    private lateinit var adapter: MeuAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler.getInstance(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvRegistros)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.svFiltro.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                initList(newText ?: "")
                return true
            }

        })



        }


    override fun onStart() {
        super.onStart()
        initList()
    }

    private fun initList(filtro: String = "") {

        val cursor: Cursor = banco.listar(filtro)

        adapter = MeuAdapter(this, cursor)

        binding.lvRegistros.adapter = adapter

    }

    fun fabIncluirOnClick(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}