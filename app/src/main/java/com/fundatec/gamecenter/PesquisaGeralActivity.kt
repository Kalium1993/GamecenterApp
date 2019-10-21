package com.fundatec.gamecenter

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fundatec.gamecenter.adapter.SectionsPagerAdapterPesquisaGeral
import com.fundatec.gamecenter.ui.main.SectionsPagerAdapter

class PesquisaGeralActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_geral)

        val sectionsPagerAdapter = SectionsPagerAdapterPesquisaGeral(this, supportFragmentManager, intent.getStringExtra("pesquisa"))

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}