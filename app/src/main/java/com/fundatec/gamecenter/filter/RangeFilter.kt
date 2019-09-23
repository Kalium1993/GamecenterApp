package com.fundatec.gamecenter.filter

import android.text.InputFilter
import android.text.Spanned

class RangeFilter (min:Double, max:Double): InputFilter {
    private var min:Double = 0.0
    private var max:Double = 0.0

    init{
        this.min = min
        this.max = max
    }

    override fun filter(source:CharSequence, start:Int, end:Int, dest: Spanned, dstart:Int, dend:Int): CharSequence? {
        try
        {
            val input = (dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length)).toDouble()
            if (isInRange(min, max, input))
                return null
        }
        catch (nfe:NumberFormatException) {}
        return ""
    }

    private fun isInRange(a:Double, b:Double, c:Double):Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}