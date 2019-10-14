package com.fundatec.gamecenter

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_comunidade.*

class ComunidadeActivity : AppCompatActivity() {
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidade)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(baseContext)


    }

}
