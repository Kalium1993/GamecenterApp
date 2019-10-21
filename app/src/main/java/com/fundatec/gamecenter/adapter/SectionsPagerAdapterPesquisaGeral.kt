package com.fundatec.gamecenter.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fundatec.gamecenter.fragment.PesquisaComunidadesFragment
import com.fundatec.gamecenter.fragment.PesquisaProdutosFragment
import com.fundatec.gamecenter.fragment.PesquisaUsuariosFragment
import com.fundatec.gamecenter.fragment.PesquisaVendedoresFragment

private val TAB_TITLES = arrayOf(
    "Usuarios",
    "Vendedores",
    "Produtos",
    "Comunidades"
)

class SectionsPagerAdapterPesquisaGeral(private val context: Context, fm: FragmentManager, val pesquisa: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PesquisaUsuariosFragment.newInstance(pesquisa)
            1 -> fragment = PesquisaVendedoresFragment.newInstance(pesquisa)
            2 -> fragment = PesquisaProdutosFragment.newInstance(pesquisa)
            3 -> fragment = PesquisaComunidadesFragment.newInstance(pesquisa)
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 4
    }
}