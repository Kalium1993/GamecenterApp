package com.fundatec.gamecenter.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fundatec.gamecenter.fragment.ComunidadeFragment
import com.fundatec.gamecenter.fragment.TopicosFragment

private val TAB_TITLES = arrayOf(
    "Comunidade",
    "Topicos"
)

class SectionsPagerAdapterComunidade(private val context: Context, fm: FragmentManager, val idComunidade: String, val pesquisa: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ComunidadeFragment.newInstance(idComunidade)
            1 -> fragment = TopicosFragment.newInstance(idComunidade, pesquisa)
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