package it.paolomazza.newsapp

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.google.gson.Gson
import it.paolomazza.newsapp.data.API
import it.paolomazza.newsapp.utils.FileUtils
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(MockitoJUnitRunner::class)
class RetrofitServiceTest {


    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: API
    lateinit var gson: Gson


    @Before
    fun setup() {
        gson = Gson()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(API::class.java)
    }


    @Test
    fun getNewsList() {
        runBlocking {
            val mockResponse = MockResponse()
            val file = getFile("news.json")
            mockWebServer.enqueue(mockResponse.setBody(file))
            val response = apiService.getNews(1,1)
            val request = mockWebServer.takeRequest()
            assertEquals("/news?offset=1&limit=1",request.path)
            assertEquals(true, response.message?.result?.size == 20)
        }
    }

    private fun getFile(filename:String) = ClassLoader.getSystemResource(filename).readText()


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}