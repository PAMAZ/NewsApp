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
            val file = getFile("news_list.json")
            mockWebServer.enqueue(mockResponse.setBody(file))
            val response = apiService.getNews(1,1)
            val request = mockWebServer.takeRequest()
            assertEquals("/news?offset=1&limit=1",request.path)
            assertEquals(true, response.message?.result?.size == 20)
        }
    }

    @Test
    fun getNewsDetail() {
        runBlocking {
            val mockResponse = MockResponse()
            val file = getFile("news_detail.json")
            mockWebServer.enqueue(mockResponse.setBody(file))
            val response = apiService.getNewsDetail(0)
            val request = mockWebServer.takeRequest()
            assertEquals("/news/0",request.path)
            assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit",response.message.title)
            assertEquals(" Curabitur finibus est et nibh suscipit, ac venenatis odio elementum",response.message.subtitle)
            assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur finibus est et nibh suscipit, ac venenatis odio elementum. Pellentesque molestie, nisi eget blandit malesuada, neque diam vestibulum mi, eget pharetra nunc dolor id odio. Quisque eleifend lacus ante, et pellentesque tellus hendrerit sit amet. Curabitur in libero quam. Nulla lorem massa, malesuada eleifend blandit ac, lacinia dignissim turpis. In lacus nisi, suscipit nec tristique ut, euismod malesuada quam. Morbi et risus ante. Proin luctus vel leo consectetur sagittis. Donec aliquet, ipsum in tincidunt accumsan, purus magna bibendum felis, varius euismod leo nulla sit amet arcu. Etiam feugiat elit lectus, eu euismod est semper id. Morbi nibh tortor, auctor dignissim nisl ut, tincidunt eleifend purus. Fusce in ante diam. Proin odio est, consequat sit amet arcu et, consequat pharetra libero. Morbi ac elit felis.",response.message.body)
            assertEquals(1624443499409, response.message.timestamp)
        }
    }

    private fun getFile(filename:String) = ClassLoader.getSystemResource(filename).readText()


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}