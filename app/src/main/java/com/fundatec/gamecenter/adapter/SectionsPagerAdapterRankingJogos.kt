package com.fundatec.gamecenter.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fundatec.gamecenter.fragment.RankingJogosMidiaFragment
import com.fundatec.gamecenter.fragment.RankingJogosUsuariosFragment

private val TAB_TITLES = arrayOf(
    "Nota Mídia",
    "Nota Usuários"
)

class SectionsPagerAdapterRankingJogos(private val context: Context, fm: FragmentManager, val s: String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RankingJogosMidiaFragment.newInstance(s)
            1 -> fragment = RankingJogosUsuariosFragment.newInstance(s)
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