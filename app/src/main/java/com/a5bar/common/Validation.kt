package com.a5bar.common

import android.content.Context
import android.widget.Toast

class Validation {

    fun checkCountryCode(context: Context, countryCode: String): String {

        if (countryCode.isNullOrEmpty()) {

            Toast.makeText(context, "Please enter a country code", Toast.LENGTH_SHORT).show()
        }

        return countryCode
    }


    fun checkSearch(context: Context, search: String): String {

        if (search.isNullOrEmpty()) {

            Toast.makeText(context, "Please enter your search word", Toast.LENGTH_SHORT).show()
        }

        return search
    }
}