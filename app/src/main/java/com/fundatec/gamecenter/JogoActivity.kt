package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.JogosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_jogo.*
import kotlinx.android.synthetic.main.content_jogo.*

class JogoActivity : AppCompatActivity() {

    private var idJogo: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)
        setSupportActionBar(toolbar)

        idJogo = intent.getStringExtra("idJogo")
        queue = Volley.newRequestQueue(baseContext)
        readJogo()

        darNotaJogo.setOnClickListener {
            notaJogo.visibility = View.VISIBLE
            salvarNotaJogo.visibility = View.VISIBLE

            salvarNotaJogo.setOnClickListener {
                salvarNota()
            }
        }
    }

    private fun readJogo() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/jogo/$idJogo"

        val request = GsonJsonClassRequest(
            url,
            JogosData::class.java,
            Response.Listener { jogo ->
                Picasso.get().load(jogo.foto).placeholder(R.drawable.no_img).fit().centerCrop().into(jogoFoto)
                jogoTitulo.text = jogo.titulo
                jogoPlataforma.text = "Plataforma: " + jogo.plataforma
                jogoNotaMidia.text = "Nota da Mídia: " +jogo.notaMidia
                jogoNotaUsuarios.text = "Nota dos Usuários: " +jogo.notaUsuarios
            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue?.add(request)
    }

    private fun salvarNota() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/jogo/$idJogo/dar-nota"
        var nota = notaJogo.text.toString()
        var jogo = JogosData(nota.toDouble())
        var edit = Gson().toJson(jogo)

        var request = GsonJsonRequest(Request.Method.PUT, url, JogosData::class.java, edit, Response.Listener { response ->
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(baseContext, "Nota registrada, obrigado por avaliar o jogo.", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

}
