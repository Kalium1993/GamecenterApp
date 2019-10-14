package com.fundatec.gamecenter

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fundatec.gamecenter.adapter.SectionsPagerAdapterVendedor
import com.fundatec.gamecenter.ui.main.SectionsPagerAdapter

class Vendedor2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor2)

        val sectionsPagerAdapter = SectionsPagerAdapterVendedor(this, supportFragmentManager, intent.getStringExtra("nickVendedor"))
        val viewPager: ViewPager = findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}