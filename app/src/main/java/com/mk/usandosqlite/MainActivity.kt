package com.mk.usandosqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mk.usandosqlite.database.DatabaseHandler
import com.mk.usandosqlite.databinding.ActivityMainBinding
import com.mk.usandosqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var banco: DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler.getInstance(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun btIncluirOnClick(view: View) {

        val cadastro = Cadastro(
            0,
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )

        banco.inserir(cadastro)

        Toast.makeText(this, "Registro inserido com sucesso", Toast.LENGTH_SHORT).show()
    }
    fun btAlterarOnClick(view: View) {

        val cadastro = Cadastro(
            binding.etCod.text.toString().toInt(),
            binding.etNome.text.toString(),
            binding.etTelefone.text.toString()
        )

        banco.alterar(cadastro)

        Toast.makeText(this, "Alteração realizada com sucesso", Toast.LENGTH_SHORT).show()
    }
    fun btExcluirOnClick(view: View) {
        banco.excluir(binding.etCod.text.toString().toInt())

        Toast.makeText(this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT).show()
    }
    fun btPesquisarOnClick(view: View) {

        val registro = banco.pesquisar(binding.etCod.text.toString().toInt())

        if (registro != null){
            binding.etNome.setText(registro.nome)
            binding.etTelefone.setText(registro.telefone)

        }else{
            binding.etNome.setText("")
            binding.etTelefone.setText("")
            Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_SHORT).show()
        }
    }
    fun btListarOnClick(view: View) {
        val registros = banco.listar()

        val saida = StringBuilder()

        while(registros.moveToNext()) {
            val nome = registros.getString(1)
            val telefone = registros.getString(2)

            saida.append("Nome: $nome - Telefone: $telefone\n\n")

        }

        Toast.makeText(this, saida.toString(), Toast.LENGTH_SHORT).show()
    }

}