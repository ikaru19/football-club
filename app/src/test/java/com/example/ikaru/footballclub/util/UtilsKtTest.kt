package com.example.ikaru.footballclub.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class UtilsKtTest {

    @Test
    fun strToDate() {
        val date = SimpleDateFormat("dd/MM/yyyy").parse("19/01/2018")
        assertEquals(date , strToDate("2018-01-19"))
    }

    @Test
    fun changeFormatDate() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("11/18/2018")
        assertEquals("Sun, 18 Nov 2018",changeFormatDate(date))
    }
}