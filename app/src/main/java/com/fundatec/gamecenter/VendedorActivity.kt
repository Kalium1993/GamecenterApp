package com.fundatec.gamecenter

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.fundatec.gamecenter.adapter.SectionsPagerAdapterVendedor

class VendedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)

        val sectionsPagerAdapter = SectionsPagerAdapterVendedor(this, supportFragmentManager, intent.getStringExtra("nickVendedor"))
        val viewPager: ViewPager = findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}