package com.mk.usandosqlite

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        if (intent.getIntExtra("cod", 0) != 0) {
            binding.etCod.setText(intent.getIntExtra("cod", 0).toString())
            binding.etNome.setText(intent.getStringExtra("nome"))
            binding.etTelefone.setText(intent.getStringExtra("telefone"))

        } else {
            binding.btExcluir.visibility = View.GONE
            binding.btPesquisar.visibility = View.GONE
        }

    }

    fun btSalvarOnClick(view: View) {

        var msg = ""

        if (binding.etCod.text.toString().isEmpty()){

            val cadastro = Cadastro(
                0,
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )

            banco.inserir(cadastro)

            msg = "Registro inserido com sucesso"

        } else {

            val cadastro = Cadastro(
                binding.etCod.text.toString().toInt(),
                binding.etNome.text.toString(),
                binding.etTelefone.text.toString()
            )

            banco.alterar(cadastro)

            msg = "Registro alterado com sucesso"

        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        finish()
    }
    fun btExcluirOnClick(view: View) {
        banco.excluir(binding.etCod.text.toString().toInt())

        Toast.makeText(this, "Exclusão realizada com sucesso", Toast.LENGTH_SHORT).show()

        finish()
    }
    fun btPesquisarOnClick(view: View) {


        val etCodigoPesquisar = EditText(this)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informe o código do registro a ser pesquisado")
        builder.setView(etCodigoPesquisar)
        builder.setCancelable(false)
        builder.setNegativeButton("Fechar", null)
        builder.setPositiveButton(
            "Pesquisar",
            { dialog, which ->

                val registro = banco.pesquisar(etCodigoPesquisar.text.toString().toInt())

                if (registro != null){
                    binding.etCod.setText(registro._id.toString())
                    binding.etNome.setText(registro.nome)
                    binding.etTelefone.setText(registro.telefone)

                }else{
                    binding.etCod.setText("")
                    binding.etNome.setText("")
                    binding.etTelefone.setText("")
                    Toast.makeText(this, "Registro não encontrado", Toast.LENGTH_SHORT).show()
                }
            })

        builder.show()

    }

    fun btListarOnClick(view: View) {

        val intent = Intent(this, ListarActivity::class.java)
        startActivity(intent)
    }
}