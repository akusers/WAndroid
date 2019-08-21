package com.aku.app_kchttp_sample

import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiTest {

    @Test
    fun testApi(){
        runBlocking {
            api.getBanner()
        }
    }
}