package com.example.cashi.util


enum class Currency(val code: String, val symbol: String) {
    USD("USD", "$"),
    EUR("EUR", "€");

    companion object {
        fun fromCode(code: String): Currency? {
            return entries.find { it.code == code }
        }

        fun getAllCurrencies(): List<Currency> = entries
    }
}
