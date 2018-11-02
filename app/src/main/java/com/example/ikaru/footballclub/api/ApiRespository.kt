package com.example.ikaru.footballclub.api

import java.net.URL

class ApiRespository {

    fun doRequest(url : String) = URL(url).readText()

}