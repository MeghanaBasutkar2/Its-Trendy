package com.example.itstrending.Utils

import java.text.DecimalFormat

class NumberFormatter {
    companion object {
        fun formatDecimalNum(decimalNum: Double): String {
            var numPattern = DecimalFormat("###")
            return numPattern.format(decimalNum)
        }
    }
}