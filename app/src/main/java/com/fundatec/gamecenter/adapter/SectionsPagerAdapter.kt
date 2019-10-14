package com.fundatec.gamecenter.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fundatec.gamecenter.fragment.VendedorFragment
import com.fundatec.gamecenter.fragment.VendedorProdutosFragment

private val TAB_TITLES = arrayOf(
    "Vendedor",
    "Produtos"
)

class SectionsPagerAdapterVendedor(private val context: Context, fm: FragmentManager, val nickVendedor: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = VendedorFragment.newInstance(nickVendedor)
            1 -> fragment = VendedorProdutosFragment.newInstance(nickVendedor)
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 2
    }
}